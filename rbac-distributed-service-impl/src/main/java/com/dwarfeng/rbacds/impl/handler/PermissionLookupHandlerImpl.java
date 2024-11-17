package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.cache.RolePermissionCache;
import com.dwarfeng.rbacds.stack.cache.UserPermissionCache;
import com.dwarfeng.rbacds.stack.handler.PermissionLookupHandler;
import com.dwarfeng.rbacds.stack.handler.PexpHandler;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class PermissionLookupHandlerImpl implements PermissionLookupHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionLookupHandlerImpl.class);

    private final RoleMaintainService roleMaintainService;
    private final PexpMaintainService pexpMaintainService;
    private final PermissionMaintainService permissionMaintainService;

    private final PexpHandler pexpHandler;

    private final UserPermissionCache userPermissionCache;
    private final RolePermissionCache rolePermissionCache;

    private final HandlerValidator handlerValidator;

    @Value("${cache.timeout.list.user_has_permission}")
    private long userHasPermissionTimeout;
    @Value("${cache.timeout.list.role_has_permission}")
    private long roleHasPermissionTimeout;

    public PermissionLookupHandlerImpl(
            RoleMaintainService roleMaintainService,
            PexpMaintainService pexpMaintainService,
            PermissionMaintainService permissionMaintainService,
            PexpHandler pexpHandler,
            UserPermissionCache userPermissionCache,
            RolePermissionCache rolePermissionCache,
            HandlerValidator handlerValidator
    ) {
        this.roleMaintainService = roleMaintainService;
        this.pexpMaintainService = pexpMaintainService;
        this.permissionMaintainService = permissionMaintainService;
        this.pexpHandler = pexpHandler;
        this.userPermissionCache = userPermissionCache;
        this.rolePermissionCache = rolePermissionCache;
        this.handlerValidator = handlerValidator;
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<Permission> lookupForUser(StringIdKey userKey) throws HandlerException {
        try {
            if (userPermissionCache.exists(userKey)) {
                return userPermissionCache.get(userKey);
            }
            List<Permission> permissions = inspectForUser(userKey);
            userPermissionCache.set(userKey, permissions, userHasPermissionTimeout);
            return permissions;
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private List<Permission> inspectForUser(StringIdKey userKey) throws Exception {
        // 确认用户存在。
        handlerValidator.makeSureUserExists(userKey);

        // 获取用户有效的权限表达式。
        List<Role> roles = roleMaintainService.lookupAsList(
                RoleMaintainService.ENABLED_ROLE_FOR_USER, new Object[]{userKey}
        );
        Map<Role, List<Pexp>> pexpsMap = new HashMap<>();
        for (Role role : roles) {
            List<Pexp> pexps = pexpMaintainService.lookupAsList(
                    PexpMaintainService.PEXP_FOR_ROLE, new Object[]{role.getKey()}
            );
            pexpsMap.put(role, pexps);
        }

        // 查询所有的权限。
        List<Permission> permissions = permissionMaintainService.lookup().getData();

        // 通过所有的权限表达式和所有的权限解析用户拥有的所有权限。
        permissions = analysePexpPermissions(pexpsMap, permissions);

        // Debug 输出用户获得的所有权限表达式。
        LOGGER.debug("查询获得用户 " + userKey.toString() + " 的权限:");
        permissions.forEach(permission -> LOGGER.debug("\t" + permission));

        // 返回用户拥有的所有权限。
        return permissions;
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<Permission> lookupForRole(StringIdKey roleKey) throws HandlerException {
        try {
            if (rolePermissionCache.exists(roleKey)) {
                return rolePermissionCache.get(roleKey);
            }
            List<Permission> permissions = inspectForRole(roleKey);
            rolePermissionCache.set(roleKey, permissions, roleHasPermissionTimeout);
            return permissions;
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private List<Permission> inspectForRole(StringIdKey roleKey) throws Exception {
        // 确认角色存在。
        handlerValidator.makeSureRoleExists(roleKey);

        // 获取角色有效的权限表达式。
        Map<Role, List<Pexp>> pexpsMap = new HashMap<>();
        Role role = roleMaintainService.get(roleKey);
        List<Pexp> pexps = pexpMaintainService.lookupAsList(
                PexpMaintainService.PEXP_FOR_ROLE, new Object[]{role.getKey()}
        );
        pexpsMap.put(role, pexps);

        // 查询所有的权限。
        List<Permission> permissions = permissionMaintainService.lookup().getData();

        // 通过所有的权限表达式和所有的权限解析角色拥有的所有权限。
        permissions = analysePexpPermissions(pexpsMap, permissions);

        // Debug 输出角色获得的所有权限表达式。
        LOGGER.debug("查询获得角色 " + roleKey.toString() + " 的权限:");
        permissions.forEach(permission -> LOGGER.debug("\t" + permission));

        // 返回角色拥有的所有权限。
        return permissions;
    }

    private List<Permission> analysePexpPermissions(Map<Role, List<Pexp>> pexpsMap, List<Permission> allPermissions)
            throws HandlerException {
        try {
            // 定义变量。
            final Map<Role, List<Permission>> acceptedPermissionsMap = new HashMap<>();
            final Map<Role, List<Permission>> rejectedPermissionsMap = new HashMap<>();
            final Set<Permission> globalRejectedPermissions = new HashSet<>();

            // 遍历角色，将角色的权限信息分类写入变量中。
            for (Map.Entry<Role, List<Pexp>> entry : pexpsMap.entrySet()) {
                Role role = entry.getKey();
                List<Pexp> pexps = entry.getValue();
                List<Permission> acceptedPermissions = new ArrayList<>();
                List<Permission> rejectPermissions = new ArrayList<>();
                for (Pexp pexp : pexps) {
                    Map<PexpHandler.PermissionReception, List<Permission>> testMap =
                            pexpHandler.testAll(pexp, allPermissions);
                    acceptedPermissions.addAll(testMap.get(PexpHandler.PermissionReception.ACCEPT));
                    rejectPermissions.addAll(testMap.get(PexpHandler.PermissionReception.REJECT));
                    globalRejectedPermissions.addAll(testMap.get(PexpHandler.PermissionReception.GLOBAL_REJECT));
                }
                acceptedPermissionsMap.put(role, acceptedPermissions);
                rejectedPermissionsMap.put(role, rejectPermissions);
            }

            // 处理角色与权限的对应信息，并最终形成用户的权限。
            List<Permission> permissions = new ArrayList<>();
            for (Role role : pexpsMap.keySet()) {
                List<Permission> rolePermissions = acceptedPermissionsMap.get(role);
                // 针对指定角色不接受任何权限的特殊情况进行优化。
                if (rolePermissions.isEmpty()) {
                    continue;
                }
                rolePermissions.removeAll(rejectedPermissionsMap.get(role));
                permissions.addAll(rolePermissions);
            }
            permissions.removeAll(globalRejectedPermissions);
            permissions.sort(PermissionComparator.INSTANCE);
            return permissions;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    private static final class PermissionComparator implements Comparator<Permission> {

        public static final PermissionComparator INSTANCE = new PermissionComparator();

        @Override
        public int compare(Permission o1, Permission o2) {
            return o1.getKey().getStringId().compareTo(o2.getKey().getStringId());
        }

        @Override
        public String toString() {
            return "PermissionComparator{}";
        }
    }
}

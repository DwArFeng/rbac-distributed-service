package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.cache.RolePermissionCache;
import com.dwarfeng.rbacds.stack.cache.UserPermissionCache;
import com.dwarfeng.rbacds.stack.handler.PexpHandler;
import com.dwarfeng.rbacds.stack.handler.PexpHandler.PermissionReception;
import com.dwarfeng.rbacds.stack.service.*;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PermissionLookupServiceImpl implements PermissionLookupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionLookupServiceImpl.class);

    private final PexpHandler pexpHandler;
    private final UserMaintainService userMaintainService;
    private final RoleMaintainService roleMaintainService;
    private final PexpMaintainService pexpMaintainService;
    private final PermissionMaintainService permissionMaintainService;
    private final UserPermissionCache userPermissionCache;
    private final RolePermissionCache rolePermissionCache;
    private final ServiceExceptionMapper sem;

    @Value("${cache.timeout.list.user_has_permission}")
    private long userHasPermissionTimeout;
    @Value("${cache.timeout.list.role_has_permission}")
    private long roleHasPermissionTimeout;

    public PermissionLookupServiceImpl(
            PexpHandler pexpHandler,
            UserMaintainService userMaintainService,
            RoleMaintainService roleMaintainService,
            PexpMaintainService pexpMaintainService,
            PermissionMaintainService permissionMaintainService,
            UserPermissionCache userPermissionCache,
            RolePermissionCache rolePermissionCache,
            ServiceExceptionMapper sem
    ) {
        this.pexpHandler = pexpHandler;
        this.userMaintainService = userMaintainService;
        this.roleMaintainService = roleMaintainService;
        this.pexpMaintainService = pexpMaintainService;
        this.permissionMaintainService = permissionMaintainService;
        this.userPermissionCache = userPermissionCache;
        this.rolePermissionCache = rolePermissionCache;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<Permission> lookupForUser(StringIdKey userKey) throws ServiceException {
        try {
            if (userPermissionCache.exists(userKey)) {
                return userPermissionCache.get(userKey);
            }
            List<Permission> permissions = inspectForUser(userKey);
            userPermissionCache.set(userKey, permissions, userHasPermissionTimeout);
            return permissions;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询用户对应的权限时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private List<Permission> inspectForUser(StringIdKey userKey) throws Exception {
        // 判断用户是否存在。
        if (!userMaintainService.exists(userKey)) {
            LOGGER.warn("指定的用户 " + userKey.toString() + " 不存在, 将抛出异常...");
            throw new ServiceException(ServiceExceptionCodes.USER_NOT_EXISTS);
        }
        // 获取用户有效的权限表达式。
        List<Role> roles = roleMaintainService.lookup(RoleMaintainService.ENABLED_ROLE_FOR_USER, new Object[]{userKey}).getData();
        Map<Role, List<Pexp>> pexpsMap = new HashMap<>();
        for (Role role : roles) {
            List<Pexp> pexps = pexpMaintainService.lookup(PexpMaintainService.PEXP_FOR_ROLE, new Object[]{role.getKey()}).getData();
            pexpsMap.put(role, pexps);
        }
        // 查询所有的权限。
        List<Permission> permissions = permissionMaintainService.lookup().getData();
        // 通过所有的权限表达式和所有的权限解析用户拥有的所有权限。
        permissions = analysePexpPermissions(pexpsMap, permissions);
        // Debug输出用户获得的所有权限表达式。
        LOGGER.debug("查询获得用户 " + userKey.toString() + " 的权限:");
        permissions.forEach(permission -> LOGGER.debug("\t" + permission));
        return permissions;
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<Permission> lookupForRole(StringIdKey roleKey) throws ServiceException {
        try {
            if (rolePermissionCache.exists(roleKey)) {
                return rolePermissionCache.get(roleKey);
            }
            List<Permission> permissions = inspectForRole(roleKey);
            rolePermissionCache.set(roleKey, permissions, roleHasPermissionTimeout);
            return permissions;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询用户对应的权限时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private List<Permission> inspectForRole(StringIdKey roleKey) throws Exception {
        // 判断角色是否存在。
        if (!roleMaintainService.exists(roleKey)) {
            LOGGER.warn("指定的角色 " + roleKey.toString() + " 不存在, 将抛出异常...");
            throw new ServiceException(ServiceExceptionCodes.ROLE_NOT_EXISTS);
        }
        // 获取角色有效的权限表达式。
        Map<Role, List<Pexp>> pexpsMap = new HashMap<>();
        Role role = roleMaintainService.get(roleKey);
        List<Pexp> pexps = pexpMaintainService.lookup(PexpMaintainService.PEXP_FOR_ROLE, new Object[]{role.getKey()}).getData();
        pexpsMap.put(role, pexps);
        // 查询所有的权限。
        List<Permission> permissions = permissionMaintainService.lookup().getData();
        // 通过所有的权限表达式和所有的权限解析角色拥有的所有权限。
        permissions = analysePexpPermissions(pexpsMap, permissions);
        // Debug输出角色获得的所有权限表达式。
        LOGGER.debug("查询获得角色 " + roleKey.toString() + " 的权限:");
        permissions.forEach(permission -> LOGGER.debug("\t" + permission));
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
                    Map<PermissionReception, List<Permission>> testMap = pexpHandler.testAll(pexp, allPermissions);
                    acceptedPermissions.addAll(testMap.get(PermissionReception.ACCEPT));
                    rejectPermissions.addAll(testMap.get(PermissionReception.REJECT));
                    globalRejectedPermissions.addAll(testMap.get(PermissionReception.GLOBAL_REJECT));
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

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Deprecated
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<Permission> lookupPermissions(StringIdKey userKey) throws ServiceException {
        return lookupPermissionsForUser(userKey);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Deprecated
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<Permission> lookupPermissionsForUser(StringIdKey userKey) throws ServiceException {
        return lookupForUser(userKey);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Deprecated
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<Permission> lookupPermissionsForRole(StringIdKey roleKey) throws ServiceException {
        return lookupForRole(roleKey);
    }

    private static final class PermissionComparator implements Comparator<Permission> {

        public static PermissionComparator INSTANCE = new PermissionComparator();

        @Override
        public int compare(Permission o1, Permission o2) {
            return o1.getKey().getStringId().compareTo(o2.getKey().getStringId());
        }
    }
}

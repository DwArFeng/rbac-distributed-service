package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.cache.PermissionUserCache;
import com.dwarfeng.rbacds.stack.handler.PexpHandler;
import com.dwarfeng.rbacds.stack.handler.UserLookupHandler;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
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
import java.util.stream.Collectors;

@Component
public class UserLookupHandlerImpl implements UserLookupHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLookupHandlerImpl.class);

    private final UserMaintainService userMaintainService;
    private final RoleMaintainService roleMaintainService;
    private final PexpMaintainService pexpMaintainService;
    private final PermissionMaintainService permissionMaintainService;

    private final PexpHandler pexpHandler;

    private final PermissionUserCache permissionUserCache;

    private final HandlerValidator handlerValidator;

    @Value("${cache.timeout.list.permission_has_user}")
    private long permissionHasUserTimeout;

    public UserLookupHandlerImpl(
            UserMaintainService userMaintainService,
            RoleMaintainService roleMaintainService,
            PexpMaintainService pexpMaintainService,
            PermissionMaintainService permissionMaintainService,
            PexpHandler pexpHandler,
            PermissionUserCache permissionUserCache,
            HandlerValidator handlerValidator
    ) {
        this.userMaintainService = userMaintainService;
        this.roleMaintainService = roleMaintainService;
        this.pexpMaintainService = pexpMaintainService;
        this.permissionMaintainService = permissionMaintainService;
        this.pexpHandler = pexpHandler;
        this.permissionUserCache = permissionUserCache;
        this.handlerValidator = handlerValidator;
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<User> lookupForPermission(StringIdKey permissionKey) throws HandlerException {
        try {
            if (permissionUserCache.exists(permissionKey)) {
                return permissionUserCache.get(permissionKey);
            }
            List<User> users = inspectForPermission(permissionKey);
            permissionUserCache.set(permissionKey, users, permissionHasUserTimeout);
            return users;
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private List<User> inspectForPermission(StringIdKey permissionKey) throws Exception {
        // 确认权限存在。
        handlerValidator.makeSurePermissionExists(permissionKey);

        // 获取所有有效角色的权限表达式。
        List<Role> roles = roleMaintainService.lookup(RoleMaintainService.ENABLED, new Object[0]).getData();
        Map<Role, List<Pexp>> pexpsMap = new HashMap<>();
        for (Role role : roles) {
            List<Pexp> pexps = pexpMaintainService.lookup(PexpMaintainService.PEXP_FOR_ROLE, new Object[]{role.getKey()}).getData();
            pexpsMap.put(role, pexps);
        }

        // 查询权限。
        Permission permission = permissionMaintainService.get(permissionKey);

        // 通过所有的权限表达式解析权限的角色信息。
        List<StringIdKey> includeRoleKeys = new ArrayList<>();
        List<StringIdKey> excludeRoleKeys = new ArrayList<>();
        analysePermissionRoles(pexpsMap, permission, includeRoleKeys, excludeRoleKeys);

        // 根据角色信息查询所有符合条件的用户。
        List<User> users;
        if (!includeRoleKeys.isEmpty() && !excludeRoleKeys.isEmpty()) {
            List<User> tempUsers = userMaintainService.lookupAsList(
                    UserMaintainService.CHILD_FOR_ROLE_SET, new Object[]{includeRoleKeys}
            );
            Map<StringIdKey, User> includeUserMap = new LinkedHashMap<>();
            for (User tempUser : tempUsers) {
                includeUserMap.put(tempUser.getKey(), tempUser);
            }
            List<StringIdKey> excludeUserKeys = userMaintainService.lookupAsList(
                    UserMaintainService.CHILD_FOR_ROLE_SET, new Object[]{excludeRoleKeys}
            ).stream().map(User::getKey).collect(Collectors.toList());
            excludeUserKeys.forEach(includeUserMap.keySet()::remove);
            users = new ArrayList<>(includeUserMap.values());
        } else if (!includeRoleKeys.isEmpty()) {
            users = new ArrayList<>(userMaintainService.lookup(UserMaintainService.CHILD_FOR_ROLE_SET,
                    new Object[]{includeRoleKeys}).getData());
        } else {
            users = Collections.emptyList();
        }

        // Debug输出用户获得的所有权限表达式。
        LOGGER.debug("查询获得权限 " + permissionKey.toString() + " 对应的用户:");
        users.forEach(user -> LOGGER.debug("\t" + user));

        // 返回权限对应的用户。
        return users;
    }

    private void analysePermissionRoles(
            Map<Role, List<Pexp>> pexpsMap, Permission permission,
            List<StringIdKey> includeRoleKeys, List<StringIdKey> excludeRoleKeys) throws HandlerException {
        try {
            for (Map.Entry<Role, List<Pexp>> entry : pexpsMap.entrySet()) {
                Role role = entry.getKey();
                List<Pexp> pexps = entry.getValue();

                boolean acceptedPermissionFlag = false;
                boolean rejectedPermissionFlag = false;
                boolean globalRejectedPermissionFlag = false;

                for (Pexp pexp : pexps) {
                    PexpHandler.PermissionReception permissionReception = pexpHandler.test(pexp, permission);
                    if (Objects.equals(permissionReception, PexpHandler.PermissionReception.ACCEPT)) {
                        acceptedPermissionFlag = true;
                    } else if (Objects.equals(permissionReception, PexpHandler.PermissionReception.REJECT)) {
                        rejectedPermissionFlag = true;
                    } else if (Objects.equals(permissionReception, PexpHandler.PermissionReception.GLOBAL_REJECT)) {
                        globalRejectedPermissionFlag = true;
                        // 一旦发现了该权限全局排除，则不用考虑其它的情形了，该角色必在排除角色之中。
                        break;
                    }
                }

                if (globalRejectedPermissionFlag) {
                    excludeRoleKeys.add(role.getKey());
                } else if (acceptedPermissionFlag && !rejectedPermissionFlag) {
                    includeRoleKeys.add(role.getKey());
                }
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}

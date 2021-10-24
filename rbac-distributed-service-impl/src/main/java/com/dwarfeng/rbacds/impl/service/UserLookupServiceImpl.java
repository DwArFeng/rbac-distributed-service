package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.cache.PermissionUserCache;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserLookupServiceImpl implements UserLookupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLookupServiceImpl.class);

    private final PexpHandler pexpHandler;
    private final UserMaintainService userMaintainService;
    private final RoleMaintainService roleMaintainService;
    private final PexpMaintainService pexpMaintainService;
    private final PermissionMaintainService permissionMaintainService;
    private final PermissionUserCache permissionUserCache;
    private final ServiceExceptionMapper sem;

    @Value("${cache.timeout.list.permission_has_user}")
    private long permissionHasUserTimeout;

    public UserLookupServiceImpl(
            PexpHandler pexpHandler,
            UserMaintainService userMaintainService,
            RoleMaintainService roleMaintainService,
            PexpMaintainService pexpMaintainService,
            PermissionMaintainService permissionMaintainService,
            PermissionUserCache permissionUserCache,
            ServiceExceptionMapper sem
    ) {
        this.pexpHandler = pexpHandler;
        this.userMaintainService = userMaintainService;
        this.roleMaintainService = roleMaintainService;
        this.pexpMaintainService = pexpMaintainService;
        this.permissionMaintainService = permissionMaintainService;
        this.permissionUserCache = permissionUserCache;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<User> lookupForPermission(StringIdKey permissionKey) throws ServiceException {
        try {
            if (permissionUserCache.exists(permissionKey)) {
                return permissionUserCache.get(permissionKey);
            }
            List<User> users = inspectForPermission(permissionKey);
            permissionUserCache.set(permissionKey, users, permissionHasUserTimeout);
            return users;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询用户对应的权限时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private List<User> inspectForPermission(StringIdKey permissionKey) throws Exception {
        // 判断用户是否存在。
        if (!permissionMaintainService.exists(permissionKey)) {
            LOGGER.warn("指定的权限 " + permissionKey.toString() + " 不存在, 将抛出异常...");
            throw new ServiceException(ServiceExceptionCodes.PERMISSION_NOT_EXISTS);
        }
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
            List<User> tempUsers = userMaintainService.lookup(UserMaintainService.CHILD_FOR_ROLE,
                    new Object[]{includeRoleKeys}).getData();
            Map<StringIdKey, User> includeUserMap = new LinkedHashMap<>();
            for (User tempUser : tempUsers) {
                includeUserMap.put(tempUser.getKey(), tempUser);
            }
            List<StringIdKey> excludeUserKeys = userMaintainService.lookup(UserMaintainService.CHILD_FOR_ROLE,
                            new Object[]{excludeRoleKeys}).getData().stream()
                    .map(User::getKey).collect(Collectors.toList());
            excludeUserKeys.forEach(includeUserMap.keySet()::remove);
            users = new ArrayList<>(includeUserMap.values());
        } else if (!includeRoleKeys.isEmpty()) {
            users = new ArrayList<>(userMaintainService.lookup(UserMaintainService.CHILD_FOR_ROLE,
                    new Object[]{includeRoleKeys}).getData());
        } else {
            users = Collections.emptyList();
        }
        // Debug输出用户获得的所有权限表达式。
        LOGGER.debug("查询获得权限 " + permissionKey.toString() + " 对应的用户:");
        users.forEach(user -> LOGGER.debug("\t" + user));
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
                    PermissionReception permissionReception = pexpHandler.test(pexp, permission);
                    if (Objects.equals(permissionReception, PermissionReception.ACCEPT)) {
                        acceptedPermissionFlag = true;
                    } else if (Objects.equals(permissionReception, PermissionReception.REJECT)) {
                        rejectedPermissionFlag = true;
                    } else if (Objects.equals(permissionReception, PermissionReception.GLOBAL_REJECT)) {
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

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Deprecated
    public List<User> lookupUsers(StringIdKey permissionKey) throws ServiceException {
        return lookupUsersForPermission(permissionKey);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Deprecated
    public List<User> lookupUsersForPermission(StringIdKey permissionKey) throws ServiceException {
        return lookupForPermission(permissionKey);
    }
}

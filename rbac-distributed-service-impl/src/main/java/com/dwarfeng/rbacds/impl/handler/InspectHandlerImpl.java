package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.dutil.basic.sdk.collection.CollectionUtil;
import com.dwarfeng.rbacds.stack.bean.dto.*;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.Scope;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.bean.key.ScopedRoleKey;
import com.dwarfeng.rbacds.stack.bean.key.ScopedUserKey;
import com.dwarfeng.rbacds.stack.handler.InspectHandler;
import com.dwarfeng.rbacds.stack.handler.PermissionUserAnalysisLocalCacheHandler;
import com.dwarfeng.rbacds.stack.handler.ScopedRolePermissionAnalysisLocalCacheHandler;
import com.dwarfeng.rbacds.stack.handler.ScopedUserPermissionAnalysisLocalCacheHandler;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.rbacds.stack.service.ScopeMaintainService;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.rbacds.stack.struct.PermissionUserAnalysis;
import com.dwarfeng.rbacds.stack.struct.ScopedRolePermissionAnalysis;
import com.dwarfeng.rbacds.stack.struct.ScopedUserPermissionAnalysis;
import com.dwarfeng.subgrade.basic.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 查看处理器实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class InspectHandlerImpl implements InspectHandler {

    private final PermissionMaintainService permissionMaintainService;
    private final ScopeMaintainService scopeMaintainService;
    private final RoleMaintainService roleMaintainService;
    private final UserMaintainService userMaintainService;

    private final PermissionUserAnalysisLocalCacheHandler permissionUserAnalysisLocalCacheHandler;
    private final ScopedRolePermissionAnalysisLocalCacheHandler scopedRolePermissionAnalysisLocalCacheHandler;
    private final ScopedUserPermissionAnalysisLocalCacheHandler scopedUserPermissionAnalysisLocalCacheHandler;

    public InspectHandlerImpl(
            PermissionMaintainService permissionMaintainService,
            ScopeMaintainService scopeMaintainService,
            RoleMaintainService roleMaintainService,
            UserMaintainService userMaintainService,
            PermissionUserAnalysisLocalCacheHandler permissionUserAnalysisLocalCacheHandler,
            ScopedRolePermissionAnalysisLocalCacheHandler scopedRolePermissionAnalysisLocalCacheHandler,
            ScopedUserPermissionAnalysisLocalCacheHandler scopedUserPermissionAnalysisLocalCacheHandler
    ) {
        this.permissionMaintainService = permissionMaintainService;
        this.scopeMaintainService = scopeMaintainService;
        this.roleMaintainService = roleMaintainService;
        this.userMaintainService = userMaintainService;
        this.permissionUserAnalysisLocalCacheHandler = permissionUserAnalysisLocalCacheHandler;
        this.scopedRolePermissionAnalysisLocalCacheHandler = scopedRolePermissionAnalysisLocalCacheHandler;
        this.scopedUserPermissionAnalysisLocalCacheHandler = scopedUserPermissionAnalysisLocalCacheHandler;
    }

    @Nullable
    @Override
    @Deprecated
    public PermissionUserInspectResult inspectPermissionUser(PermissionUserInspectInfo info) throws HandlerException {
        try {
            UserViewOfPermissionInspectResult result = inspectUserViewOfPermission0(
                    Objects.requireNonNull(permissionUserInspectInfoToUserViewOfPermissionInspectInfo(info))
            );

            return userViewOfPermissionInspectResultToPermissionUserInspectResult(result);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Nullable
    @Override
    public UserViewOfPermissionInspectResult inspectUserViewOfPermission(UserViewOfPermissionInspectInfo info)
            throws HandlerException {
        try {
            return inspectUserViewOfPermission0(info);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private UserViewOfPermissionInspectResult inspectUserViewOfPermission0(UserViewOfPermissionInspectInfo info)
            throws Exception {
        // 展开参数。
        PermissionKey permissionKey = info.getPermissionKey();
        StringIdKey userKeyMatched = info.getUserKeyMatched();
        List<StringIdKey> allOfUserKeysMatched = info.getAllOfUserKeysMatched();
        List<StringIdKey> anyOfUserKeysMatched = info.getAnyOfUserKeysMatched();
        boolean detailMode = info.isDetailMode();

        // Permission 查找。
        Permission permission;
        // 如果 permissionKey 为 null，说明没有提供权限信息，直接返回 null。
        if (Objects.isNull(permissionKey)) {
            return null;
        }
        permission = permissionMaintainService.getIfExists(permissionKey);
        // 如果 permission 为 null，说明没有找到对应的权限，直接返回 null。
        if (Objects.isNull(permission)) {
            return null;
        }

        // 从缓存中查询分析结果。
        PermissionUserAnalysis analysis = permissionUserAnalysisLocalCacheHandler.get(permission.getKey());
        // 如果分析结果为 null，说明没有缓存，直接返回 null。
        if (Objects.isNull(analysis)) {
            return null;
        }

        // 构造结果参数。
        List<User> matchedUsers = analysis.matchedUsers();
        boolean matchedFlag = false;
        if (Objects.nonNull(userKeyMatched)) {
            matchedFlag = analysis.matchedUserKeySet().contains(userKeyMatched);
        } else if (Objects.nonNull(allOfUserKeysMatched)) {
            matchedFlag = analysis.matchedUserKeySet().containsAll(allOfUserKeysMatched);
        } else if (Objects.nonNull(anyOfUserKeysMatched)) {
            for (StringIdKey anyOfUserKeyMatched : anyOfUserKeysMatched) {
                if (analysis.matchedUserKeySet().contains(anyOfUserKeyMatched)) {
                    matchedFlag = true;
                    break;
                }
            }
        }
        List<Role> acceptedRoles = null;
        List<Role> rejectedRoles = null;
        if (detailMode) {
            acceptedRoles = analysis.acceptedRoles();
            rejectedRoles = analysis.rejectedRoles();
        }

        // 返回结果。
        return new UserViewOfPermissionInspectResult(matchedUsers, matchedFlag, acceptedRoles, rejectedRoles);
    }

    @Nullable
    @Override
    @Deprecated
    public RolePermissionInspectResult inspectRolePermission(RolePermissionInspectInfo info) throws HandlerException {
        try {
            PermissionViewOfRoleInspectResult result = inspectPermissionViewOfRole0(
                    Objects.requireNonNull(rolePermissionInspectInfoToPermissionViewOfRoleInspectInfo(info))
            );
            return permissionViewOfRoleInspectResultToRolePermissionInspectResult(result);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Nullable
    @Override
    public PermissionViewOfRoleInspectResult inspectPermissionViewOfRole(PermissionViewOfRoleInspectInfo info)
            throws HandlerException {
        try {
            return inspectPermissionViewOfRole0(info);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    // 为了保证代码的可读性，此处代码不做简化。
    @SuppressWarnings({"DuplicatedCode", "OptionalOfNullableMisuse"})
    private PermissionViewOfRoleInspectResult inspectPermissionViewOfRole0(PermissionViewOfRoleInspectInfo info)
            throws Exception {
        // 展开参数。
        ScopedRoleKey scopedRoleKey = info.getScopedRoleKey();
        PermissionKey permissionKeyMatched = info.getPermissionKeyMatched();
        List<PermissionKey> allOfPermissionKeysMatched = info.getAllOfPermissionKeysMatched();
        List<PermissionKey> anyOfPermissionKeysMatched = info.getAnyOfPermissionKeysMatched();
        boolean detailMode = info.isDetailMode();

        // Scope 查找。
        StringIdKey scopeKey = Optional.ofNullable(scopedRoleKey).map(ScopedRoleKey::getScopeStringId)
                .map(StringIdKey::new).orElse(null);
        Scope scope;
        // 如果 scopeKey 为 null，说明没有提供作用域信息，直接返回 null。
        if (Objects.isNull(scopeKey)) {
            return null;
        }
        scope = scopeMaintainService.getIfExists(scopeKey);
        // 如果 scope 为 null，说明没有找到对应的作用域，直接返回 null。
        if (Objects.isNull(scope)) {
            return null;
        }

        // Role 查找。
        StringIdKey roleKey = Optional.ofNullable(scopedRoleKey).map(ScopedRoleKey::getRoleStringId)
                .map(StringIdKey::new).orElse(null);
        Role role;
        // 如果 roleKey 为 null，说明没有提供角色信息，直接返回 null。
        if (Objects.isNull(roleKey)) {
            return null;
        }
        role = roleMaintainService.getIfExists(roleKey);
        // 如果 role 为 null，说明没有找到对应的角色，直接返回 null。
        if (Objects.isNull(role)) {
            return null;
        }

        // 从缓存中查询分析结果。
        ScopedRolePermissionAnalysis analysis = scopedRolePermissionAnalysisLocalCacheHandler.get(
                new ScopedRoleKey(scope.getKey().getStringId(), role.getKey().getStringId())
        );
        // 如果分析结果为 null，说明没有缓存，直接返回 null。
        if (Objects.isNull(analysis)) {
            return null;
        }

        // 构造结果参数。
        List<Permission> matchedPermissions = analysis.getMatchedPermissions();
        boolean matchedFlag = false;
        if (Objects.nonNull(permissionKeyMatched)) {
            matchedFlag = analysis.getMatchedPermissionKeySet().contains(permissionKeyMatched);
        } else if (Objects.nonNull(allOfPermissionKeysMatched)) {
            matchedFlag = analysis.getMatchedPermissionKeySet().containsAll(allOfPermissionKeysMatched);
        } else if (Objects.nonNull(anyOfPermissionKeysMatched)) {
            for (PermissionKey anyOfPermissionKeyMatched : anyOfPermissionKeysMatched) {
                if (analysis.getMatchedPermissionKeySet().contains(anyOfPermissionKeyMatched)) {
                    matchedFlag = true;
                    break;
                }
            }
        }
        List<Permission> acceptedPermissions = null;
        List<Permission> rejectedPermissions = null;
        List<Permission> globalRejectedPermissions = null;
        if (detailMode) {
            acceptedPermissions = new ArrayList<>(analysis.getAcceptedPermissionMap().size());
            for (Permission permission : analysis.getAcceptedPermissionMap().values()) {
                CollectionUtil.insertByOrder(
                        acceptedPermissions, permission, Comparator.comparing(p -> p.getKey().getPermissionStringId())
                );
            }
            rejectedPermissions = new ArrayList<>(analysis.getRejectedPermissionMap().size());
            for (Permission permission : analysis.getRejectedPermissionMap().values()) {
                CollectionUtil.insertByOrder(
                        rejectedPermissions, permission, Comparator.comparing(p -> p.getKey().getPermissionStringId())
                );
            }
            globalRejectedPermissions = new ArrayList<>(analysis.getGlobalRejectedPermissionMap().size());
            for (Permission permission : analysis.getGlobalRejectedPermissionMap().values()) {
                CollectionUtil.insertByOrder(
                        globalRejectedPermissions, permission,
                        Comparator.comparing(p -> p.getKey().getPermissionStringId())
                );
            }
        }

        // 返回结果。
        return new PermissionViewOfRoleInspectResult(
                matchedPermissions, matchedFlag, acceptedPermissions, rejectedPermissions, globalRejectedPermissions
        );
    }

    @Nullable
    @Override
    @Deprecated
    public UserPermissionInspectResult inspectUserPermission(UserPermissionInspectInfo info) throws HandlerException {
        try {
            PermissionViewOfUserInspectResult result = inspectPermissionViewOfUser0(
                    Objects.requireNonNull(userPermissionInspectInfoToPermissionViewOfUserInspectInfo(info))
            );
            return permissionViewOfUserInspectResultToUserPermissionInspectResult(result);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Nullable
    @Override
    public PermissionViewOfUserInspectResult inspectPermissionViewOfUser(PermissionViewOfUserInspectInfo info)
            throws HandlerException {
        try {
            return inspectPermissionViewOfUser0(info);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    // 为了保证代码的可读性，此处代码不做简化。
    @SuppressWarnings({"DuplicatedCode", "OptionalOfNullableMisuse"})
    private PermissionViewOfUserInspectResult inspectPermissionViewOfUser0(PermissionViewOfUserInspectInfo info)
            throws Exception {
        // 展开参数。
        ScopedUserKey scopedUserKey = info.getScopedUserKey();
        PermissionKey permissionKeyMatched = info.getPermissionKeyMatched();
        List<PermissionKey> allOfPermissionKeysMatched = info.getAllOfPermissionKeysMatched();
        List<PermissionKey> anyOfPermissionKeysMatched = info.getAnyOfPermissionKeysMatched();
        boolean detailMode = info.isDetailMode();

        // Scope 查找。
        StringIdKey scopeKey = Optional.ofNullable(scopedUserKey).map(ScopedUserKey::getScopeStringId)
                .map(StringIdKey::new).orElse(null);
        Scope scope;
        // 如果 scopeKey 为 null，说明没有提供作用域信息，直接返回 null。
        if (Objects.isNull(scopeKey)) {
            return null;
        }
        scope = scopeMaintainService.getIfExists(scopeKey);
        // 如果 scope 为 null，说明没有找到对应的作用域，直接返回 null。
        if (Objects.isNull(scope)) {
            return null;
        }

        // User 查找。
        User user;
        StringIdKey userKey = Optional.ofNullable(scopedUserKey).map(ScopedUserKey::getUserStringId)
                .map(StringIdKey::new).orElse(null);
        // 如果 userKey 为 null，说明没有提供用户信息，直接返回 null。
        if (Objects.isNull(userKey)) {
            return null;
        }
        user = userMaintainService.getIfExists(userKey);
        // 如果 user 为 null，说明没有找到对应的用户，直接返回 null。
        if (Objects.isNull(user)) {
            return null;
        }

        // 从缓存中查询分析结果。
        ScopedUserPermissionAnalysis analysis = scopedUserPermissionAnalysisLocalCacheHandler.get(
                new ScopedUserKey(scope.getKey().getStringId(), user.getKey().getStringId())
        );
        // 如果分析结果为 null，说明没有缓存，直接返回 null。
        if (Objects.isNull(analysis)) {
            return null;
        }

        // 构造结果参数。
        List<Permission> matchedPermissions = analysis.getMatchedPermissions();
        boolean matchedFlag = false;
        if (Objects.nonNull(permissionKeyMatched)) {
            matchedFlag = analysis.getMatchedPermissionKeySet().contains(permissionKeyMatched);
        } else if (Objects.nonNull(allOfPermissionKeysMatched)) {
            matchedFlag = analysis.getMatchedPermissionKeySet().containsAll(allOfPermissionKeysMatched);
        } else if (Objects.nonNull(anyOfPermissionKeysMatched)) {
            for (PermissionKey anyOfPermissionKeyMatched : anyOfPermissionKeysMatched) {
                if (analysis.getMatchedPermissionKeySet().contains(anyOfPermissionKeyMatched)) {
                    matchedFlag = true;
                    break;
                }
            }
        }
        List<PermissionViewOfUserInspectResult.RoleDetail> roleDetails = null;
        if (detailMode) {
            roleDetails = new ArrayList<>(analysis.getRoleDetails().size());
            for (ScopedUserPermissionAnalysis.RoleDetail analysisRoleDetail : analysis.getRoleDetails()) {
                roleDetails.add(new PermissionViewOfUserInspectResult.RoleDetail(
                        analysisRoleDetail.getRole(),
                        analysisRoleDetail.getAcceptedPermissions(),
                        analysisRoleDetail.getRejectedPermissions(),
                        analysisRoleDetail.getGlobalRejectedPermissions()
                ));
            }
        }

        // 返回结果。
        return new PermissionViewOfUserInspectResult(matchedPermissions, matchedFlag, roleDetails);
    }

    @Deprecated
    private UserViewOfPermissionInspectInfo permissionUserInspectInfoToUserViewOfPermissionInspectInfo(
            PermissionUserInspectInfo info
    ) {
        if (Objects.isNull(info)) {
            return null;
        }
        return new UserViewOfPermissionInspectInfo(
                info.getPermissionKey(),
                info.getUserKeyMatched(),
                info.getAllOfUserKeysMatched(),
                info.getAnyOfUserKeysMatched(),
                info.isDetailMode()
        );
    }

    @Deprecated
    private PermissionUserInspectResult userViewOfPermissionInspectResultToPermissionUserInspectResult(
            UserViewOfPermissionInspectResult result
    ) {
        if (Objects.isNull(result)) {
            return null;
        }
        return new PermissionUserInspectResult(
                result.getMatchedUsers(),
                result.isMatchedFlag(),
                result.getAcceptedRoles(),
                result.getRejectedRoles()
        );
    }

    @Deprecated
    private PermissionViewOfRoleInspectInfo rolePermissionInspectInfoToPermissionViewOfRoleInspectInfo(
            RolePermissionInspectInfo info
    ) {
        if (Objects.isNull(info)) {
            return null;
        }
        return new PermissionViewOfRoleInspectInfo(
                info.getScopedRoleKey(),
                info.getPermissionKeyMatched(),
                info.getAllOfPermissionKeysMatched(),
                info.getAnyOfPermissionKeysMatched(),
                info.isDetailMode()
        );
    }

    @Deprecated
    private RolePermissionInspectResult permissionViewOfRoleInspectResultToRolePermissionInspectResult(
            PermissionViewOfRoleInspectResult result
    ) {
        if (Objects.isNull(result)) {
            return null;
        }
        return new RolePermissionInspectResult(
                result.getMatchedPermissions(),
                result.isMatchedFlag(),
                result.getAcceptedPermissions(),
                result.getRejectedPermissions(),
                result.getGlobalRejectedPermissions()
        );
    }

    @Deprecated
    private PermissionViewOfUserInspectInfo userPermissionInspectInfoToPermissionViewOfUserInspectInfo(
            UserPermissionInspectInfo info
    ) {
        if (Objects.isNull(info)) {
            return null;
        }
        return new PermissionViewOfUserInspectInfo(
                info.getScopedUserKey(),
                info.getPermissionKeyMatched(),
                info.getAllOfPermissionKeysMatched(),
                info.getAnyOfPermissionKeysMatched(),
                info.isDetailMode()
        );
    }

    @Deprecated
    private UserPermissionInspectResult permissionViewOfUserInspectResultToUserPermissionInspectResult(
            PermissionViewOfUserInspectResult result
    ) {
        if (Objects.isNull(result)) {
            return null;
        }
        List<UserPermissionInspectResult.RoleDetail> roleDetails = null;
        if (Objects.nonNull(result.getRoleDetails())) {
            roleDetails = new ArrayList<>(result.getRoleDetails().size());
            for (PermissionViewOfUserInspectResult.RoleDetail rd : result.getRoleDetails()) {
                roleDetails.add(new UserPermissionInspectResult.RoleDetail(
                        rd.getRole(),
                        rd.getAcceptedPermissions(),
                        rd.getRejectedPermissions(),
                        rd.getGlobalRejectedPermissions()
                ));
            }
        }
        return new UserPermissionInspectResult(
                result.getMatchedPermissions(),
                result.isMatchedFlag(),
                roleDetails
        );
    }
}

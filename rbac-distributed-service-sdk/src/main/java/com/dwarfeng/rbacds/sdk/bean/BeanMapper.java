package com.dwarfeng.rbacds.sdk.bean;

import com.dwarfeng.rbacds.sdk.bean.dto.*;
import com.dwarfeng.rbacds.sdk.bean.entity.*;
import com.dwarfeng.rbacds.sdk.bean.key.*;
import com.dwarfeng.rbacds.stack.bean.dto.*;
import com.dwarfeng.rbacds.stack.bean.entity.*;
import com.dwarfeng.rbacds.stack.bean.key.*;
import com.dwarfeng.subgrade.basic.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.web.sdk.bean.key.*;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
@Mapper
public interface BeanMapper {

    // region Subgrade Key

    FastJsonLongIdKey longIdKeyToFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromFastJson(FastJsonLongIdKey fastJsonLongIdKey);

    FastJsonStringIdKey stringIdKeyToFastJson(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromFastJson(FastJsonStringIdKey fastJsonStringIdKey);

    JSFixedFastJsonLongIdKey longIdKeyToJSFixedFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromJSFixedFastJson(JSFixedFastJsonLongIdKey jSFixedFastJsonLongIdKey);

    WebInputLongIdKey longIdKeyToWebInput(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromWebInput(WebInputLongIdKey webInputLongIdKey);

    WebInputStringIdKey stringIdKeyToWebInput(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromWebInput(WebInputStringIdKey webInputStringIdKey);

    // endregion

    // region Rbac Key

    FastJsonPermissionGroupKey permissionGroupKeyToFastJson(PermissionGroupKey permissionGroupKey);

    @InheritInverseConfiguration
    PermissionGroupKey permissionGroupKeyFromFastJson(FastJsonPermissionGroupKey fastJsonPermissionGroupKey);

    FastJsonPermissionKey permissionKeyToFastJson(PermissionKey permissionKey);

    @InheritInverseConfiguration
    PermissionKey permissionKeyFromFastJson(FastJsonPermissionKey fastJsonPermissionKey);

    FastJsonPexpKey pexpKeyToFastJson(PexpKey pexpKey);

    @InheritInverseConfiguration
    PexpKey pexpKeyFromFastJson(FastJsonPexpKey fastJsonPexpKey);

    FastJsonRoleUserRelationKey roleUserRelationKeyToFastJson(RoleUserRelationKey roleUserRelationKey);

    @InheritInverseConfiguration
    RoleUserRelationKey roleUserRelationKeyFromFastJson(FastJsonRoleUserRelationKey fastJsonRoleUserRelationKey);

    WebInputPermissionGroupKey permissionGroupKeyToWebInput(PermissionGroupKey permissionGroupKey);

    @InheritInverseConfiguration
    PermissionGroupKey permissionGroupKeyFromWebInput(WebInputPermissionGroupKey webInputPermissionGroupKey);

    WebInputPermissionKey permissionKeyToWebInput(PermissionKey permissionKey);

    @InheritInverseConfiguration
    PermissionKey permissionKeyFromWebInput(WebInputPermissionKey webInputPermissionKey);

    WebInputPexpKey pexpKeyToWebInput(PexpKey pexpKey);

    @InheritInverseConfiguration
    PexpKey pexpKeyFromWebInput(WebInputPexpKey webInputPexpKey);

    WebInputRoleUserRelationKey roleUserRelationKeyToWebInput(RoleUserRelationKey roleUserRelationKey);

    @InheritInverseConfiguration
    RoleUserRelationKey roleUserRelationKeyFromWebInput(WebInputRoleUserRelationKey webInputRoleUserRelationKey);

    FastJsonScopedRoleKey scopedRoleKeyToFastJson(ScopedRoleKey scopedRoleKey);

    @InheritInverseConfiguration
    ScopedRoleKey scopedRoleKeyFromFastJson(FastJsonScopedRoleKey fastJsonScopedRoleKey);

    FastJsonScopedUserKey scopedUserKeyToFastJson(ScopedUserKey scopedUserKey);

    @InheritInverseConfiguration
    ScopedUserKey scopedUserKeyFromFastJson(FastJsonScopedUserKey fastJsonScopedUserKey);

    WebInputScopedRoleKey scopedRoleKeyToWebInput(ScopedRoleKey scopedRoleKey);

    @InheritInverseConfiguration
    ScopedRoleKey scopedRoleKeyFromWebInput(WebInputScopedRoleKey webInputScopedRoleKey);

    WebInputScopedUserKey scopedUserKeyToWebInput(ScopedUserKey scopedUserKey);

    @InheritInverseConfiguration
    ScopedUserKey scopedUserKeyFromWebInput(WebInputScopedUserKey webInputScopedUserKey);

    // endregion

    // region Rbac Entity

    FastJsonFilterSupport filterSupportToFastJson(FilterSupport filterSupport);

    @InheritInverseConfiguration
    FilterSupport filterSupportFromFastJson(FastJsonFilterSupport fastJsonFilterSupport);

    FastJsonPermission permissionToFastJson(Permission permission);

    @InheritInverseConfiguration
    Permission permissionFromFastJson(FastJsonPermission fastJsonPermission);

    FastJsonPermissionGroup permissionGroupToFastJson(PermissionGroup permissionGroup);

    @InheritInverseConfiguration
    PermissionGroup permissionGroupFromFastJson(FastJsonPermissionGroup fastJsonPermissionGroup);

    FastJsonPexp pexpToFastJson(Pexp pexp);

    @InheritInverseConfiguration
    Pexp pexpFromFastJson(FastJsonPexp fastJsonPexp);

    FastJsonRole roleToFastJson(Role role);

    @InheritInverseConfiguration
    Role roleFromFastJson(FastJsonRole fastJsonRole);

    FastJsonRoleUserRelation roleUserRelationToFastJson(RoleUserRelation roleUserRelation);

    @InheritInverseConfiguration
    RoleUserRelation roleUserRelationFromFastJson(FastJsonRoleUserRelation fastJsonRoleUserRelation);

    FastJsonScope scopeToFastJson(Scope scope);

    @InheritInverseConfiguration
    Scope scopeFromFastJson(FastJsonScope fastJsonScope);

    FastJsonUser userToFastJson(User user);

    @InheritInverseConfiguration
    User userFromFastJson(FastJsonUser fastJsonUser);

    WebInputRole roleToWebInput(Role role);

    @InheritInverseConfiguration
    Role roleFromWebInput(WebInputRole webInputRole);

    WebInputRoleUserRelation roleUserRelationToWebInput(RoleUserRelation roleUserRelation);

    @InheritInverseConfiguration
    RoleUserRelation roleUserRelationFromWebInput(WebInputRoleUserRelation webInputRoleUserRelation);

    WebInputScope scopeToWebInput(Scope scope);

    @InheritInverseConfiguration
    Scope scopeFromWebInput(WebInputScope webInputScope);

    WebInputUser userToWebInput(User user);

    @InheritInverseConfiguration
    User userFromWebInput(WebInputUser webInputUser);

    // endregion

    // region Rbac DTO

    FastJsonPermissionCreateResult permissionCreateResultToFastJson(PermissionCreateResult permissionCreateResult);

    @InheritInverseConfiguration
    PermissionCreateResult permissionCreateResultFromFastJson(
            FastJsonPermissionCreateResult fastJsonPermissionCreateResult
    );

    FastJsonPermissionGroupCreateResult permissionGroupCreateResultToFastJson(
            PermissionGroupCreateResult permissionGroupCreateResult
    );

    @InheritInverseConfiguration
    PermissionGroupCreateResult permissionGroupCreateResultFromFastJson(
            FastJsonPermissionGroupCreateResult fastJsonPermissionGroupCreateResult
    );

    FastJsonPexpCreateResult pexpCreateResultToFastJson(PexpCreateResult pexpCreateResult);

    @InheritInverseConfiguration
    PexpCreateResult pexpCreateResultFromFastJson(FastJsonPexpCreateResult fastJsonPexpCreateResult);

    WebInputPermissionCreateInfo permissionCreateInfoToWebInput(PermissionCreateInfo permissionCreateInfo);

    @InheritInverseConfiguration
    PermissionCreateInfo permissionCreateInfoFromWebInput(WebInputPermissionCreateInfo webInputPermissionCreateInfo);

    WebInputPermissionGroupCreateInfo permissionGroupCreateInfoToWebInput(
            PermissionGroupCreateInfo permissionGroupCreateInfo
    );

    @InheritInverseConfiguration
    PermissionGroupCreateInfo permissionGroupCreateInfoFromWebInput(
            WebInputPermissionGroupCreateInfo webInputPermissionGroupCreateInfo
    );

    WebInputPermissionGroupRemoveInfo permissionGroupRemoveInfoToWebInput(
            PermissionGroupRemoveInfo permissionGroupRemoveInfo
    );

    @InheritInverseConfiguration
    PermissionGroupRemoveInfo permissionGroupRemoveInfoFromWebInput(
            WebInputPermissionGroupRemoveInfo webInputPermissionGroupRemoveInfo
    );

    WebInputPermissionGroupUpdateInfo permissionGroupUpdateInfoToWebInput(
            PermissionGroupUpdateInfo permissionGroupUpdateInfo
    );

    @InheritInverseConfiguration
    PermissionGroupUpdateInfo permissionGroupUpdateInfoFromWebInput(
            WebInputPermissionGroupUpdateInfo webInputPermissionGroupUpdateInfo
    );

    WebInputPermissionRemoveInfo permissionRemoveInfoToWebInput(PermissionRemoveInfo permissionRemoveInfo);

    @InheritInverseConfiguration
    PermissionRemoveInfo permissionRemoveInfoFromWebInput(WebInputPermissionRemoveInfo webInputPermissionRemoveInfo);

    WebInputPermissionUpdateInfo permissionUpdateInfoToWebInput(PermissionUpdateInfo permissionUpdateInfo);

    @InheritInverseConfiguration
    PermissionUpdateInfo permissionUpdateInfoFromWebInput(WebInputPermissionUpdateInfo webInputPermissionUpdateInfo);

    WebInputPexpCreateInfo pexpCreateInfoToWebInput(PexpCreateInfo pexpCreateInfo);

    @InheritInverseConfiguration
    PexpCreateInfo pexpCreateInfoFromWebInput(WebInputPexpCreateInfo webInputPexpCreateInfo);

    WebInputPexpRemoveInfo pexpRemoveInfoToWebInput(PexpRemoveInfo pexpRemoveInfo);

    @InheritInverseConfiguration
    PexpRemoveInfo pexpRemoveInfoFromWebInput(WebInputPexpRemoveInfo webInputPexpRemoveInfo);

    WebInputPexpUpdateInfo pexpUpdateInfoToWebInput(PexpUpdateInfo pexpUpdateInfo);

    @InheritInverseConfiguration
    PexpUpdateInfo pexpUpdateInfoFromWebInput(WebInputPexpUpdateInfo webInputPexpUpdateInfo);

    @Deprecated
    FastJsonPermissionUserInspectResult permissionUserInspectResultToFastJson(
            PermissionUserInspectResult permissionUserInspectResult
    );

    @Deprecated
    @InheritInverseConfiguration
    PermissionUserInspectResult permissionUserInspectResultFromFastJson(
            FastJsonPermissionUserInspectResult fastJsonPermissionUserInspectResult
    );

    @Deprecated
    FastJsonRolePermissionInspectResult rolePermissionInspectResultToFastJson(
            RolePermissionInspectResult rolePermissionInspectResult
    );

    @Deprecated
    @InheritInverseConfiguration
    RolePermissionInspectResult rolePermissionInspectResultFromFastJson(
            FastJsonRolePermissionInspectResult fastJsonRolePermissionInspectResult
    );

    @Deprecated
    FastJsonUserPermissionInspectResult userPermissionInspectResultToFastJson(
            UserPermissionInspectResult userPermissionInspectResult
    );

    @Deprecated
    @InheritInverseConfiguration
    UserPermissionInspectResult userPermissionInspectResultFromFastJson(
            FastJsonUserPermissionInspectResult fastJsonUserPermissionInspectResult
    );

    @Deprecated
    WebInputPermissionUserInspectInfo permissionUserInspectInfoToWebInput(
            PermissionUserInspectInfo permissionUserInspectInfo
    );

    @Deprecated
    @InheritInverseConfiguration
    PermissionUserInspectInfo permissionUserInspectInfoFromWebInput(
            WebInputPermissionUserInspectInfo webInputPermissionUserInspectInfo
    );

    @Deprecated
    WebInputRolePermissionInspectInfo rolePermissionInspectInfoToWebInput(
            RolePermissionInspectInfo rolePermissionInspectInfo
    );

    @Deprecated
    @InheritInverseConfiguration
    RolePermissionInspectInfo rolePermissionInspectInfoFromWebInput(
            WebInputRolePermissionInspectInfo webInputRolePermissionInspectInfo
    );

    @Deprecated
    WebInputUserPermissionInspectInfo userPermissionInspectInfoToWebInput(
            UserPermissionInspectInfo userPermissionInspectInfo
    );

    @Deprecated
    @InheritInverseConfiguration
    UserPermissionInspectInfo userPermissionInspectInfoFromWebInput(
            WebInputUserPermissionInspectInfo webInputUserPermissionInspectInfo
    );

    FastJsonUserViewOfPermissionInspectResult userViewOfPermissionInspectResultToFastJson(
            UserViewOfPermissionInspectResult userViewOfPermissionInspectResult
    );

    @InheritInverseConfiguration
    UserViewOfPermissionInspectResult userViewOfPermissionInspectResultFromFastJson(
            FastJsonUserViewOfPermissionInspectResult fastJsonUserViewOfPermissionInspectResult
    );

    FastJsonPermissionViewOfRoleInspectResult permissionViewOfRoleInspectResultToFastJson(
            PermissionViewOfRoleInspectResult permissionViewOfRoleInspectResult
    );

    @InheritInverseConfiguration
    PermissionViewOfRoleInspectResult permissionViewOfRoleInspectResultFromFastJson(
            FastJsonPermissionViewOfRoleInspectResult fastJsonPermissionViewOfRoleInspectResult
    );

    FastJsonPermissionViewOfUserInspectResult permissionViewOfUserInspectResultToFastJson(
            PermissionViewOfUserInspectResult permissionViewOfUserInspectResult
    );

    @InheritInverseConfiguration
    PermissionViewOfUserInspectResult permissionViewOfUserInspectResultFromFastJson(
            FastJsonPermissionViewOfUserInspectResult fastJsonPermissionViewOfUserInspectResult
    );

    WebInputUserViewOfPermissionInspectInfo userViewOfPermissionInspectInfoToWebInput(
            UserViewOfPermissionInspectInfo userViewOfPermissionInspectInfo
    );

    @InheritInverseConfiguration
    UserViewOfPermissionInspectInfo userViewOfPermissionInspectInfoFromWebInput(
            WebInputUserViewOfPermissionInspectInfo webInputUserViewOfPermissionInspectInfo
    );

    WebInputPermissionViewOfRoleInspectInfo permissionViewOfRoleInspectInfoToWebInput(
            PermissionViewOfRoleInspectInfo permissionViewOfRoleInspectInfo
    );

    @InheritInverseConfiguration
    PermissionViewOfRoleInspectInfo permissionViewOfRoleInspectInfoFromWebInput(
            WebInputPermissionViewOfRoleInspectInfo webInputPermissionViewOfRoleInspectInfo
    );

    WebInputPermissionViewOfUserInspectInfo permissionViewOfUserInspectInfoToWebInput(
            PermissionViewOfUserInspectInfo permissionViewOfUserInspectInfo
    );

    @InheritInverseConfiguration
    PermissionViewOfUserInspectInfo permissionViewOfUserInspectInfoFromWebInput(
            WebInputPermissionViewOfUserInspectInfo webInputPermissionViewOfUserInspectInfo
    );

    // endregion
}

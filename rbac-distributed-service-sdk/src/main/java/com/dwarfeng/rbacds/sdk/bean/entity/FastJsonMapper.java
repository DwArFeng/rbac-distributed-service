package com.dwarfeng.rbacds.sdk.bean.entity;

import com.dwarfeng.rbacds.stack.bean.entity.*;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * FastJson Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
@Mapper
public interface FastJsonMapper {

    FastJsonLongIdKey longIdKeyToFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromFastJson(FastJsonLongIdKey fastJsonLongIdKey);

    FastJsonStringIdKey stringIdKeyToFastJson(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromFastJson(FastJsonStringIdKey fastJsonStringIdKey);

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

    FastJsonUser userToFastJson(User user);

    @InheritInverseConfiguration
    User userFromFastJson(FastJsonUser fastJsonUser);
}

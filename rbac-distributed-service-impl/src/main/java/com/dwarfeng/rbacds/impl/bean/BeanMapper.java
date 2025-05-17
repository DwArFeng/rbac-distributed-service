package com.dwarfeng.rbacds.impl.bean;

import com.dwarfeng.rbacds.impl.bean.entity.*;
import com.dwarfeng.rbacds.stack.bean.entity.*;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>impl</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
@Mapper
public interface BeanMapper {

    // -----------------------------------------------------------Subgrade Key-----------------------------------------------------------
    HibernateLongIdKey longIdKeyToHibernate(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromHibernate(HibernateLongIdKey hibernateLongIdKey);

    HibernateStringIdKey stringIdKeyToHibernate(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromHibernate(HibernateStringIdKey hibernateStringIdKey);

    // -----------------------------------------------------------Rbac Entity-----------------------------------------------------------
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "stringId", ignore = true)
    @Mapping(target = "groupStringId", ignore = true)
    @Mapping(target = "group", ignore = true)
    HibernatePermission permissionToHibernate(Permission permission);

    @InheritInverseConfiguration
    Permission permissionFromHibernate(HibernatePermission hibernatePermission);

    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "stringId", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "parentStringId", ignore = true)
    @Mapping(target = "parentGroup", ignore = true)
    @Mapping(target = "childGroups", ignore = true)
    HibernatePermissionGroup permissionGroupToHibernate(PermissionGroup permissionGroup);

    @InheritInverseConfiguration
    PermissionGroup permissionGroupFromHibernate(HibernatePermissionGroup hibernatePermissionGroup);

    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "roleId", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernatePexp pexpToHibernate(Pexp pexp);

    @InheritInverseConfiguration
    Pexp pexpFromHibernate(HibernatePexp hibernatePexp);

    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "stringId", ignore = true)
    @Mapping(target = "pexps", ignore = true)
    HibernateRole roleToHibernate(Role role);

    @InheritInverseConfiguration
    Role roleFromHibernate(HibernateRole hibernateRole);

    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "stringId", ignore = true)
    @Mapping(target = "roles", ignore = true)
    HibernateUser userToHibernate(User user);

    @InheritInverseConfiguration
    User userFromHibernate(HibernateUser hibernateUser);

    @Mapping(target = "stringId", ignore = true)
    HibernatePermissionFilterSupport permissionFilterSupportToHibernate(
            PermissionFilterSupport permissionFilterSupport
    );

    @InheritInverseConfiguration
    PermissionFilterSupport permissionFilterSupportFromHibernate(
            HibernatePermissionFilterSupport hibernatePermissionFilterSupport
    );
}

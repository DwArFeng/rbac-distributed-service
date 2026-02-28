package com.dwarfeng.rbacds.impl.bean;

import com.dwarfeng.rbacds.impl.bean.entity.*;
import com.dwarfeng.rbacds.impl.bean.key.HibernatePermissionGroupKey;
import com.dwarfeng.rbacds.impl.bean.key.HibernatePermissionKey;
import com.dwarfeng.rbacds.impl.bean.key.HibernatePexpKey;
import com.dwarfeng.rbacds.impl.bean.key.HibernateRoleUserRelationKey;
import com.dwarfeng.rbacds.stack.bean.entity.*;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.rbacds.stack.bean.key.RoleUserRelationKey;
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

    // -----------------------------------------------------------Rbac Key-----------------------------------------------------------
    HibernatePermissionGroupKey permissionGroupKeyToHibernate(PermissionGroupKey permissionGroupKey);

    @InheritInverseConfiguration
    PermissionGroupKey permissionGroupKeyFromHibernate(HibernatePermissionGroupKey hibernatePermissionGroupKey);

    HibernatePermissionKey permissionKeyToHibernate(PermissionKey permissionKey);

    @InheritInverseConfiguration
    PermissionKey permissionKeyFromHibernate(HibernatePermissionKey hibernatePermissionKey);

    HibernatePexpKey pexpKeyToHibernate(PexpKey pexpKey);

    @InheritInverseConfiguration
    PexpKey pexpKeyFromHibernate(HibernatePexpKey hibernatePexpKey);

    HibernateRoleUserRelationKey roleUserRelationKeyToHibernate(RoleUserRelationKey roleUserRelationKey);

    @InheritInverseConfiguration
    RoleUserRelationKey roleUserRelationKeyFromHibernate(HibernateRoleUserRelationKey hibernateRoleUserRelationKey);

    // -----------------------------------------------------------Rbac Entity-----------------------------------------------------------
    @Mapping(target = "scopeStringId", ignore = true)
    @Mapping(target = "scope", ignore = true)
    @Mapping(target = "permissionStringId", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "groupScopeStringId", ignore = true)
    @Mapping(target = "groupPermissionGroupStringId", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    HibernatePermission permissionToHibernate(Permission permission);

    @InheritInverseConfiguration
    Permission permissionFromHibernate(HibernatePermission hibernatePermission);

    @Mapping(target = "scopeStringId", ignore = true)
    @Mapping(target = "scope", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "permissionGroupStringId", ignore = true)
    @Mapping(target = "parentScopeStringId", ignore = true)
    @Mapping(target = "parentPermissionGroupStringId", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "children", ignore = true)
    HibernatePermissionGroup permissionGroupToHibernate(PermissionGroup permissionGroup);

    @InheritInverseConfiguration
    PermissionGroup permissionGroupFromHibernate(HibernatePermissionGroup hibernatePermissionGroup);

    @Mapping(target = "scopeStringId", ignore = true)
    @Mapping(target = "scope", ignore = true)
    @Mapping(target = "roleStringId", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "pexpStringId", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    HibernatePexp pexpToHibernate(Pexp pexp);

    @InheritInverseConfiguration
    Pexp pexpFromHibernate(HibernatePexp hibernatePexp);

    @Mapping(target = "stringId", ignore = true)
    @Mapping(target = "roleUserRelations", ignore = true)
    @Mapping(target = "pexps", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    HibernateRole roleToHibernate(Role role);

    @InheritInverseConfiguration
    Role roleFromHibernate(HibernateRole hibernateRole);

    @Mapping(target = "stringId", ignore = true)
    @Mapping(target = "roleUserRelations", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    HibernateUser userToHibernate(User user);

    @InheritInverseConfiguration
    User userFromHibernate(HibernateUser hibernateUser);

    @Mapping(target = "stringId", ignore = true)
    HibernateFilterSupport filterSupportToHibernate(FilterSupport filterSupport);

    @InheritInverseConfiguration
    FilterSupport filterSupportFromHibernate(HibernateFilterSupport hibernateFilterSupport);

    @Mapping(target = "userStringId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "roleStringId", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    HibernateRoleUserRelation roleUserRelationToHibernate(RoleUserRelation roleUserRelation);

    @InheritInverseConfiguration
    RoleUserRelation roleUserRelationFromHibernate(HibernateRoleUserRelation hibernateRoleUserRelation);

    @Mapping(target = "stringId", ignore = true)
    @Mapping(target = "pexps", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "permissionGroups", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    HibernateScope scopeToHibernate(Scope scope);

    @InheritInverseConfiguration
    Scope scopeFromHibernate(HibernateScope hibernateScope);
}

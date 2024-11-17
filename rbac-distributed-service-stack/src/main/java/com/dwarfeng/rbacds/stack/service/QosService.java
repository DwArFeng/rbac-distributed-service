package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * QOS 服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 * @deprecated 该类由于命名不规范，已经被废弃，
 * 请使用 {@link PermissionLookupQosService} 以及 {@link UserLookupQosService} 代替。
 */
@Deprecated
public interface QosService extends Service {

    /**
     * 查询指定的用户对应的权限。
     *
     * @param userKey 指定的用户。
     * @return 指定的用户对应的权限组成的集合。
     * @deprecated 由于功能扩展，该方法不再符合命名规范。
     */
    @Deprecated
    List<Permission> lookupPermissions(StringIdKey userKey) throws ServiceException;

    /**
     * 查询指定的权限对应的所有用户。
     *
     * @param permissionKey 指定的权限。
     * @return 指定的权限对应的用户组成的集合。
     * @since 1.1.0
     * @deprecated 由于功能扩展，该方法不再符合命名规范。
     */
    @Deprecated
    List<User> lookupUsers(StringIdKey permissionKey) throws ServiceException;

    /**
     * 查询指定的用户对应的权限。
     *
     * @param userKey 指定的用户。
     * @return 指定的用户对应的权限组成的集合。
     * @since 1.2.4
     */
    List<Permission> lookupPermissionsForUser(StringIdKey userKey) throws ServiceException;

    /**
     * 查询指定的角色对应的权限。
     *
     * @param roleKey 指定的角色。
     * @return 指定的角色对应的权限组成的集合。
     * @since 1.2.4
     */
    List<Permission> lookupPermissionsForRole(StringIdKey roleKey) throws ServiceException;

    /**
     * 查询指定的权限对应的所有用户。
     *
     * @param permissionKey 指定的权限。
     * @return 指定的权限对应的用户组成的集合。
     * @since 1.2.4
     */
    List<User> lookupUsersForPermission(StringIdKey permissionKey) throws ServiceException;
}

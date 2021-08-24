package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 权限查询服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PermissionLookupService extends Service {

    /**
     * 查询指定的用户对应的权限。
     *
     * @param userKey 指定的用户。
     * @return 指定的用户对应的权限组成的集合。
     * @deprecated 由于功能扩展，该方法不再符合命名规范。
     */
    List<Permission> lookupPermissions(StringIdKey userKey) throws ServiceException;

    /**
     * 查询指定的用户对应的权限。
     *
     * @param userKey 指定的用户。
     * @return 指定的用户对应的权限组成的集合。
     */
    List<Permission> lookupPermissionsForUser(StringIdKey userKey) throws ServiceException;

    /**
     * 查询指定的角色对应的权限。
     *
     * @param roleKey 指定的角色。
     * @return 指定的用户对应的权限组成的集合。
     */
    List<Permission> lookupPermissionsForRole(StringIdKey roleKey) throws ServiceException;
}

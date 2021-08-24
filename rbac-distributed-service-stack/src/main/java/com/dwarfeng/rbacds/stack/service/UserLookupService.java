package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 用户查询服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface UserLookupService extends Service {

    /**
     * 查询指定的权限对应的所有用户。
     *
     * @param permissionKey 指定的权限。
     * @return 指定的权限对应的用户组成的集合。
     * @deprecated 由于功能扩展，该方法不再符合命名规范。
     */
    List<User> lookupUsers(StringIdKey permissionKey) throws ServiceException;

    /**
     * 查询指定的权限对应的所有用户。
     *
     * @param permissionKey 指定的权限。
     * @return 指定的权限对应的用户组成的集合。
     * @since 1.2.4
     */
    List<User> lookupUsersForPermission(StringIdKey permissionKey) throws ServiceException;
}

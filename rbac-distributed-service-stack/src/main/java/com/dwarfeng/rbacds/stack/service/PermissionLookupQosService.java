package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 权限查询 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface PermissionLookupQosService extends Service {

    /**
     * 查询指定的用户对应的权限。
     *
     * @param userKey 指定的用户。
     * @return 指定的用户对应的权限组成的集合。
     * @throws ServiceException 服务异常。
     */
    List<Permission> lookupForUser(StringIdKey userKey) throws ServiceException;

    /**
     * 查询指定的角色对应的权限。
     *
     * @param roleKey 指定的角色。
     * @return 指定的角色对应的权限组成的集合。
     * @throws ServiceException 服务异常。
     */
    List<Permission> lookupForRole(StringIdKey roleKey) throws ServiceException;
}

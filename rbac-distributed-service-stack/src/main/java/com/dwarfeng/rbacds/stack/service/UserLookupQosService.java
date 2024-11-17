package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 用户查询 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface UserLookupQosService extends Service {

    /**
     * 查询指定的权限对应的所有用户。
     *
     * @param permissionKey 指定的权限。
     * @return 指定的权限对应的用户组成的集合。
     * @throws ServiceException 服务异常。
     */
    List<User> lookupForPermission(StringIdKey permissionKey) throws ServiceException;
}

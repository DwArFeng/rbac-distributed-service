package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.handler.PermissionFilter;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 权限过滤 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public interface PermissionFilterQosService extends Service {

    /**
     * 获取指定类型的权限过滤器。
     *
     * @param permissionFilterType 指定的类型。
     * @return 指定类型的权限过滤器，或者是 null。
     * @throws ServiceException 服务异常。
     */
    PermissionFilter getPermissionFilter(String permissionFilterType) throws ServiceException;

    /**
     * 清除本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearLocalCache() throws ServiceException;
}

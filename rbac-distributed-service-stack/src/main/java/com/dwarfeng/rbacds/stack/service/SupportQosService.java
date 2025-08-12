package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 支持 QoS 服务。
 *
 * @author DwArFeng
 * @since 1.9.0
 */
public interface SupportQosService extends Service {

    /**
     * 重置权限过滤器。
     *
     * @throws ServiceException 服务异常。
     */
    void resetPermissionFilter() throws ServiceException;
}

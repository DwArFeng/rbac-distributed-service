package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.struct.PermissionUserAnalysis;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import javax.annotation.Nullable;

/**
 * 权限用户分析本地缓存 QOS 服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface PermissionUserAnalysisLocalCacheQosService extends Service {

    /**
     * 获取指定权限键的权限用户分析。
     *
     * @param key 权限键。
     * @return 权限用户分析，或者是 null。
     * @throws ServiceException 服务异常。
     */
    @Nullable
    PermissionUserAnalysis get(PermissionKey key) throws ServiceException;

    /**
     * 清除本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearLocalCache() throws ServiceException;
}

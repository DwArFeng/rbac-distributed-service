package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.key.ScopedUserKey;
import com.dwarfeng.rbacds.stack.struct.ScopedUserPermissionAnalysis;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import javax.annotation.Nullable;

/**
 * 作用域用户权限分析本地缓存 QOS 服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface ScopedUserPermissionAnalysisLocalCacheQosService extends Service {

    /**
     * 获取指定作用域用户键的作用域用户权限分析。
     *
     * @param key 作用域用户键。
     * @return 作用域用户权限分析，或者是 null。
     * @throws ServiceException 服务异常。
     */
    @Nullable
    ScopedUserPermissionAnalysis get(ScopedUserKey key) throws ServiceException;

    /**
     * 清除本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearLocalCache() throws ServiceException;
}

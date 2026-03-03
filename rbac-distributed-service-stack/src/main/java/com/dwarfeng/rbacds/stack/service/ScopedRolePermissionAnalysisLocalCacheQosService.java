package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.key.ScopedRoleKey;
import com.dwarfeng.rbacds.stack.struct.ScopedRolePermissionAnalysis;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import javax.annotation.Nullable;

/**
 * 作用域角色权限分析本地缓存 QOS 服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface ScopedRolePermissionAnalysisLocalCacheQosService extends Service {

    /**
     * 获取指定作用域角色键的作用域角色权限分析。
     *
     * @param key 作用域角色键。
     * @return 作用域角色权限分析，或者是 null。
     * @throws ServiceException 服务异常。
     */
    @Nullable
    ScopedRolePermissionAnalysis get(ScopedRoleKey key) throws ServiceException;

    /**
     * 清除本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearLocalCache() throws ServiceException;
}

package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.struct.UserRoleAnalysis;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import javax.annotation.Nullable;

/**
 * 用户角色分析本地缓存 QOS 服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface UserRoleAnalysisLocalCacheQosService extends Service {

    /**
     * 获取指定用户键的用户角色分析。
     *
     * @param key 用户键。
     * @return 用户角色分析，或者是 null。
     * @throws ServiceException 服务异常。
     */
    @Nullable
    UserRoleAnalysis get(StringIdKey key) throws ServiceException;

    /**
     * 清除本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearLocalCache() throws ServiceException;
}

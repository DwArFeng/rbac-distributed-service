package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.key.ScopedRoleKey;
import com.dwarfeng.rbacds.stack.handler.ScopedRolePermissionAnalysisLocalCacheHandler;
import com.dwarfeng.rbacds.stack.service.ScopedRolePermissionAnalysisLocalCacheQosService;
import com.dwarfeng.rbacds.stack.struct.ScopedRolePermissionAnalysis;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 作用域角色权限分析本地缓存 QOS 服务实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Service
public class ScopedRolePermissionAnalysisLocalCacheQosServiceImpl implements ScopedRolePermissionAnalysisLocalCacheQosService {

    private final ScopedRolePermissionAnalysisLocalCacheHandler scopedRolePermissionAnalysisLocalCacheHandler;
    private final ServiceExceptionMapper sem;
    private final Lock lock = new ReentrantLock();

    public ScopedRolePermissionAnalysisLocalCacheQosServiceImpl(
            ScopedRolePermissionAnalysisLocalCacheHandler scopedRolePermissionAnalysisLocalCacheHandler,
            ServiceExceptionMapper sem
    ) {
        this.scopedRolePermissionAnalysisLocalCacheHandler = scopedRolePermissionAnalysisLocalCacheHandler;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    public ScopedRolePermissionAnalysis get(ScopedRoleKey key) throws ServiceException {
        lock.lock();
        try {
            return scopedRolePermissionAnalysisLocalCacheHandler.get(key);
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("从本地缓存中获取作用域角色权限分析时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void clearLocalCache() throws ServiceException {
        lock.lock();
        try {
            scopedRolePermissionAnalysisLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除本地缓存时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }
}

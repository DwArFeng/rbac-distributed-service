package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.key.ScopedUserKey;
import com.dwarfeng.rbacds.stack.handler.ScopedUserPermissionAnalysisLocalCacheHandler;
import com.dwarfeng.rbacds.stack.service.ScopedUserPermissionAnalysisLocalCacheQosService;
import com.dwarfeng.rbacds.stack.struct.ScopedUserPermissionAnalysis;
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
 * 作用域用户权限分析本地缓存 QOS 服务实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Service
public class ScopedUserPermissionAnalysisLocalCacheQosServiceImpl implements ScopedUserPermissionAnalysisLocalCacheQosService {

    private final ScopedUserPermissionAnalysisLocalCacheHandler scopedUserPermissionAnalysisLocalCacheHandler;
    private final ServiceExceptionMapper sem;
    private final Lock lock = new ReentrantLock();

    public ScopedUserPermissionAnalysisLocalCacheQosServiceImpl(
            ScopedUserPermissionAnalysisLocalCacheHandler scopedUserPermissionAnalysisLocalCacheHandler,
            ServiceExceptionMapper sem
    ) {
        this.scopedUserPermissionAnalysisLocalCacheHandler = scopedUserPermissionAnalysisLocalCacheHandler;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    public ScopedUserPermissionAnalysis get(ScopedUserKey key) throws ServiceException {
        lock.lock();
        try {
            return scopedUserPermissionAnalysisLocalCacheHandler.get(key);
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("从本地缓存中获取作用域用户权限分析时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void clearLocalCache() throws ServiceException {
        lock.lock();
        try {
            scopedUserPermissionAnalysisLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除本地缓存时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }
}

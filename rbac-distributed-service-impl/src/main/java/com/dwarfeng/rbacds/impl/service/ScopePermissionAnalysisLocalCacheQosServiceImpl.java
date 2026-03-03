package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.handler.ScopePermissionAnalysisLocalCacheHandler;
import com.dwarfeng.rbacds.stack.service.ScopePermissionAnalysisLocalCacheQosService;
import com.dwarfeng.rbacds.stack.struct.ScopePermissionAnalysis;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 作用域权限分析本地缓存 QOS 服务实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Service
public class ScopePermissionAnalysisLocalCacheQosServiceImpl implements ScopePermissionAnalysisLocalCacheQosService {

    private final ScopePermissionAnalysisLocalCacheHandler scopePermissionAnalysisLocalCacheHandler;
    private final ServiceExceptionMapper sem;
    private final Lock lock = new ReentrantLock();

    public ScopePermissionAnalysisLocalCacheQosServiceImpl(
            ScopePermissionAnalysisLocalCacheHandler scopePermissionAnalysisLocalCacheHandler,
            ServiceExceptionMapper sem
    ) {
        this.scopePermissionAnalysisLocalCacheHandler = scopePermissionAnalysisLocalCacheHandler;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    public ScopePermissionAnalysis get(StringIdKey key) throws ServiceException {
        lock.lock();
        try {
            return scopePermissionAnalysisLocalCacheHandler.get(key);
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("从本地缓存中获取作用域权限分析时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void clearLocalCache() throws ServiceException {
        lock.lock();
        try {
            scopePermissionAnalysisLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除本地缓存时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }
}

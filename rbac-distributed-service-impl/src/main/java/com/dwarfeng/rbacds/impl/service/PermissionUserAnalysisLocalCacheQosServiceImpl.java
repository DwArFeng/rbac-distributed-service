package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.handler.PermissionUserAnalysisLocalCacheHandler;
import com.dwarfeng.rbacds.stack.service.PermissionUserAnalysisLocalCacheQosService;
import com.dwarfeng.rbacds.stack.struct.PermissionUserAnalysis;
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
 * 权限用户分析本地缓存 QOS 服务实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Service
public class PermissionUserAnalysisLocalCacheQosServiceImpl implements PermissionUserAnalysisLocalCacheQosService {

    private final PermissionUserAnalysisLocalCacheHandler permissionUserAnalysisLocalCacheHandler;
    private final ServiceExceptionMapper sem;
    private final Lock lock = new ReentrantLock();

    public PermissionUserAnalysisLocalCacheQosServiceImpl(
            PermissionUserAnalysisLocalCacheHandler permissionUserAnalysisLocalCacheHandler,
            ServiceExceptionMapper sem
    ) {
        this.permissionUserAnalysisLocalCacheHandler = permissionUserAnalysisLocalCacheHandler;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    public PermissionUserAnalysis get(PermissionKey key) throws ServiceException {
        lock.lock();
        try {
            return permissionUserAnalysisLocalCacheHandler.get(key);
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("从本地缓存中获取权限用户分析时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void clearLocalCache() throws ServiceException {
        lock.lock();
        try {
            permissionUserAnalysisLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除本地缓存时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }
}

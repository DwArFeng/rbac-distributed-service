package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.handler.PermissionFilter;
import com.dwarfeng.rbacds.stack.handler.PermissionFilterLocalCacheHandler;
import com.dwarfeng.rbacds.stack.service.PermissionFilterQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class PermissionFilterQosServiceImpl implements PermissionFilterQosService {

    final
    PermissionFilterLocalCacheHandler permissionFilterLocalCacheHandler;

    private final ServiceExceptionMapper sem;

    private final Lock lock = new ReentrantLock();

    public PermissionFilterQosServiceImpl(
            PermissionFilterLocalCacheHandler permissionFilterLocalCacheHandler,
            ServiceExceptionMapper sem
    ) {
        this.permissionFilterLocalCacheHandler = permissionFilterLocalCacheHandler;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    public PermissionFilter getPermissionFilter(String permissionFilterType) throws ServiceException {
        lock.lock();
        try {
            return permissionFilterLocalCacheHandler.get(permissionFilterType);
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("从本地缓存中获取权限过滤器时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void clearLocalCache() throws ServiceException {
        lock.lock();
        try {
            permissionFilterLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除本地缓存时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }
}

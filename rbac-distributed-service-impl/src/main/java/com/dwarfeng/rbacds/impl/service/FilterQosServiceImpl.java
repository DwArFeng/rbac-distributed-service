package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.handler.Filter;
import com.dwarfeng.rbacds.stack.handler.FilterLocalCacheHandler;
import com.dwarfeng.rbacds.stack.service.FilterQosService;
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
public class FilterQosServiceImpl implements FilterQosService {

    final
    FilterLocalCacheHandler filterLocalCacheHandler;

    private final ServiceExceptionMapper sem;

    private final Lock lock = new ReentrantLock();

    public FilterQosServiceImpl(
            FilterLocalCacheHandler filterLocalCacheHandler,
            ServiceExceptionMapper sem
    ) {
        this.filterLocalCacheHandler = filterLocalCacheHandler;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    public Filter getFilter(String filterType) throws ServiceException {
        lock.lock();
        try {
            return filterLocalCacheHandler.get(filterType);
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("从本地缓存中获取过滤器时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void clearLocalCache() throws ServiceException {
        lock.lock();
        try {
            filterLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除本地缓存时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }
}

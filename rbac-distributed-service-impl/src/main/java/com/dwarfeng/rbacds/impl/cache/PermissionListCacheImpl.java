package com.dwarfeng.rbacds.impl.cache;

import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermission;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.cache.PermissionListCache;
import com.dwarfeng.subgrade.impl.cache.RedisListCache;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public class PermissionListCacheImpl implements PermissionListCache {

    @Autowired
    private RedisListCache<Permission, FastJsonPermission> redisListDelegate;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists() throws CacheException {
        return redisListDelegate.exists();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public int size() throws CacheException {
        return redisListDelegate.size();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public List<Permission> get() throws CacheException {
        return redisListDelegate.get();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public List<Permission> get(int beginIndex, int maxEntity) throws CacheException {
        return redisListDelegate.get(beginIndex, maxEntity);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public List<Permission> get(PagingInfo pagingInfo) throws CacheException {
        return redisListDelegate.get(pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void set(@SkipRecord Collection<Permission> entities, long timeout) throws CacheException {
        redisListDelegate.set(entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void leftPush(@SkipRecord Collection<Permission> entities, long timeout) throws CacheException {
        redisListDelegate.leftPush(entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void rightPush(@SkipRecord Collection<Permission> entities, long timeout) throws CacheException {
        redisListDelegate.rightPush(entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void clear() throws CacheException {
        redisListDelegate.clear();
    }
}

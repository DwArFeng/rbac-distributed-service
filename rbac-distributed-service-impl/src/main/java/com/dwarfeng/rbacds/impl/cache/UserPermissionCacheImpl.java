package com.dwarfeng.rbacds.impl.cache;

import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermission;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.cache.UserPermissionCache;
import com.dwarfeng.subgrade.impl.cache.RedisKeyListCache;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public class UserPermissionCacheImpl implements UserPermissionCache {

    private final RedisKeyListCache<StringIdKey, Permission, FastJsonPermission> redisKeyListDelegate;

    public UserPermissionCacheImpl(
            @Qualifier("userPermissionRedisKeyListCache")
            RedisKeyListCache<StringIdKey, Permission, FastJsonPermission> redisKeyListDelegate
    ) {
        this.redisKeyListDelegate = redisKeyListDelegate;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(StringIdKey key) throws CacheException {
        return redisKeyListDelegate.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public int size(StringIdKey key) throws CacheException {
        return redisKeyListDelegate.size(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public List<Permission> get(StringIdKey key) throws CacheException {
        return redisKeyListDelegate.get(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public List<Permission> get(StringIdKey key, int beginIndex, int maxEntity) throws CacheException {
        return redisKeyListDelegate.get(key, beginIndex, maxEntity);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public List<Permission> get(StringIdKey key, PagingInfo pagingInfo) throws CacheException {
        return redisKeyListDelegate.get(key, pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void set(StringIdKey key, @SkipRecord Collection<Permission> entities, long timeout) throws CacheException {
        redisKeyListDelegate.set(key, entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void leftPush(StringIdKey key, @SkipRecord Collection<Permission> entities, long timeout)
            throws CacheException {
        redisKeyListDelegate.leftPush(key, entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void rightPush(StringIdKey key, @SkipRecord Collection<Permission> entities, long timeout)
            throws CacheException {
        redisKeyListDelegate.rightPush(key, entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(StringIdKey key) throws CacheException {
        redisKeyListDelegate.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void clear() throws CacheException {
        redisKeyListDelegate.clear();
    }
}

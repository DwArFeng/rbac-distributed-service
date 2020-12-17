package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.cache.PermissionUserCache;
import com.dwarfeng.rbacds.stack.cache.PexpCache;
import com.dwarfeng.rbacds.stack.cache.UserPermissionCache;
import com.dwarfeng.rbacds.stack.dao.PexpDao;
import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class PexpMaintainServiceImpl implements PexpMaintainService {

    @Autowired
    private KeyFetcher<LongIdKey> keyFetcher;

    @Autowired
    private PexpDao permissionDao;

    @Autowired
    private PexpCache permissionCache;
    @Autowired
    private UserPermissionCache userPermissionCache;
    @Autowired
    private PermissionUserCache permissionUserCache;

    @Autowired
    private ServiceExceptionMapper sem;

    @Value("${cache.timeout.entity.permission}")
    private long permissionTimeout;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(LongIdKey key) throws ServiceException {
        try {
            return internalExists(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("判断实体是否存在时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private boolean internalExists(LongIdKey key) throws Exception {
        return permissionCache.exists(key) || permissionDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public Pexp get(LongIdKey key) throws ServiceException {
        try {
            return internalGet(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private Pexp internalGet(LongIdKey key) throws Exception {
        if (permissionCache.exists(key)) {
            return permissionCache.get(key);
        } else {
            if (!permissionDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            Pexp permission = permissionDao.get(key);
            permissionCache.push(permission, permissionTimeout);
            return permission;
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public LongIdKey insert(Pexp permission) throws ServiceException {
        try {
            return internalInsert(permission);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private LongIdKey internalInsert(Pexp permission) throws Exception {
        if (Objects.nonNull(permission.getKey()) && internalExists(permission.getKey())) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_EXISTED);
        }
        if (Objects.isNull(permission.getKey())) {
            permission.setKey(keyFetcher.fetchKey());
        }

        userPermissionCache.clear();
        permissionUserCache.clear();

        permissionDao.insert(permission);
        permissionCache.push(permission, permissionTimeout);
        return permission.getKey();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void update(Pexp permission) throws ServiceException {
        try {
            internalUpdate(permission);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("更新实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private void internalUpdate(Pexp permission) throws Exception {
        if (Objects.nonNull(permission.getKey()) && !internalExists(permission.getKey())) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }

        userPermissionCache.clear();
        permissionUserCache.clear();

        permissionCache.push(permission, permissionTimeout);
        permissionDao.update(permission);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(LongIdKey key) throws ServiceException {
        try {
            internalDelete(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("删除实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private void internalDelete(LongIdKey key) throws Exception {
        if (!internalExists(key)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }

        userPermissionCache.clear();
        permissionUserCache.clear();

        permissionDao.delete(key);
        permissionCache.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public Pexp getIfExists(LongIdKey key) throws ServiceException {
        try {
            return internalExists(key) ? internalGet(key) : null;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public LongIdKey insertIfNotExists(Pexp pexp) throws ServiceException {
        try {
            if (Objects.isNull(pexp.getKey()) || !internalExists(pexp.getKey())) {
                return internalInsert(pexp);
            }
            return null;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void updateIfExists(Pexp pexp) throws ServiceException {
        try {
            if (internalExists(pexp.getKey())) {
                internalUpdate(pexp);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("更新实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void deleteIfExists(LongIdKey key) throws ServiceException {
        try {
            if (internalExists(key)) {
                internalDelete(key);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("删除实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public LongIdKey insertOrUpdate(Pexp pexp) throws ServiceException {
        try {
            if (internalExists(pexp.getKey())) {
                internalUpdate(pexp);
                return null;
            } else {
                return internalInsert(pexp);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入或更新实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<Pexp> lookup(String preset, Object[] objs) throws ServiceException {
        try {
            return PagingUtil.pagedData(permissionDao.lookup(preset, objs));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<Pexp> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(pagingInfo, permissionDao.lookupCount(preset, objs), permissionDao.lookup(preset, objs, pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }
}

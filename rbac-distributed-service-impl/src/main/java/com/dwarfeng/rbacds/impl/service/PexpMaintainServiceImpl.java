package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.cache.PermissionUserCache;
import com.dwarfeng.rbacds.stack.cache.PexpCache;
import com.dwarfeng.rbacds.stack.cache.RolePermissionCache;
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
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.generation.KeyGenerator;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class PexpMaintainServiceImpl implements PexpMaintainService {

    private final KeyGenerator<LongIdKey> keyGenerator;

    private final PexpDao pexpDao;
    private final PexpCache pexpCache;

    private final UserPermissionCache userPermissionCache;
    private final RolePermissionCache rolePermissionCache;
    private final PermissionUserCache permissionUserCache;

    private final ServiceExceptionMapper sem;

    @Value("${cache.timeout.entity.pexp}")
    private long pexpTimeout;

    public PexpMaintainServiceImpl(
            KeyGenerator<LongIdKey> keyGenerator,
            PexpDao pexpDao, PexpCache pexpCache,
            UserPermissionCache userPermissionCache,
            RolePermissionCache rolePermissionCache,
            PermissionUserCache permissionUserCache,
            ServiceExceptionMapper sem
    ) {
        this.keyGenerator = keyGenerator;
        this.pexpDao = pexpDao;
        this.pexpCache = pexpCache;
        this.userPermissionCache = userPermissionCache;
        this.rolePermissionCache = rolePermissionCache;
        this.permissionUserCache = permissionUserCache;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(LongIdKey key) throws ServiceException {
        try {
            return internalExists(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断实体是否存在时发生异常", LogLevel.WARN, e, sem);
        }
    }

    private boolean internalExists(LongIdKey key) throws Exception {
        return pexpCache.exists(key) || pexpDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public Pexp get(LongIdKey key) throws ServiceException {
        try {
            return internalGet(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取实体时发生异常", LogLevel.WARN, e, sem);
        }
    }

    private Pexp internalGet(LongIdKey key) throws Exception {
        if (pexpCache.exists(key)) {
            return pexpCache.get(key);
        } else {
            if (!pexpDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            Pexp permission = pexpDao.get(key);
            pexpCache.push(permission, pexpTimeout);
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
            throw ServiceExceptionHelper.logParse("插入实体时发生异常", LogLevel.WARN, e, sem);
        }
    }

    private LongIdKey internalInsert(Pexp permission) throws Exception {
        if (Objects.nonNull(permission.getKey()) && internalExists(permission.getKey())) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_EXISTED);
        }
        if (Objects.isNull(permission.getKey())) {
            permission.setKey(keyGenerator.generate());
        }

        userPermissionCache.clear();
        rolePermissionCache.clear();
        permissionUserCache.clear();

        pexpDao.insert(permission);
        pexpCache.push(permission, pexpTimeout);
        return permission.getKey();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void update(Pexp permission) throws ServiceException {
        try {
            internalUpdate(permission);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("更新实体时发生异常", LogLevel.WARN, e, sem);
        }
    }

    private void internalUpdate(Pexp permission) throws Exception {
        if (Objects.nonNull(permission.getKey()) && !internalExists(permission.getKey())) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }

        userPermissionCache.clear();
        rolePermissionCache.clear();
        permissionUserCache.clear();

        pexpCache.push(permission, pexpTimeout);
        pexpDao.update(permission);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(LongIdKey key) throws ServiceException {
        try {
            internalDelete(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("删除实体时发生异常", LogLevel.WARN, e, sem);
        }
    }

    private void internalDelete(LongIdKey key) throws Exception {
        if (!internalExists(key)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }

        userPermissionCache.clear();
        rolePermissionCache.clear();
        permissionUserCache.clear();

        pexpDao.delete(key);
        pexpCache.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public Pexp getIfExists(LongIdKey key) throws ServiceException {
        try {
            return internalExists(key) ? internalGet(key) : null;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取实体时发生异常", LogLevel.WARN, e, sem);
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
            throw ServiceExceptionHelper.logParse("插入实体时发生异常", LogLevel.WARN, e, sem);
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
            throw ServiceExceptionHelper.logParse("更新实体时发生异常", LogLevel.WARN, e, sem);
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
            throw ServiceExceptionHelper.logParse("删除实体时发生异常", LogLevel.WARN, e, sem);
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
            throw ServiceExceptionHelper.logParse("插入或更新实体时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<Pexp> lookup(String preset, Object[] objs) throws ServiceException {
        try {
            return PagingUtil.pagedData(pexpDao.lookup(preset, objs));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询实体时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<Pexp> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(pagingInfo, pexpDao.lookupCount(preset, objs), pexpDao.lookup(preset, objs, pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询实体时发生异常", LogLevel.WARN, e, sem);
        }
    }
}

package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.cache.*;
import com.dwarfeng.rbacds.stack.dao.PermissionDao;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class PermissionMaintainServiceImpl implements PermissionMaintainService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private PermissionCache permissionCache;
    @Autowired
    private PermissionListCache permissionListCache;
    @Autowired
    private UserPermissionCache userPermissionCache;
    @Autowired
    private RolePermissionCache rolePermissionCache;
    @Autowired
    private PermissionUserCache permissionUserCache;

    @Autowired
    private ServiceExceptionMapper sem;
    @Value("${cache.timeout.entity.permission}")
    private long permissionTimeout;
    @Value("${cache.timeout.list.permission}")
    private long permissionListTimeout;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(StringIdKey key) throws ServiceException {
        try {
            return internalExists(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("判断实体是否存在时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private boolean internalExists(StringIdKey key) throws Exception {
        return permissionCache.exists(key) || permissionDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public Permission get(StringIdKey key) throws ServiceException {
        try {
            return internalGet(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private Permission internalGet(StringIdKey key) throws Exception {
        if (permissionCache.exists(key)) {
            return permissionCache.get(key);
        } else {
            if (!permissionDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            Permission permission = permissionDao.get(key);
            permissionCache.push(permission, permissionTimeout);
            return permission;
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public StringIdKey insert(Permission permission) throws ServiceException {
        try {
            return internalInsert(permission);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private StringIdKey internalInsert(Permission permission) throws Exception {
        if (Objects.nonNull(permission.getKey()) && internalExists(permission.getKey())) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_EXISTED);
        }

        permissionListCache.clear();
        userPermissionCache.clear();
        rolePermissionCache.clear();
        permissionUserCache.clear();

        permissionDao.insert(permission);
        permissionCache.push(permission, permissionTimeout);
        return permission.getKey();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void update(Permission permission) throws ServiceException {
        try {
            internalUpdate(permission);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("更新实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void internalUpdate(Permission permission) throws Exception {
        if (Objects.nonNull(permission.getKey()) && !internalExists(permission.getKey())) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }

        permissionListCache.clear();
        userPermissionCache.clear();
        rolePermissionCache.clear();
        permissionUserCache.clear();

        permissionCache.push(permission, permissionTimeout);
        permissionDao.update(permission);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(StringIdKey key) throws ServiceException {
        try {
            internalDelete(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("删除实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private void internalDelete(StringIdKey key) throws Exception {
        if (!internalExists(key)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }

        permissionListCache.clear();
        userPermissionCache.clear();
        rolePermissionCache.clear();
        permissionUserCache.clear();

        permissionDao.delete(key);
        permissionCache.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public Permission getIfExists(StringIdKey key) throws ServiceException {
        try {
            return internalExists(key) ? internalGet(key) : null;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public StringIdKey insertIfNotExists(Permission permission) throws ServiceException {
        try {
            if (Objects.isNull(permission.getKey()) || !internalExists(permission.getKey())) {
                return internalInsert(permission);
            }
            return null;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void updateIfExists(Permission permission) throws ServiceException {
        try {
            if (internalExists(permission.getKey())) {
                internalUpdate(permission);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("更新实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void deleteIfExists(StringIdKey key) throws ServiceException {
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
    public StringIdKey insertOrUpdate(Permission permission) throws ServiceException {
        try {
            if (internalExists(permission.getKey())) {
                internalUpdate(permission);
                return null;
            } else {
                return internalInsert(permission);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入或更新实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<Permission> lookup() throws ServiceException {
        try {
            if (permissionListCache.exists()) {
                return PagingUtil.pagedData(permissionListCache.get());
            }
            List<Permission> lookup = permissionDao.lookup();
            permissionListCache.set(lookup, permissionListTimeout);
            return PagingUtil.pagedData(lookup);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询全部时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<Permission> lookup(PagingInfo pagingInfo) throws ServiceException {
        try {
            if (permissionListCache.exists()) {
                List<Permission> permissions = permissionListCache.get(pagingInfo);
                return PagingUtil.pagedData(pagingInfo, permissionListCache.size(), permissions);
            }
            List<Permission> lookup = permissionDao.lookup();
            permissionListCache.set(lookup, permissionListTimeout);
            return PagingUtil.pagedData(pagingInfo, lookup.size(), PagingUtil.subList(lookup, pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询全部时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<Permission> lookup(String preset, Object[] objs) throws ServiceException {
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
    public PagedData<Permission> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(pagingInfo, permissionDao.lookupCount(preset, objs), permissionDao.lookup(preset, objs, pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }
}


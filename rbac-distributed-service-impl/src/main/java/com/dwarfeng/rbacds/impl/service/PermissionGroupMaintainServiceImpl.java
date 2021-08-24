package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.cache.*;
import com.dwarfeng.rbacds.stack.dao.PermissionDao;
import com.dwarfeng.rbacds.stack.dao.PermissionGroupDao;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
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
import java.util.stream.Collectors;

@Service
public class PermissionGroupMaintainServiceImpl implements PermissionGroupMaintainService {

    @Autowired
    private PermissionGroupDao permissionGroupDao;
    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private PermissionGroupCache permissionGroupCache;
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
    @Value("${cache.timeout.entity.permission_group}")
    private long permissionGroupTimeout;

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
        return permissionGroupCache.exists(key) || permissionGroupDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public PermissionGroup get(StringIdKey key) throws ServiceException {
        try {
            return internalGet(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private PermissionGroup internalGet(StringIdKey key) throws Exception {
        if (permissionGroupCache.exists(key)) {
            return permissionGroupCache.get(key);
        } else {
            if (!permissionGroupDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            PermissionGroup permissionGroup = permissionGroupDao.get(key);
            permissionGroupCache.push(permissionGroup, permissionGroupTimeout);
            return permissionGroup;
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public StringIdKey insert(PermissionGroup permissionGroup) throws ServiceException {
        try {
            return internalInsert(permissionGroup);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private StringIdKey internalInsert(PermissionGroup permissionGroup) throws Exception {
        if (Objects.nonNull(permissionGroup.getKey()) && internalExists(permissionGroup.getKey())) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_EXISTED);
        }

        permissionListCache.clear();
        userPermissionCache.clear();
        rolePermissionCache.clear();
        permissionUserCache.clear();

        permissionGroupDao.insert(permissionGroup);
        permissionGroupCache.push(permissionGroup, permissionGroupTimeout);
        return permissionGroup.getKey();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void update(PermissionGroup permissionGroup) throws ServiceException {
        try {
            internalUpdate(permissionGroup);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("更新实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void internalUpdate(PermissionGroup permissionGroup) throws Exception {
        if (Objects.nonNull(permissionGroup.getKey()) && !internalExists(permissionGroup.getKey())) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }

        permissionListCache.clear();
        userPermissionCache.clear();
        rolePermissionCache.clear();
        permissionUserCache.clear();

        permissionGroupCache.push(permissionGroup, permissionGroupTimeout);
        permissionGroupDao.update(permissionGroup);
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

    @SuppressWarnings("DuplicatedCode")
    private void internalDelete(StringIdKey key) throws Exception {
        if (!internalExists(key)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }

        List<PermissionGroup> children = permissionGroupDao.lookup(
                PermissionGroupMaintainService.CHILD_FOR_PARENT, new Object[]{key}
        );
        children.forEach(c -> c.setParentKey(null));
        permissionGroupCache.batchDelete(children.stream().map(PermissionGroup::getKey).collect(Collectors.toList()));
        permissionGroupDao.batchUpdate(children);

        List<Permission> permissions = permissionDao.lookup(PermissionMaintainService.CHILD_FOR_GROUP, new Object[]{key});
        permissions.forEach(p -> p.setGroupKey(null));
        permissionCache.batchDelete(permissions.stream().map(Permission::getKey).collect(Collectors.toList()));
        permissionDao.batchUpdate(permissions);

        permissionListCache.clear();
        userPermissionCache.clear();
        rolePermissionCache.clear();
        permissionUserCache.clear();

        permissionGroupDao.delete(key);
        permissionGroupCache.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public PermissionGroup getIfExists(StringIdKey key) throws ServiceException {
        try {
            return internalExists(key) ? internalGet(key) : null;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public StringIdKey insertIfNotExists(PermissionGroup permissionGroup) throws ServiceException {
        try {
            if (Objects.isNull(permissionGroup.getKey()) || !internalExists(permissionGroup.getKey())) {
                return internalInsert(permissionGroup);
            }
            return null;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void updateIfExists(PermissionGroup permissionGroup) throws ServiceException {
        try {
            if (internalExists(permissionGroup.getKey())) {
                internalUpdate(permissionGroup);
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
    public StringIdKey insertOrUpdate(PermissionGroup permissionGroup) throws ServiceException {
        try {
            if (internalExists(permissionGroup.getKey())) {
                internalUpdate(permissionGroup);
                return null;
            } else {
                return internalInsert(permissionGroup);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入或更新实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<PermissionGroup> lookup() throws ServiceException {
        try {
            return PagingUtil.pagedData(permissionGroupDao.lookup());
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询全部时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<PermissionGroup> lookup(PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(permissionGroupDao.lookup(pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询全部时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<PermissionGroup> lookup(String preset, Object[] objs) throws ServiceException {
        try {
            return PagingUtil.pagedData(permissionGroupDao.lookup(preset, objs));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<PermissionGroup> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(
                    pagingInfo, permissionGroupDao.lookupCount(preset, objs),
                    permissionGroupDao.lookup(preset, objs, pagingInfo)
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }
}


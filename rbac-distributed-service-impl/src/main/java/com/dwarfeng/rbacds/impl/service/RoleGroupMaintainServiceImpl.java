package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.RoleGroup;
import com.dwarfeng.rbacds.stack.cache.*;
import com.dwarfeng.rbacds.stack.dao.RoleDao;
import com.dwarfeng.rbacds.stack.dao.RoleGroupDao;
import com.dwarfeng.rbacds.stack.service.RoleGroupMaintainService;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
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
public class RoleGroupMaintainServiceImpl implements RoleGroupMaintainService {

    @Autowired
    private RoleGroupDao roleGroupDao;
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleGroupCache roleGroupCache;
    @Autowired
    private RoleCache roleCache;
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
    @Value("${cache.timeout.entity.role_group}")
    private long roleGroupTimeout;

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
        return roleGroupCache.exists(key) || roleGroupDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public RoleGroup get(StringIdKey key) throws ServiceException {
        try {
            return internalGet(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private RoleGroup internalGet(StringIdKey key) throws Exception {
        if (roleGroupCache.exists(key)) {
            return roleGroupCache.get(key);
        } else {
            if (!roleGroupDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            RoleGroup roleGroup = roleGroupDao.get(key);
            roleGroupCache.push(roleGroup, roleGroupTimeout);
            return roleGroup;
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public StringIdKey insert(RoleGroup roleGroup) throws ServiceException {
        try {
            return internalInsert(roleGroup);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private StringIdKey internalInsert(RoleGroup roleGroup) throws Exception {
        if (Objects.nonNull(roleGroup.getKey()) && internalExists(roleGroup.getKey())) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_EXISTED);
        }

        permissionListCache.clear();
        userPermissionCache.clear();
        rolePermissionCache.clear();
        permissionUserCache.clear();

        roleGroupDao.insert(roleGroup);
        roleGroupCache.push(roleGroup, roleGroupTimeout);
        return roleGroup.getKey();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void update(RoleGroup roleGroup) throws ServiceException {
        try {
            internalUpdate(roleGroup);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("更新实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void internalUpdate(RoleGroup roleGroup) throws Exception {
        if (Objects.nonNull(roleGroup.getKey()) && !internalExists(roleGroup.getKey())) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }

        permissionListCache.clear();
        userPermissionCache.clear();
        rolePermissionCache.clear();
        permissionUserCache.clear();

        roleGroupCache.push(roleGroup, roleGroupTimeout);
        roleGroupDao.update(roleGroup);
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

        List<RoleGroup> children = roleGroupDao.lookup(
                RoleGroupMaintainService.CHILD_FOR_PARENT, new Object[]{key}
        );
        children.forEach(c -> c.setParentKey(null));
        roleGroupCache.batchDelete(children.stream().map(RoleGroup::getKey).collect(Collectors.toList()));
        roleGroupDao.batchUpdate(children);

        List<Role> roles = roleDao.lookup(RoleMaintainService.CHILD_FOR_GROUP, new Object[]{key});
        roles.forEach(r -> r.setGroupKey(null));
        roleCache.batchDelete(roles.stream().map(Role::getKey).collect(Collectors.toList()));
        roleDao.batchUpdate(roles);

        permissionListCache.clear();
        userPermissionCache.clear();
        rolePermissionCache.clear();
        permissionUserCache.clear();

        roleGroupDao.delete(key);
        roleGroupCache.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public RoleGroup getIfExists(StringIdKey key) throws ServiceException {
        try {
            return internalExists(key) ? internalGet(key) : null;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public StringIdKey insertIfNotExists(RoleGroup roleGroup) throws ServiceException {
        try {
            if (Objects.isNull(roleGroup.getKey()) || !internalExists(roleGroup.getKey())) {
                return internalInsert(roleGroup);
            }
            return null;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void updateIfExists(RoleGroup roleGroup) throws ServiceException {
        try {
            if (internalExists(roleGroup.getKey())) {
                internalUpdate(roleGroup);
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
    public StringIdKey insertOrUpdate(RoleGroup roleGroup) throws ServiceException {
        try {
            if (internalExists(roleGroup.getKey())) {
                internalUpdate(roleGroup);
                return null;
            } else {
                return internalInsert(roleGroup);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入或更新实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<RoleGroup> lookup() throws ServiceException {
        try {
            return PagingUtil.pagedData(roleGroupDao.lookup());
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询全部时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<RoleGroup> lookup(PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(roleGroupDao.lookup(pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询全部时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<RoleGroup> lookup(String preset, Object[] objs) throws ServiceException {
        try {
            return PagingUtil.pagedData(roleGroupDao.lookup(preset, objs));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<RoleGroup> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(
                    pagingInfo, roleGroupDao.lookupCount(preset, objs),
                    roleGroupDao.lookup(preset, objs, pagingInfo)
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }
}


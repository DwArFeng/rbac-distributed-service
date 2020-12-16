package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.cache.PermissionUserCache;
import com.dwarfeng.rbacds.stack.cache.RoleCache;
import com.dwarfeng.rbacds.stack.cache.UserPermissionCache;
import com.dwarfeng.rbacds.stack.dao.RoleDao;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
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
public class RoleMaintainServiceImpl implements RoleMaintainService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleCache roleCache;
    @Autowired
    private UserPermissionCache userPermissionCache;
    @Autowired
    private PermissionUserCache permissionUserCache;

    @Autowired
    private ServiceExceptionMapper sem;

    @Value("${cache.timeout.entity.role}")
    private long roleTimeout;

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
        return roleCache.exists(key) || roleDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public Role get(StringIdKey key) throws ServiceException {
        try {
            return internalGet(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private Role internalGet(StringIdKey key) throws Exception {
        if (roleCache.exists(key)) {
            return roleCache.get(key);
        } else {
            if (!roleDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            Role role = roleDao.get(key);
            roleCache.push(role, roleTimeout);
            return role;
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public StringIdKey insert(Role role) throws ServiceException {
        try {
            return internalInsert(role);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private StringIdKey internalInsert(Role role) throws Exception {
        if (Objects.nonNull(role.getKey()) && internalExists(role.getKey())) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_EXISTED);
        }

        userPermissionCache.clear();
        permissionUserCache.clear();

        roleDao.insert(role);
        roleCache.push(role, roleTimeout);
        return role.getKey();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void update(Role role) throws ServiceException {
        try {
            internalUpdate(role);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("更新实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private void internalUpdate(Role role) throws Exception {
        if (Objects.nonNull(role.getKey()) && !internalExists(role.getKey())) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }

        userPermissionCache.clear();
        permissionUserCache.clear();

        roleCache.push(role, roleTimeout);
        roleDao.update(role);
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

        userPermissionCache.clear();
        permissionUserCache.clear();

        roleDao.delete(key);
        roleCache.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public Role getIfExists(StringIdKey key) throws ServiceException {
        try {
            return internalExists(key) ? internalGet(key) : null;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public StringIdKey insertIfNotExists(Role role) throws ServiceException {
        try {
            if (Objects.isNull(role.getKey()) || !internalExists(role.getKey())) {
                return internalInsert(role);
            }
            return null;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void updateIfExists(Role role) throws ServiceException {
        try {
            if (internalExists(role.getKey())) {
                internalUpdate(role);
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
    public StringIdKey insertOrUpdate(Role role) throws ServiceException {
        try {
            if (internalExists(role.getKey())) {
                internalUpdate(role);
                return null;
            } else {
                return internalInsert(role);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("插入或更新实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public PagedData<Role> lookup(String preset, Object[] objs) throws ServiceException {
        try {
            return PagingUtil.pagedData(roleDao.lookup(preset, objs));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public PagedData<Role> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(pagingInfo, roleDao.lookupCount(preset, objs), roleDao.lookup(preset, objs, pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询实体时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public PagedData<Role> lookup() throws ServiceException {
        try {
            return PagingUtil.pagedData(roleDao.lookup());
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询全部时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public PagedData<Role> lookup(PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(pagingInfo, roleDao.lookupCount(), roleDao.lookup(pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询全部时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void addUserRelation(StringIdKey roleIdKey, StringIdKey userIdKey) throws ServiceException {
        try {
            userPermissionCache.clear();
            roleDao.addUserRelation(roleIdKey, userIdKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("添加角色与用户的关联时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void deleteUserRelation(StringIdKey roleIdKey, StringIdKey userIdKey) throws ServiceException {
        try {
            userPermissionCache.clear();
            permissionUserCache.clear();

            roleDao.deleteUserRelation(roleIdKey, userIdKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("删除角色与用户的关联时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchAddUserRelations(StringIdKey roleIdKey, List<StringIdKey> userIdKeys) throws ServiceException {
        try {
            userPermissionCache.clear();
            permissionUserCache.clear();

            roleDao.batchAddUserRelations(roleIdKey, userIdKeys);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("添加角色与用户的关联时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchDeleteUserRelations(StringIdKey roleIdKey, List<StringIdKey> userIdKeys) throws ServiceException {
        try {
            userPermissionCache.clear();
            permissionUserCache.clear();

            roleDao.batchDeleteUserRelations(roleIdKey, userIdKeys);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("删除角色与用户的关联时发生异常", LogLevel.WARN, sem, e);
        }
    }
}

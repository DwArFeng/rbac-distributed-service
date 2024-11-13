package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.cache.PermissionUserCache;
import com.dwarfeng.rbacds.stack.cache.UserCache;
import com.dwarfeng.rbacds.stack.cache.UserPermissionCache;
import com.dwarfeng.rbacds.stack.dao.UserDao;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserMaintainServiceImpl implements UserMaintainService {

    private final UserDao userDao;
    private final UserCache userCache;

    private final UserPermissionCache userPermissionCache;
    private final PermissionUserCache permissionUserCache;

    private final ServiceExceptionMapper sem;

    @Value("${cache.timeout.entity.user}")
    private long userTimeout;

    public UserMaintainServiceImpl(
            UserDao userDao, UserCache userCache,
            UserPermissionCache userPermissionCache,
            PermissionUserCache permissionUserCache,
            ServiceExceptionMapper sem
    ) {
        this.userDao = userDao;
        this.userCache = userCache;
        this.userPermissionCache = userPermissionCache;
        this.permissionUserCache = permissionUserCache;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(StringIdKey key) throws ServiceException {
        try {
            return internalExists(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断实体是否存在时发生异常", LogLevel.WARN, e, sem);
        }
    }

    private boolean internalExists(StringIdKey key) throws Exception {
        return userCache.exists(key) || userDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public User get(StringIdKey key) throws ServiceException {
        try {
            return internalGet(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取实体时发生异常", LogLevel.WARN, e, sem);
        }
    }

    private User internalGet(StringIdKey key) throws Exception {
        if (userCache.exists(key)) {
            return userCache.get(key);
        } else {
            if (!userDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            User user = userDao.get(key);
            userCache.push(user, userTimeout);
            return user;
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public StringIdKey insert(User user) throws ServiceException {
        try {
            return internalInsert(user);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("插入实体时发生异常", LogLevel.WARN, e, sem);
        }
    }

    private StringIdKey internalInsert(User user) throws Exception {
        if (Objects.nonNull(user.getKey()) && internalExists(user.getKey())) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_EXISTED);
        }

        permissionUserCache.clear();

        userDao.insert(user);
        userCache.push(user, userTimeout);
        return user.getKey();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void update(User user) throws ServiceException {
        try {
            internalUpdate(user);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("更新实体时发生异常", LogLevel.WARN, e, sem);
        }
    }

    private void internalUpdate(User user) throws Exception {
        if (Objects.nonNull(user.getKey()) && !internalExists(user.getKey())) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }
        userCache.push(user, userTimeout);
        userDao.update(user);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(StringIdKey key) throws ServiceException {
        try {
            internalDelete(key);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("删除实体时发生异常", LogLevel.WARN, e, sem);
        }
    }

    private void internalDelete(StringIdKey key) throws Exception {
        if (!internalExists(key)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }

        permissionUserCache.clear();

        userDao.delete(key);
        userCache.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public User getIfExists(StringIdKey key) throws ServiceException {
        try {
            return internalExists(key) ? internalGet(key) : null;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取实体时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public StringIdKey insertIfNotExists(User user) throws ServiceException {
        try {
            if (Objects.isNull(user.getKey()) || !internalExists(user.getKey())) {
                return internalInsert(user);
            }
            return null;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("插入实体时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void updateIfExists(User user) throws ServiceException {
        try {
            if (internalExists(user.getKey())) {
                internalUpdate(user);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("更新实体时发生异常", LogLevel.WARN, e, sem);
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
            throw ServiceExceptionHelper.logParse("删除实体时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public StringIdKey insertOrUpdate(User user) throws ServiceException {
        try {
            if (internalExists(user.getKey())) {
                internalUpdate(user);
                return null;
            } else {
                return internalInsert(user);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("插入或更新实体时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<User> lookup(String preset, Object[] objs) throws ServiceException {
        try {
            return PagingUtil.pagedData(userDao.lookup(preset, objs));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询实体时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<User> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(pagingInfo, userDao.lookupCount(preset, objs), userDao.lookup(preset, objs, pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询实体时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<User> lookup() throws ServiceException {
        try {
            return PagingUtil.pagedData(userDao.lookup());
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询全部时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public PagedData<User> lookup(PagingInfo pagingInfo) throws ServiceException {
        try {
            return PagingUtil.pagedData(pagingInfo, userDao.lookupCount(), userDao.lookup(pagingInfo));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询全部时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void addRoleRelation(StringIdKey userIdKey, StringIdKey roleIdKey) throws ServiceException {
        try {
            userPermissionCache.clear();
            permissionUserCache.clear();

            userDao.addRoleRelation(userIdKey, roleIdKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("添加用户与角色的关联时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void deleteRoleRelation(StringIdKey userIdKey, StringIdKey roleIdKey) throws ServiceException {
        try {
            userPermissionCache.clear();
            permissionUserCache.clear();

            userDao.deleteRoleRelation(userIdKey, roleIdKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("删除用户与角色的关联时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchAddRoleRelations(StringIdKey userIdKey, @SkipRecord List<StringIdKey> roleIdKeys) throws ServiceException {
        try {
            userPermissionCache.clear();
            permissionUserCache.clear();

            userDao.batchAddRoleRelations(userIdKey, roleIdKeys);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("添加用户与角色的关联时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchDeleteRoleRelations(StringIdKey userIdKey, @SkipRecord List<StringIdKey> roleIdKeys) throws ServiceException {
        try {
            userPermissionCache.clear();
            permissionUserCache.clear();

            userDao.batchDeleteRoleRelations(userIdKey, roleIdKeys);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("删除用户与角色的关联时发生异常", LogLevel.WARN, e, sem);
        }
    }
}

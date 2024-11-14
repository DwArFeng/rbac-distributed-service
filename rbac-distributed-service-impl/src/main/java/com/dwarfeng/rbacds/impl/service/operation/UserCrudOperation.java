package com.dwarfeng.rbacds.impl.service.operation;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.cache.PermissionUserCache;
import com.dwarfeng.rbacds.stack.cache.RoleCache;
import com.dwarfeng.rbacds.stack.cache.UserCache;
import com.dwarfeng.rbacds.stack.cache.UserPermissionCache;
import com.dwarfeng.rbacds.stack.dao.RoleDao;
import com.dwarfeng.rbacds.stack.dao.UserDao;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserCrudOperation implements BatchCrudOperation<StringIdKey, User> {

    private final UserDao userDao;
    private final UserCache userCache;

    private final RoleDao roleDao;
    private final RoleCache roleCache;

    private final UserPermissionCache userPermissionCache;
    private final PermissionUserCache permissionUserCache;

    @Value("${cache.timeout.entity.user}")
    private long userTimeout;

    public UserCrudOperation(
            UserDao userDao,
            UserCache userCache,
            RoleDao roleDao,
            RoleCache roleCache,
            UserPermissionCache userPermissionCache,
            PermissionUserCache permissionUserCache
    ) {
        this.userDao = userDao;
        this.userCache = userCache;
        this.roleDao = roleDao;
        this.roleCache = roleCache;
        this.userPermissionCache = userPermissionCache;
        this.permissionUserCache = permissionUserCache;
    }

    @Override
    public boolean exists(StringIdKey key) throws Exception {
        return userCache.exists(key) || userDao.exists(key);
    }

    @Override
    public User get(StringIdKey key) throws Exception {
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
    public StringIdKey insert(User user) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 插入用户自身。
        userDao.insert(user);
        userCache.push(user, userTimeout);
        return user.getKey();
    }

    @Override
    public void update(User user) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 更新用户自身。
        userCache.push(user, userTimeout);
        userDao.update(user);
    }

    @Override
    public void delete(StringIdKey key) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        delete0(key);
    }

    private void delete0(StringIdKey key) throws DaoException, CacheException {
        // 查找并清除所有相关的角色的关联。
        List<StringIdKey> roleKeys = roleDao.lookup(
                RoleMaintainService.ROLE_FOR_USER, new Object[]{key}
        ).stream().map(Role::getKey).collect(Collectors.toList());
        roleCache.batchDelete(roleKeys);
        userDao.batchDeleteRoleRelations(key, roleKeys);

        // 删除用户自身。
        userCache.delete(key);
        userDao.delete(key);
    }

    @Override
    public boolean allExists(List<StringIdKey> keys) throws Exception {
        return userCache.allExists(keys) || userDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<StringIdKey> keys) throws Exception {
        return userCache.nonExists(keys) && userDao.nonExists(keys);
    }

    @Override
    public List<User> batchGet(List<StringIdKey> keys) throws Exception {
        if (userCache.allExists(keys)) {
            return userCache.batchGet(keys);
        } else {
            if (!userDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<User> users = userDao.batchGet(keys);
            userCache.batchPush(users, userTimeout);
            return users;
        }
    }

    @Override
    public List<StringIdKey> batchInsert(List<User> users) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 插入用户自身。
        userCache.batchPush(users, userTimeout);
        return userDao.batchInsert(users);
    }

    @Override
    public void batchUpdate(List<User> users) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 更新用户自身。
        userCache.batchPush(users, userTimeout);
        userDao.batchUpdate(users);
    }

    @Override
    public void batchDelete(List<StringIdKey> keys) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 删除用户自身。
        for (StringIdKey key : keys) {
            delete0(key);
        }
    }
}

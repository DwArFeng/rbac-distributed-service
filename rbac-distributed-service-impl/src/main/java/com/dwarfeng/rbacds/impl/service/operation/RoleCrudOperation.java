package com.dwarfeng.rbacds.impl.service.operation;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.cache.*;
import com.dwarfeng.rbacds.stack.dao.PexpDao;
import com.dwarfeng.rbacds.stack.dao.RoleDao;
import com.dwarfeng.rbacds.stack.dao.UserDao;
import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleCrudOperation implements BatchCrudOperation<StringIdKey, Role> {

    private final RoleDao roleDao;
    private final RoleCache roleCache;

    private final UserDao userDao;
    private final UserCache userCache;

    private final PexpDao pexpDao;
    private final PexpCache pexpCache;

    private final UserPermissionCache userPermissionCache;
    private final RolePermissionCache rolePermissionCache;
    private final PermissionUserCache permissionUserCache;

    @Value("${cache.timeout.entity.role}")
    private long roleTimeout;

    public RoleCrudOperation(
            RoleDao roleDao,
            RoleCache roleCache,
            UserDao userDao,
            UserCache userCache,
            PexpDao pexpDao,
            PexpCache pexpCache,
            UserPermissionCache userPermissionCache,
            RolePermissionCache rolePermissionCache,
            PermissionUserCache permissionUserCache
    ) {
        this.roleDao = roleDao;
        this.roleCache = roleCache;
        this.userDao = userDao;
        this.userCache = userCache;
        this.pexpDao = pexpDao;
        this.pexpCache = pexpCache;
        this.userPermissionCache = userPermissionCache;
        this.rolePermissionCache = rolePermissionCache;
        this.permissionUserCache = permissionUserCache;
    }

    @Override
    public boolean exists(StringIdKey key) throws Exception {
        return roleCache.exists(key) || roleDao.exists(key);
    }

    @Override
    public Role get(StringIdKey key) throws Exception {
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
    public StringIdKey insert(Role role) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 插入角色自身。
        roleDao.insert(role);
        roleCache.push(role, roleTimeout);
        return role.getKey();
    }

    @Override
    public void update(Role role) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 更新角色自身。
        roleCache.push(role, roleTimeout);
        roleDao.update(role);
    }

    @Override
    public void delete(StringIdKey key) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        delete0(key);
    }

    private void delete0(StringIdKey key) throws DaoException, CacheException {
        // 删除与角色相关的用户。
        List<StringIdKey> userKeys = userDao.lookup(
                UserMaintainService.CHILD_FOR_ROLE, new Object[]{key}
        ).stream().map(User::getKey).collect(Collectors.toList());
        userCache.batchDelete(userKeys);
        roleDao.batchDeleteUserRelations(key, userKeys);

        // 删除与角色相关的权限表达式。
        List<LongIdKey> pexpKeys = pexpDao.lookup(
                PexpMaintainService.PEXP_FOR_ROLE, new Object[]{key}
        ).stream().map(Pexp::getKey).collect(Collectors.toList());
        pexpCache.batchDelete(pexpKeys);
        pexpDao.batchDelete(pexpKeys);

        // 删除角色自身。
        roleCache.delete(key);
        roleDao.delete(key);
    }

    @Override
    public boolean allExists(List<StringIdKey> keys) throws Exception {
        return roleCache.allExists(keys) || roleDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<StringIdKey> keys) throws Exception {
        return roleCache.nonExists(keys) && roleDao.nonExists(keys);
    }

    @Override
    public List<Role> batchGet(List<StringIdKey> keys) throws Exception {
        if (roleCache.allExists(keys)) {
            return roleCache.batchGet(keys);
        } else {
            if (!roleDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<Role> roles = roleDao.batchGet(keys);
            roleCache.batchPush(roles, roleTimeout);
            return roles;
        }
    }

    @Override
    public List<StringIdKey> batchInsert(List<Role> roles) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 插入角色自身。
        roleCache.batchPush(roles, roleTimeout);
        return roleDao.batchInsert(roles);
    }

    @Override
    public void batchUpdate(List<Role> roles) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 更新角色自身。
        roleCache.batchPush(roles, roleTimeout);
        roleDao.batchUpdate(roles);
    }

    @Override
    public void batchDelete(List<StringIdKey> keys) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 删除角色自身。
        for (StringIdKey key : keys) {
            delete0(key);
        }
    }
}

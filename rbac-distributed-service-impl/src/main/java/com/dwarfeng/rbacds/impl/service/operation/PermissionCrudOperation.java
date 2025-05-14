package com.dwarfeng.rbacds.impl.service.operation;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionMeta;
import com.dwarfeng.rbacds.stack.bean.key.PermissionMetaKey;
import com.dwarfeng.rbacds.stack.cache.*;
import com.dwarfeng.rbacds.stack.dao.PermissionDao;
import com.dwarfeng.rbacds.stack.dao.PermissionMetaDao;
import com.dwarfeng.rbacds.stack.service.PermissionMetaMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionCrudOperation implements BatchCrudOperation<StringIdKey, Permission> {

    private final PermissionDao permissionDao;
    private final PermissionCache permissionCache;

    private final PermissionMetaDao permissionMetaDao;
    private final PermissionMetaCache permissionMetaCache;

    private final UserPermissionCache userPermissionCache;
    private final RolePermissionCache rolePermissionCache;
    private final PermissionUserCache permissionUserCache;

    @Value("${cache.timeout.entity.permission}")
    private long permissionTimeout;

    public PermissionCrudOperation(
            PermissionDao permissionDao,
            PermissionCache permissionCache,
            PermissionMetaDao permissionMetaDao,
            PermissionMetaCache permissionMetaCache,
            UserPermissionCache userPermissionCache,
            RolePermissionCache rolePermissionCache,
            PermissionUserCache permissionUserCache
    ) {
        this.permissionDao = permissionDao;
        this.permissionCache = permissionCache;
        this.permissionMetaDao = permissionMetaDao;
        this.permissionMetaCache = permissionMetaCache;
        this.userPermissionCache = userPermissionCache;
        this.rolePermissionCache = rolePermissionCache;
        this.permissionUserCache = permissionUserCache;
    }

    @Override
    public boolean exists(StringIdKey key) throws Exception {
        return permissionCache.exists(key) || permissionDao.exists(key);
    }

    @Override
    public Permission get(StringIdKey key) throws Exception {
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
    public StringIdKey insert(Permission permission) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 插入权限自身。
        permissionDao.insert(permission);
        permissionCache.push(permission, permissionTimeout);
        return permission.getKey();
    }

    @Override
    public void update(Permission permission) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 更新权限自身。
        permissionCache.push(permission, permissionTimeout);
        permissionDao.update(permission);
    }

    @Override
    public void delete(StringIdKey key) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 删除与权限相关的元数据。
        List<PermissionMetaKey> permissionMetaKeys = permissionMetaDao.lookup(
                PermissionMetaMaintainService.CHILD_FOR_PERMISSION, new Object[]{key}
        ).stream().map(PermissionMeta::getKey).collect(Collectors.toList());
        permissionMetaDao.batchDelete(permissionMetaKeys);
        permissionMetaCache.batchDelete(permissionMetaKeys);

        // 删除权限自身。
        permissionCache.delete(key);
        permissionDao.delete(key);
    }

    @Override
    public boolean allExists(List<StringIdKey> keys) throws Exception {
        return permissionCache.allExists(keys) || permissionDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<StringIdKey> keys) throws Exception {
        return permissionCache.nonExists(keys) && permissionDao.nonExists(keys);
    }

    @Override
    public List<Permission> batchGet(List<StringIdKey> keys) throws Exception {
        if (permissionCache.allExists(keys)) {
            return permissionCache.batchGet(keys);
        } else {
            if (!permissionDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<Permission> permissions = permissionDao.batchGet(keys);
            permissionCache.batchPush(permissions, permissionTimeout);
            return permissions;
        }
    }

    @Override
    public List<StringIdKey> batchInsert(List<Permission> permissions) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 插入权限自身。
        permissionCache.batchPush(permissions, permissionTimeout);
        return permissionDao.batchInsert(permissions);
    }

    @Override
    public void batchUpdate(List<Permission> permissions) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 更新权限自身。
        permissionCache.batchPush(permissions, permissionTimeout);
        permissionDao.batchUpdate(permissions);
    }

    @Override
    public void batchDelete(List<StringIdKey> keys) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 删除权限自身。
        permissionCache.batchDelete(keys);
        permissionDao.batchDelete(keys);
    }
}

package com.dwarfeng.rbacds.impl.service.operation;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.cache.PermissionCache;
import com.dwarfeng.rbacds.stack.dao.PermissionDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionCrudOperation implements BatchCrudOperation<PermissionKey, Permission> {

    private final PermissionDao permissionDao;
    private final PermissionCache permissionCache;

    @Value("${cache.timeout.entity.permission}")
    private long permissionTimeout;

    public PermissionCrudOperation(
            PermissionDao permissionDao,
            PermissionCache permissionCache
    ) {
        this.permissionDao = permissionDao;
        this.permissionCache = permissionCache;
    }

    @Override
    public boolean exists(PermissionKey key) throws Exception {
        return permissionCache.exists(key) || permissionDao.exists(key);
    }

    @Override
    public Permission get(PermissionKey key) throws Exception {
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
    public PermissionKey insert(Permission permission) throws Exception {
        permissionDao.insert(permission);
        permissionCache.push(permission, permissionTimeout);
        return permission.getKey();
    }

    @Override
    public void update(Permission permission) throws Exception {
        permissionCache.push(permission, permissionTimeout);
        permissionDao.update(permission);
    }

    @Override
    public void delete(PermissionKey key) throws Exception {
        // 删除权限自身。
        permissionCache.delete(key);
        permissionDao.delete(key);
    }

    @Override
    public boolean allExists(List<PermissionKey> keys) throws Exception {
        return permissionCache.allExists(keys) || permissionDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<PermissionKey> keys) throws Exception {
        return permissionCache.nonExists(keys) && permissionDao.nonExists(keys);
    }

    @Override
    public List<Permission> batchGet(List<PermissionKey> keys) throws Exception {
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
    public List<PermissionKey> batchInsert(List<Permission> permissions) throws Exception {
        permissionCache.batchPush(permissions, permissionTimeout);
        return permissionDao.batchInsert(permissions);
    }

    @Override
    public void batchUpdate(List<Permission> permissions) throws Exception {
        permissionCache.batchPush(permissions, permissionTimeout);
        permissionDao.batchUpdate(permissions);
    }

    @Override
    public void batchDelete(List<PermissionKey> keys) throws Exception {
        for (PermissionKey key : keys) {
            delete(key);
        }
    }
}

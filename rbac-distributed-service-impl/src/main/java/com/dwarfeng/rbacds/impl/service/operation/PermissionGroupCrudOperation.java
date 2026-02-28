package com.dwarfeng.rbacds.impl.service.operation;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.cache.PermissionCache;
import com.dwarfeng.rbacds.stack.cache.PermissionGroupCache;
import com.dwarfeng.rbacds.stack.dao.PermissionDao;
import com.dwarfeng.rbacds.stack.dao.PermissionGroupDao;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionGroupCrudOperation implements BatchCrudOperation<PermissionGroupKey, PermissionGroup> {

    private final PermissionGroupDao permissionGroupDao;
    private final PermissionGroupCache permissionGroupCache;

    private final PermissionDao permissionDao;
    private final PermissionCache permissionCache;

    @Value("${cache.timeout.entity.permission_group}")
    private long permissionGroupTimeout;

    public PermissionGroupCrudOperation(
            PermissionGroupDao permissionGroupDao,
            PermissionGroupCache permissionGroupCache,
            PermissionDao permissionDao,
            PermissionCache permissionCache
    ) {
        this.permissionGroupDao = permissionGroupDao;
        this.permissionGroupCache = permissionGroupCache;
        this.permissionDao = permissionDao;
        this.permissionCache = permissionCache;
    }

    @Override
    public boolean exists(PermissionGroupKey key) throws Exception {
        return permissionGroupCache.exists(key) || permissionGroupDao.exists(key);
    }

    @Override
    public PermissionGroup get(PermissionGroupKey key) throws Exception {
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
    public PermissionGroupKey insert(PermissionGroup permissionGroup) throws Exception {
        permissionGroupDao.insert(permissionGroup);
        permissionGroupCache.push(permissionGroup, permissionGroupTimeout);
        return permissionGroup.getKey();
    }

    @Override
    public void update(PermissionGroup permissionGroup) throws Exception {
        permissionGroupCache.push(permissionGroup, permissionGroupTimeout);
        permissionGroupDao.update(permissionGroup);
    }

    @Override
    public void delete(PermissionGroupKey key) throws Exception {
        // 清除子权限组的关联。
        List<PermissionGroup> childPermissionGroups = permissionGroupDao.lookup(
                PermissionGroupMaintainService.CHILD_FOR_PARENT, new Object[]{key}
        );
        childPermissionGroups.forEach(c -> c.setParentKey(null));
        permissionGroupCache.batchDelete(
                childPermissionGroups.stream().map(PermissionGroup::getKey).collect(Collectors.toList())
        );
        permissionGroupDao.batchUpdate(childPermissionGroups);

        // 清除权限的关联。
        List<Permission> permissions = permissionDao.lookup(
                PermissionMaintainService.CHILD_FOR_GROUP, new Object[]{key}
        );
        permissions.forEach(p -> p.setGroupKey(null));
        permissionCache.batchDelete(permissions.stream().map(Permission::getKey).collect(Collectors.toList()));
        permissionDao.batchUpdate(permissions);

        // 删除权限组自身。
        permissionGroupCache.delete(key);
        permissionGroupDao.delete(key);
    }

    @Override
    public boolean allExists(List<PermissionGroupKey> keys) throws Exception {
        return permissionGroupCache.allExists(keys) || permissionGroupDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<PermissionGroupKey> keys) throws Exception {
        return permissionGroupCache.nonExists(keys) && permissionGroupDao.nonExists(keys);
    }

    @Override
    public List<PermissionGroup> batchGet(List<PermissionGroupKey> keys) throws Exception {
        if (permissionGroupCache.allExists(keys)) {
            return permissionGroupCache.batchGet(keys);
        } else {
            if (!permissionGroupDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<PermissionGroup> permissionGroups = permissionGroupDao.batchGet(keys);
            permissionGroupCache.batchPush(permissionGroups, permissionGroupTimeout);
            return permissionGroups;
        }
    }

    @Override
    public List<PermissionGroupKey> batchInsert(List<PermissionGroup> permissionGroups) throws Exception {
        permissionGroupCache.batchPush(permissionGroups, permissionGroupTimeout);
        return permissionGroupDao.batchInsert(permissionGroups);
    }

    @Override
    public void batchUpdate(List<PermissionGroup> permissionGroups) throws Exception {
        permissionGroupCache.batchPush(permissionGroups, permissionGroupTimeout);
        permissionGroupDao.batchUpdate(permissionGroups);
    }

    @Override
    public void batchDelete(List<PermissionGroupKey> keys) throws Exception {
        for (PermissionGroupKey key : keys) {
            delete(key);
        }
    }
}

package com.dwarfeng.rbacds.impl.service.operation;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionMeta;
import com.dwarfeng.rbacds.stack.bean.key.PermissionMetaKey;
import com.dwarfeng.rbacds.stack.cache.*;
import com.dwarfeng.rbacds.stack.dao.PermissionMetaDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionMetaCrudOperation implements BatchCrudOperation<PermissionMetaKey, PermissionMeta> {

    private final PermissionMetaDao permissionMetaDao;
    private final PermissionMetaCache permissionMetaCache;

    private final PermissionListCache permissionListCache;
    private final UserPermissionCache userPermissionCache;
    private final RolePermissionCache rolePermissionCache;
    private final PermissionUserCache permissionUserCache;

    @Value("${cache.timeout.entity.permission_meta}")
    private long permissionMetaTimeout;

    public PermissionMetaCrudOperation(
            PermissionMetaDao permissionMetaDao,
            PermissionMetaCache permissionMetaCache,
            PermissionListCache permissionListCache,
            UserPermissionCache userPermissionCache,
            RolePermissionCache rolePermissionCache,
            PermissionUserCache permissionUserCache
    ) {
        this.permissionMetaDao = permissionMetaDao;
        this.permissionMetaCache = permissionMetaCache;
        this.permissionListCache = permissionListCache;
        this.userPermissionCache = userPermissionCache;
        this.rolePermissionCache = rolePermissionCache;
        this.permissionUserCache = permissionUserCache;
    }

    @Override
    public boolean exists(PermissionMetaKey key) throws Exception {
        return permissionMetaCache.exists(key) || permissionMetaDao.exists(key);
    }

    @Override
    public PermissionMeta get(PermissionMetaKey key) throws Exception {
        if (permissionMetaCache.exists(key)) {
            return permissionMetaCache.get(key);
        } else {
            if (!permissionMetaDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            PermissionMeta permissionMeta = permissionMetaDao.get(key);
            permissionMetaCache.push(permissionMeta, permissionMetaTimeout);
            return permissionMeta;
        }
    }

    @Override
    public PermissionMetaKey insert(PermissionMeta permissionMeta) throws Exception {
        // 清空权限列表缓存。
        permissionListCache.clear();
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 插入权限元数据自身。
        permissionMetaDao.insert(permissionMeta);
        permissionMetaCache.push(permissionMeta, permissionMetaTimeout);
        return permissionMeta.getKey();
    }

    @Override
    public void update(PermissionMeta permissionMeta) throws Exception {
        // 清空权限列表缓存。
        permissionListCache.clear();
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 更新权限元数据自身。
        permissionMetaCache.push(permissionMeta, permissionMetaTimeout);
        permissionMetaDao.update(permissionMeta);
    }

    @Override
    public void delete(PermissionMetaKey key) throws Exception {
        // 清空权限列表缓存。
        permissionListCache.clear();
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 删除权限元数据自身。
        delete0(key);
    }

    private void delete0(PermissionMetaKey key) throws DaoException, CacheException {
        // 删除权限元数据自身。
        permissionMetaCache.delete(key);
        permissionMetaDao.delete(key);
    }

    @Override
    public boolean allExists(List<PermissionMetaKey> keys) throws Exception {
        return permissionMetaCache.allExists(keys) || permissionMetaDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<PermissionMetaKey> keys) throws Exception {
        return permissionMetaCache.nonExists(keys) && permissionMetaDao.nonExists(keys);
    }

    @Override
    public List<PermissionMeta> batchGet(List<PermissionMetaKey> keys) throws Exception {
        if (permissionMetaCache.allExists(keys)) {
            return permissionMetaCache.batchGet(keys);
        } else {
            if (!permissionMetaDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<PermissionMeta> permissionMetas = permissionMetaDao.batchGet(keys);
            permissionMetaCache.batchPush(permissionMetas, permissionMetaTimeout);
            return permissionMetas;
        }
    }

    @Override
    public List<PermissionMetaKey> batchInsert(List<PermissionMeta> permissionMetas) throws Exception {
        // 清空权限列表缓存。
        permissionListCache.clear();
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 插入权限元数据自身。
        permissionMetaCache.batchPush(permissionMetas, permissionMetaTimeout);
        return permissionMetaDao.batchInsert(permissionMetas);
    }

    @Override
    public void batchUpdate(List<PermissionMeta> permissionMetas) throws Exception {
        // 清空权限列表缓存。
        permissionListCache.clear();
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 更新权限元数据自身。
        permissionMetaCache.batchPush(permissionMetas, permissionMetaTimeout);
        permissionMetaDao.batchUpdate(permissionMetas);
    }

    @Override
    public void batchDelete(List<PermissionMetaKey> keys) throws Exception {
        // 清空权限列表缓存。
        permissionListCache.clear();
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 删除权限元数据自身。
        for (PermissionMetaKey key : keys) {
            delete0(key);
        }
    }
}

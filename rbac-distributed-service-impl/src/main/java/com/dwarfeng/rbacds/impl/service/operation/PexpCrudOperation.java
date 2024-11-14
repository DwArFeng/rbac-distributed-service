package com.dwarfeng.rbacds.impl.service.operation;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.cache.PermissionUserCache;
import com.dwarfeng.rbacds.stack.cache.PexpCache;
import com.dwarfeng.rbacds.stack.cache.RolePermissionCache;
import com.dwarfeng.rbacds.stack.cache.UserPermissionCache;
import com.dwarfeng.rbacds.stack.dao.PexpDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PexpCrudOperation implements BatchCrudOperation<LongIdKey, Pexp> {

    private final PexpDao pexpDao;
    private final PexpCache pexpCache;

    private final UserPermissionCache userPermissionCache;
    private final RolePermissionCache rolePermissionCache;
    private final PermissionUserCache permissionUserCache;

    @Value("${cache.timeout.entity.pexp}")
    private long pexpTimeout;

    public PexpCrudOperation(
            PexpDao pexpDao,
            PexpCache pexpCache,
            UserPermissionCache userPermissionCache,
            RolePermissionCache rolePermissionCache,
            PermissionUserCache permissionUserCache
    ) {
        this.pexpDao = pexpDao;
        this.pexpCache = pexpCache;
        this.userPermissionCache = userPermissionCache;
        this.rolePermissionCache = rolePermissionCache;
        this.permissionUserCache = permissionUserCache;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return pexpCache.exists(key) || pexpDao.exists(key);
    }

    @Override
    public Pexp get(LongIdKey key) throws Exception {
        if (pexpCache.exists(key)) {
            return pexpCache.get(key);
        } else {
            if (!pexpDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            Pexp permission = pexpDao.get(key);
            pexpCache.push(permission, pexpTimeout);
            return permission;
        }
    }

    @Override
    public LongIdKey insert(Pexp pexp) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 插入权限表达式自身。
        pexpDao.insert(pexp);
        pexpCache.push(pexp, pexpTimeout);
        return pexp.getKey();
    }

    @Override
    public void update(Pexp pexp) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 更新权限表达式自身。
        pexpCache.push(pexp, pexpTimeout);
        pexpDao.update(pexp);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 删除权限表达式自身。
        pexpCache.delete(key);
        pexpDao.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return pexpCache.allExists(keys) || pexpDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return pexpCache.nonExists(keys) && pexpDao.nonExists(keys);
    }

    @Override
    public List<Pexp> batchGet(List<LongIdKey> keys) throws Exception {
        if (pexpCache.allExists(keys)) {
            return pexpCache.batchGet(keys);
        } else {
            if (!pexpDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<Pexp> pexps = pexpDao.batchGet(keys);
            pexpCache.batchPush(pexps, pexpTimeout);
            return pexps;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<Pexp> pexps) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 插入权限表达式自身。
        pexpCache.batchPush(pexps, pexpTimeout);
        return pexpDao.batchInsert(pexps);
    }

    @Override
    public void batchUpdate(List<Pexp> pexps) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 更新权限表达式自身。
        pexpCache.batchPush(pexps, pexpTimeout);
        pexpDao.batchUpdate(pexps);
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        // 清空用户权限缓存。
        userPermissionCache.clear();
        // 清空角色权限缓存。
        rolePermissionCache.clear();
        // 清空权限用户缓存。
        permissionUserCache.clear();

        // 删除权限表达式自身。
        pexpCache.batchDelete(keys);
        pexpDao.batchDelete(keys);
    }
}

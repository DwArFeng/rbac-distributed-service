package com.dwarfeng.rbacds.impl.service.operation;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.RoleUserRelation;
import com.dwarfeng.rbacds.stack.bean.key.RoleUserRelationKey;
import com.dwarfeng.rbacds.stack.cache.PexpCache;
import com.dwarfeng.rbacds.stack.cache.RoleCache;
import com.dwarfeng.rbacds.stack.cache.RoleUserRelationCache;
import com.dwarfeng.rbacds.stack.dao.PexpDao;
import com.dwarfeng.rbacds.stack.dao.RoleDao;
import com.dwarfeng.rbacds.stack.dao.RoleUserRelationDao;
import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.rbacds.stack.service.RoleUserRelationMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleCrudOperation implements BatchCrudOperation<StringIdKey, Role> {

    private final RoleDao roleDao;
    private final RoleCache roleCache;

    private final PexpDao pexpDao;
    private final PexpCache pexpCache;

    private final RoleUserRelationDao roleUserRelationDao;
    private final RoleUserRelationCache roleUserRelationCache;

    @Value("${cache.timeout.entity.role}")
    private long roleTimeout;

    public RoleCrudOperation(
            RoleDao roleDao,
            RoleCache roleCache,
            PexpDao pexpDao,
            PexpCache pexpCache,
            RoleUserRelationDao roleUserRelationDao,
            RoleUserRelationCache roleUserRelationCache
    ) {
        this.roleDao = roleDao;
        this.roleCache = roleCache;
        this.pexpDao = pexpDao;
        this.pexpCache = pexpCache;
        this.roleUserRelationDao = roleUserRelationDao;
        this.roleUserRelationCache = roleUserRelationCache;
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
        roleDao.insert(role);
        roleCache.push(role, roleTimeout);
        return role.getKey();
    }

    @Override
    public void update(Role role) throws Exception {
        roleCache.push(role, roleTimeout);
        roleDao.update(role);
    }

    @Override
    public void delete(StringIdKey key) throws Exception {
        // 删除与角色相关的权限表达式。
        List<LongIdKey> pexpKeys = pexpDao.lookup(
                PexpMaintainService.PEXP_FOR_ROLE, new Object[]{key}
        ).stream().map(Pexp::getKey).collect(Collectors.toList());
        pexpCache.batchDelete(pexpKeys);
        pexpDao.batchDelete(pexpKeys);

        // 删除与角色相关的角色用户关系。
        List<RoleUserRelationKey> roleUserRelationKeys = roleUserRelationDao.lookup(
                RoleUserRelationMaintainService.CHILD_FOR_ROLE, new Object[]{key}
        ).stream().map(RoleUserRelation::getKey).collect(Collectors.toList());
        roleUserRelationCache.batchDelete(roleUserRelationKeys);
        roleUserRelationDao.batchDelete(roleUserRelationKeys);

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
        roleCache.batchPush(roles, roleTimeout);
        return roleDao.batchInsert(roles);
    }

    @Override
    public void batchUpdate(List<Role> roles) throws Exception {
        roleCache.batchPush(roles, roleTimeout);
        roleDao.batchUpdate(roles);
    }

    @Override
    public void batchDelete(List<StringIdKey> keys) throws Exception {
        for (StringIdKey key : keys) {
            delete(key);
        }
    }
}

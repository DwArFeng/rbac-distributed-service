package com.dwarfeng.rbacds.impl.service.operation;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Scope;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.rbacds.stack.cache.PermissionCache;
import com.dwarfeng.rbacds.stack.cache.PermissionGroupCache;
import com.dwarfeng.rbacds.stack.cache.PexpCache;
import com.dwarfeng.rbacds.stack.cache.ScopeCache;
import com.dwarfeng.rbacds.stack.dao.PermissionDao;
import com.dwarfeng.rbacds.stack.dao.PermissionGroupDao;
import com.dwarfeng.rbacds.stack.dao.PexpDao;
import com.dwarfeng.rbacds.stack.dao.ScopeDao;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ScopeCrudOperation implements BatchCrudOperation<StringIdKey, Scope> {

    private final ScopeDao scopeDao;
    private final ScopeCache scopeCache;

    private final PermissionDao permissionDao;
    private final PermissionCache permissionCache;

    private final PermissionGroupDao permissionGroupDao;
    private final PermissionGroupCache permissionGroupCache;

    private final PexpDao pexpDao;
    private final PexpCache pexpCache;

    @Value("${cache.timeout.entity.scope}")
    private long scopeTimeout;

    public ScopeCrudOperation(
            ScopeDao scopeDao,
            ScopeCache scopeCache,
            PermissionDao permissionDao,
            PermissionCache permissionCache,
            PermissionGroupDao permissionGroupDao,
            PermissionGroupCache permissionGroupCache,
            PexpDao pexpDao,
            PexpCache pexpCache
    ) {
        this.scopeDao = scopeDao;
        this.scopeCache = scopeCache;
        this.permissionDao = permissionDao;
        this.permissionCache = permissionCache;
        this.permissionGroupDao = permissionGroupDao;
        this.permissionGroupCache = permissionGroupCache;
        this.pexpDao = pexpDao;
        this.pexpCache = pexpCache;
    }

    @Override
    public boolean exists(StringIdKey key) throws Exception {
        return scopeCache.exists(key) || scopeDao.exists(key);
    }

    @Override
    public Scope get(StringIdKey key) throws Exception {
        if (scopeCache.exists(key)) {
            return scopeCache.get(key);
        } else {
            if (!scopeDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            Scope scope = scopeDao.get(key);
            scopeCache.push(scope, scopeTimeout);
            return scope;
        }
    }

    @Override
    public StringIdKey insert(Scope scope) throws Exception {
        scopeCache.push(scope, scopeTimeout);
        return scopeDao.insert(scope);
    }

    @Override
    public void update(Scope scope) throws Exception {
        scopeCache.push(scope, scopeTimeout);
        scopeDao.update(scope);
    }

    @Override
    public void delete(StringIdKey key) throws Exception {
        // 删除与 作用域 相关的 权限。
        List<PermissionKey> permissionKeys = permissionDao.lookup(
                PermissionMaintainService.CHILD_FOR_SCOPE, new Object[]{key}
        ).stream().map(Permission::getKey).collect(Collectors.toList());
        permissionDao.batchDelete(permissionKeys);
        permissionCache.batchDelete(permissionKeys);

        // 删除与 作用域 相关的 权限组。
        List<PermissionGroup> permissionGroups = permissionGroupDao.lookup(
                PermissionGroupMaintainService.CHILD_FOR_SCOPE, new Object[]{key}
        );
        // 按子组在父组前的顺序排序，以避免外键约束异常。
        permissionGroups = sortPermissionGroupsForDeletion(permissionGroups);
        List<PermissionGroupKey> permissionGroupKeys = permissionGroups.stream().map(PermissionGroup::getKey)
                .collect(Collectors.toList());
        permissionGroupDao.batchDelete(permissionGroupKeys);
        permissionGroupCache.batchDelete(permissionGroupKeys);

        // 删除与 作用域 相关的 权限表达式。
        List<PexpKey> pexpKeys = pexpDao.lookup(PexpMaintainService.CHILD_FOR_SCOPE, new Object[]{key}).stream()
                .map(Pexp::getKey).collect(Collectors.toList());
        pexpDao.batchDelete(pexpKeys);
        pexpCache.batchDelete(pexpKeys);

        // 删除 作用域 自身。
        scopeDao.delete(key);
        scopeCache.delete(key);
    }

    @Override
    public boolean allExists(List<StringIdKey> keys) throws Exception {
        return scopeCache.allExists(keys) || scopeDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<StringIdKey> keys) throws Exception {
        return scopeCache.nonExists(keys) && scopeDao.nonExists(keys);
    }

    @Override
    public List<Scope> batchGet(List<StringIdKey> keys) throws Exception {
        if (scopeCache.allExists(keys)) {
            return scopeCache.batchGet(keys);
        } else {
            if (!scopeDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<Scope> scopes = scopeDao.batchGet(keys);
            scopeCache.batchPush(scopes, scopeTimeout);
            return scopes;
        }
    }

    @Override
    public List<StringIdKey> batchInsert(List<Scope> scopes) throws Exception {
        scopeCache.batchPush(scopes, scopeTimeout);
        return scopeDao.batchInsert(scopes);
    }

    @Override
    public void batchUpdate(List<Scope> scopes) throws Exception {
        scopeCache.batchPush(scopes, scopeTimeout);
        scopeDao.batchUpdate(scopes);
    }

    /**
     * 将权限组按删除顺序排序：子组在父组前面。
     *
     * <p>
     * 使用迭代的 Kahn 算法变体，避免递归，保证执行效率。
     * 包含成环检测，若检测到权限组之间存在环则抛出异常。
     *
     * @param permissionGroups 待排序的权限组列表。
     * @return 按删除顺序排序的权限组列表（子组在前，父组在后）。
     * @throws IllegalStateException 若权限组之间存在环。
     */
    private List<PermissionGroup> sortPermissionGroupsForDeletion(List<PermissionGroup> permissionGroups) {
        if (permissionGroups == null || permissionGroups.isEmpty()) {
            return permissionGroups;
        }

        // 构建 key -> 权限组 映射。
        Map<PermissionGroupKey, PermissionGroup> keyToGroup = new HashMap<>();
        for (PermissionGroup pg : permissionGroups) {
            keyToGroup.put(pg.getKey(), pg);
        }

        // 构建 父 -> 子列表 映射（仅包含在待删除集合中的子组）。
        Map<PermissionGroupKey, List<PermissionGroup>> parentToChildren = new HashMap<>();
        for (PermissionGroup pg : permissionGroups) {
            if (pg.getParentKey() != null && keyToGroup.containsKey(pg.getParentKey())) {
                parentToChildren.computeIfAbsent(pg.getParentKey(), k -> new ArrayList<>()).add(pg);
            }
        }

        // 计算每个节点的出度（在待删除集合中的子节点数量）。
        Map<PermissionGroupKey, Integer> outDegree = new HashMap<>();
        for (PermissionGroup pg : permissionGroups) {
            List<PermissionGroup> children = parentToChildren.getOrDefault(pg.getKey(), Collections.emptyList());
            outDegree.put(pg.getKey(), children.size());
        }

        // 迭代：重复找出出度为 0 的节点（叶节点），加入结果，然后递减其父节点的出度。
        List<PermissionGroup> result = new ArrayList<>();
        int remainingCount = permissionGroups.size();
        // 成环时无法收敛，防止死循环。
        int maxIterations = permissionGroups.size() * (permissionGroups.size() + 1);

        for (int iter = 0; iter < maxIterations && remainingCount > 0; iter++) {
            List<PermissionGroup> toProcess = new ArrayList<>();
            for (PermissionGroup pg : permissionGroups) {
                if (outDegree.getOrDefault(pg.getKey(), -1) == 0) {
                    toProcess.add(pg);
                }
            }

            if (toProcess.isEmpty()) {
                // 仍有未处理的节点但无出度为 0 的节点，说明存在环。
                throw new IllegalStateException("权限组之间存在环, 无法确定删除顺序");
            }

            for (PermissionGroup pg : toProcess) {
                result.add(pg);
                // 标记为已处理。
                outDegree.put(pg.getKey(), -1);
                remainingCount--;

                // 若有父节点在待删除集合中，递减其出度。
                if (pg.getParentKey() != null && keyToGroup.containsKey(pg.getParentKey())) {
                    outDegree.merge(pg.getParentKey(), 1, (a, b) -> a - b);
                }
            }
        }

        return result;
    }

    @Override
    public void batchDelete(List<StringIdKey> keys) throws Exception {
        for (StringIdKey key : keys) {
            delete(key);
        }
    }
}

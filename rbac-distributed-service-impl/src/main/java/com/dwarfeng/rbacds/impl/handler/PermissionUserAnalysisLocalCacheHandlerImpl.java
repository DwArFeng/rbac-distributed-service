package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.dutil.basic.cna.CollectionUtil;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.bean.key.ScopedRoleKey;
import com.dwarfeng.rbacds.stack.handler.PermissionUserAnalysisLocalCacheHandler;
import com.dwarfeng.rbacds.stack.handler.ScopedRolePermissionAnalysisLocalCacheHandler;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.rbacds.stack.struct.PermissionUserAnalysis;
import com.dwarfeng.rbacds.stack.struct.ScopedRolePermissionAnalysis;
import com.dwarfeng.subgrade.impl.handler.ExpirableLocalCacheHandler;
import com.dwarfeng.subgrade.impl.handler.Fetcher;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PermissionUserAnalysisLocalCacheHandlerImpl implements PermissionUserAnalysisLocalCacheHandler {

    private final ExpirableLocalCacheHandler<PermissionKey, PermissionUserAnalysis> handler;

    public PermissionUserAnalysisLocalCacheHandlerImpl(
            PermissionUserAnalysisFetcher permissionUserAnalysisFetcher,
            ThreadPoolTaskScheduler scheduler,
            @Value("${local_cache.permission_user_analysis.ttl}") long ttl,
            @Value("${local_cache.permission_user_analysis.cleanup_interval}") long cleanupInterval
    ) {
        handler = new ExpirableLocalCacheHandler<>(permissionUserAnalysisFetcher, scheduler, ttl, cleanupInterval);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(PermissionKey key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @Override
    public PermissionUserAnalysis get(PermissionKey key) throws HandlerException {
        return handler.get(key);
    }

    @BehaviorAnalyse
    @Override
    public boolean remove(PermissionKey key) {
        return handler.remove(key);
    }

    @BehaviorAnalyse
    @Override
    public void clear() {
        handler.clear();
    }

    @Component
    public static class PermissionUserAnalysisFetcher implements Fetcher<PermissionKey, PermissionUserAnalysis> {

        private final PermissionMaintainService permissionMaintainService;
        private final RoleMaintainService roleMaintainService;
        private final UserMaintainService userMaintainService;

        private final ScopedRolePermissionAnalysisLocalCacheHandler scopedRolePermissionAnalysisLocalCacheHandler;

        public PermissionUserAnalysisFetcher(
                PermissionMaintainService permissionMaintainService,
                RoleMaintainService roleMaintainService,
                UserMaintainService userMaintainService,
                ScopedRolePermissionAnalysisLocalCacheHandler scopedRolePermissionAnalysisLocalCacheHandler
        ) {
            this.permissionMaintainService = permissionMaintainService;
            this.roleMaintainService = roleMaintainService;
            this.userMaintainService = userMaintainService;
            this.scopedRolePermissionAnalysisLocalCacheHandler = scopedRolePermissionAnalysisLocalCacheHandler;
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public boolean exists(PermissionKey key) throws Exception {
            return permissionMaintainService.exists(key);
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public PermissionUserAnalysis fetch(PermissionKey key) throws Exception {
            // 获取权限对象。
            Permission permission = permissionMaintainService.get(key);
            // 获取权限对象的作用域字符串 ID。
            String scopeStringId = permission.getKey().getScopeStringId();
            // 获取所有启用的角色。
            List<Role> enabledRoles = roleMaintainService.lookupAsList(RoleMaintainService.ENABLED, new Object[0]);
            // 定义中间变量。
            Map<StringIdKey, Role> acceptedRoleMap = new HashMap<>();
            Map<StringIdKey, Role> rejectedRoleMap = new HashMap<>();
            // 遍历所有的启用角色，基于 ScopedRolePermissionAnalysis 进行角色划分。
            for (Role role : enabledRoles) {
                // 获取 ScopedRolePermissionAnalysis。
                StringIdKey roleKey = role.getKey();
                String roleStringIdKey = roleKey.getStringId();
                ScopedRoleKey scopedRoleKey = new ScopedRoleKey(scopeStringId, roleStringIdKey);
                ScopedRolePermissionAnalysis scopedRolePermissionAnalysis =
                        scopedRolePermissionAnalysisLocalCacheHandler.get(scopedRoleKey);
                // 如果不存在 ScopedRolePermissionAnalysis，则跳过对应权限的判断。
                if (Objects.isNull(scopedRolePermissionAnalysis)) {
                    continue;
                }
                // 展开参数。
                Map<PermissionKey, Permission> acceptedPermissionMap =
                        scopedRolePermissionAnalysis.getAcceptedPermissionMap();
                Map<PermissionKey, Permission> rejectedPermissionMap =
                        scopedRolePermissionAnalysis.getRejectedPermissionMap();
                Map<PermissionKey, Permission> globalRejectPermissionMap =
                        scopedRolePermissionAnalysis.getGlobalRejectedPermissionMap();
                // 如果 acceptedPermissionMap 包含权限主键，且 rejectedPermissionMap 不包含权限主键，
                // 则将角色主键添加到 shouldInRoleKeys 中。
                if (acceptedPermissionMap.containsKey(key) && !rejectedPermissionMap.containsKey(key)) {
                    acceptedRoleMap.putIfAbsent(roleKey, role);
                }
                // 如果 globalRejectPermissionMap 包含权限主键，则将角色主键添加到 shouldNotInRoleKeys 中。
                if (globalRejectPermissionMap.containsKey(key)) {
                    rejectedRoleMap.putIfAbsent(roleKey, role);
                }
            }
            // 查询所有符合要求的用户。
            Set<StringIdKey> acceptedRoleKeySet = acceptedRoleMap.keySet();
            Set<StringIdKey> rejectedRoleKeySet = rejectedRoleMap.keySet();
            List<User> matchedUsers = userMaintainService.lookupAsList(
                    UserMaintainService.ROLE_IN_ROLE_NOT_IN_KEY_ASC,
                    new Object[]{acceptedRoleKeySet, rejectedRoleKeySet}
            );
            Set<StringIdKey> matchedUserKeySet = matchedUsers.stream().map(User::getKey).collect(Collectors.toSet());
            // 构造结果并返回。
            List<Role> acceptedRoles = new ArrayList<>(acceptedRoleMap.size());
            List<Role> rejectedRoles = new ArrayList<>(rejectedRoleMap.size());
            for (Role role : acceptedRoleMap.values()) {
                CollectionUtil.insertByOrder(acceptedRoles, role, Comparator.comparing(r -> r.getKey().getStringId()));
            }
            for (Role role : rejectedRoleMap.values()) {
                CollectionUtil.insertByOrder(rejectedRoles, role, Comparator.comparing(r -> r.getKey().getStringId()));
            }
            return new PermissionUserAnalysis(matchedUsers, matchedUserKeySet, acceptedRoles, rejectedRoles);
        }
    }
}

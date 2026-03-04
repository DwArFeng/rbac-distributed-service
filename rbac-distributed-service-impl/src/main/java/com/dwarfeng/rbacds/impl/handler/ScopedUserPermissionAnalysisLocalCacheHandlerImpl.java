package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.dutil.basic.cna.CollectionUtil;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.bean.key.ScopedRoleKey;
import com.dwarfeng.rbacds.stack.bean.key.ScopedUserKey;
import com.dwarfeng.rbacds.stack.handler.ScopedRolePermissionAnalysisLocalCacheHandler;
import com.dwarfeng.rbacds.stack.handler.ScopedUserPermissionAnalysisLocalCacheHandler;
import com.dwarfeng.rbacds.stack.handler.UserRoleAnalysisLocalCacheHandler;
import com.dwarfeng.rbacds.stack.service.ScopeMaintainService;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.rbacds.stack.struct.ScopedRolePermissionAnalysis;
import com.dwarfeng.rbacds.stack.struct.ScopedUserPermissionAnalysis;
import com.dwarfeng.rbacds.stack.struct.UserRoleAnalysis;
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
public class ScopedUserPermissionAnalysisLocalCacheHandlerImpl implements
        ScopedUserPermissionAnalysisLocalCacheHandler {

    private final ExpirableLocalCacheHandler<ScopedUserKey, ScopedUserPermissionAnalysis> handler;

    public ScopedUserPermissionAnalysisLocalCacheHandlerImpl(
            UserPermissionAnalysisFetcher userPermissionAnalysisFetcher,
            ThreadPoolTaskScheduler scheduler,
            @Value("${local_cache.scoped_user_permission_analysis.ttl}") long ttl,
            @Value("${local_cache.scoped_user_permission_analysis.cleanup_interval}") long cleanupInterval
    ) {
        handler = new ExpirableLocalCacheHandler<>(userPermissionAnalysisFetcher, scheduler, ttl, cleanupInterval);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(ScopedUserKey key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @Override
    public ScopedUserPermissionAnalysis get(ScopedUserKey key) throws HandlerException {
        return handler.get(key);
    }

    @BehaviorAnalyse
    @Override
    public boolean remove(ScopedUserKey key) {
        return handler.remove(key);
    }

    @BehaviorAnalyse
    @Override
    public void clear() {
        handler.clear();
    }

    @Component
    public static class UserPermissionAnalysisFetcher implements Fetcher<ScopedUserKey, ScopedUserPermissionAnalysis> {

        private final ScopeMaintainService scopeMaintainService;
        private final UserMaintainService userMaintainService;

        private final UserRoleAnalysisLocalCacheHandler userRoleAnalysisLocalCacheHandler;
        private final ScopedRolePermissionAnalysisLocalCacheHandler scopedRolePermissionAnalysisLocalCacheHandler;

        public UserPermissionAnalysisFetcher(
                ScopeMaintainService scopeMaintainService,
                UserMaintainService userMaintainService,
                UserRoleAnalysisLocalCacheHandler userRoleAnalysisLocalCacheHandler,
                ScopedRolePermissionAnalysisLocalCacheHandler scopedRolePermissionAnalysisLocalCacheHandler
        ) {
            this.scopeMaintainService = scopeMaintainService;
            this.userMaintainService = userMaintainService;
            this.userRoleAnalysisLocalCacheHandler = userRoleAnalysisLocalCacheHandler;
            this.scopedRolePermissionAnalysisLocalCacheHandler = scopedRolePermissionAnalysisLocalCacheHandler;
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public boolean exists(ScopedUserKey key) throws Exception {
            String scopeStringId = key.getScopeStringId();
            String userStringId = key.getUserStringId();
            StringIdKey scopeKey = new StringIdKey(scopeStringId);
            StringIdKey userKey = new StringIdKey(userStringId);
            boolean scopeExists = scopeMaintainService.exists(scopeKey);
            boolean userExists = userMaintainService.exists(userKey);
            return scopeExists && userExists;
        }

        @SuppressWarnings("DuplicatedCode")
        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public ScopedUserPermissionAnalysis fetch(ScopedUserKey key) throws Exception {
            // 展开参数。
            String scopeStringId = key.getScopeStringId();
            String userStringId = key.getUserStringId();

            // 查询用户有效的角色。
            StringIdKey userKey = new StringIdKey(userStringId);
            UserRoleAnalysis userRoleAnalysis = userRoleAnalysisLocalCacheHandler.get(userKey);
            List<Role> matchedRoles = userRoleAnalysis.getMatchedRoles();

            // 对每个角色进行权限分析，并进行合并。
            Map<PermissionKey, Permission> acceptedPermissionMap = new HashMap<>();
            Set<PermissionKey> globalRejectedPermissionKeys = new HashSet<>();
            List<ScopedUserPermissionAnalysis.RoleDetail> roleDetails = new ArrayList<>(matchedRoles.size());
            for (Role role : matchedRoles) {
                analyseSingleRolePermission(
                        scopeStringId, role, acceptedPermissionMap, globalRejectedPermissionKeys, roleDetails
                );
            }

            // 排除全局拒绝的权限。
            acceptedPermissionMap.keySet().removeAll(globalRejectedPermissionKeys);

            // 剩余的权限即为用户的有效权限，按照权限主键进行排序后返回。
            List<Permission> matchedPermissions = new ArrayList<>(acceptedPermissionMap.size());
            for (Permission permission : acceptedPermissionMap.values()) {
                CollectionUtil.insertByOrder(
                        matchedPermissions, permission, Comparator.comparing(p -> p.getKey().getPermissionStringId())
                );
            }
            Set<PermissionKey> matchedPermissionsKeySet = matchedPermissions.stream().map(Permission::getKey)
                    .collect(Collectors.toSet());

            // 构造结果并返回。
            return new ScopedUserPermissionAnalysis(matchedPermissions, matchedPermissionsKeySet, roleDetails);
        }

        private void analyseSingleRolePermission(
                String scopeStringId, Role role, Map<PermissionKey, Permission> acceptedPermissionMap,
                Set<PermissionKey> globalRejectedPermissionKeys,
                List<ScopedUserPermissionAnalysis.RoleDetail> roleDetails
        ) throws Exception {
            // 获取角色权限分析。
            String roleStringId = role.getKey().getStringId();
            ScopedRoleKey scopedRoleKey = new ScopedRoleKey(scopeStringId, roleStringId);
            ScopedRolePermissionAnalysis scopedRolePermissionAnalysis =
                    scopedRolePermissionAnalysisLocalCacheHandler.get(scopedRoleKey);

            // 将角色权限分析中的接受权限排除拒绝权限，并加入到接受权限中。
            Map<PermissionKey, Permission> singleRoleAcceptedPermissionMap =
                    scopedRolePermissionAnalysis.getAcceptedPermissionMap();
            Map<PermissionKey, Permission> singleRoleRejectedPermissionMap =
                    scopedRolePermissionAnalysis.getRejectedPermissionMap();
            Map<PermissionKey, Permission> singleRoleAcceptedPermissionMapDejaVu =
                    new HashMap<>(singleRoleAcceptedPermissionMap);
            singleRoleAcceptedPermissionMapDejaVu.keySet().removeAll(singleRoleRejectedPermissionMap.keySet());
            acceptedPermissionMap.putAll(singleRoleAcceptedPermissionMapDejaVu);

            // 将角色权限分析中的拒绝权限加入到全局拒绝权限中。
            Map<PermissionKey, Permission> singleRoleGlobalRejectPermissionMap =
                    scopedRolePermissionAnalysis.getGlobalRejectedPermissionMap();
            globalRejectedPermissionKeys.addAll(singleRoleGlobalRejectPermissionMap.keySet());

            // 构造角色详情并加入到角色详情列表中。
            List<Permission> singleRoleAcceptedPermissions = new ArrayList<>(
                    singleRoleAcceptedPermissionMap.size()
            );
            List<Permission> singleRoleRejectedPermissions = new ArrayList<>(
                    singleRoleRejectedPermissionMap.size()
            );
            List<Permission> singleRoleGlobalRejectedPermissions = new ArrayList<>(
                    singleRoleGlobalRejectPermissionMap.size()
            );
            for (Permission permission : singleRoleAcceptedPermissionMap.values()) {
                CollectionUtil.insertByOrder(
                        singleRoleAcceptedPermissions, permission,
                        Comparator.comparing(p -> p.getKey().getPermissionStringId())
                );
            }
            for (Permission permission : singleRoleRejectedPermissionMap.values()) {
                CollectionUtil.insertByOrder(
                        singleRoleRejectedPermissions, permission,
                        Comparator.comparing(p -> p.getKey().getPermissionStringId())
                );
            }
            for (Permission permission : singleRoleGlobalRejectPermissionMap.values()) {
                CollectionUtil.insertByOrder(
                        singleRoleGlobalRejectedPermissions, permission,
                        Comparator.comparing(p -> p.getKey().getPermissionStringId())
                );
            }
            ScopedUserPermissionAnalysis.RoleDetail roleDetail = new ScopedUserPermissionAnalysis.RoleDetail(
                    role, singleRoleAcceptedPermissions, singleRoleRejectedPermissions,
                    singleRoleGlobalRejectedPermissions
            );
            CollectionUtil.insertByOrder(
                    roleDetails, roleDetail, Comparator.comparing(r -> r.getRole().getKey().getStringId())
            );
        }
    }
}

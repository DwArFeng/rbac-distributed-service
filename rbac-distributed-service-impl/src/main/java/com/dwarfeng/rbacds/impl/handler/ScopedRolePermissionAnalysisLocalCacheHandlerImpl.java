package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.dutil.basic.cna.CollectionUtil;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.bean.key.ScopedRoleKey;
import com.dwarfeng.rbacds.stack.handler.PexpHandler;
import com.dwarfeng.rbacds.stack.handler.ScopePermissionAnalysisLocalCacheHandler;
import com.dwarfeng.rbacds.stack.handler.ScopedRolePermissionAnalysisLocalCacheHandler;
import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.rbacds.stack.service.ScopeMaintainService;
import com.dwarfeng.rbacds.stack.struct.ScopePermissionAnalysis;
import com.dwarfeng.rbacds.stack.struct.ScopedRolePermissionAnalysis;
import com.dwarfeng.subgrade.impl.handler.Fetcher;
import com.dwarfeng.subgrade.impl.handler.GeneralLocalCacheHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

@Component
public class ScopedRolePermissionAnalysisLocalCacheHandlerImpl implements
        ScopedRolePermissionAnalysisLocalCacheHandler {

    private final GeneralLocalCacheHandler<ScopedRoleKey, ScopedRolePermissionAnalysis> handler;

    public ScopedRolePermissionAnalysisLocalCacheHandlerImpl(
            ScopedRolePermissionAnalysisFetcher scopedRolePermissionAnalysisFetcher
    ) {
        handler = new GeneralLocalCacheHandler<>(scopedRolePermissionAnalysisFetcher);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(ScopedRoleKey key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @Override
    public ScopedRolePermissionAnalysis get(ScopedRoleKey key) throws HandlerException {
        return handler.get(key);
    }

    @BehaviorAnalyse
    @Override
    public boolean remove(ScopedRoleKey key) {
        return handler.remove(key);
    }

    @BehaviorAnalyse
    @Override
    public void clear() {
        handler.clear();
    }

    @Component
    public static class ScopedRolePermissionAnalysisFetcher implements
            Fetcher<ScopedRoleKey, ScopedRolePermissionAnalysis> {

        private final ScopeMaintainService scopeMaintainService;
        private final RoleMaintainService roleMaintainService;
        private final PexpMaintainService pexpMaintainService;

        private final ScopePermissionAnalysisLocalCacheHandler scopePermissionAnalysisLocalCacheHandler;

        private final PexpHandler pexpHandler;

        private final ThreadPoolTaskScheduler executor;

        public ScopedRolePermissionAnalysisFetcher(
                ScopeMaintainService scopeMaintainService,
                RoleMaintainService roleMaintainService,
                PexpMaintainService pexpMaintainService,
                ScopePermissionAnalysisLocalCacheHandler scopePermissionAnalysisLocalCacheHandler,
                PexpHandler pexpHandler,
                ThreadPoolTaskScheduler executor
        ) {
            this.scopeMaintainService = scopeMaintainService;
            this.roleMaintainService = roleMaintainService;
            this.pexpMaintainService = pexpMaintainService;
            this.scopePermissionAnalysisLocalCacheHandler = scopePermissionAnalysisLocalCacheHandler;
            this.pexpHandler = pexpHandler;
            this.executor = executor;
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public boolean exists(ScopedRoleKey key) throws Exception {
            String scopeStringId = key.getScopeStringId();
            String roleStringId = key.getRoleStringId();
            StringIdKey scopeKey = new StringIdKey(scopeStringId);
            StringIdKey roleKey = new StringIdKey(roleStringId);
            boolean scopeExists = scopeMaintainService.exists(scopeKey);
            boolean roleFlag;
            Role role = roleMaintainService.getIfExists(roleKey);
            if (Objects.isNull(role)) {
                roleFlag = false;
            } else {
                roleFlag = role.isEnabled();
            }
            return scopeExists && roleFlag;
        }

        @SuppressWarnings("DuplicatedCode")
        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public ScopedRolePermissionAnalysis fetch(ScopedRoleKey key) throws Exception {
            // 展开参数。
            String scopeStringId = key.getScopeStringId();
            String roleStringId = key.getRoleStringId();

            // 获取作用域对应的所有权限。
            StringIdKey scopeKey = new StringIdKey(scopeStringId);
            ScopePermissionAnalysis scopePermissionAnalysis = scopePermissionAnalysisLocalCacheHandler.get(scopeKey);
            List<Permission> permissions = scopePermissionAnalysis.getPermissions();

            // 再找到属于作用域和角色的所有权限表达式。
            StringIdKey roleKey = new StringIdKey(roleStringId);
            List<Pexp> pexps = pexpMaintainService.lookupAsList(
                    PexpMaintainService.CHILD_FOR_SCOPE_CHILD_FOR_ROLE, new Object[]{scopeKey, roleKey}
            );

            // 定义中间变量。
            Map<PermissionKey, Permission> acceptedPermissionMap = new HashMap<>();
            Map<PermissionKey, Permission> rejectedPermissionMap = new HashMap<>();
            Map<PermissionKey, Permission> globalRejectedPermissionMap = new HashMap<>();

            // 遍历权限表达式，进行权限分析。
            List<CompletableFuture<List<PexpHandler.Reception>>> futures = new ArrayList<>(pexps.size());
            for (Pexp pexp : pexps) {
                CompletableFuture<List<PexpHandler.Reception>> future = CompletableFuture.supplyAsync(
                        () -> {
                            try {
                                return pexpHandler.testAll(pexp, permissions);
                            } catch (Exception e) {
                                throw new CompletionException(e);
                            }
                        },
                        executor
                );
                futures.add(future);
            }

            // 等待所有的权限分析完成。
            List<PexpHandler.Reception> receptions = new ArrayList<>(pexps.size() * permissions.size());
            for (CompletableFuture<List<PexpHandler.Reception>> future : futures) {
                receptions.addAll(future.join());
            }

            // 遍历权限表达式，进行权限分析。
            for (int i = 0; i < receptions.size(); i++) {
                PexpHandler.Reception reception = receptions.get(i);
                Permission permission = permissions.get(i % permissions.size());
                switch (reception) {
                    case ACCEPT:
                        acceptedPermissionMap.putIfAbsent(permission.getKey(), permission);
                        break;
                    case REJECT:
                        rejectedPermissionMap.putIfAbsent(permission.getKey(), permission);
                        break;
                    case GLOBAL_REJECT:
                        globalRejectedPermissionMap.putIfAbsent(permission.getKey(), permission);
                        break;
                }
            }

            // 构造结果并返回。
            Map<PermissionKey, Permission> matchedPermissionMap = new HashMap<>(acceptedPermissionMap);
            matchedPermissionMap.keySet().removeAll(rejectedPermissionMap.keySet());
            matchedPermissionMap.keySet().removeAll(globalRejectedPermissionMap.keySet());
            List<Permission> matchedPermissions = new ArrayList<>(matchedPermissionMap.size());
            for (Permission permission : matchedPermissionMap.values()) {
                CollectionUtil.insertByOrder(
                        matchedPermissions, permission, Comparator.comparing(p -> p.getKey().getPermissionStringId())
                );
            }
            Set<PermissionKey> matchedPermissionsKeySet = matchedPermissions.stream().map(Permission::getKey)
                    .collect(Collectors.toSet());
            return new ScopedRolePermissionAnalysis(
                    matchedPermissions, matchedPermissionsKeySet, acceptedPermissionMap, rejectedPermissionMap,
                    globalRejectedPermissionMap
            );
        }
    }
}

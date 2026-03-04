package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.handler.ScopePermissionAnalysisLocalCacheHandler;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.rbacds.stack.service.ScopeMaintainService;
import com.dwarfeng.rbacds.stack.struct.ScopePermissionAnalysis;
import com.dwarfeng.subgrade.impl.handler.Fetcher;
import com.dwarfeng.subgrade.impl.handler.GeneralLocalCacheHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ScopePermissionAnalysisLocalCacheHandlerImpl implements ScopePermissionAnalysisLocalCacheHandler {

    private final GeneralLocalCacheHandler<StringIdKey, ScopePermissionAnalysis> handler;

    public ScopePermissionAnalysisLocalCacheHandlerImpl(ScopePermissionAnalysisFetcher scopePermissionAnalysisFetcher) {
        handler = new GeneralLocalCacheHandler<>(scopePermissionAnalysisFetcher);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(StringIdKey key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @Override
    public ScopePermissionAnalysis get(StringIdKey key) throws HandlerException {
        return handler.get(key);
    }

    @BehaviorAnalyse
    @Override
    public boolean remove(StringIdKey key) {
        return handler.remove(key);
    }

    @BehaviorAnalyse
    @Override
    public void clear() {
        handler.clear();
    }

    @Component
    public static class ScopePermissionAnalysisFetcher implements Fetcher<StringIdKey, ScopePermissionAnalysis> {

        private final ScopeMaintainService scopeMaintainService;
        private final PermissionMaintainService permissionMaintainService;

        public ScopePermissionAnalysisFetcher(
                ScopeMaintainService scopeMaintainService,
                PermissionMaintainService permissionMaintainService
        ) {
            this.scopeMaintainService = scopeMaintainService;
            this.permissionMaintainService = permissionMaintainService;
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public boolean exists(StringIdKey key) throws Exception {
            return scopeMaintainService.exists(key);
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public ScopePermissionAnalysis fetch(StringIdKey key) throws Exception {
            List<Permission> permissions = permissionMaintainService.lookupAsList(
                    PermissionMaintainService.CHILD_FOR_SCOPE_PERMISSION_STRING_ID_ASC, new Object[]{key}
            );
            Set<PermissionKey> permissionKeySet = permissions.stream().map(Permission::getKey)
                    .collect(Collectors.toSet());
            return new ScopePermissionAnalysis(permissions, permissionKeySet);
        }
    }
}

package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.handler.UserRoleAnalysisLocalCacheHandler;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
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

import java.util.List;
import java.util.Set;

@Component
public class UserRoleAnalysisLocalCacheHandlerImpl implements UserRoleAnalysisLocalCacheHandler {

    private final ExpirableLocalCacheHandler<StringIdKey, UserRoleAnalysis> handler;

    public UserRoleAnalysisLocalCacheHandlerImpl(
            UserRoleAnalysisFetcher userRoleAnalysisFetcher,
            ThreadPoolTaskScheduler scheduler,
            @Value("${local_cache.user_role_analysis.ttl}") long ttl,
            @Value("${local_cache.user_role_analysis.cleanup_interval}") long cleanupInterval
    ) {
        handler = new ExpirableLocalCacheHandler<>(userRoleAnalysisFetcher, scheduler, ttl, cleanupInterval);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(StringIdKey key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @Override
    public UserRoleAnalysis get(StringIdKey key) throws HandlerException {
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
    public static class UserRoleAnalysisFetcher implements Fetcher<StringIdKey, UserRoleAnalysis> {

        private final UserMaintainService userMaintainService;
        private final RoleMaintainService roleMaintainService;

        public UserRoleAnalysisFetcher(
                UserMaintainService userMaintainService,
                RoleMaintainService roleMaintainService
        ) {
            this.userMaintainService = userMaintainService;
            this.roleMaintainService = roleMaintainService;
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public boolean exists(StringIdKey key) throws Exception {
            return userMaintainService.exists(key);
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public UserRoleAnalysis fetch(StringIdKey key) throws Exception {
            List<Role> roles = roleMaintainService.lookupAsList(
                    RoleMaintainService.CHILD_FOR_USER_AVAILABLE_KEY_ASC, new Object[]{key}
            );
            Set<StringIdKey> roleKeySet = roles.stream().map(Role::getKey).collect(java.util.stream.Collectors.toSet());
            return new UserRoleAnalysis(roles, roleKeySet);
        }
    }
}

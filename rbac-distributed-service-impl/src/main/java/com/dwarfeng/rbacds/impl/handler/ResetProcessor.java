package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.handler.*;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 重置处理器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Component
public class ResetProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetProcessor.class);

    private final FilterLocalCacheHandler filterLocalCacheHandler;
    private final ScopedRolePermissionAnalysisLocalCacheHandler scopedRolePermissionAnalysisLocalCacheHandler;
    private final ScopedUserPermissionAnalysisLocalCacheHandler scopedUserPermissionAnalysisLocalCacheHandler;
    private final ScopePermissionAnalysisLocalCacheHandler scopePermissionAnalysisLocalCacheHandler;
    private final UserRoleAnalysisLocalCacheHandler userRoleAnalysisLocalCacheHandler;
    private final PermissionUserAnalysisLocalCacheHandler permissionUserAnalysisLocalCacheHandler;

    private final PushHandler pushHandler;

    public ResetProcessor(
            FilterLocalCacheHandler filterLocalCacheHandler,
            ScopedRolePermissionAnalysisLocalCacheHandler scopedRolePermissionAnalysisLocalCacheHandler,
            ScopedUserPermissionAnalysisLocalCacheHandler scopedUserPermissionAnalysisLocalCacheHandler,
            ScopePermissionAnalysisLocalCacheHandler scopePermissionAnalysisLocalCacheHandler,
            UserRoleAnalysisLocalCacheHandler userRoleAnalysisLocalCacheHandler,
            PermissionUserAnalysisLocalCacheHandler permissionUserAnalysisLocalCacheHandler,
            PushHandler pushHandler
    ) {
        this.filterLocalCacheHandler = filterLocalCacheHandler;
        this.scopedRolePermissionAnalysisLocalCacheHandler = scopedRolePermissionAnalysisLocalCacheHandler;
        this.scopedUserPermissionAnalysisLocalCacheHandler = scopedUserPermissionAnalysisLocalCacheHandler;
        this.scopePermissionAnalysisLocalCacheHandler = scopePermissionAnalysisLocalCacheHandler;
        this.userRoleAnalysisLocalCacheHandler = userRoleAnalysisLocalCacheHandler;
        this.permissionUserAnalysisLocalCacheHandler = permissionUserAnalysisLocalCacheHandler;
        this.pushHandler = pushHandler;
    }

    public void resetFilter() throws HandlerException {
        // 重置过滤。
        filterLocalCacheHandler.clear();

        // 推送消息。
        try {
            pushHandler.filterReset();
        } catch (Exception e) {
            LOGGER.warn("推送过滤功能重置消息时发生异常, 本次消息将不会被推送, 异常信息如下: ", e);
        }
    }

    public void resetAnalysis() throws HandlerException {
        // 重置分析结果。
        scopedRolePermissionAnalysisLocalCacheHandler.clear();
        scopedUserPermissionAnalysisLocalCacheHandler.clear();
        scopePermissionAnalysisLocalCacheHandler.clear();
        userRoleAnalysisLocalCacheHandler.clear();
        permissionUserAnalysisLocalCacheHandler.clear();

        // 推送消息。
        try {
            pushHandler.analysisReset();
        } catch (Exception e) {
            LOGGER.warn("推送分析结果重置消息时发生异常, 本次消息将不会被推送, 异常信息如下: ", e);
        }
    }
}

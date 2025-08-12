package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.sdk.handler.PermissionFilterSupporter;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionFilterSupport;
import com.dwarfeng.rbacds.stack.handler.SupportHandler;
import com.dwarfeng.rbacds.stack.service.PermissionFilterSupportMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SupportHandlerImpl implements SupportHandler {

    private final PermissionFilterSupportMaintainService permissionFilterSupportMaintainService;

    private final List<PermissionFilterSupporter> permissionFilterSupporters;

    public SupportHandlerImpl(
            PermissionFilterSupportMaintainService permissionFilterSupportMaintainService,
            List<PermissionFilterSupporter> permissionFilterSupporters
    ) {
        this.permissionFilterSupportMaintainService = permissionFilterSupportMaintainService;
        this.permissionFilterSupporters =
                Optional.ofNullable(permissionFilterSupporters).orElse(Collections.emptyList());
    }

    @Override
    @BehaviorAnalyse
    public void resetPermissionFilter() throws HandlerException {
        try {
            doResetPermissionFilter();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetPermissionFilter() throws Exception {
        List<StringIdKey> permissionFilterKeys = permissionFilterSupportMaintainService.lookupAsList().stream()
                .map(PermissionFilterSupport::getKey).collect(Collectors.toList());
        permissionFilterSupportMaintainService.batchDelete(permissionFilterKeys);
        List<PermissionFilterSupport> permissionFilterSupports = permissionFilterSupporters.stream().map(
                supporter -> new PermissionFilterSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExamplePattern()
                )
        ).collect(Collectors.toList());
        permissionFilterSupportMaintainService.batchInsert(permissionFilterSupports);
    }
}

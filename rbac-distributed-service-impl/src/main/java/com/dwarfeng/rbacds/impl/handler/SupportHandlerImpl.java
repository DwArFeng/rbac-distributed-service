package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.sdk.handler.FilterSupporter;
import com.dwarfeng.rbacds.stack.bean.entity.FilterSupport;
import com.dwarfeng.rbacds.stack.handler.SupportHandler;
import com.dwarfeng.rbacds.stack.service.FilterSupportMaintainService;
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

    private final FilterSupportMaintainService filterSupportMaintainService;

    private final List<FilterSupporter> filterSupporters;

    public SupportHandlerImpl(
            FilterSupportMaintainService filterSupportMaintainService,
            List<FilterSupporter> filterSupporters
    ) {
        this.filterSupportMaintainService = filterSupportMaintainService;
        this.filterSupporters =
                Optional.ofNullable(filterSupporters).orElse(Collections.emptyList());
    }

    @Override
    @BehaviorAnalyse
    public void resetFilter() throws HandlerException {
        try {
            doResetFilter();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetFilter() throws Exception {
        List<StringIdKey> filterKeys = filterSupportMaintainService.lookupAsList().stream()
                .map(FilterSupport::getKey).collect(Collectors.toList());
        filterSupportMaintainService.batchDelete(filterKeys);
        List<FilterSupport> filterSupports = filterSupporters.stream().map(
                supporter -> new FilterSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExamplePattern()
                )
        ).collect(Collectors.toList());
        filterSupportMaintainService.batchInsert(filterSupports);
    }
}

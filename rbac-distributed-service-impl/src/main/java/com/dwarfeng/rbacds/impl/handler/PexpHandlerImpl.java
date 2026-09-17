package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.exception.UnsupportedFilterTypeException;
import com.dwarfeng.rbacds.stack.handler.Filter;
import com.dwarfeng.rbacds.stack.handler.FilterLocalCacheHandler;
import com.dwarfeng.rbacds.stack.handler.PexpHandler;
import com.dwarfeng.rbacds.stack.handler.PexpParseHandler;
import com.dwarfeng.subgrade.aop.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.aop.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.basic.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class PexpHandlerImpl implements PexpHandler {

    private final PexpParseHandler pexpParseHandler;
    private final FilterLocalCacheHandler filterLocalCacheHandler;

    public PexpHandlerImpl(
            PexpParseHandler pexpParseHandler,
            FilterLocalCacheHandler filterLocalCacheHandler
    ) {
        this.pexpParseHandler = pexpParseHandler;
        this.filterLocalCacheHandler = filterLocalCacheHandler;
    }

    @Override
    @BehaviorAnalyse
    public Reception test(Pexp pexp, Permission permission) throws HandlerException {
        try {
            // 分析并取得权限表达式的信息。
            PexpParseHandler.ParseResult parseResult = pexpParseHandler.parse(pexp);

            // 判断权限表达式是否被接受。
            boolean acceptFlag = true;
            for (PexpParseHandler.PipeUnit pipeUnit : parseResult.pipe()) {
                String filterType = pipeUnit.filterType();
                Filter filter = filterLocalCacheHandler.get(filterType);
                if (Objects.isNull(filter)) {
                    throw new UnsupportedFilterTypeException(filterType);
                }
                if (!filter.accept(pipeUnit.filterPattern(), permission)) {
                    acceptFlag = false;
                    break;
                }
            }

            // 如果权限表达式不被接受，则返回 NOT_ACCEPT。
            if (!acceptFlag) {
                return Reception.NOT_ACCEPT;
            }

            // 否则，根据修饰符，返回对应的结果。
            PexpParseHandler.Modifier modifier = parseResult.modifier();
            return switch (modifier) {
                case ACCEPT -> Reception.ACCEPT;
                case REJECT -> Reception.REJECT;
                case GLOBAL_REJECT -> Reception.GLOBAL_REJECT;
            };
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<Reception> testAll(Pexp pexp, @SkipRecord List<Permission> permissions) throws HandlerException {
        try {
            // 特殊情况判断。
            if (permissions.isEmpty()) {
                return Collections.emptyList();
            }

            // 分析并取得权限表达式的信息。
            PexpParseHandler.ParseResult parseResult = pexpParseHandler.parse(pexp);
            PexpParseHandler.Modifier modifier = parseResult.modifier();

            // 确定权限接受时的接受程度。
            Reception acceptReception = switch (modifier) {
                case ACCEPT -> Reception.ACCEPT;
                case REJECT -> Reception.REJECT;
                case GLOBAL_REJECT -> Reception.GLOBAL_REJECT;
            };

            // 构造结果列表。
            List<Reception> result = new ArrayList<>();

            // 遍历所有权限，填充结果列表。
            outer:
            for (Permission permission : permissions) {
                // 判断权限表达式是否被接受。
                for (PexpParseHandler.PipeUnit pipeUnit : parseResult.pipe()) {
                    String filterType = pipeUnit.filterType();
                    Filter filter = filterLocalCacheHandler.get(filterType);
                    if (Objects.isNull(filter)) {
                        throw new UnsupportedFilterTypeException(filterType);
                    }
                    // 如果有任一过滤器不接受该权限，则该权限不被接受，向结果列表中加入 NOT_ACCEPT。
                    if (!filter.accept(pipeUnit.filterPattern(), permission)) {
                        result.add(Reception.NOT_ACCEPT);
                        continue outer;
                    }
                }
                // 如果管道中的每一个单元都接受了该权限，则该权限被接受，向结果列表中加入 acceptReception。
                result.add(acceptReception);
            }

            // 返回结果。
            return result;
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }
}

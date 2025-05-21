package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.exception.UnsupportedPermissionFilterTypeException;
import com.dwarfeng.rbacds.stack.handler.PermissionFilter;
import com.dwarfeng.rbacds.stack.handler.PermissionFilterLocalCacheHandler;
import com.dwarfeng.rbacds.stack.handler.PexpHandler;
import com.dwarfeng.rbacds.stack.handler.PexpParseHandler;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PexpHandlerImpl implements PexpHandler {

    private final PexpParseHandler pexpParseHandler;
    private final PermissionFilterLocalCacheHandler permissionFilterLocalCacheHandler;

    public PexpHandlerImpl(
            PexpParseHandler pexpParseHandler,
            PermissionFilterLocalCacheHandler permissionFilterLocalCacheHandler
    ) {
        this.pexpParseHandler = pexpParseHandler;
        this.permissionFilterLocalCacheHandler = permissionFilterLocalCacheHandler;
    }

    @Override
    @BehaviorAnalyse
    public PermissionReception test(Pexp pexp, Permission permission) throws HandlerException {
        try {
            // 分析并取得权限表达式的信息。
            PexpParseHandler.ParseResult parseResult = pexpParseHandler.parse(pexp);

            // 判断权限表达式是否被接受。
            boolean acceptFlag = true;
            for (PexpParseHandler.PipeUnit pipeUnit : parseResult.getPipe()) {
                String filterType = pipeUnit.getFilterType();
                PermissionFilter permissionFilter = permissionFilterLocalCacheHandler.get(filterType);
                if (Objects.isNull(permissionFilter)) {
                    throw new UnsupportedPermissionFilterTypeException(filterType);
                }
                if (!permissionFilter.accept(pipeUnit.getFilterPattern(), permission)) {
                    acceptFlag = false;
                    break;
                }
            }

            // 如果权限表达式不被接受，则返回 NOT_ACCEPT。
            if (!acceptFlag) {
                return PermissionReception.NOT_ACCEPT;
            }

            // 否则，根据修饰符，返回对应的结果。
            PexpParseHandler.Modifier modifier = parseResult.getModifier();
            switch (modifier) {
                case ACCEPT:
                    return PermissionReception.ACCEPT;
                case REJECT:
                    return PermissionReception.REJECT;
                case GLOBAL_REJECT:
                    return PermissionReception.GLOBAL_REJECT;
                default:
                    throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public Map<PermissionReception, List<Permission>> testAll(Pexp pexp, @SkipRecord List<Permission> permissions)
            throws HandlerException {
        try {
            // 特殊情况判断。
            if (permissions.isEmpty()) {
                return Collections.emptyMap();
            }

            // 分析并取得权限表达式的信息。
            PexpParseHandler.ParseResult parseResult = pexpParseHandler.parse(pexp);

            // 定义列表，存放接受和不接受的权限。
            List<Permission> acceptedPermissions = new ArrayList<>();
            List<Permission> notAcceptedPermissions = new ArrayList<>();

            // 遍历所有权限，确认权限过滤器的接收情况。
            outer:
            for (Permission permission : permissions) {
                // 判断权限表达式是否被接受。
                for (PexpParseHandler.PipeUnit pipeUnit : parseResult.getPipe()) {
                    String filterType = pipeUnit.getFilterType();
                    PermissionFilter permissionFilter = permissionFilterLocalCacheHandler.get(filterType);
                    if (Objects.isNull(permissionFilter)) {
                        throw new UnsupportedPermissionFilterTypeException(filterType);
                    }
                    if (!permissionFilter.accept(pipeUnit.getFilterPattern(), permission)) {
                        notAcceptedPermissions.add(permission);
                        continue outer;
                    }
                }
                // 如果管道中的每一个单元都接受了该权限，则加入到接受列表中。
                acceptedPermissions.add(permission);
            }

            // 构造结果并返回。
            Map<PermissionReception, List<Permission>> result = new EnumMap<>(PermissionReception.class);
            result.put(PermissionReception.NOT_ACCEPT, notAcceptedPermissions);
            PexpParseHandler.Modifier modifier = parseResult.getModifier();
            switch (modifier) {
                case ACCEPT:
                    result.put(PermissionReception.ACCEPT, acceptedPermissions);
                    result.put(PermissionReception.REJECT, Collections.emptyList());
                    result.put(PermissionReception.GLOBAL_REJECT, Collections.emptyList());
                    break;
                case REJECT:
                    result.put(PermissionReception.ACCEPT, Collections.emptyList());
                    result.put(PermissionReception.REJECT, acceptedPermissions);
                    result.put(PermissionReception.GLOBAL_REJECT, Collections.emptyList());
                    break;
                case GLOBAL_REJECT:
                    result.put(PermissionReception.ACCEPT, Collections.emptyList());
                    result.put(PermissionReception.REJECT, Collections.emptyList());
                    result.put(PermissionReception.GLOBAL_REJECT, acceptedPermissions);
                    break;
                default:
                    throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
            }
            return result;
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }
}

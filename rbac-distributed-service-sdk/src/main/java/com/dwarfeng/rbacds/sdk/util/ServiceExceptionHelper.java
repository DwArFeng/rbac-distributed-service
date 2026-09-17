package com.dwarfeng.rbacds.sdk.util;

import com.dwarfeng.rbacds.sdk.exception.ServiceExceptionCodeSuppliers;
import com.dwarfeng.rbacds.stack.exception.*;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * RBAC Distributed Service 模块服务异常帮助类。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public final class ServiceExceptionHelper {

    /**
     * 向指定的映射中添加 RBAC Distributed Service 模块默认的目标映射。
     *
     * <p>
     * 该方法可以在配置类中快速搭建异常目标映射。映射保存异常代码供应器，调用方在映射异常时解析当前代码快照。
     *
     * @param map 指定的映射，允许为 null。
     * @return 添加了默认目标的映射。
     */
    public static Map<Class<? extends Exception>, Supplier<ServiceException.Code>> putDefaultDestination(
            Map<Class<? extends Exception>, Supplier<ServiceException.Code>> map
    ) {
        if (Objects.isNull(map)) {
            map = new HashMap<>();
        }

        map.put(PexpFormatException.class, ServiceExceptionCodeSuppliers.PEXP_FORMAT_ERROR);
        map.put(PexpTestException.class, ServiceExceptionCodeSuppliers.PEXP_TEST_ERROR);
        map.put(PermissionNotExistsException.class, ServiceExceptionCodeSuppliers.PERMISSION_NOT_EXISTS);
        map.put(RoleNotExistsException.class, ServiceExceptionCodeSuppliers.ROLE_NOT_EXISTS);
        map.put(UserNotExistsException.class, ServiceExceptionCodeSuppliers.USER_NOT_EXISTS);
        map.put(FilterException.class, ServiceExceptionCodeSuppliers.FILTER_FAILED);
        map.put(FilterMakeException.class, ServiceExceptionCodeSuppliers.FILTER_MAKE_FAILED);
        map.put(UnsupportedFilterTypeException.class, ServiceExceptionCodeSuppliers.FILTER_TYPE_UNSUPPORTED);
        map.put(FilterExecutionException.class, ServiceExceptionCodeSuppliers.FILTER_EXECUTION_FAILED);
        map.put(PermissionAlreadyExistsException.class, ServiceExceptionCodeSuppliers.PERMISSION_ALREADY_EXISTS);
        map.put(
                PermissionGroupAlreadyExistsException.class,
                ServiceExceptionCodeSuppliers.PERMISSION_GROUP_ALREADY_EXISTS
        );
        map.put(PermissionGroupNotExistsException.class, ServiceExceptionCodeSuppliers.PERMISSION_GROUP_NOT_EXISTS);
        map.put(PermissionGroupNotInScopeException.class, ServiceExceptionCodeSuppliers.PERMISSION_GROUP_NOT_IN_SCOPE);
        map.put(PexpAlreadyExistsException.class, ServiceExceptionCodeSuppliers.PEXP_ALREADY_EXISTS);
        map.put(PexpNotExistsException.class, ServiceExceptionCodeSuppliers.PEXP_NOT_EXISTS);
        map.put(ScopeNotExistsException.class, ServiceExceptionCodeSuppliers.SCOPE_NOT_EXISTS);
        return map;
    }

    private ServiceExceptionHelper() {
        throw new IllegalStateException("禁止外部实例化");
    }
}

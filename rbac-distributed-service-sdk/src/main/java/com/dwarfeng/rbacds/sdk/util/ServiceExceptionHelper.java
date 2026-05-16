package com.dwarfeng.rbacds.sdk.util;

import com.dwarfeng.rbacds.stack.exception.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 异常的帮助工具类。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public final class ServiceExceptionHelper {

    /**
     * 向指定的映射中添加 rbac-distributed-service 默认的目标映射。
     *
     * <p>
     * 该方法可以在配置类中快速的搭建目标映射。
     *
     * @param map 指定的映射，允许为 <code>null</code>。
     * @return 添加了默认目标的映射。
     */
    public static Map<Class<? extends Exception>, ServiceException.Code> putDefaultDestination(
            Map<Class<? extends Exception>, ServiceException.Code> map
    ) {
        if (Objects.isNull(map)) {
            map = new HashMap<>();
        }

        map.put(PexpFormatException.class, ServiceExceptionCodes.PEXP_FORMAT_ERROR);
        map.put(PexpTestException.class, ServiceExceptionCodes.PEXP_TEST_ERROR);
        map.put(PermissionNotExistsException.class, ServiceExceptionCodes.PERMISSION_NOT_EXISTS);
        map.put(RoleNotExistsException.class, ServiceExceptionCodes.ROLE_NOT_EXISTS);
        map.put(UserNotExistsException.class, ServiceExceptionCodes.USER_NOT_EXISTS);
        map.put(FilterException.class, ServiceExceptionCodes.FILTER_FAILED);
        map.put(FilterMakeException.class, ServiceExceptionCodes.FILTER_MAKE_FAILED);
        map.put(UnsupportedFilterTypeException.class, ServiceExceptionCodes.FILTER_TYPE_UNSUPPORTED);
        map.put(FilterExecutionException.class, ServiceExceptionCodes.FILTER_EXECUTION_FAILED);
        map.put(PermissionAlreadyExistsException.class, ServiceExceptionCodes.PERMISSION_ALREADY_EXISTS);
        map.put(PermissionGroupAlreadyExistsException.class, ServiceExceptionCodes.PERMISSION_GROUP_ALREADY_EXISTS);
        map.put(PermissionGroupNotExistsException.class, ServiceExceptionCodes.PERMISSION_GROUP_NOT_EXISTS);
        map.put(PermissionGroupNotInScopeException.class, ServiceExceptionCodes.PERMISSION_GROUP_NOT_IN_SCOPE);
        map.put(PexpAlreadyExistsException.class, ServiceExceptionCodes.PEXP_ALREADY_EXISTS);
        map.put(PexpNotExistsException.class, ServiceExceptionCodes.PEXP_NOT_EXISTS);
        map.put(ScopeNotExistsException.class, ServiceExceptionCodes.SCOPE_NOT_EXISTS);
        return map;
    }

    private ServiceExceptionHelper() {
        throw new IllegalStateException("禁止外部实例化");
    }
}

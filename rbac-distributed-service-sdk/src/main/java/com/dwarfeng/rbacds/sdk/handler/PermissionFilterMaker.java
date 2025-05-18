package com.dwarfeng.rbacds.sdk.handler;

import com.dwarfeng.rbacds.stack.exception.PermissionFilterException;
import com.dwarfeng.rbacds.stack.handler.PermissionFilter;

/**
 * 权限过滤器制造器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public interface PermissionFilterMaker {

    /**
     * 权限过滤器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 构造权限过滤器。
     *
     * @return 构造的权限过滤器。
     * @throws PermissionFilterException 权限过滤器异常。
     */
    PermissionFilter makePermissionFilter() throws PermissionFilterException;
}

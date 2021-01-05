package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;

/**
 * 权限过滤器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PermissionFilter {

    /**
     * 获取过滤器的标识符。
     *
     * @return 过滤器的标识符。
     */
    String getIdentifier();

    /**
     * 判断是否接受指定的权限。
     *
     * @param pattern    权限表达式的匹配模式。
     * @param permission 权限实体。
     * @return 是否接受指定的权限。
     * @throws Exception 测试过程中发生的任何异常。
     */
    boolean accept(String pattern, Permission permission) throws Exception;
}

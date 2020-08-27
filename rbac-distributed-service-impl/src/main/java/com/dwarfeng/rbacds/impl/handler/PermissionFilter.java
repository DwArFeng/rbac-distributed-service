package com.dwarfeng.rbacds.impl.handler;

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
     * @param pattern           权限表达式的匹配模式。
     * @param permissionContent 权限的内容。
     * @return 是否接受指定的权限。
     */
    boolean accept(String pattern, String permissionContent);
}

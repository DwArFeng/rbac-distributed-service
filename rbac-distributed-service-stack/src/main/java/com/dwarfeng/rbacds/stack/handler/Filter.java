package com.dwarfeng.rbacds.stack.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.exception.FilterException;

/**
 * 过滤器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface Filter {

    /**
     * 判断是否接受指定的权限。
     *
     * @param pattern    权限表达式的匹配模式。
     * @param permission 待过滤的权限实体。
     * @return 是否接受权限实体。
     * @throws FilterException 过滤器异常。
     */
    boolean accept(String pattern, Permission permission) throws FilterException;
}

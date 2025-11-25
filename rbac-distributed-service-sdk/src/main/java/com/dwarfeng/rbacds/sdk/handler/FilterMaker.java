package com.dwarfeng.rbacds.sdk.handler;

import com.dwarfeng.rbacds.stack.exception.FilterException;
import com.dwarfeng.rbacds.stack.handler.Filter;

/**
 * 过滤器制造器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface FilterMaker {

    /**
     * 过滤器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 构造过滤器。
     *
     * @return 构造的过滤器。
     * @throws FilterException 过滤器异常。
     */
    Filter makeFilter() throws FilterException;
}

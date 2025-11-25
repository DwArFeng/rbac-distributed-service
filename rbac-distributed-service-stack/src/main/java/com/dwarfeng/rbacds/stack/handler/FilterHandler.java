package com.dwarfeng.rbacds.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 过滤器处理器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface FilterHandler extends Handler {

    /**
     * 查询处理器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 处理器是否支持指定的类型。
     * @throws HandlerException 处理器异常。
     */
    boolean supportType(String type) throws HandlerException;

    /**
     * 构造过滤器。
     *
     * @param type 过滤器的类型。
     * @return 构造出的过滤器。
     * @throws HandlerException 处理器异常。
     */
    Filter make(String type) throws HandlerException;
}

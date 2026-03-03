package com.dwarfeng.rbacds.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 重置器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public interface Resetter {

    /**
     * 初始化重置器。
     *
     * <p>
     * 该方法会在重置器初始化后调用，请将 context 存放在重置器的字段中。<br>
     * 当重置器被触发后，执行上下文中的相应方法即可。
     *
     * @param context 重置器的上下文。
     */
    void init(Context context);

    /**
     * 重置开始。
     *
     * @throws HandlerException 处理器异常。
     */
    void start() throws HandlerException;

    /**
     * 重置结束。
     *
     * @throws HandlerException 处理器异常。
     */
    void stop() throws HandlerException;

    /**
     * 重置器上下文。
     *
     * @author DwArFeng
     * @since 1.8.0
     */
    interface Context {

        /**
         * 重置过滤器。
         *
         * @throws Exception 执行重置时抛出的任何异常。
         * @since 2.0.0
         */
        void resetFilter() throws Exception;

        /**
         * 重置分析结果。
         *
         * <p>
         * 清空所有分析相关的本地缓存，包括作用域角色权限分析、作用域用户权限分析、
         * 作用域权限分析、用户角色分析、权限用户分析等。
         *
         * @throws Exception 执行重置时抛出的任何异常。
         * @since 2.0.0
         */
        void resetAnalysis() throws Exception;
    }
}

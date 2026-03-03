package com.dwarfeng.rbacds.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 推送器处理器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public interface PushHandler extends Handler {

    /**
     * 过滤重置时执行的推送操作。
     *
     * @since 2.0.0
     */
    void filterReset() throws HandlerException;

    /**
     * 分析结果重置时执行的推送操作。
     *
     * @since 2.0.0
     */
    void analysisReset() throws HandlerException;
}

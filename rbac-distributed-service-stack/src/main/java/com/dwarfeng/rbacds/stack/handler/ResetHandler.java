package com.dwarfeng.rbacds.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.StartableHandler;

/**
 * 重置处理器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public interface ResetHandler extends StartableHandler {

    /**
     * 重置权限过滤。
     *
     * @throws HandlerException 处理器异常。
     */
    void resetPermissionFilter() throws HandlerException;
}

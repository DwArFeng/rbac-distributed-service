package com.dwarfeng.rbacds.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 重置器处理器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public interface ResetterHandler extends Handler {

    /**
     * 列出在用的全部重置器。
     *
     * @return 在用的全部重置器组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<Resetter> all() throws HandlerException;
}

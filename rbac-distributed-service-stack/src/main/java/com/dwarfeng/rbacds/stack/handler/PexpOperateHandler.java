package com.dwarfeng.rbacds.stack.handler;

import com.dwarfeng.rbacds.stack.bean.dto.PexpCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PexpCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PexpRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PexpUpdateInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 权限表达式操作处理器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface PexpOperateHandler extends Handler {

    /**
     * 创建权限表达式。
     *
     * @param info 创建信息。
     * @return 创建结果。
     * @throws HandlerException 处理器异常。
     */
    PexpCreateResult create(PexpCreateInfo info) throws HandlerException;

    /**
     * 更新权限表达式。
     *
     * @param info 更新信息。
     * @throws HandlerException 处理器异常。
     */
    void update(PexpUpdateInfo info) throws HandlerException;

    /**
     * 移除权限表达式。
     *
     * @param info 移除信息。
     * @throws HandlerException 处理器异常。
     */
    void remove(PexpRemoveInfo info) throws HandlerException;
}

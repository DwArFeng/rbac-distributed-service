package com.dwarfeng.rbacds.stack.handler;

import com.dwarfeng.rbacds.stack.bean.dto.PermissionCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionUpdateInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 权限操作处理器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface PermissionOperateHandler extends Handler {

    /**
     * 创建权限。
     *
     * @param info 创建信息。
     * @return 创建结果。
     * @throws HandlerException 处理器异常。
     */
    PermissionCreateResult create(PermissionCreateInfo info) throws HandlerException;

    /**
     * 更新权限。
     *
     * @param info 更新信息。
     * @throws HandlerException 处理器异常。
     */
    void update(PermissionUpdateInfo info) throws HandlerException;

    /**
     * 移除权限。
     *
     * @param info 移除信息。
     * @throws HandlerException 处理器异常。
     */
    void remove(PermissionRemoveInfo info) throws HandlerException;
}

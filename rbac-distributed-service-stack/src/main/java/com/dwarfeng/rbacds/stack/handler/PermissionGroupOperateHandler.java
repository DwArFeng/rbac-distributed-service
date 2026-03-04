package com.dwarfeng.rbacds.stack.handler;

import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupUpdateInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 权限组操作处理器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface PermissionGroupOperateHandler extends Handler {

    /**
     * 创建权限组。
     *
     * @param info 创建信息。
     * @return 创建结果。
     * @throws HandlerException 处理器异常。
     */
    PermissionGroupCreateResult create(PermissionGroupCreateInfo info) throws HandlerException;

    /**
     * 更新权限组。
     *
     * @param info 更新信息。
     * @throws HandlerException 处理器异常。
     */
    void update(PermissionGroupUpdateInfo info) throws HandlerException;

    /**
     * 移除权限组。
     *
     * @param info 移除信息。
     * @throws HandlerException 处理器异常。
     */
    void remove(PermissionGroupRemoveInfo info) throws HandlerException;
}

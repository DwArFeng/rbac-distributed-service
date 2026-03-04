package com.dwarfeng.rbacds.stack.handler;

import com.dwarfeng.rbacds.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import javax.annotation.Nullable;

/**
 * 查看处理器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface InspectHandler extends Handler {

    /**
     * 查询权限的用户信息。
     *
     * <p>
     * 该方法返回指定的权限用户查看信息对应的权限用户的查看结果。<br>
     * 如果指定的权限不存在，则返回 <code>null</code>。
     *
     * @param info 权限用户查看信息。
     * @return 权限用户查看结果。
     * @throws HandlerException 处理器异常。
     */
    @Nullable
    PermissionUserInspectResult inspectPermissionUser(PermissionUserInspectInfo info) throws HandlerException;

    /**
     * 查询角色的权限信息。
     *
     * <p>
     * 该方法返回指定的角色权限查看信息对应的角色权限的查看结果。<br>
     * 如果指定的角色不存在，则返回 <code>null</code>。
     *
     * @param info 角色权限查看信息。
     * @return 角色权限查看结果。
     * @throws HandlerException 处理器异常。
     */
    @Nullable
    RolePermissionInspectResult inspectRolePermission(RolePermissionInspectInfo info) throws HandlerException;

    /**
     * 查询用户的权限信息。
     *
     * <p>
     * 该方法返回指定的用户权限查看信息对应的用户权限的查看结果。<br>
     * 如果指定的用户不存在，则返回 <code>null</code>。
     *
     * @param info 用户权限查看信息。
     * @return 用户权限查看结果。
     * @throws HandlerException 处理器异常。
     */
    @Nullable
    UserPermissionInspectResult inspectUserPermission(UserPermissionInspectInfo info) throws HandlerException;
}

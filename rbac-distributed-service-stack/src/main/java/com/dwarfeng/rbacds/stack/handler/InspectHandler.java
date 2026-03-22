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
     * @see #inspectUserViewOfPermission(UserViewOfPermissionInspectInfo)
     * @deprecated 命名歧义，请使用 {@link #inspectUserViewOfPermission(UserViewOfPermissionInspectInfo)}
     */
    @Deprecated
    @Nullable
    PermissionUserInspectResult inspectPermissionUser(PermissionUserInspectInfo info) throws HandlerException;

    /**
     * 以权限为依据查看用户视图。
     *
     * <p>
     * 该方法返回以指定的权限为依据，查看用户视图的查看结果。<br>
     * 如果指定的权限不存在，则返回 <code>null</code>。
     *
     * @param info 以权限为依据的用户视图查看信息。
     * @return 以权限为依据的用户视图查看结果。
     * @throws HandlerException 处理器异常。
     * @since 2.0.2
     */
    @Nullable
    UserViewOfPermissionInspectResult inspectUserViewOfPermission(UserViewOfPermissionInspectInfo info)
            throws HandlerException;

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
     * @see #inspectPermissionViewOfRole(PermissionViewOfRoleInspectInfo)
     * @deprecated 命名歧义，请使用 {@link #inspectPermissionViewOfRole(PermissionViewOfRoleInspectInfo)}
     */
    @Deprecated
    @Nullable
    RolePermissionInspectResult inspectRolePermission(RolePermissionInspectInfo info) throws HandlerException;

    /**
     * 以角色为依据查看权限视图。
     *
     * <p>
     * 该方法返回以指定的角色为依据，查看权限视图的查看结果。<br>
     * 如果指定的角色不存在，则返回 <code>null</code>。
     *
     * @param info 以角色为依据的权限视图查看信息。
     * @return 以角色为依据的权限视图查看结果。
     * @throws HandlerException 处理器异常。
     * @since 2.0.2
     */
    @Nullable
    PermissionViewOfRoleInspectResult inspectPermissionViewOfRole(PermissionViewOfRoleInspectInfo info)
            throws HandlerException;

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
     * @see #inspectPermissionViewOfUser(PermissionViewOfUserInspectInfo)
     * @deprecated 命名歧义，请使用 {@link #inspectPermissionViewOfUser(PermissionViewOfUserInspectInfo)}
     */
    @Deprecated
    @Nullable
    UserPermissionInspectResult inspectUserPermission(UserPermissionInspectInfo info) throws HandlerException;

    /**
     * 以用户为依据查看权限视图。
     *
     * <p>
     * 该方法返回以指定的用户为依据，查看权限视图的查看结果。<br>
     * 如果指定的用户不存在，则返回 <code>null</code>。
     *
     * @param info 以用户为依据的权限视图查看信息。
     * @return 以用户为依据的权限视图查看结果。
     * @throws HandlerException 处理器异常。
     * @since 2.0.2
     */
    @Nullable
    PermissionViewOfUserInspectResult inspectPermissionViewOfUser(PermissionViewOfUserInspectInfo info)
            throws HandlerException;
}

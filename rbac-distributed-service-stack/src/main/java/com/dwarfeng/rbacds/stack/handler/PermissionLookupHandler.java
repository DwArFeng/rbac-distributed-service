package com.dwarfeng.rbacds.stack.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 权限查询处理器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface PermissionLookupHandler extends Handler {

    /**
     * 查询指定的用户对应的权限。
     *
     * @param userKey 指定的用户。
     * @return 指定的用户对应的权限组成的集合。
     * @throws HandlerException 处理器异常。
     */
    List<Permission> lookupForUser(StringIdKey userKey) throws HandlerException;

    /**
     * 查询指定的角色对应的权限。
     *
     * @param roleKey 指定的角色。
     * @return 指定的用户对应的权限组成的集合。
     * @throws HandlerException 处理器异常。
     */
    List<Permission> lookupForRole(StringIdKey roleKey) throws HandlerException;
}

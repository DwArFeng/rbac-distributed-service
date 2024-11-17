package com.dwarfeng.rbacds.stack.handler;

import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 用户查询处理器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface UserLookupHandler extends Handler {

    /**
     * 查询指定的权限对应的所有用户。
     *
     * @param permissionKey 指定的权限。
     * @return 指定的权限对应的用户组成的集合。
     * @throws HandlerException 处理器异常。
     */
    List<User> lookupForPermission(StringIdKey permissionKey) throws HandlerException;
}

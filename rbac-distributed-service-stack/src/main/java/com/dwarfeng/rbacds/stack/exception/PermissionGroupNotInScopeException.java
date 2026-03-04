package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 权限组不在作用域内异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionGroupNotInScopeException extends HandlerException {

    private static final long serialVersionUID = 8007574719119236605L;

    private final PermissionGroupKey permissionGroupKey;
    private final StringIdKey scopeKey;

    public PermissionGroupNotInScopeException(PermissionGroupKey permissionGroupKey, StringIdKey scopeKey) {
        this.permissionGroupKey = permissionGroupKey;
        this.scopeKey = scopeKey;
    }

    public PermissionGroupNotInScopeException(
            Throwable cause, PermissionGroupKey permissionGroupKey, StringIdKey scopeKey
    ) {
        super(cause);
        this.permissionGroupKey = permissionGroupKey;
        this.scopeKey = scopeKey;
    }

    @Override
    public String getMessage() {
        return "PermissionGroup " + permissionGroupKey + " is not in Scope " + scopeKey;
    }
}

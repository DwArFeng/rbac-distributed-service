package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.rbacds.stack.internal.i18n.StackMessages;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;

import java.io.Serial;

/**
 * 权限组不在作用域内异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionGroupNotInScopeException extends HandlerException {

    @Serial
    private static final long serialVersionUID = 1638475458128869336L;

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
        return StackMessages.message(
                StackMessageKey.EXCEPTION_PERMISSION_GROUP_NOT_IN_SCOPE, permissionGroupKey, scopeKey
        );
    }
}

package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.rbacds.stack.internal.i18n.StackMessages;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;

import java.io.Serial;

/**
 * 权限已存在异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionAlreadyExistsException extends HandlerException {

    @Serial
    private static final long serialVersionUID = -2968061634053511337L;

    private final PermissionKey permissionKey;

    public PermissionAlreadyExistsException(PermissionKey permissionKey) {
        this.permissionKey = permissionKey;
    }

    public PermissionAlreadyExistsException(Throwable cause, PermissionKey permissionKey) {
        super(cause);
        this.permissionKey = permissionKey;
    }

    @Override
    public String getMessage() {
        return StackMessages.message(StackMessageKey.EXCEPTION_PERMISSION_ALREADY_EXISTS, permissionKey);
    }
}

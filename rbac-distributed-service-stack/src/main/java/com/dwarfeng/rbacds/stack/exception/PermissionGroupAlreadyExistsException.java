package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.rbacds.stack.internal.i18n.StackMessages;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;

import java.io.Serial;

/**
 * 权限组已存在异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionGroupAlreadyExistsException extends HandlerException {

    @Serial
    private static final long serialVersionUID = 8160405039955311743L;

    private final PermissionGroupKey permissionGroupKey;

    public PermissionGroupAlreadyExistsException(PermissionGroupKey permissionGroupKey) {
        this.permissionGroupKey = permissionGroupKey;
    }

    public PermissionGroupAlreadyExistsException(Throwable cause, PermissionGroupKey permissionGroupKey) {
        super(cause);
        this.permissionGroupKey = permissionGroupKey;
    }

    @Override
    public String getMessage() {
        return StackMessages.message(StackMessageKey.EXCEPTION_PERMISSION_GROUP_ALREADY_EXISTS, permissionGroupKey);
    }
}

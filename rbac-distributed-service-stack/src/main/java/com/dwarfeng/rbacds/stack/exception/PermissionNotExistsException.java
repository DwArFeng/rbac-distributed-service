package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 权限不存在异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionNotExistsException extends HandlerException {

    private static final long serialVersionUID = -3734287562874381240L;

    private final PermissionKey permissionKey;

    public PermissionNotExistsException(PermissionKey permissionKey) {
        this.permissionKey = permissionKey;
    }

    public PermissionNotExistsException(Throwable cause, PermissionKey permissionKey) {
        super(cause);
        this.permissionKey = permissionKey;
    }

    @Override
    public String getMessage() {
        return "Permission " + permissionKey + " does not exist";
    }
}

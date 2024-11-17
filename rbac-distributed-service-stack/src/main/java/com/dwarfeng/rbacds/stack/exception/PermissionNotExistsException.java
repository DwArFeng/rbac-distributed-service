package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 权限不存在异常。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class PermissionNotExistsException extends HandlerException {

    private static final long serialVersionUID = -8109091147333006227L;

    private final StringIdKey permissionKey;

    public PermissionNotExistsException(StringIdKey permissionKey) {
        this.permissionKey = permissionKey;
    }

    public PermissionNotExistsException(Throwable cause, StringIdKey permissionKey) {
        super(cause);
        this.permissionKey = permissionKey;
    }

    @Override
    public String getMessage() {
        return "Permission " + permissionKey + " does not exist";
    }
}

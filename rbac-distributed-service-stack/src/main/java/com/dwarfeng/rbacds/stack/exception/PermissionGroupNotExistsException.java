package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 权限组不存在异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionGroupNotExistsException extends HandlerException {

    private static final long serialVersionUID = -4254591914983981031L;

    private final PermissionGroupKey permissionGroupKey;

    public PermissionGroupNotExistsException(PermissionGroupKey permissionGroupKey) {
        this.permissionGroupKey = permissionGroupKey;
    }

    public PermissionGroupNotExistsException(Throwable cause, PermissionGroupKey permissionGroupKey) {
        super(cause);
        this.permissionGroupKey = permissionGroupKey;
    }

    @Override
    public String getMessage() {
        return "PermissionGroup " + permissionGroupKey + " does not exist";
    }
}

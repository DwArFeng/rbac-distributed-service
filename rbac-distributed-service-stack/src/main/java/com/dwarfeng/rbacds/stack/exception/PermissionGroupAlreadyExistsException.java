package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 权限组已存在异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionGroupAlreadyExistsException extends HandlerException {

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
        return "PermissionGroup " + permissionGroupKey + " already exists";
    }
}

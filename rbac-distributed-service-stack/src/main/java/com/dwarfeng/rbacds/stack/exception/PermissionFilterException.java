package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 权限过滤器异常。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public class PermissionFilterException extends HandlerException {

    private static final long serialVersionUID = -8638659388991190062L;

    public PermissionFilterException() {
    }

    public PermissionFilterException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionFilterException(String message) {
        super(message);
    }

    public PermissionFilterException(Throwable cause) {
        super(cause);
    }
}

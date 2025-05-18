package com.dwarfeng.rbacds.stack.exception;

/**
 * 权限过滤器构造异常。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public class PermissionFilterMakeException extends PermissionFilterException {

    private static final long serialVersionUID = -5846724427215115353L;

    public PermissionFilterMakeException() {
    }

    public PermissionFilterMakeException(String message) {
        super(message);
    }

    public PermissionFilterMakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionFilterMakeException(Throwable cause) {
        super(cause);
    }
}

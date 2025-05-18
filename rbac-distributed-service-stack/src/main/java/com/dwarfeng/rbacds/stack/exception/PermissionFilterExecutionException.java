package com.dwarfeng.rbacds.stack.exception;

/**
 * 权限过滤器执行异常。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public class PermissionFilterExecutionException extends PermissionFilterException {

    private static final long serialVersionUID = 8510356884641232864L;

    public PermissionFilterExecutionException() {
    }

    public PermissionFilterExecutionException(String message) {
        super(message);
    }

    public PermissionFilterExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionFilterExecutionException(Throwable cause) {
        super(cause);
    }
}

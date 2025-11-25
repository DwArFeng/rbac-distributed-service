package com.dwarfeng.rbacds.stack.exception;

/**
 * 过滤器构造异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FilterMakeException extends FilterException {

    private static final long serialVersionUID = -16520337556346559L;

    public FilterMakeException() {
    }

    public FilterMakeException(String message) {
        super(message);
    }

    public FilterMakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilterMakeException(Throwable cause) {
        super(cause);
    }
}

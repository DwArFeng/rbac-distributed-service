package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 过滤器异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FilterException extends HandlerException {

    private static final long serialVersionUID = -5226176154146395648L;

    public FilterException() {
    }

    public FilterException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilterException(String message) {
        super(message);
    }

    public FilterException(Throwable cause) {
        super(cause);
    }
}

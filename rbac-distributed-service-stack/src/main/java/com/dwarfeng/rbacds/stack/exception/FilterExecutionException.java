package com.dwarfeng.rbacds.stack.exception;

/**
 * 过滤器执行异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FilterExecutionException extends FilterException {

    private static final long serialVersionUID = -6071777584763649202L;

    public FilterExecutionException() {
    }

    public FilterExecutionException(String message) {
        super(message);
    }

    public FilterExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilterExecutionException(Throwable cause) {
        super(cause);
    }
}

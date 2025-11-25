package com.dwarfeng.rbacds.stack.exception;

/**
 * 不支持的过滤器类型。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class UnsupportedFilterTypeException extends FilterException {

    private static final long serialVersionUID = -1687771654019312979L;

    private final String type;

    public UnsupportedFilterTypeException(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return "不支持的过滤器类型: " + type;
    }
}

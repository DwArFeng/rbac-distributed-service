package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.rbacds.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.rbacds.stack.internal.i18n.StackMessages;

import java.io.Serial;

/**
 * 不支持的过滤器类型。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class UnsupportedFilterTypeException extends FilterException {

    @Serial
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
        return StackMessages.message(StackMessageKey.EXCEPTION_UNSUPPORTED_FILTER_TYPE, type);
    }
}

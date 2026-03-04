package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 作用域不存在异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class ScopeNotExistsException extends HandlerException {

    private static final long serialVersionUID = 3096362163716944200L;

    private final StringIdKey scopeKey;

    public ScopeNotExistsException(StringIdKey scopeKey) {
        this.scopeKey = scopeKey;
    }

    public ScopeNotExistsException(Throwable cause, StringIdKey scopeKey) {
        super(cause);
        this.scopeKey = scopeKey;
    }

    @Override
    public String getMessage() {
        return "Scope " + scopeKey + " does not exist";
    }
}

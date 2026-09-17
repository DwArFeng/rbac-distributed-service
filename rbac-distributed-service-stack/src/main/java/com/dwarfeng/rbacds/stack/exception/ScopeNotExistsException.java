package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.rbacds.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.rbacds.stack.internal.i18n.StackMessages;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;

import java.io.Serial;

/**
 * 作用域不存在异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class ScopeNotExistsException extends HandlerException {

    @Serial
    private static final long serialVersionUID = 5028798888093142228L;

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
        return StackMessages.message(StackMessageKey.EXCEPTION_SCOPE_NOT_EXISTS, scopeKey);
    }
}

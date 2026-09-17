package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.rbacds.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.rbacds.stack.internal.i18n.StackMessages;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;

import java.io.Serial;

/**
 * 用户不存在异常。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class RoleNotExistsException extends HandlerException {

    @Serial
    private static final long serialVersionUID = -6679945363943885401L;

    private final StringIdKey roleKey;

    public RoleNotExistsException(StringIdKey roleKey) {
        this.roleKey = roleKey;
    }

    public RoleNotExistsException(Throwable cause, StringIdKey roleKey) {
        super(cause);
        this.roleKey = roleKey;
    }

    @Override
    public String getMessage() {
        return StackMessages.message(StackMessageKey.EXCEPTION_ROLE_NOT_EXISTS, roleKey);
    }
}

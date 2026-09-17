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
public class UserNotExistsException extends HandlerException {

    @Serial
    private static final long serialVersionUID = -2217400662730166102L;

    private final StringIdKey userKey;

    public UserNotExistsException(StringIdKey userKey) {
        this.userKey = userKey;
    }

    public UserNotExistsException(Throwable cause, StringIdKey userKey) {
        super(cause);
        this.userKey = userKey;
    }

    @Override
    public String getMessage() {
        return StackMessages.message(StackMessageKey.EXCEPTION_USER_NOT_EXISTS, userKey);
    }
}

package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 用户不存在异常。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class UserNotExistsException extends HandlerException {

    private static final long serialVersionUID = 1785016778025100015L;

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
        return "User " + userKey + " does not exist";
    }
}

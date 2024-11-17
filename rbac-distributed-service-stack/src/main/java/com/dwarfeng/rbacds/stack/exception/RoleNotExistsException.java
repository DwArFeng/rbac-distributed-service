package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 用户不存在异常。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class RoleNotExistsException extends HandlerException {

    private static final long serialVersionUID = 975816910312846963L;

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
        return "Role " + roleKey + " does not exist";
    }
}

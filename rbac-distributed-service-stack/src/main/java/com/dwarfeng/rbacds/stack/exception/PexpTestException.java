package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 权限表达式测试异常。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
public class PexpTestException extends HandlerException {

    private static final long serialVersionUID = -947506398580333541L;

    private final Pexp pexp;
    private final Permission permission;

    public PexpTestException(Pexp pexp, Permission permission) {
        this.pexp = pexp;
        this.permission = permission;
    }

    public PexpTestException(Throwable cause, Pexp pexp, Permission permission) {
        super(cause);
        this.pexp = pexp;
        this.permission = permission;
    }

    @Override
    public String getMessage() {
        return "Exception occurred when testing pexp. pexp: " + pexp.toString() +
                ", permission: " + permission.toString();
    }
}

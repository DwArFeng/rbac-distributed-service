package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 权限表达式格式异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class PexpFormatException extends HandlerException {

    private static final long serialVersionUID = -947506398580333541L;

    private final Pexp pexp;

    public PexpFormatException(Pexp pexp) {
        this.pexp = pexp;
    }

    public PexpFormatException(String message, Throwable cause, Pexp pexp) {
        super(message, cause);
        this.pexp = pexp;
    }

    public PexpFormatException(String message, Pexp pexp) {
        super(message);
        this.pexp = pexp;
    }

    public PexpFormatException(Throwable cause, Pexp pexp) {
        super(cause);
        this.pexp = pexp;
    }

    @Override
    public String getMessage() {
        return "Invalid pexp format " + pexp.toString();
    }
}

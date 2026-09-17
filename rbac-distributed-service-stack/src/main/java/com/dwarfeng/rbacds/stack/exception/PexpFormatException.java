package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.rbacds.stack.internal.i18n.StackMessages;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;

import java.io.Serial;

/**
 * 权限表达式格式异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class PexpFormatException extends HandlerException {

    @Serial
    private static final long serialVersionUID = -7513972492748916727L;

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
        return StackMessages.message(StackMessageKey.EXCEPTION_PEXP_FORMAT_INVALID, pexp);
    }
}

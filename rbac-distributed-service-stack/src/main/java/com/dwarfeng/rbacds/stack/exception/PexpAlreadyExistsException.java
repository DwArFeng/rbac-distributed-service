package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.rbacds.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.rbacds.stack.internal.i18n.StackMessages;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;

import java.io.Serial;

/**
 * 权限表达式已存在异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PexpAlreadyExistsException extends HandlerException {

    @Serial
    private static final long serialVersionUID = 7594125816891285647L;

    private final PexpKey pexpKey;

    public PexpAlreadyExistsException(PexpKey pexpKey) {
        this.pexpKey = pexpKey;
    }

    public PexpAlreadyExistsException(Throwable cause, PexpKey pexpKey) {
        super(cause);
        this.pexpKey = pexpKey;
    }

    @Override
    public String getMessage() {
        return StackMessages.message(StackMessageKey.EXCEPTION_PEXP_ALREADY_EXISTS, pexpKey);
    }
}

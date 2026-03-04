package com.dwarfeng.rbacds.stack.exception;

import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 权限表达式不存在异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PexpNotExistsException extends HandlerException {

    private static final long serialVersionUID = -696911931814756841L;

    private final PexpKey pexpKey;

    public PexpNotExistsException(PexpKey pexpKey) {
        this.pexpKey = pexpKey;
    }

    public PexpNotExistsException(Throwable cause, PexpKey pexpKey) {
        super(cause);
        this.pexpKey = pexpKey;
    }

    @Override
    public String getMessage() {
        return "Pexp " + pexpKey + " does not exist";
    }
}

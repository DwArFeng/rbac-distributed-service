package com.dwarfeng.rbacds.sdk.util;

/**
 * 约束类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class Constraints {

    /**
     * ID的长度约束。
     */
    public static final int LENGTH_ID = 100;
    /**
     * 备注的长度约束。
     */
    public static final int LENGTH_REMARK = 100;
    /**
     * 内容的长度约束。
     */
    public static final int LENGTH_CONTENT = 150;
    /**
     * 密码的长度（此处指加密过的密码的长度）。
     */
    public static final int LENGTH_PASSWORD = 60;

    private Constraints() {
        throw new IllegalStateException("禁止实例化");
    }
}

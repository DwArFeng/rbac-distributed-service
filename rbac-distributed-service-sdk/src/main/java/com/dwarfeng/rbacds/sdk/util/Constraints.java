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
    public static final int LENGTH_ID = 150;

    /**
     * 备注的长度约束。
     */
    public static final int LENGTH_REMARK = 200;

    /**
     * 内容的长度约束。
     */
    public static final int LENGTH_CONTENT = 150;

    /**
     * 名称的长度。
     */
    public static final int LENGTH_NAME = 150;

    /**
     * 类型的长度约束。
     *
     * @since 1.8.0
     */
    public static final int LENGTH_TYPE = 50;

    private Constraints() {
        throw new IllegalStateException("禁止实例化");
    }
}

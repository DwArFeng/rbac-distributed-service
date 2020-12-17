package com.dwarfeng.rbacds.impl.service.telqos;

/**
 * Telqos 工具类。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public final class TelqosUtil {

    /**
     * 获取数字位数。
     *
     * @param num 指定的数字。
     * @return 数字的位数。
     */
    public static int numLength(int num) {
        return Integer.toString(num).length();
    }

    private TelqosUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}

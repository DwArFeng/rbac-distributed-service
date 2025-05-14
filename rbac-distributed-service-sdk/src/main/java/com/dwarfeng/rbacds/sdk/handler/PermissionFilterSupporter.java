package com.dwarfeng.rbacds.sdk.handler;

/**
 * 权限过滤器支持器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public interface PermissionFilterSupporter {

    /**
     * 提供类型。
     *
     * @return 类型。
     */
    String provideType();

    /**
     * 提供标签。
     *
     * @return 标签。
     */
    String provideLabel();

    /**
     * 提供描述。
     *
     * @return 描述。
     */
    String provideDescription();

    /**
     * 提供示例匹配模式。
     *
     * @return 示例匹配模式。
     */
    String provideExamplePattern();
}

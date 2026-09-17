package com.dwarfeng.rbacds.stack.internal.i18n;

import static com.dwarfeng.rbacds.stack.internal.i18n.StackMessages.Catalog.STACK;

/**
 * Stack 模块消息键。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public enum StackMessageKey {

    EXCEPTION_PERMISSION_ALREADY_EXISTS(STACK, "exception.permission_already_exists"),
    EXCEPTION_PERMISSION_GROUP_ALREADY_EXISTS(STACK, "exception.permission_group_already_exists"),
    EXCEPTION_PERMISSION_GROUP_NOT_EXISTS(STACK, "exception.permission_group_not_exists"),
    EXCEPTION_PERMISSION_GROUP_NOT_IN_SCOPE(STACK, "exception.permission_group_not_in_scope"),
    EXCEPTION_PERMISSION_NOT_EXISTS(STACK, "exception.permission_not_exists"),
    EXCEPTION_PEXP_ALREADY_EXISTS(STACK, "exception.pexp_already_exists"),
    EXCEPTION_PEXP_FORMAT_INVALID(STACK, "exception.pexp_format_invalid"),
    EXCEPTION_PEXP_NOT_EXISTS(STACK, "exception.pexp_not_exists"),
    EXCEPTION_PEXP_TEST_FAILED(STACK, "exception.pexp_test_failed"),
    EXCEPTION_ROLE_NOT_EXISTS(STACK, "exception.role_not_exists"),
    EXCEPTION_SCOPE_NOT_EXISTS(STACK, "exception.scope_not_exists"),
    EXCEPTION_UNSUPPORTED_FILTER_TYPE(STACK, "exception.unsupported_filter_type"),
    EXCEPTION_USER_NOT_EXISTS(STACK, "exception.user_not_exists");

    private final StackMessages.Catalog catalog;
    private final String key;

    StackMessageKey(StackMessages.Catalog catalog, String key) {
        this.catalog = catalog;
        this.key = key;
    }

    StackMessages.Catalog catalog() {
        return catalog;
    }

    /**
     * 获取消息键。
     *
     * @return 消息键。
     */
    public String key() {
        return key;
    }
}

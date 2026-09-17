package com.dwarfeng.rbacds.sdk.internal.i18n;

import static com.dwarfeng.rbacds.sdk.internal.i18n.SdkMessages.Catalog.SDK;

/**
 * SDK 模块消息键。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public enum SdkMessageKey {

    SERVICE_EXCEPTION_FILTER_EXECUTION_FAILED(SDK, "service_exception.filter_execution_failed"),
    SERVICE_EXCEPTION_FILTER_FAILED(SDK, "service_exception.filter_failed"),
    SERVICE_EXCEPTION_FILTER_MAKE_FAILED(SDK, "service_exception.filter_make_failed"),
    SERVICE_EXCEPTION_FILTER_TYPE_UNSUPPORTED(SDK, "service_exception.filter_type_unsupported"),
    SERVICE_EXCEPTION_PERMISSION_ALREADY_EXISTS(SDK, "service_exception.permission_already_exists"),
    SERVICE_EXCEPTION_PERMISSION_GROUP_ALREADY_EXISTS(SDK, "service_exception.permission_group_already_exists"),
    SERVICE_EXCEPTION_PERMISSION_GROUP_NOT_EXISTS(SDK, "service_exception.permission_group_not_exists"),
    SERVICE_EXCEPTION_PERMISSION_GROUP_NOT_IN_SCOPE(SDK, "service_exception.permission_group_not_in_scope"),
    SERVICE_EXCEPTION_PERMISSION_NOT_EXISTS(SDK, "service_exception.permission_not_exists"),
    SERVICE_EXCEPTION_PEXP_ALREADY_EXISTS(SDK, "service_exception.pexp_already_exists"),
    SERVICE_EXCEPTION_PEXP_FORMAT_ERROR(SDK, "service_exception.pexp_format_error"),
    SERVICE_EXCEPTION_PEXP_NOT_EXISTS(SDK, "service_exception.pexp_not_exists"),
    SERVICE_EXCEPTION_PEXP_TEST_ERROR(SDK, "service_exception.pexp_test_error"),
    SERVICE_EXCEPTION_ROLE_NOT_EXISTS(SDK, "service_exception.role_not_exists"),
    SERVICE_EXCEPTION_SCOPE_NOT_EXISTS(SDK, "service_exception.scope_not_exists"),
    SERVICE_EXCEPTION_USER_NOT_EXISTS(SDK, "service_exception.user_not_exists");

    private final SdkMessages.Catalog catalog;
    private final String key;

    SdkMessageKey(SdkMessages.Catalog catalog, String key) {
        this.catalog = catalog;
        this.key = key;
    }

    SdkMessages.Catalog catalog() {
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

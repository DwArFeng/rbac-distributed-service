package com.dwarfeng.rbacds.sdk.exception;

import com.dwarfeng.rbacds.sdk.internal.i18n.SdkMessageKey;
import com.dwarfeng.rbacds.sdk.internal.i18n.SdkMessages;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;

import java.util.function.Supplier;

/**
 * RBAC Distributed Service 模块服务异常代码供应器。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public final class ServiceExceptionCodeSuppliers {

    private static volatile int EXCEPTION_CODE_OFFSET = 500;

    public static final Supplier<ServiceException.Code> USER_NOT_EXISTS =
            () -> code(0, SdkMessageKey.SERVICE_EXCEPTION_USER_NOT_EXISTS);
    public static final Supplier<ServiceException.Code> PEXP_FORMAT_ERROR =
            () -> code(10, SdkMessageKey.SERVICE_EXCEPTION_PEXP_FORMAT_ERROR);
    public static final Supplier<ServiceException.Code> PERMISSION_NOT_EXISTS =
            () -> code(20, SdkMessageKey.SERVICE_EXCEPTION_PERMISSION_NOT_EXISTS);
    public static final Supplier<ServiceException.Code> PEXP_TEST_ERROR =
            () -> code(30, SdkMessageKey.SERVICE_EXCEPTION_PEXP_TEST_ERROR);
    public static final Supplier<ServiceException.Code> ROLE_NOT_EXISTS =
            () -> code(40, SdkMessageKey.SERVICE_EXCEPTION_ROLE_NOT_EXISTS);
    public static final Supplier<ServiceException.Code> FILTER_FAILED =
            () -> code(50, SdkMessageKey.SERVICE_EXCEPTION_FILTER_FAILED);
    public static final Supplier<ServiceException.Code> FILTER_MAKE_FAILED =
            () -> code(51, SdkMessageKey.SERVICE_EXCEPTION_FILTER_MAKE_FAILED);
    public static final Supplier<ServiceException.Code> FILTER_TYPE_UNSUPPORTED =
            () -> code(52, SdkMessageKey.SERVICE_EXCEPTION_FILTER_TYPE_UNSUPPORTED);
    public static final Supplier<ServiceException.Code> FILTER_EXECUTION_FAILED =
            () -> code(53, SdkMessageKey.SERVICE_EXCEPTION_FILTER_EXECUTION_FAILED);
    public static final Supplier<ServiceException.Code> PERMISSION_ALREADY_EXISTS =
            () -> code(60, SdkMessageKey.SERVICE_EXCEPTION_PERMISSION_ALREADY_EXISTS);
    public static final Supplier<ServiceException.Code> PERMISSION_GROUP_ALREADY_EXISTS =
            () -> code(70, SdkMessageKey.SERVICE_EXCEPTION_PERMISSION_GROUP_ALREADY_EXISTS);
    public static final Supplier<ServiceException.Code> PERMISSION_GROUP_NOT_EXISTS =
            () -> code(80, SdkMessageKey.SERVICE_EXCEPTION_PERMISSION_GROUP_NOT_EXISTS);
    public static final Supplier<ServiceException.Code> PERMISSION_GROUP_NOT_IN_SCOPE =
            () -> code(90, SdkMessageKey.SERVICE_EXCEPTION_PERMISSION_GROUP_NOT_IN_SCOPE);
    public static final Supplier<ServiceException.Code> PEXP_ALREADY_EXISTS =
            () -> code(100, SdkMessageKey.SERVICE_EXCEPTION_PEXP_ALREADY_EXISTS);
    public static final Supplier<ServiceException.Code> PEXP_NOT_EXISTS =
            () -> code(110, SdkMessageKey.SERVICE_EXCEPTION_PEXP_NOT_EXISTS);
    public static final Supplier<ServiceException.Code> SCOPE_NOT_EXISTS =
            () -> code(120, SdkMessageKey.SERVICE_EXCEPTION_SCOPE_NOT_EXISTS);

    private static ServiceException.Code code(int code, SdkMessageKey key) {
        return new ServiceException.Code(EXCEPTION_CODE_OFFSET + code, SdkMessages.message(key));
    }

    public static int getExceptionCodeOffset() {
        return EXCEPTION_CODE_OFFSET;
    }

    public static void setExceptionCodeOffset(int exceptionCodeOffset) {
        EXCEPTION_CODE_OFFSET = exceptionCodeOffset;
    }

    private ServiceExceptionCodeSuppliers() {
        throw new IllegalStateException("禁止实例化");
    }
}

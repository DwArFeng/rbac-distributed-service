package com.dwarfeng.rbacds.sdk.util;

import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * 服务异常代码。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ServiceExceptionCodes {

    private static int EXCEPTION_CODE_OFFSET = 500;

    /**
     * 用户不存在。
     */
    public static final ServiceException.Code USER_NOT_EXISTS =
            new ServiceException.Code(offset(0), "user not exists");

    /**
     * 权限表达式格式错误。
     */
    public static final ServiceException.Code PEXP_FORMAT_ERROR =
            new ServiceException.Code(offset(10), "pexp format error");

    /**
     * 权限不存在。
     */
    public static final ServiceException.Code PERMISSION_NOT_EXISTS =
            new ServiceException.Code(offset(20), "permission not exists");

    /**
     * 权限表达式测试错误。
     */
    public static final ServiceException.Code PEXP_TEST_ERROR =
            new ServiceException.Code(offset(30), "pexp test error");

    /**
     * 角色不存在。
     */
    public static final ServiceException.Code ROLE_NOT_EXISTS =
            new ServiceException.Code(offset(40), "role not exists");

    public static final ServiceException.Code PERMISSION_FILTER_FAILED =
            new ServiceException.Code(offset(50), "permission filter failed");
    public static final ServiceException.Code PERMISSION_FILTER_MAKE_FAILED =
            new ServiceException.Code(offset(51), "permission filter make failed");
    public static final ServiceException.Code PERMISSION_FILTER_TYPE_UNSUPPORTED =
            new ServiceException.Code(offset(52), "permission filter type unsupported");
    public static final ServiceException.Code PERMISSION_FILTER_EXECUTION_FAILED =
            new ServiceException.Code(offset(53), "permission filter execution failed");

    private static int offset(int i) {
        return EXCEPTION_CODE_OFFSET + i;
    }

    /**
     * 获取异常代号的偏移量。
     *
     * @return 异常代号的偏移量。
     */
    public static int getExceptionCodeOffset() {
        return EXCEPTION_CODE_OFFSET;
    }

    /**
     * 设置异常代号的偏移量。
     *
     * @param exceptionCodeOffset 指定的异常代号的偏移量。
     */
    public static void setExceptionCodeOffset(int exceptionCodeOffset) {
        // 设置 EXCEPTION_CODE_OFFSET 的值。
        EXCEPTION_CODE_OFFSET = exceptionCodeOffset;

        // 以新的 EXCEPTION_CODE_OFFSET 为基准，更新异常代码的值。
        USER_NOT_EXISTS.setCode(offset(0));
        PEXP_FORMAT_ERROR.setCode(offset(10));
        PERMISSION_NOT_EXISTS.setCode(offset(20));
        PEXP_TEST_ERROR.setCode(offset(30));
        ROLE_NOT_EXISTS.setCode(offset(40));
        PERMISSION_FILTER_FAILED.setCode(offset(50));
        PERMISSION_FILTER_MAKE_FAILED.setCode(offset(51));
        PERMISSION_FILTER_TYPE_UNSUPPORTED.setCode(offset(52));
        PERMISSION_FILTER_EXECUTION_FAILED.setCode(offset(53));
    }

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}

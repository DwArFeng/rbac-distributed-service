package com.dwarfeng.rbacds.stack.exception;

/**
 * 不支持的权限过滤器类型。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public class UnsupportedPermissionFilterTypeException extends PermissionFilterException {

    private static final long serialVersionUID = -3322048867803265208L;
    private final String type;

    public UnsupportedPermissionFilterTypeException(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return "不支持的权限过滤器类型: " + type;
    }
}

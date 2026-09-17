package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.FastJsonPermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupCreateResult;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;

import java.io.Serial;
import java.util.Objects;

/**
 * FastJson 权限组创建结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonPermissionGroupCreateResult implements Bean {

    @Serial
    private static final long serialVersionUID = 3622372284891236028L;

    public static FastJsonPermissionGroupCreateResult of(PermissionGroupCreateResult permissionGroupCreateResult) {
        if (Objects.isNull(permissionGroupCreateResult)) {
            return null;
        } else {
            return new FastJsonPermissionGroupCreateResult(
                    FastJsonPermissionGroupKey.of(permissionGroupCreateResult.getKey())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonPermissionGroupKey key;

    public FastJsonPermissionGroupCreateResult() {
    }

    public FastJsonPermissionGroupCreateResult(FastJsonPermissionGroupKey key) {
        this.key = key;
    }

    public FastJsonPermissionGroupKey getKey() {
        return key;
    }

    public void setKey(FastJsonPermissionGroupKey key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "FastJsonPermissionGroupCreateResult{" +
                "key=" + key +
                '}';
    }
}

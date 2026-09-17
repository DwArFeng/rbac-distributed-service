package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.FastJsonPermissionKey;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionCreateResult;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;

import java.io.Serial;
import java.util.Objects;

/**
 * FastJson 权限创建结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonPermissionCreateResult implements Bean {

    @Serial
    private static final long serialVersionUID = 7227149523862002910L;

    public static FastJsonPermissionCreateResult of(PermissionCreateResult permissionCreateResult) {
        if (Objects.isNull(permissionCreateResult)) {
            return null;
        } else {
            return new FastJsonPermissionCreateResult(
                    FastJsonPermissionKey.of(permissionCreateResult.getKey())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonPermissionKey key;

    public FastJsonPermissionCreateResult() {
    }

    public FastJsonPermissionCreateResult(FastJsonPermissionKey key) {
        this.key = key;
    }

    public FastJsonPermissionKey getKey() {
        return key;
    }

    public void setKey(FastJsonPermissionKey key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "FastJsonPermissionCreateResult{" +
                "key=" + key +
                '}';
    }
}

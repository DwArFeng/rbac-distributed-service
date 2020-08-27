package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 权限。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonPermission implements Bean {

    private static final long serialVersionUID = -1611452886001308613L;

    public static FastJsonPermission of(Permission permission) {
        if (Objects.isNull(permission)) {
            return null;
        }
        return new FastJsonPermission(
                FastJsonStringIdKey.of(permission.getKey()),
                permission.getRemark()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "remark", ordinal = 2)
    private String remark;

    public FastJsonPermission() {
    }

    public FastJsonPermission(FastJsonStringIdKey key, String remark) {
        this.key = key;
        this.remark = remark;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
        this.key = key;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonPermission{" +
                "key=" + key +
                ", remark='" + remark + '\'' +
                '}';
    }
}

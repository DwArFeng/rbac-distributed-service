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
                FastJsonStringIdKey.of(permission.getGroupKey()),
                permission.getName(),
                permission.getRemark()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "group_key", ordinal = 2)
    private FastJsonStringIdKey groupKey;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    public FastJsonPermission() {
    }

    public FastJsonPermission(FastJsonStringIdKey key, FastJsonStringIdKey groupKey, String name, String remark) {
        this.key = key;
        this.groupKey = groupKey;
        this.name = name;
        this.remark = remark;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
        this.key = key;
    }

    public FastJsonStringIdKey getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(FastJsonStringIdKey groupKey) {
        this.groupKey = groupKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", groupKey=" + groupKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

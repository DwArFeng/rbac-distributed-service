package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 角色。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonRole implements Bean {

    private static final long serialVersionUID = -4501705687291160541L;

    public static FastJsonRole of(Role role) {
        if (Objects.isNull(role)) {
            return null;
        }
        return new FastJsonRole(
                FastJsonStringIdKey.of(role.getKey()),
                FastJsonStringIdKey.of(role.getGroupKey()),
                role.getName(),
                role.isEnabled(),
                role.getRemark()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "group_key", ordinal = 2)
    private FastJsonStringIdKey groupKey;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "enabled", ordinal = 4)
    private boolean enabled;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    public FastJsonRole() {
    }

    public FastJsonRole(FastJsonStringIdKey key, FastJsonStringIdKey groupKey, String name, boolean enabled, String remark) {
        this.key = key;
        this.groupKey = groupKey;
        this.name = name;
        this.enabled = enabled;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonRole{" +
                "key=" + key +
                ", groupKey=" + groupKey +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                '}';
    }
}

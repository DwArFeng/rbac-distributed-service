package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;
import com.dwarfeng.subgrade.web.sdk.bean.key.FastJsonStringIdKey;

import java.io.Serial;
import java.util.Objects;

/**
 * FastJson 角色。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonRole implements Bean {

    @Serial
    private static final long serialVersionUID = 1355042432707787830L;

    public static FastJsonRole of(Role role) {
        if (Objects.isNull(role)) {
            return null;
        }
        return new FastJsonRole(
                FastJsonStringIdKey.of(role.getKey()),
                role.getName(),
                role.isEnabled(),
                role.getRemark()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "enabled", ordinal = 3)
    private boolean enabled;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    public FastJsonRole() {
    }

    public FastJsonRole(FastJsonStringIdKey key, String name, boolean enabled, String remark) {
        this.key = key;
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
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                '}';
    }
}

package com.dwarfeng.rbacds.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 角色。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Role implements Entity<StringIdKey> {

    private static final long serialVersionUID = 3954346769079299113L;

    private StringIdKey key;
    private String name;
    private boolean enabled;
    private String remark;

    public Role() {
    }

    public Role(StringIdKey key, String name, boolean enabled, String remark) {
        this.key = key;
        this.name = name;
        this.enabled = enabled;
        this.remark = remark;
    }

    @Override
    public StringIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(StringIdKey key) {
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
        return "Role{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                '}';
    }
}

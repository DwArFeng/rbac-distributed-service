package com.dwarfeng.rbacds.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 权限组。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
public class PermissionGroup implements Entity<StringIdKey> {

    private static final long serialVersionUID = 5082174087000324332L;

    private StringIdKey key;
    private StringIdKey parentKey;
    private String name;
    private String remark;

    public PermissionGroup() {
    }

    public PermissionGroup(StringIdKey key, StringIdKey parentKey, String name, String remark) {
        this.key = key;
        this.parentKey = parentKey;
        this.name = name;
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

    public StringIdKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(StringIdKey parentKey) {
        this.parentKey = parentKey;
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
        return "PermissionGroup{" +
                "key=" + key +
                ", parentKey=" + parentKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

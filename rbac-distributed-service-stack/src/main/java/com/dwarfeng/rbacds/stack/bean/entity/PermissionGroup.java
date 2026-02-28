package com.dwarfeng.rbacds.stack.bean.entity;

import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

/**
 * 权限组。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionGroup implements Entity<PermissionGroupKey> {

    private static final long serialVersionUID = 5540724740545327297L;

    private PermissionGroupKey key;
    private PermissionGroupKey parentKey;
    private String name;
    private String remark;

    public PermissionGroup() {
    }

    public PermissionGroup(PermissionGroupKey key, PermissionGroupKey parentKey, String name, String remark) {
        this.key = key;
        this.parentKey = parentKey;
        this.name = name;
        this.remark = remark;
    }

    @Override
    public PermissionGroupKey getKey() {
        return key;
    }

    @Override
    public void setKey(PermissionGroupKey key) {
        this.key = key;
    }

    public PermissionGroupKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(PermissionGroupKey parentKey) {
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

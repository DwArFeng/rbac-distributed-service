package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;

import java.io.Serial;

/**
 * 权限组更新信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionGroupUpdateInfo implements Dto {

    @Serial
    private static final long serialVersionUID = 3836043687693890688L;

    private PermissionGroupKey key;
    private PermissionGroupKey parentKey;
    private String name;
    private String remark;

    public PermissionGroupUpdateInfo() {
    }

    public PermissionGroupUpdateInfo(PermissionGroupKey key, PermissionGroupKey parentKey, String name, String remark) {
        this.key = key;
        this.parentKey = parentKey;
        this.name = name;
        this.remark = remark;
    }

    public PermissionGroupKey getKey() {
        return key;
    }

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
        return "PermissionGroupUpdateInfo{" +
                "key=" + key +
                ", parentKey=" + parentKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

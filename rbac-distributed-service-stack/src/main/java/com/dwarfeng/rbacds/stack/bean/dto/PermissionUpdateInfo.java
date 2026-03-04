package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 权限更新信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionUpdateInfo implements Dto {

    private static final long serialVersionUID = -3329894233403241135L;

    private PermissionKey key;
    private PermissionGroupKey groupKey;
    private String name;
    private String remark;
    private int level;

    public PermissionUpdateInfo() {
    }

    public PermissionUpdateInfo(PermissionKey key, PermissionGroupKey groupKey, String name, String remark, int level) {
        this.key = key;
        this.groupKey = groupKey;
        this.name = name;
        this.remark = remark;
        this.level = level;
    }

    public PermissionKey getKey() {
        return key;
    }

    public void setKey(PermissionKey key) {
        this.key = key;
    }

    public PermissionGroupKey getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(PermissionGroupKey groupKey) {
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "PermissionUpdateInfo{" +
                "key=" + key +
                ", groupKey=" + groupKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", level=" + level +
                '}';
    }
}

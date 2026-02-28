package com.dwarfeng.rbacds.stack.bean.entity;

import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

import java.util.Arrays;

/**
 * 权限。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class Permission implements Entity<PermissionKey> {

    private static final long serialVersionUID = 2513417150315478306L;

    private PermissionKey key;
    private PermissionGroupKey groupKey;
    private String name;
    private String remark;

    /**
     * 权限等级。
     *
     * <p>
     * 该字段表示权限的等级，值越大，权限越高。
     */
    private int level;

    /**
     * 权限组路径。
     *
     * <p>
     * 该字段表示权限所属权限组的路径，冗余字段，用于加速查询速度。
     */
    private String[] groupPath;

    public Permission() {
    }

    public Permission(
            PermissionKey key, PermissionGroupKey groupKey, String name, String remark, int level, String[] groupPath
    ) {
        this.key = key;
        this.groupKey = groupKey;
        this.name = name;
        this.remark = remark;
        this.level = level;
        this.groupPath = groupPath;
    }

    @Override
    public PermissionKey getKey() {
        return key;
    }

    @Override
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

    public String[] getGroupPath() {
        return groupPath;
    }

    public void setGroupPath(String[] groupPath) {
        this.groupPath = groupPath;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "key=" + key +
                ", groupKey=" + groupKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", level=" + level +
                ", groupPath=" + Arrays.toString(groupPath) +
                '}';
    }
}

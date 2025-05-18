package com.dwarfeng.rbacds.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 权限。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Permission implements Entity<StringIdKey> {

    private static final long serialVersionUID = 4858980545164383670L;

    private StringIdKey key;
    private StringIdKey groupKey;
    private String name;
    private String remark;

    /**
     * 权限等级。
     *
     * <p>
     * 该字段表示权限的等级，值越大，权限越高。
     *
     * @since 1.8.0
     */
    private int level;

    public Permission() {
    }

    public Permission(StringIdKey key, StringIdKey groupKey, String name, String remark, int level) {
        this.key = key;
        this.groupKey = groupKey;
        this.name = name;
        this.remark = remark;
        this.level = level;
    }

    @Override
    public StringIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(StringIdKey key) {
        this.key = key;
    }

    public StringIdKey getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(StringIdKey groupKey) {
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
        return "Permission{" +
                "key=" + key +
                ", groupKey=" + groupKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", level=" + level +
                '}';
    }
}

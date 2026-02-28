package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.FastJsonPermissionGroupKey;
import com.dwarfeng.rbacds.sdk.bean.key.FastJsonPermissionKey;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Arrays;
import java.util.Objects;

/**
 * FastJson 权限。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonPermission implements Bean {

    private static final long serialVersionUID = 986194088117322968L;

    public static FastJsonPermission of(Permission permission) {
        if (Objects.isNull(permission)) {
            return null;
        } else {
            return new FastJsonPermission(
                    FastJsonPermissionKey.of(permission.getKey()),
                    FastJsonPermissionGroupKey.of(permission.getGroupKey()),
                    permission.getName(),
                    permission.getRemark(),
                    permission.getLevel(),
                    permission.getGroupPath()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonPermissionKey key;

    @JSONField(name = "group_key", ordinal = 2)
    private FastJsonPermissionGroupKey groupKey;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    @JSONField(name = "level", ordinal = 5)
    private int level;

    @JSONField(name = "group_path", ordinal = 6)
    private String[] groupPath;

    public FastJsonPermission() {
    }

    public FastJsonPermission(
            FastJsonPermissionKey key, FastJsonPermissionGroupKey groupKey, String name, String remark, int level,
            String[] groupPath
    ) {
        this.key = key;
        this.groupKey = groupKey;
        this.name = name;
        this.remark = remark;
        this.level = level;
        this.groupPath = groupPath;
    }

    public FastJsonPermissionKey getKey() {
        return key;
    }

    public void setKey(FastJsonPermissionKey key) {
        this.key = key;
    }

    public FastJsonPermissionGroupKey getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(FastJsonPermissionGroupKey groupKey) {
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
        return "FastJsonPermission{" +
                "key=" + key +
                ", groupKey=" + groupKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", level=" + level +
                ", groupPath=" + Arrays.toString(groupPath) +
                '}';
    }
}

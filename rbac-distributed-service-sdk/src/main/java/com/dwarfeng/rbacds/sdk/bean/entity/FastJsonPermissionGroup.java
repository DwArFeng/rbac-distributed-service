package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.FastJsonPermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 权限组。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonPermissionGroup implements Bean {

    private static final long serialVersionUID = 6445359533205157636L;

    public static FastJsonPermissionGroup of(PermissionGroup permissionGroup) {
        if (Objects.isNull(permissionGroup)) {
            return null;
        } else {
            return new FastJsonPermissionGroup(
                    FastJsonPermissionGroupKey.of(permissionGroup.getKey()),
                    FastJsonPermissionGroupKey.of(permissionGroup.getParentKey()),
                    permissionGroup.getName(),
                    permissionGroup.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonPermissionGroupKey key;

    @JSONField(name = "parent_key", ordinal = 2)
    private FastJsonPermissionGroupKey parentKey;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    public FastJsonPermissionGroup() {
    }

    public FastJsonPermissionGroup(
            FastJsonPermissionGroupKey key, FastJsonPermissionGroupKey parentKey, String name, String remark
    ) {
        this.key = key;
        this.parentKey = parentKey;
        this.name = name;
        this.remark = remark;
    }

    public FastJsonPermissionGroupKey getKey() {
        return key;
    }

    public void setKey(FastJsonPermissionGroupKey key) {
        this.key = key;
    }

    public FastJsonPermissionGroupKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(FastJsonPermissionGroupKey parentKey) {
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
        return "FastJsonPermissionGroup{" +
                "key=" + key +
                ", parentKey=" + parentKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

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

    private static final long serialVersionUID = -1534203776335886L;

    private StringIdKey key;
    private String remark;

    public Permission() {
    }

    public Permission(StringIdKey key, String remark) {
        this.key = key;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "key=" + key +
                ", remark='" + remark + '\'' +
                '}';
    }
}

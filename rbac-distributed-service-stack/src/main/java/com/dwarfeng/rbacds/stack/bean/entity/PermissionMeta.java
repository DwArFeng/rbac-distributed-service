package com.dwarfeng.rbacds.stack.bean.entity;

import com.dwarfeng.rbacds.stack.bean.key.PermissionMetaKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

/**
 * 权限元数据。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public class PermissionMeta implements Entity<PermissionMetaKey> {

    private static final long serialVersionUID = -2757013060240490010L;
    
    private PermissionMetaKey key;
    private String value;
    private String remark;

    public PermissionMeta() {
    }

    public PermissionMeta(PermissionMetaKey key, String value, String remark) {
        this.key = key;
        this.value = value;
        this.remark = remark;
    }

    @Override
    public PermissionMetaKey getKey() {
        return key;
    }

    @Override
    public void setKey(PermissionMetaKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "PermissionMeta{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

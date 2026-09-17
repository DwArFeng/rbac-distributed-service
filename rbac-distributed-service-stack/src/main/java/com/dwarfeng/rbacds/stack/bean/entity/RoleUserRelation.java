package com.dwarfeng.rbacds.stack.bean.entity;

import com.dwarfeng.rbacds.stack.bean.key.RoleUserRelationKey;
import com.dwarfeng.subgrade.basic.stack.bean.entity.Entity;

import java.io.Serial;

/**
 * 角色用户关系。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class RoleUserRelation implements Entity<RoleUserRelationKey> {

    @Serial
    private static final long serialVersionUID = -6763826124654513054L;

    private RoleUserRelationKey key;
    private boolean enabled;
    private String remark;

    public RoleUserRelation() {
    }

    public RoleUserRelation(RoleUserRelationKey key, boolean enabled, String remark) {
        this.key = key;
        this.enabled = enabled;
        this.remark = remark;
    }

    @Override
    public RoleUserRelationKey getKey() {
        return key;
    }

    @Override
    public void setKey(RoleUserRelationKey key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "RoleUserRelation{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                '}';
    }
}

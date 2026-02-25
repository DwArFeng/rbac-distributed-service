package com.dwarfeng.rbacds.stack.bean.entity;

import com.dwarfeng.rbacds.stack.bean.key.RoleUserRelationKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

/**
 * 角色用户关系。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class RoleUserRelation implements Entity<RoleUserRelationKey> {

    private static final long serialVersionUID = 4131524298160879198L;

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

package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.FastJsonRoleUserRelationKey;
import com.dwarfeng.rbacds.stack.bean.entity.RoleUserRelation;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 角色用户关系。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonRoleUserRelation implements Bean {

    private static final long serialVersionUID = 6812268216325802038L;

    public static FastJsonRoleUserRelation of(RoleUserRelation roleUserRelation) {
        if (Objects.isNull(roleUserRelation)) {
            return null;
        } else {
            return new FastJsonRoleUserRelation(
                    FastJsonRoleUserRelationKey.of(roleUserRelation.getKey()),
                    roleUserRelation.isEnabled(),
                    roleUserRelation.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonRoleUserRelationKey key;

    @JSONField(name = "enabled", ordinal = 2)
    private boolean enabled;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    public FastJsonRoleUserRelation() {
    }

    public FastJsonRoleUserRelation(FastJsonRoleUserRelationKey key, boolean enabled, String remark) {
        this.key = key;
        this.enabled = enabled;
        this.remark = remark;
    }

    public FastJsonRoleUserRelationKey getKey() {
        return key;
    }

    public void setKey(FastJsonRoleUserRelationKey key) {
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
        return "FastJsonRoleUserRelation{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                '}';
    }
}

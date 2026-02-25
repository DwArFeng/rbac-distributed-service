package com.dwarfeng.rbacds.impl.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

/**
 * Hibernate 角色用户关系键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class HibernateRoleUserRelationKey implements Key {

    private static final long serialVersionUID = -2185342250479685458L;

    private String roleStringId;
    private String userStringId;

    public HibernateRoleUserRelationKey() {
    }

    public HibernateRoleUserRelationKey(String roleStringId, String userStringId) {
        this.roleStringId = roleStringId;
        this.userStringId = userStringId;
    }

    public String getRoleStringId() {
        return roleStringId;
    }

    public void setRoleStringId(String roleStringId) {
        this.roleStringId = roleStringId;
    }

    public String getUserStringId() {
        return userStringId;
    }

    public void setUserStringId(String userStringId) {
        this.userStringId = userStringId;
    }

    @Override
    public String toString() {
        return "HibernateRoleUserRelationKey{" +
                "roleStringId='" + roleStringId + '\'' +
                ", userStringId='" + userStringId + '\'' +
                '}';
    }
}

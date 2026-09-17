package com.dwarfeng.rbacds.impl.bean.key;

import com.dwarfeng.subgrade.basic.stack.bean.key.Key;

import java.io.Serial;
import java.util.Objects;

/**
 * Hibernate 角色用户关系键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class HibernateRoleUserRelationKey implements Key {

    @Serial
    private static final long serialVersionUID = -5005380765768978235L;

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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        HibernateRoleUserRelationKey that = (HibernateRoleUserRelationKey) o;
        return Objects.equals(roleStringId, that.roleStringId) && Objects.equals(userStringId, that.userStringId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(roleStringId);
        result = 31 * result + Objects.hashCode(userStringId);
        return result;
    }

    @Override
    public String toString() {
        return "HibernateRoleUserRelationKey{" +
                "roleStringId='" + roleStringId + '\'' +
                ", userStringId='" + userStringId + '\'' +
                '}';
    }
}

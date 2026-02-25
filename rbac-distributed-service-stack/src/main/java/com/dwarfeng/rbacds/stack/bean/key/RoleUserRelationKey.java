package com.dwarfeng.rbacds.stack.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * 角色用户关系键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class RoleUserRelationKey implements Key {

    private static final long serialVersionUID = 5962883543945057190L;

    private String roleStringId;
    private String userStringId;

    public RoleUserRelationKey() {
    }

    public RoleUserRelationKey(String roleStringId, String userStringId) {
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

        RoleUserRelationKey that = (RoleUserRelationKey) o;
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
        return "RoleUserRelationKey{" +
                "roleStringId='" + roleStringId + '\'' +
                ", userStringId='" + userStringId + '\'' +
                '}';
    }
}

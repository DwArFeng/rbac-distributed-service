package com.dwarfeng.rbacds.stack.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * 作用域角色键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class ScopedRoleKey implements Key {

    private static final long serialVersionUID = 7078973995492528171L;

    private String scopeStringId;
    private String roleStringId;

    public ScopedRoleKey() {
    }

    public ScopedRoleKey(String scopeStringId, String roleStringId) {
        this.scopeStringId = scopeStringId;
        this.roleStringId = roleStringId;
    }

    public String getScopeStringId() {
        return scopeStringId;
    }

    public void setScopeStringId(String scopeStringId) {
        this.scopeStringId = scopeStringId;
    }

    public String getRoleStringId() {
        return roleStringId;
    }

    public void setRoleStringId(String roleStringId) {
        this.roleStringId = roleStringId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ScopedRoleKey that = (ScopedRoleKey) o;
        return Objects.equals(scopeStringId, that.scopeStringId) && Objects.equals(roleStringId, that.roleStringId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(scopeStringId);
        result = 31 * result + Objects.hashCode(roleStringId);
        return result;
    }

    @Override
    public String toString() {
        return "ScopedRoleKey{" +
                "scopeStringId='" + scopeStringId + '\'' +
                ", roleStringId='" + roleStringId + '\'' +
                '}';
    }
}

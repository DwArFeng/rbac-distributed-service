package com.dwarfeng.rbacds.impl.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * Hibernate 权限表达式键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class HibernatePexpKey implements Key {

    private static final long serialVersionUID = -2834186879994244604L;

    private String scopeStringId;
    private String roleStringId;
    private String pexpStringId;

    public HibernatePexpKey() {
    }

    public HibernatePexpKey(String scopeStringId, String roleStringId, String pexpStringId) {
        this.scopeStringId = scopeStringId;
        this.roleStringId = roleStringId;
        this.pexpStringId = pexpStringId;
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

    public String getPexpStringId() {
        return pexpStringId;
    }

    public void setPexpStringId(String pexpStringId) {
        this.pexpStringId = pexpStringId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        HibernatePexpKey that = (HibernatePexpKey) o;
        return Objects.equals(scopeStringId, that.scopeStringId) && Objects.equals(roleStringId, that.roleStringId) &&
                Objects.equals(pexpStringId, that.pexpStringId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(scopeStringId);
        result = 31 * result + Objects.hashCode(roleStringId);
        result = 31 * result + Objects.hashCode(pexpStringId);
        return result;
    }

    @Override
    public String toString() {
        return "HibernatePexpKey{" +
                "scopeStringId='" + scopeStringId + '\'' +
                ", roleStringId='" + roleStringId + '\'' +
                ", pexpStringId='" + pexpStringId + '\'' +
                '}';
    }
}

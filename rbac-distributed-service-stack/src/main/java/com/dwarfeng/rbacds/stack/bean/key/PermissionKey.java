package com.dwarfeng.rbacds.stack.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * 权限键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionKey implements Key {

    private static final long serialVersionUID = 3702046147196882119L;

    private String scopeStringId;
    private String permissionStringId;

    public PermissionKey() {
    }

    public PermissionKey(String scopeStringId, String permissionStringId) {
        this.scopeStringId = scopeStringId;
        this.permissionStringId = permissionStringId;
    }

    public String getScopeStringId() {
        return scopeStringId;
    }

    public void setScopeStringId(String scopeStringId) {
        this.scopeStringId = scopeStringId;
    }

    public String getPermissionStringId() {
        return permissionStringId;
    }

    public void setPermissionStringId(String permissionStringId) {
        this.permissionStringId = permissionStringId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        PermissionKey that = (PermissionKey) o;
        return Objects.equals(scopeStringId, that.scopeStringId) &&
                Objects.equals(permissionStringId, that.permissionStringId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(scopeStringId);
        result = 31 * result + Objects.hashCode(permissionStringId);
        return result;
    }

    @Override
    public String toString() {
        return "PermissionKey{" +
                "scopeStringId='" + scopeStringId + '\'' +
                ", permissionStringId='" + permissionStringId + '\'' +
                '}';
    }
}

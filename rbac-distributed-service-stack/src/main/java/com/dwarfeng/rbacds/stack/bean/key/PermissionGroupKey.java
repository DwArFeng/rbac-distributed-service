package com.dwarfeng.rbacds.stack.bean.key;

import com.dwarfeng.subgrade.basic.stack.bean.key.Key;

import java.io.Serial;
import java.util.Objects;

/**
 * 权限组键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionGroupKey implements Key {

    @Serial
    private static final long serialVersionUID = 1636736177212132886L;

    private String scopeStringId;
    private String permissionGroupStringId;

    public PermissionGroupKey() {
    }

    public PermissionGroupKey(String scopeStringId, String permissionGroupStringId) {
        this.scopeStringId = scopeStringId;
        this.permissionGroupStringId = permissionGroupStringId;
    }

    public String getScopeStringId() {
        return scopeStringId;
    }

    public void setScopeStringId(String scopeStringId) {
        this.scopeStringId = scopeStringId;
    }

    public String getPermissionGroupStringId() {
        return permissionGroupStringId;
    }

    public void setPermissionGroupStringId(String permissionGroupStringId) {
        this.permissionGroupStringId = permissionGroupStringId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        PermissionGroupKey that = (PermissionGroupKey) o;
        return Objects.equals(scopeStringId, that.scopeStringId) &&
                Objects.equals(permissionGroupStringId, that.permissionGroupStringId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(scopeStringId);
        result = 31 * result + Objects.hashCode(permissionGroupStringId);
        return result;
    }

    @Override
    public String toString() {
        return "PermissionGroupKey{" +
                "scopeStringId='" + scopeStringId + '\'' +
                ", permissionGroupStringId='" + permissionGroupStringId + '\'' +
                '}';
    }
}

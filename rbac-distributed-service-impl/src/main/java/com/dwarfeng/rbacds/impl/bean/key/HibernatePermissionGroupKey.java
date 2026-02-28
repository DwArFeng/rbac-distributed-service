package com.dwarfeng.rbacds.impl.bean.key;

import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * Hibernate 权限组键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class HibernatePermissionGroupKey implements Key {

    private static final long serialVersionUID = -1085481535942938210L;

    private String scopeStringId;
    private String permissionGroupStringId;

    public HibernatePermissionGroupKey() {
    }

    public HibernatePermissionGroupKey(String scopeStringId, String permissionGroupStringId) {
        this.scopeStringId = scopeStringId;
        this.permissionGroupStringId = permissionGroupStringId;
    }

    public static HibernatePermissionGroupKey of(PermissionGroupKey permissionGroupKey) {
        if (Objects.isNull(permissionGroupKey)) {
            return null;
        } else {
            return new HibernatePermissionGroupKey(
                    permissionGroupKey.getScopeStringId(),
                    permissionGroupKey.getPermissionGroupStringId()
            );
        }
    }

    public static PermissionGroupKey toStackBean(HibernatePermissionGroupKey hibernate) {
        if (Objects.isNull(hibernate)) {
            return null;
        } else {
            return new PermissionGroupKey(
                    hibernate.getScopeStringId(),
                    hibernate.getPermissionGroupStringId()
            );
        }
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

        HibernatePermissionGroupKey that = (HibernatePermissionGroupKey) o;
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
        return "HibernatePermissionGroupKey{" +
                "scopeStringId='" + scopeStringId + '\'' +
                ", permissionGroupStringId='" + permissionGroupStringId + '\'' +
                '}';
    }
}

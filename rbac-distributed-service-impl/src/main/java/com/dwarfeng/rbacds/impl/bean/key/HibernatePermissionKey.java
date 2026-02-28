package com.dwarfeng.rbacds.impl.bean.key;

import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * Hibernate 权限键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class HibernatePermissionKey implements Key {

    private static final long serialVersionUID = 7746297341666041686L;

    private String scopeStringId;
    private String permissionStringId;

    public HibernatePermissionKey() {
    }

    public HibernatePermissionKey(String scopeStringId, String permissionStringId) {
        this.scopeStringId = scopeStringId;
        this.permissionStringId = permissionStringId;
    }

    public static HibernatePermissionKey of(PermissionKey permissionKey) {
        if (Objects.isNull(permissionKey)) {
            return null;
        } else {
            return new HibernatePermissionKey(
                    permissionKey.getScopeStringId(),
                    permissionKey.getPermissionStringId()
            );
        }
    }

    public static PermissionKey toStackBean(HibernatePermissionKey hibernate) {
        if (Objects.isNull(hibernate)) {
            return null;
        } else {
            return new PermissionKey(
                    hibernate.getScopeStringId(),
                    hibernate.getPermissionStringId()
            );
        }
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

        HibernatePermissionKey that = (HibernatePermissionKey) o;
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
        return "HibernatePermissionKey{" +
                "scopeStringId='" + scopeStringId + '\'' +
                ", permissionStringId='" + permissionStringId + '\'' +
                '}';
    }
}

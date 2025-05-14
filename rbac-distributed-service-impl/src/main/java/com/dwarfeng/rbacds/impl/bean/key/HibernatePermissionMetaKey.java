package com.dwarfeng.rbacds.impl.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * Hibernate 权限元数据键。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public class HibernatePermissionMetaKey implements Key {

    private static final long serialVersionUID = 3228021989996235781L;
    
    private String permissionStringId;
    private String metaStringId;

    public HibernatePermissionMetaKey() {
    }

    public HibernatePermissionMetaKey(String permissionStringId, String metaStringId) {
        this.permissionStringId = permissionStringId;
        this.metaStringId = metaStringId;
    }

    public String getPermissionStringId() {
        return permissionStringId;
    }

    public void setPermissionStringId(String permissionStringId) {
        this.permissionStringId = permissionStringId;
    }

    public String getMetaStringId() {
        return metaStringId;
    }

    public void setMetaStringId(String metaStringId) {
        this.metaStringId = metaStringId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        HibernatePermissionMetaKey that = (HibernatePermissionMetaKey) o;
        return Objects.equals(permissionStringId, that.permissionStringId) &&
                Objects.equals(metaStringId, that.metaStringId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(permissionStringId);
        result = 31 * result + Objects.hashCode(metaStringId);
        return result;
    }

    @Override
    public String toString() {
        return "HibernatePermissionMetaKey{" +
                "permissionStringId='" + permissionStringId + '\'' +
                ", metaStringId='" + metaStringId + '\'' +
                '}';
    }
}

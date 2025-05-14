package com.dwarfeng.rbacds.stack.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * 权限元数据键。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public class PermissionMetaKey implements Key {

    private static final long serialVersionUID = 6765257605140606043L;
    
    private String permissionStringId;
    private String metaStringId;

    public PermissionMetaKey() {
    }

    public PermissionMetaKey(String permissionStringId, String metaStringId) {
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

        PermissionMetaKey that = (PermissionMetaKey) o;
        return Objects.equals(permissionStringId, that.permissionStringId) && Objects.equals(metaStringId, that.metaStringId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(permissionStringId);
        result = 31 * result + Objects.hashCode(metaStringId);
        return result;
    }

    @Override
    public String toString() {
        return "PermissionMetaKey{" +
                "permissionStringId='" + permissionStringId + '\'' +
                ", metaStringId='" + metaStringId + '\'' +
                '}';
    }
}

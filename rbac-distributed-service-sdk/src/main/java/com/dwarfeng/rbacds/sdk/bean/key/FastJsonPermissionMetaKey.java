package com.dwarfeng.rbacds.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.key.PermissionMetaKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * FastJson 权限元数据键。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public class FastJsonPermissionMetaKey implements Key {

    private static final long serialVersionUID = 6125628426751594658L;

    public static FastJsonPermissionMetaKey of(PermissionMetaKey permissionMetaKey) {
        if (Objects.isNull(permissionMetaKey)) {
            return null;
        } else {
            return new FastJsonPermissionMetaKey(
                    permissionMetaKey.getPermissionStringId(),
                    permissionMetaKey.getMetaStringId()
            );
        }
    }

    @JSONField(name = "permission_string_id", ordinal = 1)
    private String permissionStringId;

    @JSONField(name = "meta_string_id", ordinal = 2)
    private String metaStringId;

    public FastJsonPermissionMetaKey() {
    }

    public FastJsonPermissionMetaKey(String permissionStringId, String metaStringId) {
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

        FastJsonPermissionMetaKey that = (FastJsonPermissionMetaKey) o;
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
        return "FastJsonPermissionMetaKey{" +
                "permissionStringId='" + permissionStringId + '\'' +
                ", metaStringId='" + metaStringId + '\'' +
                '}';
    }
}

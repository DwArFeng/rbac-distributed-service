package com.dwarfeng.rbacds.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * FastJson 权限键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonPermissionKey implements Key {

    private static final long serialVersionUID = 7372919678382560100L;

    public static FastJsonPermissionKey of(PermissionKey permissionKey) {
        if (Objects.isNull(permissionKey)) {
            return null;
        } else {
            return new FastJsonPermissionKey(
                    permissionKey.getScopeStringId(),
                    permissionKey.getPermissionStringId()
            );
        }
    }

    @JSONField(name = "scope_string_id", ordinal = 1)
    private String scopeStringId;

    @JSONField(name = "permission_string_id", ordinal = 2)
    private String permissionStringId;

    public FastJsonPermissionKey() {
    }

    public FastJsonPermissionKey(String scopeStringId, String permissionStringId) {
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

        FastJsonPermissionKey that = (FastJsonPermissionKey) o;
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
        return "FastJsonPermissionKey{" +
                "scopeStringId='" + scopeStringId + '\'' +
                ", permissionStringId='" + permissionStringId + '\'' +
                '}';
    }
}

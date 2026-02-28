package com.dwarfeng.rbacds.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * FastJson 权限组键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonPermissionGroupKey implements Key {

    private static final long serialVersionUID = -6845584299414361204L;

    public static FastJsonPermissionGroupKey of(PermissionGroupKey permissionGroupKey) {
        if (Objects.isNull(permissionGroupKey)) {
            return null;
        } else {
            return new FastJsonPermissionGroupKey(
                    permissionGroupKey.getScopeStringId(),
                    permissionGroupKey.getPermissionGroupStringId()
            );
        }
    }

    @JSONField(name = "scope_string_id", ordinal = 1)
    private String scopeStringId;

    @JSONField(name = "permission_group_string_id", ordinal = 2)
    private String permissionGroupStringId;

    public FastJsonPermissionGroupKey() {
    }

    public FastJsonPermissionGroupKey(String scopeStringId, String permissionGroupStringId) {
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

        FastJsonPermissionGroupKey that = (FastJsonPermissionGroupKey) o;
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
        return "FastJsonPermissionGroupKey{" +
                "scopeStringId='" + scopeStringId + '\'' +
                ", permissionGroupStringId='" + permissionGroupStringId + '\'' +
                '}';
    }
}

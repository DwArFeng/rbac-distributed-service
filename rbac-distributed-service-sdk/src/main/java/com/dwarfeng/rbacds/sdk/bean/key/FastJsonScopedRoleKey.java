package com.dwarfeng.rbacds.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.key.ScopedRoleKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * FastJson 作用域角色键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonScopedRoleKey implements Key {

    private static final long serialVersionUID = -8852930790482077934L;

    public static FastJsonScopedRoleKey of(ScopedRoleKey scopedRoleKey) {
        if (Objects.isNull(scopedRoleKey)) {
            return null;
        } else {
            return new FastJsonScopedRoleKey(
                    scopedRoleKey.getScopeStringId(),
                    scopedRoleKey.getRoleStringId()
            );
        }
    }

    @JSONField(name = "scope_string_id", ordinal = 1)
    private String scopeStringId;

    @JSONField(name = "role_string_id", ordinal = 2)
    private String roleStringId;

    public FastJsonScopedRoleKey() {
    }

    public FastJsonScopedRoleKey(String scopeStringId, String roleStringId) {
        this.scopeStringId = scopeStringId;
        this.roleStringId = roleStringId;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        FastJsonScopedRoleKey that = (FastJsonScopedRoleKey) o;
        return Objects.equals(scopeStringId, that.scopeStringId) && Objects.equals(roleStringId, that.roleStringId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(scopeStringId);
        result = 31 * result + Objects.hashCode(roleStringId);
        return result;
    }

    @Override
    public String toString() {
        return "FastJsonScopedRoleKey{" +
                "scopeStringId='" + scopeStringId + '\'' +
                ", roleStringId='" + roleStringId + '\'' +
                '}';
    }
}

package com.dwarfeng.rbacds.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.key.ScopedUserKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * FastJson 作用域用户键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonScopedUserKey implements Key {

    private static final long serialVersionUID = -5037633203273150859L;

    public static FastJsonScopedUserKey of(ScopedUserKey scopedUserKey) {
        if (Objects.isNull(scopedUserKey)) {
            return null;
        } else {
            return new FastJsonScopedUserKey(
                    scopedUserKey.getScopeStringId(),
                    scopedUserKey.getUserStringId()
            );
        }
    }

    @JSONField(name = "scope_string_id", ordinal = 1)
    private String scopeStringId;

    @JSONField(name = "user_string_id", ordinal = 2)
    private String userStringId;

    public FastJsonScopedUserKey() {
    }

    public FastJsonScopedUserKey(String scopeStringId, String userStringId) {
        this.scopeStringId = scopeStringId;
        this.userStringId = userStringId;
    }

    public String getScopeStringId() {
        return scopeStringId;
    }

    public void setScopeStringId(String scopeStringId) {
        this.scopeStringId = scopeStringId;
    }

    public String getUserStringId() {
        return userStringId;
    }

    public void setUserStringId(String userStringId) {
        this.userStringId = userStringId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        FastJsonScopedUserKey that = (FastJsonScopedUserKey) o;
        return Objects.equals(scopeStringId, that.scopeStringId) && Objects.equals(userStringId, that.userStringId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(scopeStringId);
        result = 31 * result + Objects.hashCode(userStringId);
        return result;
    }

    @Override
    public String toString() {
        return "FastJsonScopedUserKey{" +
                "scopeStringId='" + scopeStringId + '\'' +
                ", userStringId='" + userStringId + '\'' +
                '}';
    }
}

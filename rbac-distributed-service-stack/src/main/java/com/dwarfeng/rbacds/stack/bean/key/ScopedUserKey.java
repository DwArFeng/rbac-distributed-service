package com.dwarfeng.rbacds.stack.bean.key;

import com.dwarfeng.subgrade.basic.stack.bean.key.Key;

import java.io.Serial;
import java.util.Objects;

/**
 * 作用域用户键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class ScopedUserKey implements Key {

    @Serial
    private static final long serialVersionUID = 7065181708997457641L;

    private String scopeStringId;
    private String userStringId;

    public ScopedUserKey() {
    }

    public ScopedUserKey(String scopeStringId, String userStringId) {
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

        ScopedUserKey that = (ScopedUserKey) o;
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
        return "ScopedUserKey{" +
                "scopeStringId='" + scopeStringId + '\'' +
                ", userStringId='" + userStringId + '\'' +
                '}';
    }
}

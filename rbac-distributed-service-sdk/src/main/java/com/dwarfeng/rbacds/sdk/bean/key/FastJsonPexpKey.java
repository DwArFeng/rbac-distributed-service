package com.dwarfeng.rbacds.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * FastJson 权限表达式键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonPexpKey implements Key {

    private static final long serialVersionUID = -2141072409386314403L;

    public static FastJsonPexpKey of(PexpKey pexpKey) {
        if (Objects.isNull(pexpKey)) {
            return null;
        } else {
            return new FastJsonPexpKey(
                    pexpKey.getScopeStringId(),
                    pexpKey.getRoleStringId(),
                    pexpKey.getPexpStringId()
            );
        }
    }

    @JSONField(name = "scope_string_id", ordinal = 1)
    private String scopeStringId;

    @JSONField(name = "role_string_id", ordinal = 2)
    private String roleStringId;

    @JSONField(name = "pexp_string_id", ordinal = 3)
    private String pexpStringId;

    public FastJsonPexpKey() {
    }

    public FastJsonPexpKey(String scopeStringId, String roleStringId, String pexpStringId) {
        this.scopeStringId = scopeStringId;
        this.roleStringId = roleStringId;
        this.pexpStringId = pexpStringId;
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

    public String getPexpStringId() {
        return pexpStringId;
    }

    public void setPexpStringId(String pexpStringId) {
        this.pexpStringId = pexpStringId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        FastJsonPexpKey that = (FastJsonPexpKey) o;
        return Objects.equals(scopeStringId, that.scopeStringId) && Objects.equals(roleStringId, that.roleStringId) &&
                Objects.equals(pexpStringId, that.pexpStringId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(scopeStringId);
        result = 31 * result + Objects.hashCode(roleStringId);
        result = 31 * result + Objects.hashCode(pexpStringId);
        return result;
    }

    @Override
    public String toString() {
        return "FastJsonPexpKey{" +
                "scopeStringId='" + scopeStringId + '\'' +
                ", roleStringId='" + roleStringId + '\'' +
                ", pexpStringId='" + pexpStringId + '\'' +
                '}';
    }
}

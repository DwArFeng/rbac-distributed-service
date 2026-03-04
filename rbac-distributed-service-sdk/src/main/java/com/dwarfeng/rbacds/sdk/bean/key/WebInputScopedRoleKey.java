package com.dwarfeng.rbacds.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.rbacds.stack.bean.key.ScopedRoleKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 作用域角色键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputScopedRoleKey implements Key {

    private static final long serialVersionUID = -9182736450123456789L;

    public static ScopedRoleKey toStackBean(WebInputScopedRoleKey webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new ScopedRoleKey(
                    webInput.getScopeStringId(),
                    webInput.getRoleStringId()
            );
        }
    }

    @JSONField(name = "scope_string_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_ID)
    private String scopeStringId;

    @JSONField(name = "role_string_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_ID)
    private String roleStringId;

    public WebInputScopedRoleKey() {
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

        WebInputScopedRoleKey that = (WebInputScopedRoleKey) o;
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
        return "WebInputScopedRoleKey{" +
                "scopeStringId='" + scopeStringId + '\'' +
                ", roleStringId='" + roleStringId + '\'' +
                '}';
    }
}

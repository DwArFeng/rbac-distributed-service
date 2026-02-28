package com.dwarfeng.rbacds.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 权限表达式键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputPexpKey implements Key {

    private static final long serialVersionUID = 5452442233072393198L;

    public static PexpKey toStackBean(WebInputPexpKey webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PexpKey(
                    webInput.getScopeStringId(),
                    webInput.getRoleStringId(),
                    webInput.getPexpStringId()
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

    @JSONField(name = "pexp_string_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_ID)
    private String pexpStringId;

    public WebInputPexpKey() {
    }

    public WebInputPexpKey(String scopeStringId, String roleStringId, String pexpStringId) {
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

        WebInputPexpKey that = (WebInputPexpKey) o;
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
        return "WebInputPexpKey{" +
                "scopeStringId='" + scopeStringId + '\'' +
                ", roleStringId='" + roleStringId + '\'' +
                ", pexpStringId='" + pexpStringId + '\'' +
                '}';
    }
}

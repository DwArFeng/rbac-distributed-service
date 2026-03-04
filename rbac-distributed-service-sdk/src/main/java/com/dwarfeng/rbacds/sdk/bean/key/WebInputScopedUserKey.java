package com.dwarfeng.rbacds.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.rbacds.stack.bean.key.ScopedUserKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 作用域用户键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputScopedUserKey implements Key {

    private static final long serialVersionUID = -9182736450123456790L;

    public static ScopedUserKey toStackBean(WebInputScopedUserKey webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new ScopedUserKey(
                    webInput.getScopeStringId(),
                    webInput.getUserStringId()
            );
        }
    }

    @JSONField(name = "scope_string_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_ID)
    private String scopeStringId;

    @JSONField(name = "user_string_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_ID)
    private String userStringId;

    public WebInputScopedUserKey() {
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

        WebInputScopedUserKey that = (WebInputScopedUserKey) o;
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
        return "WebInputScopedUserKey{" +
                "scopeStringId='" + scopeStringId + '\'' +
                ", userStringId='" + userStringId + '\'' +
                '}';
    }
}

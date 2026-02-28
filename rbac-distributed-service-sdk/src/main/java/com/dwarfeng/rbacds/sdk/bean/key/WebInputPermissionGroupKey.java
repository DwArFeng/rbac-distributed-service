package com.dwarfeng.rbacds.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 权限组键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputPermissionGroupKey implements Key {

    private static final long serialVersionUID = 2446066598536298039L;

    public static PermissionGroupKey toStackBean(WebInputPermissionGroupKey webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PermissionGroupKey(
                    webInput.getScopeStringId(),
                    webInput.getPermissionGroupStringId()
            );
        }
    }

    @JSONField(name = "scope_string_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_ID)
    private String scopeStringId;

    @JSONField(name = "permission_group_string_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_ID)
    private String permissionGroupStringId;

    public WebInputPermissionGroupKey() {
    }

    public WebInputPermissionGroupKey(String scopeStringId, String permissionGroupStringId) {
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

        WebInputPermissionGroupKey that = (WebInputPermissionGroupKey) o;
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
        return "WebInputPermissionGroupKey{" +
                "scopeStringId='" + scopeStringId + '\'' +
                ", permissionGroupStringId='" + permissionGroupStringId + '\'' +
                '}';
    }
}

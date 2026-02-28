package com.dwarfeng.rbacds.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 权限键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputPermissionKey implements Key {

    private static final long serialVersionUID = -9175750195106633960L;

    public static PermissionKey toStackBean(WebInputPermissionKey webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PermissionKey(
                    webInput.getScopeStringId(),
                    webInput.getPermissionStringId()
            );
        }
    }

    @JSONField(name = "scope_string_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_ID)
    private String scopeStringId;

    @JSONField(name = "permission_string_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_ID)
    private String permissionStringId;

    public WebInputPermissionKey() {
    }

    public WebInputPermissionKey(String scopeStringId, String permissionStringId) {
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

        WebInputPermissionKey that = (WebInputPermissionKey) o;
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
        return "WebInputPermissionKey{" +
                "scopeStringId='" + scopeStringId + '\'' +
                ", permissionStringId='" + permissionStringId + '\'' +
                '}';
    }
}

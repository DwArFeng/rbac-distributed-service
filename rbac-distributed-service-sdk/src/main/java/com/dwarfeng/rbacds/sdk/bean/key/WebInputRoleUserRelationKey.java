package com.dwarfeng.rbacds.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.rbacds.stack.bean.key.RoleUserRelationKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 角色用户关系键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputRoleUserRelationKey implements Key {

    private static final long serialVersionUID = 3428596332841626862L;

    public static RoleUserRelationKey toStackBean(WebInputRoleUserRelationKey webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new RoleUserRelationKey(
                    webInput.getRoleStringId(),
                    webInput.getUserStringId()
            );
        }
    }

    @JSONField(name = "role_string_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_ID)
    private String roleStringId;

    @JSONField(name = "user_string_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_ID)
    private String userStringId;

    public WebInputRoleUserRelationKey() {
    }

    public String getRoleStringId() {
        return roleStringId;
    }

    public void setRoleStringId(String roleStringId) {
        this.roleStringId = roleStringId;
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

        WebInputRoleUserRelationKey that = (WebInputRoleUserRelationKey) o;
        return Objects.equals(roleStringId, that.roleStringId) && Objects.equals(userStringId, that.userStringId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(roleStringId);
        result = 31 * result + Objects.hashCode(userStringId);
        return result;
    }

    @Override
    public String toString() {
        return "WebInputRoleUserRelationKey{" +
                "roleStringId='" + roleStringId + '\'' +
                ", userStringId='" + userStringId + '\'' +
                '}';
    }
}

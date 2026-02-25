package com.dwarfeng.rbacds.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.key.RoleUserRelationKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * FastJson 角色用户关系键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonRoleUserRelationKey implements Key {

    private static final long serialVersionUID = -703688646658221621L;

    public static FastJsonRoleUserRelationKey of(RoleUserRelationKey roleUserRelationKey) {
        if (Objects.isNull(roleUserRelationKey)) {
            return null;
        } else {
            return new FastJsonRoleUserRelationKey(
                    roleUserRelationKey.getRoleStringId(),
                    roleUserRelationKey.getUserStringId()
            );
        }
    }

    @JSONField(name = "role_string_id", ordinal = 1)
    private String roleStringId;

    @JSONField(name = "user_string_id", ordinal = 2)
    private String userStringId;

    public FastJsonRoleUserRelationKey() {
    }

    public FastJsonRoleUserRelationKey(String roleStringId, String userStringId) {
        this.roleStringId = roleStringId;
        this.userStringId = userStringId;
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

        FastJsonRoleUserRelationKey that = (FastJsonRoleUserRelationKey) o;
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
        return "FastJsonRoleUserRelationKey{" +
                "roleStringId='" + roleStringId + '\'' +
                ", userStringId='" + userStringId + '\'' +
                '}';
    }
}

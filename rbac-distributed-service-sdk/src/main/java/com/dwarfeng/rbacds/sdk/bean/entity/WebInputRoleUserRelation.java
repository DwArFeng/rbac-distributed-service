package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputRoleUserRelationKey;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.rbacds.stack.bean.entity.RoleUserRelation;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 角色用户关系。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputRoleUserRelation implements Bean {

    private static final long serialVersionUID = 376976406962543515L;

    public static RoleUserRelation toStackBean(WebInputRoleUserRelation webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new RoleUserRelation(
                    WebInputRoleUserRelationKey.toStackBean(webInput.getKey()),
                    webInput.isEnabled(),
                    webInput.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputRoleUserRelationKey key;

    @JSONField(name = "enabled")
    private boolean enabled;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputRoleUserRelation() {
    }

    public WebInputRoleUserRelationKey getKey() {
        return key;
    }

    public void setKey(WebInputRoleUserRelationKey key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        WebInputRoleUserRelation that = (WebInputRoleUserRelation) o;
        return enabled == that.enabled && Objects.equals(key, that.key) && Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(key);
        result = 31 * result + Boolean.hashCode(enabled);
        result = 31 * result + Objects.hashCode(remark);
        return result;
    }

    @Override
    public String toString() {
        return "WebInputRoleUserRelation{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                '}';
    }
}

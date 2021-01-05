package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.entity.RoleGroup;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 角色组。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
public class WebInputRoleGroup implements Bean {

    private static final long serialVersionUID = -8084096734371753884L;

    public static RoleGroup toStackBean(WebInputRoleGroup webInputRoleGroup) {
        if (Objects.isNull(webInputRoleGroup)) {
            return null;
        } else {
            return new RoleGroup(
                    WebInputStringIdKey.toStackBean(webInputRoleGroup.getKey()),
                    WebInputStringIdKey.toStackBean(webInputRoleGroup.getParentKey()),
                    webInputRoleGroup.getName(),
                    webInputRoleGroup.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    @NotNull
    private WebInputStringIdKey key;

    @JSONField(name = "parent_key")
    @Valid
    private WebInputStringIdKey parentKey;

    @JSONField(name = "name")
    @NotNull
    @NotEmpty
    private String name;

    @JSONField(name = "remark")
    private String remark;

    public WebInputRoleGroup() {
    }

    public WebInputStringIdKey getKey() {
        return key;
    }

    public void setKey(WebInputStringIdKey key) {
        this.key = key;
    }

    public WebInputStringIdKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(WebInputStringIdKey parentKey) {
        this.parentKey = parentKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputRoleGroup{" +
                "key=" + key +
                ", parentKey=" + parentKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

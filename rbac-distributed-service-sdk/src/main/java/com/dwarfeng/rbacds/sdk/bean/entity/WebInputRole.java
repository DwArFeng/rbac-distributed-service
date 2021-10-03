package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 角色。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputRole implements Bean {

    private static final long serialVersionUID = -8046229980071331113L;

    public static Role toStackBean(WebInputRole webInputRole) {
        if (Objects.isNull(webInputRole)) {
            return null;
        }
        return new Role(
                WebInputStringIdKey.toStackBean(webInputRole.getKey()),
                webInputRole.getName(),
                webInputRole.isEnabled(),
                webInputRole.getRemark()
        );
    }

    @JSONField(name = "key")
    @Valid
    @NotNull
    private WebInputStringIdKey key;

    @JSONField(name = "name")
    @NotNull
    @NotEmpty
    private String name;

    @JSONField(name = "enabled")
    private boolean enabled;

    @JSONField(name = "remark")
    private String remark;

    public WebInputRole() {
    }

    public WebInputStringIdKey getKey() {
        return key;
    }

    public void setKey(WebInputStringIdKey key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public String toString() {
        return "WebInputRole{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                '}';
    }
}

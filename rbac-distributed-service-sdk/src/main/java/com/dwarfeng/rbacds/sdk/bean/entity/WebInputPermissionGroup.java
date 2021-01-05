package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 权限组。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
public class WebInputPermissionGroup implements Bean {

    private static final long serialVersionUID = -5706836672595777407L;

    public static PermissionGroup toStackBean(WebInputPermissionGroup webInputPermissionGroup) {
        if (Objects.isNull(webInputPermissionGroup)) {
            return null;
        } else {
            return new PermissionGroup(
                    WebInputStringIdKey.toStackBean(webInputPermissionGroup.getKey()),
                    WebInputStringIdKey.toStackBean(webInputPermissionGroup.getParentKey()),
                    webInputPermissionGroup.getName(),
                    webInputPermissionGroup.getRemark()
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

    public WebInputPermissionGroup() {
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
        return "WebInputPermissionGroup{" +
                "key=" + key +
                ", parentKey=" + parentKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPermissionGroupKey;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupCreateInfo;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 权限组创建信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputPermissionGroupCreateInfo implements Bean {

    private static final long serialVersionUID = -9049011681613615806L;

    public static PermissionGroupCreateInfo toStackBean(WebInputPermissionGroupCreateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PermissionGroupCreateInfo(
                    WebInputPermissionGroupKey.toStackBean(webInput.getKey()),
                    WebInputPermissionGroupKey.toStackBean(webInput.getParentKey()),
                    webInput.getName(),
                    webInput.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputPermissionGroupKey key;

    @JSONField(name = "parent_key")
    @Valid
    private WebInputPermissionGroupKey parentKey;

    @JSONField(name = "name")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_NAME)
    private String name;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputPermissionGroupCreateInfo() {
    }

    public WebInputPermissionGroupKey getKey() {
        return key;
    }

    public void setKey(WebInputPermissionGroupKey key) {
        this.key = key;
    }

    public WebInputPermissionGroupKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(WebInputPermissionGroupKey parentKey) {
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
        return "WebInputPermissionGroupCreateInfo{" +
                "key=" + key +
                ", parentKey=" + parentKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPermissionGroupKey;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupUpdateInfo;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 权限组更新信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputPermissionGroupUpdateInfo implements Bean {

    private static final long serialVersionUID = -374438882305481148L;

    public static PermissionGroupUpdateInfo toStackBean(WebInputPermissionGroupUpdateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PermissionGroupUpdateInfo(
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

    public WebInputPermissionGroupUpdateInfo() {
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
        return "WebInputPermissionGroupUpdateInfo{" +
                "key=" + key +
                ", parentKey=" + parentKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPermissionGroupKey;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPermissionKey;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionUpdateInfo;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 权限更新信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputPermissionUpdateInfo implements Bean {

    private static final long serialVersionUID = -3296327984656081637L;

    public static PermissionUpdateInfo toStackBean(WebInputPermissionUpdateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PermissionUpdateInfo(
                    WebInputPermissionKey.toStackBean(webInput.getKey()),
                    WebInputPermissionGroupKey.toStackBean(webInput.getGroupKey()),
                    webInput.getName(),
                    webInput.getRemark(),
                    webInput.getLevel()
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputPermissionKey key;

    @JSONField(name = "group_key")
    @Valid
    private WebInputPermissionGroupKey groupKey;

    @JSONField(name = "name")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_NAME)
    private String name;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    @JSONField(name = "level")
    private int level;

    public WebInputPermissionUpdateInfo() {
    }

    public WebInputPermissionKey getKey() {
        return key;
    }

    public void setKey(WebInputPermissionKey key) {
        this.key = key;
    }

    public WebInputPermissionGroupKey getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(WebInputPermissionGroupKey groupKey) {
        this.groupKey = groupKey;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "WebInputPermissionUpdateInfo{" +
                "key=" + key +
                ", groupKey=" + groupKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", level=" + level +
                '}';
    }
}

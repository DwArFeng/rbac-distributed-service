package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupRemoveInfo;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 权限组移除信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputPermissionGroupRemoveInfo implements Bean {

    private static final long serialVersionUID = 1431123608381470562L;

    public static PermissionGroupRemoveInfo toStackBean(WebInputPermissionGroupRemoveInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PermissionGroupRemoveInfo(
                    WebInputPermissionGroupKey.toStackBean(webInput.getKey())
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputPermissionGroupKey key;

    public WebInputPermissionGroupRemoveInfo() {
    }

    public WebInputPermissionGroupKey getKey() {
        return key;
    }

    public void setKey(WebInputPermissionGroupKey key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "WebInputPermissionGroupRemoveInfo{" +
                "key=" + key +
                '}';
    }
}

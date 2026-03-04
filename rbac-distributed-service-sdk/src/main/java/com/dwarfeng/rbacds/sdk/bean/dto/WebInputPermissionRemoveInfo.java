package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPermissionKey;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionRemoveInfo;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 权限移除信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputPermissionRemoveInfo implements Bean {

    private static final long serialVersionUID = 1214352335124948887L;

    public static PermissionRemoveInfo toStackBean(WebInputPermissionRemoveInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PermissionRemoveInfo(
                    WebInputPermissionKey.toStackBean(webInput.getKey())
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputPermissionKey key;

    public WebInputPermissionRemoveInfo() {
    }

    public WebInputPermissionKey getKey() {
        return key;
    }

    public void setKey(WebInputPermissionKey key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "WebInputPermissionRemoveInfo{" +
                "key=" + key +
                '}';
    }
}

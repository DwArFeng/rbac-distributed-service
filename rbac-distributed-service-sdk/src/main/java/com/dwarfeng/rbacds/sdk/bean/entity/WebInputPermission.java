package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 权限。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputPermission implements Bean {

    private static final long serialVersionUID = 7403275233867303245L;

    public static Permission toStackBean(WebInputPermission webInputPermission) {
        if (Objects.isNull(webInputPermission)) {
            return null;
        }
        return new Permission(
                WebInputStringIdKey.toStackBean(webInputPermission.getKey()),
                webInputPermission.getRemark()
        );
    }

    @JSONField(name = "key")
    @Valid
    @NotNull
    private WebInputStringIdKey key;

    @JSONField(name = "remark")
    private String remark;

    public WebInputPermission() {
    }

    public WebInputStringIdKey getKey() {
        return key;
    }

    public void setKey(WebInputStringIdKey key) {
        this.key = key;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputPermission{" +
                "key=" + key +
                ", remark='" + remark + '\'' +
                '}';
    }
}

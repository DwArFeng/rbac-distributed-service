package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 用户。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputUser implements Bean {

    private static final long serialVersionUID = 3139169583343730301L;

    public static User toStackBean(WebInputUser webInputUser) {
        if (Objects.isNull(webInputUser)) {
            return null;
        }
        return new User(
                WebInputStringIdKey.toStackBean(webInputUser.getKey()),
                webInputUser.getRemark()
        );
    }

    @JSONField(name = "key")
    @Valid
    @NotNull
    private WebInputStringIdKey key;

    @JSONField(name = "remark")
    private String remark;

    public WebInputUser() {
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
        return "WebInputUser{" +
                "key=" + key +
                ", remark='" + remark + '\'' +
                '}';
    }
}

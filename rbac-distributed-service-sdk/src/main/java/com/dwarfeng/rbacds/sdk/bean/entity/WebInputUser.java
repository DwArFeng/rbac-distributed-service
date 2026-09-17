package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;
import com.dwarfeng.subgrade.web.sdk.bean.key.WebInputStringIdKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.util.Objects;

/**
 * WebInput 用户。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputUser implements Bean {

    @Serial
    private static final long serialVersionUID = 3464216996890266014L;

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
    @Length(max = Constraints.LENGTH_REMARK)
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

package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.Objects;

/**
 * WebInput 权限表达式。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputPexp implements Bean {

    private static final long serialVersionUID = 955803288564850694L;

    public static Pexp toStackBean(WebInputPexp webInputPexp) {
        if (Objects.isNull(webInputPexp)) {
            return null;
        }
        return new Pexp(
                WebInputLongIdKey.toStackBean(webInputPexp.getKey()),
                WebInputStringIdKey.toStackBean(webInputPexp.getRoleKey()),
                webInputPexp.getContent(),
                webInputPexp.getRemark()
        );
    }

    @JSONField(name = "key")
    @Valid
    @NotNull(groups = Default.class)
    private WebInputLongIdKey key;

    @JSONField(name = "role_key")
    @Valid
    private WebInputStringIdKey roleKey;

    @JSONField(name = "content")
    @NotNull
    @NotEmpty
    private String content;

    @JSONField(name = "remark")
    private String remark;

    public WebInputPexp() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
        this.key = key;
    }

    public WebInputStringIdKey getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(WebInputStringIdKey roleKey) {
        this.roleKey = roleKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputPexp{" +
                "key=" + key +
                ", roleKey=" + roleKey +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

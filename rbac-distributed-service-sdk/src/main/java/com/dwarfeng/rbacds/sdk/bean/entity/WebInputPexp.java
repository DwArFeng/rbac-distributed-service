package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

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

    private static final long serialVersionUID = -7939569734171035493L;

    public static Pexp toStackBean(WebInputPexp webInputPexp) {
        if (Objects.isNull(webInputPexp)) {
            return null;
        }
        return new Pexp(
                WebInputLongIdKey.toStackBean(webInputPexp.getKey()),
                WebInputStringIdKey.toStackBean(webInputPexp.getRoleKey()),
                webInputPexp.getContent(),
                webInputPexp.getDescription(),
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
    @Length(max = Constraints.LENGTH_CONTENT)
    private String content;

    @JSONField(name = "description")
    @Length(max = Constraints.LENGTH_DESCRIPTION)
    private String description;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPexpKey;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.rbacds.stack.bean.dto.PexpCreateInfo;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.util.Objects;

/**
 * WebInput 权限表达式创建信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputPexpCreateInfo implements Bean {

    @Serial
    private static final long serialVersionUID = -4038932233179507120L;

    public static PexpCreateInfo toStackBean(WebInputPexpCreateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PexpCreateInfo(
                    WebInputPexpKey.toStackBean(webInput.getKey()),
                    webInput.getContent(),
                    webInput.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputPexpKey key;

    @JSONField(name = "content")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_CONTENT)
    private String content;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputPexpCreateInfo() {
    }

    public WebInputPexpKey getKey() {
        return key;
    }

    public void setKey(WebInputPexpKey key) {
        this.key = key;
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
        return "WebInputPexpCreateInfo{" +
                "key=" + key +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

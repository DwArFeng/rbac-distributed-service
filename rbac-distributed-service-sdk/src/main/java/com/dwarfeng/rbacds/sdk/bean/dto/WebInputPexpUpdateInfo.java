package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPexpKey;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.rbacds.stack.bean.dto.PexpUpdateInfo;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 权限表达式更新信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputPexpUpdateInfo implements Bean {

    private static final long serialVersionUID = -6304715031060938743L;

    public static PexpUpdateInfo toStackBean(WebInputPexpUpdateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PexpUpdateInfo(
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

    public WebInputPexpUpdateInfo() {
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
        return "WebInputPexpUpdateInfo{" +
                "key=" + key +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPexpKey;
import com.dwarfeng.rbacds.stack.bean.dto.PexpRemoveInfo;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 权限表达式移除信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputPexpRemoveInfo implements Bean {

    private static final long serialVersionUID = -466245090000048466L;

    public static PexpRemoveInfo toStackBean(WebInputPexpRemoveInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PexpRemoveInfo(
                    WebInputPexpKey.toStackBean(webInput.getKey())
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputPexpKey key;

    public WebInputPexpRemoveInfo() {
    }

    public WebInputPexpKey getKey() {
        return key;
    }

    public void setKey(WebInputPexpKey key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "WebInputPexpRemoveInfo{" +
                "key=" + key +
                '}';
    }
}

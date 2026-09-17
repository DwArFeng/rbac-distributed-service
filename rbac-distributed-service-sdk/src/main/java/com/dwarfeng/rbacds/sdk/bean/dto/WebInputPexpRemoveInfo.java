package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPexpKey;
import com.dwarfeng.rbacds.stack.bean.dto.PexpRemoveInfo;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.util.Objects;

/**
 * WebInput 权限表达式移除信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputPexpRemoveInfo implements Bean {

    @Serial
    private static final long serialVersionUID = 5884305523704217014L;

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

package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPermissionMetaKey;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionMeta;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 权限元数据。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public class WebInputPermissionMeta implements Bean {

    private static final long serialVersionUID = -7220551153802806110L;

    public static PermissionMeta toStackBean(WebInputPermissionMeta webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PermissionMeta(
                    WebInputPermissionMetaKey.toStackBean(webInput.getKey()),
                    webInput.getValue(),
                    webInput.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputPermissionMetaKey key;

    @JSONField(name = "value")
    @Length(max = Constraints.LENGTH_CONTENT)
    private String value;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputPermissionMeta() {
    }

    public WebInputPermissionMetaKey getKey() {
        return key;
    }

    public void setKey(WebInputPermissionMetaKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputPermissionMeta{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.FastJsonPermissionMetaKey;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionMeta;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 权限元数据。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public class FastJsonPermissionMeta implements Bean {

    private static final long serialVersionUID = 6197582645467465011L;

    public static FastJsonPermissionMeta of(PermissionMeta permissionMeta) {
        if (Objects.isNull(permissionMeta)) {
            return null;
        } else {
            return new FastJsonPermissionMeta(
                    FastJsonPermissionMetaKey.of(permissionMeta.getKey()),
                    permissionMeta.getValue(),
                    permissionMeta.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonPermissionMetaKey key;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    public FastJsonPermissionMeta() {
    }

    public FastJsonPermissionMeta(FastJsonPermissionMetaKey key, String value, String remark) {
        this.key = key;
        this.value = value;
        this.remark = remark;
    }

    public FastJsonPermissionMetaKey getKey() {
        return key;
    }

    public void setKey(FastJsonPermissionMetaKey key) {
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
        return "FastJsonPermissionMeta{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

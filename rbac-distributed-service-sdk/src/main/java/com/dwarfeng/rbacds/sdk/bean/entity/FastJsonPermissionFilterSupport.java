package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionFilterSupport;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 权限过滤器支持。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public class FastJsonPermissionFilterSupport implements Bean {

    private static final long serialVersionUID = -4596234215773592235L;

    public static FastJsonPermissionFilterSupport of(PermissionFilterSupport permissionFilterSupport) {
        if (Objects.isNull(permissionFilterSupport)) {
            return null;
        } else {
            return new FastJsonPermissionFilterSupport(
                    FastJsonStringIdKey.of(permissionFilterSupport.getKey()),
                    permissionFilterSupport.getLabel(),
                    permissionFilterSupport.getDescription(),
                    permissionFilterSupport.getExamplePattern()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "label", ordinal = 2)
    private String label;

    @JSONField(name = "description", ordinal = 3)
    private String description;

    @JSONField(name = "example_pattern", ordinal = 4)
    private String examplePattern;

    public FastJsonPermissionFilterSupport() {
    }

    public FastJsonPermissionFilterSupport(
            FastJsonStringIdKey key, String label, String description, String examplePattern
    ) {
        this.key = key;
        this.label = label;
        this.description = description;
        this.examplePattern = examplePattern;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExamplePattern() {
        return examplePattern;
    }

    public void setExamplePattern(String examplePattern) {
        this.examplePattern = examplePattern;
    }

    @Override
    public String toString() {
        return "FastJsonPermissionFilterSupport{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", examplePattern='" + examplePattern + '\'' +
                '}';
    }
}

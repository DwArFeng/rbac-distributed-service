package com.dwarfeng.rbacds.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 过滤器支持。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FilterSupport implements Entity<StringIdKey> {

    private static final long serialVersionUID = 2925205433490290389L;

    private StringIdKey key;
    private String label;
    private String description;
    private String examplePattern;

    public FilterSupport() {
    }

    public FilterSupport(StringIdKey key, String label, String description, String examplePattern) {
        this.key = key;
        this.label = label;
        this.description = description;
        this.examplePattern = examplePattern;
    }

    @Override
    public StringIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(StringIdKey key) {
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
        return "FilterSupport{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", examplePattern='" + examplePattern + '\'' +
                '}';
    }
}

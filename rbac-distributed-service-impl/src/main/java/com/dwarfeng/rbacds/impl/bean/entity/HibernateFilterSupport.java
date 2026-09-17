package com.dwarfeng.rbacds.impl.bean.entity;

import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;
import com.dwarfeng.subgrade.data.sdk.bean.key.hibernate.HibernateStringIdKey;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.io.Serial;
import java.util.Objects;

@jakarta.persistence.Entity
@IdClass(HibernateStringIdKey.class)
@Table(name = "tbl_filter_support")
public class HibernateFilterSupport implements Bean {

    @Serial
    private static final long serialVersionUID = -6798842563775884841L;

    // region 主键

    @Id
    @Column(name = "id", length = Constraints.LENGTH_TYPE, nullable = false, unique = true)
    private String stringId;

    // endregion

    // region 主属性字段

    @Column(name = "label", length = 50, nullable = false)
    private String label;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "example_pattern", columnDefinition = "TEXT")
    private String examplePattern;

    // endregion

    public HibernateFilterSupport() {
    }

    // region 映射用属性区

    public HibernateStringIdKey getKey() {
        if (Objects.isNull(stringId)) {
            return null;
        }
        return new HibernateStringIdKey(stringId);
    }

    public void setKey(HibernateStringIdKey key) {
        if (Objects.isNull(key)) {
            this.stringId = null;
        }
        this.stringId = key.getStringId();
    }

    // endregion

    // region 常规属性区

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
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

    // endregion

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "stringId = " + stringId + ", " +
                "label = " + label + ", " +
                "description = " + description + ", " +
                "examplePattern = " + examplePattern + ")";
    }
}

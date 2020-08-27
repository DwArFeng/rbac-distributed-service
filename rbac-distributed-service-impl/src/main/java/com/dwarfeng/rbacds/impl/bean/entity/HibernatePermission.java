package com.dwarfeng.rbacds.impl.bean.entity;

import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Optional;

@Entity
@IdClass(HibernateStringIdKey.class)
@Table(name = "tbl_permission")
public class HibernatePermission implements Bean {

    private static final long serialVersionUID = 6157426388760428480L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String stringId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    public HibernatePermission() {
    }

    public HibernateStringIdKey getKey() {
        return Optional.ofNullable(stringId).map(HibernateStringIdKey::new).orElse(null);
    }

    public void setKey(HibernateStringIdKey stringIdKey) {
        this.stringId = Optional.ofNullable(stringIdKey).map(HibernateStringIdKey::getStringId).orElse(null);
    }

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "HibernatePermission{" +
                "stringId='" + stringId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

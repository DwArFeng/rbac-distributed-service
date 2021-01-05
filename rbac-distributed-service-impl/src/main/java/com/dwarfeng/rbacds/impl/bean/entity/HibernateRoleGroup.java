package com.dwarfeng.rbacds.impl.bean.entity;

import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateStringIdKey.class)
@Table(name = "tbl_role_group")
public class HibernateRoleGroup implements Bean {

    private static final long serialVersionUID = 4550847700428838390L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String stringId;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "parent_id", length = Constraints.LENGTH_ID)
    private String parentStringId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "name", length = Constraints.LENGTH_NAME)
    private String name;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateRoleGroup.class)
    @JoinColumns({ //
            @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateRoleGroup parentGroup;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateRoleGroup.class, mappedBy = "parentGroup")
    private Set<HibernateRoleGroup> childGroups = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateRole.class, mappedBy = "group")
    private Set<HibernateRole> roles = new HashSet<>();

    public HibernateRoleGroup() {
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

    public HibernateStringIdKey getParentKey() {
        return Optional.ofNullable(parentStringId).map(HibernateStringIdKey::new).orElse(null);
    }

    public void setParentKey(HibernateStringIdKey stringIdKey) {
        this.parentStringId = Optional.ofNullable(stringIdKey).map(HibernateStringIdKey::getStringId).orElse(null);
    }

    public String getParentStringId() {
        return parentStringId;
    }

    public void setParentStringId(String parentStringId) {
        this.parentStringId = parentStringId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HibernateRoleGroup getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(HibernateRoleGroup parentGroup) {
        this.parentGroup = parentGroup;
    }

    public Set<HibernateRoleGroup> getChildGroups() {
        return childGroups;
    }

    public void setChildGroups(Set<HibernateRoleGroup> childGroups) {
        this.childGroups = childGroups;
    }

    public Set<HibernateRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<HibernateRole> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "HibernateRoleGroup{" +
                "stringId='" + stringId + '\'' +
                ", parentStringId='" + parentStringId + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

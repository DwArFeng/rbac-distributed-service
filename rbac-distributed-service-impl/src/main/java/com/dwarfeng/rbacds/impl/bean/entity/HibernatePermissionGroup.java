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
@Table(name = "tbl_permission_group")
public class HibernatePermissionGroup implements Bean {

    private static final long serialVersionUID = -3619161801409043810L;

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
    @ManyToOne(targetEntity = HibernatePermissionGroup.class)
    @JoinColumns({ //
            @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernatePermissionGroup parentGroup;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernatePermissionGroup.class, mappedBy = "parentGroup")
    private Set<HibernatePermissionGroup> childGroups = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernatePermission.class, mappedBy = "group")
    private Set<HibernatePermission> permissions = new HashSet<>();

    public HibernatePermissionGroup() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateStringIdKey getKey() {
        return Optional.ofNullable(stringId).map(HibernateStringIdKey::new).orElse(null);
    }

    public void setKey(HibernateStringIdKey stringIdKey) {
        this.stringId = Optional.ofNullable(stringIdKey).map(HibernateStringIdKey::getStringId).orElse(null);
    }

    public HibernateStringIdKey getParentKey() {
        return Optional.ofNullable(parentStringId).map(HibernateStringIdKey::new).orElse(null);
    }

    public void setParentKey(HibernateStringIdKey stringIdKey) {
        this.parentStringId = Optional.ofNullable(stringIdKey).map(HibernateStringIdKey::getStringId).orElse(null);
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
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

    public HibernatePermissionGroup getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(HibernatePermissionGroup parentGroup) {
        this.parentGroup = parentGroup;
    }

    public Set<HibernatePermissionGroup> getChildGroups() {
        return childGroups;
    }

    public void setChildGroups(Set<HibernatePermissionGroup> childGroups) {
        this.childGroups = childGroups;
    }

    public Set<HibernatePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<HibernatePermission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "stringId = " + stringId + ", " +
                "parentStringId = " + parentStringId + ", " +
                "name = " + name + ", " +
                "remark = " + remark + ", " +
                "parentGroup = " + parentGroup + ")";
    }
}

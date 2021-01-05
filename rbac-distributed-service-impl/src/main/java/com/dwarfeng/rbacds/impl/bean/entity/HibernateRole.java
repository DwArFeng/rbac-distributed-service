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
@Table(name = "tbl_role")
public class HibernateRole implements Bean {

    private static final long serialVersionUID = -178166498064464971L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", length = Constraints.LENGTH_ID, nullable = false, unique = true)
    private String stringId;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "group_id", length = Constraints.LENGTH_ID)
    private String groupStringId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "name", length = Constraints.LENGTH_NAME)
    private String name;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateRoleGroup.class)
    @JoinColumns({ //
            @JoinColumn(name = "group_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateRoleGroup group;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernatePexp.class, mappedBy = "role")
    private Set<HibernatePexp> pexps = new HashSet<>();

    // -----------------------------------------------------------多对多-----------------------------------------------------------
    @ManyToMany(targetEntity = HibernateUser.class)
    @JoinTable(name = "tbl_user_has_role", joinColumns = { //
            @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)//
    }, inverseJoinColumns = { //
            @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false),//
    })
    private Set<HibernateUser> users = new HashSet<>();

    public HibernateRole() {
    }

    public HibernateStringIdKey getKey() {
        return Optional.ofNullable(stringId).map(HibernateStringIdKey::new).orElse(null);
    }

    public void setKey(HibernateStringIdKey uuidKey) {
        this.stringId = Optional.ofNullable(uuidKey).map(HibernateStringIdKey::getStringId).orElse(null);
    }

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    public HibernateStringIdKey getGroupKey() {
        return Optional.ofNullable(groupStringId).map(HibernateStringIdKey::new).orElse(null);
    }

    public void setGroupKey(HibernateStringIdKey stringIdKey) {
        this.groupStringId = Optional.ofNullable(stringIdKey).map(HibernateStringIdKey::getStringId).orElse(null);
    }

    public String getGroupStringId() {
        return groupStringId;
    }

    public void setGroupStringId(String groupStringId) {
        this.groupStringId = groupStringId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HibernateRoleGroup getGroup() {
        return group;
    }

    public void setGroup(HibernateRoleGroup group) {
        this.group = group;
    }

    public Set<HibernatePexp> getPexps() {
        return pexps;
    }

    public void setPexps(Set<HibernatePexp> pexps) {
        this.pexps = pexps;
    }

    public Set<HibernateUser> getUsers() {
        return users;
    }

    public void setUsers(Set<HibernateUser> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "HibernateRole{" +
                "stringId='" + stringId + '\'' +
                ", groupStringId='" + groupStringId + '\'' +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                '}';
    }
}

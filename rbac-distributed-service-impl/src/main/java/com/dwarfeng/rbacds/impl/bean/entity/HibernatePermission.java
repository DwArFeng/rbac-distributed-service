package com.dwarfeng.rbacds.impl.bean.entity;

import com.dwarfeng.datamark.bean.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.bean.jpa.DatamarkField;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Optional;

@Entity
@IdClass(HibernateStringIdKey.class)
@Table(name = "tbl_permission")
@EntityListeners(DatamarkEntityListener.class)
public class HibernatePermission implements Bean {

    private static final long serialVersionUID = -7985932144962633665L;

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

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    @Column(name = "level")
    private int level;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernatePermissionGroup.class)
    @JoinColumns({ //
            @JoinColumn(name = "group_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernatePermissionGroup group;

    // -----------------------------------------------------------审计-----------------------------------------------------------
    @DatamarkField(handlerName = "permissionDatamarkHandler")
    @Column(
            name = "created_datamark",
            length = com.dwarfeng.datamark.util.Constraints.LENGTH_DATAMARK_VALUE,
            updatable = false
    )
    private String createdDatamark;

    @DatamarkField(handlerName = "permissionDatamarkHandler")
    @Column(
            name = "modified_datamark",
            length = com.dwarfeng.datamark.util.Constraints.LENGTH_DATAMARK_VALUE
    )
    private String modifiedDatamark;

    public HibernatePermission() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateStringIdKey getKey() {
        return Optional.ofNullable(stringId).map(HibernateStringIdKey::new).orElse(null);
    }

    public void setKey(HibernateStringIdKey stringIdKey) {
        this.stringId = Optional.ofNullable(stringIdKey).map(HibernateStringIdKey::getStringId).orElse(null);
    }

    public HibernateStringIdKey getGroupKey() {
        return Optional.ofNullable(groupStringId).map(HibernateStringIdKey::new).orElse(null);
    }

    public void setGroupKey(HibernateStringIdKey stringIdKey) {
        this.groupStringId = Optional.ofNullable(stringIdKey).map(HibernateStringIdKey::getStringId).orElse(null);
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public HibernatePermissionGroup getGroup() {
        return group;
    }

    public void setGroup(HibernatePermissionGroup group) {
        this.group = group;
    }

    public String getCreatedDatamark() {
        return createdDatamark;
    }

    public void setCreatedDatamark(String createdDatamark) {
        this.createdDatamark = createdDatamark;
    }

    public String getModifiedDatamark() {
        return modifiedDatamark;
    }

    public void setModifiedDatamark(String modifiedDatamark) {
        this.modifiedDatamark = modifiedDatamark;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "stringId = " + stringId + ", " +
                "groupStringId = " + groupStringId + ", " +
                "name = " + name + ", " +
                "remark = " + remark + ", " +
                "level = " + level + ", " +
                "group = " + group + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}

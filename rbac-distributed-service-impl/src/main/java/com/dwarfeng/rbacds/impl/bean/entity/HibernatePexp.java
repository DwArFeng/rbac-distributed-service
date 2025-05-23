package com.dwarfeng.rbacds.impl.bean.entity;

import com.dwarfeng.datamark.bean.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.bean.jpa.DatamarkField;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Optional;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_pexp")
@EntityListeners(DatamarkEntityListener.class)
public class HibernatePexp implements Bean {

    private static final long serialVersionUID = 2112197332733629188L;
    
    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "role_id", length = Constraints.LENGTH_ID)
    private String roleId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "content", length = Constraints.LENGTH_CONTENT, nullable = false)
    private String content;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateRole.class)
    @JoinColumns({ //
            @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateRole role;

    // -----------------------------------------------------------审计-----------------------------------------------------------
    @DatamarkField(handlerName = "roleDatamarkHandler")
    @Column(
            name = "created_datamark",
            length = com.dwarfeng.datamark.util.Constraints.LENGTH_DATAMARK_VALUE,
            updatable = false
    )
    private String createdDatamark;

    @DatamarkField(handlerName = "roleDatamarkHandler")
    @Column(
            name = "modified_datamark",
            length = com.dwarfeng.datamark.util.Constraints.LENGTH_DATAMARK_VALUE
    )
    private String modifiedDatamark;

    public HibernatePexp() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey guidKey) {
        this.longId = Optional.ofNullable(guidKey).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public HibernateStringIdKey getRoleKey() {
        return Optional.ofNullable(roleId).map(HibernateStringIdKey::new).orElse(null);
    }

    public void setRoleKey(HibernateStringIdKey guidKey) {
        this.roleId = Optional.ofNullable(guidKey).map(HibernateStringIdKey::getStringId).orElse(null);
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------

    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long guid) {
        this.longId = guid;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HibernateRole getRole() {
        return role;
    }

    public void setRole(HibernateRole role) {
        this.role = role;
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
                "longId = " + longId + ", " +
                "roleId = " + roleId + ", " +
                "content = " + content + ", " +
                "remark = " + remark + ", " +
                "role = " + role + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}

package com.dwarfeng.rbacds.impl.bean.entity;

import com.dwarfeng.datamark.bean.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.bean.jpa.DatamarkField;
import com.dwarfeng.rbacds.impl.bean.key.HibernateRoleUserRelationKey;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(HibernateRoleUserRelationKey.class)
@Table(name = "tbl_role_user_relation")
@EntityListeners(DatamarkEntityListener.class)
public class HibernateRoleUserRelation implements Bean {

    private static final long serialVersionUID = 632553143161078004L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "role_id", length = Constraints.LENGTH_ID, nullable = false)
    private String roleStringId;

    @Id
    @Column(name = "user_id", length = Constraints.LENGTH_ID, nullable = false)
    private String userStringId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateRole.class)
    @JoinColumns({ //
            @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateRole role;

    @ManyToOne(targetEntity = HibernateUser.class)
    @JoinColumns({ //
            @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateUser user;

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

    public HibernateRoleUserRelation() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateRoleUserRelationKey getKey() {
        if (Objects.isNull(roleStringId) || Objects.isNull(userStringId)) {
            return null;
        }
        return new HibernateRoleUserRelationKey(roleStringId, userStringId);
    }

    public void setKey(HibernateRoleUserRelationKey key) {
        if (Objects.isNull(key)) {
            this.roleStringId = null;
            this.userStringId = null;
        } else {
            this.roleStringId = key.getRoleStringId();
            this.userStringId = key.getUserStringId();
        }
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public String getRoleStringId() {
        return roleStringId;
    }

    public void setRoleStringId(String roleStringId) {
        this.roleStringId = roleStringId;
    }

    public String getUserStringId() {
        return userStringId;
    }

    public void setUserStringId(String userStringId) {
        this.userStringId = userStringId;
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

    public HibernateRole getRole() {
        return role;
    }

    public void setRole(HibernateRole role) {
        this.role = role;
    }

    public HibernateUser getUser() {
        return user;
    }

    public void setUser(HibernateUser user) {
        this.user = user;
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
                "roleStringId = " + roleStringId + ", " +
                "userStringId = " + userStringId + ", " +
                "enabled = " + enabled + ", " +
                "remark = " + remark + ", " +
                "role = " + role + ", " +
                "user = " + user + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}

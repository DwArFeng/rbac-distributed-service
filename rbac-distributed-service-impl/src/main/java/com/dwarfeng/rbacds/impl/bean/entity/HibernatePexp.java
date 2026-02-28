package com.dwarfeng.rbacds.impl.bean.entity;

import com.dwarfeng.datamark.bean.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.bean.jpa.DatamarkField;
import com.dwarfeng.rbacds.impl.bean.key.HibernatePexpKey;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(HibernatePexpKey.class)
@Table(name = "tbl_pexp")
@EntityListeners(DatamarkEntityListener.class)
public class HibernatePexp implements Bean {

    private static final long serialVersionUID = -847743809356659532L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "scope_id", length = Constraints.LENGTH_ID, nullable = false)
    private String scopeStringId;

    @Column(name = "role_id", length = Constraints.LENGTH_ID)
    private String roleStringId;

    @Id
    @Column(name = "pexp_id", nullable = false)
    private String pexpStringId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "content", length = Constraints.LENGTH_CONTENT, nullable = false)
    private String content;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateScope.class)
    @JoinColumns({ //
            @JoinColumn(name = "scope_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateScope scope;

    @ManyToOne(targetEntity = HibernateRole.class)
    @JoinColumns({ //
            @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateRole role;

    // -----------------------------------------------------------审计-----------------------------------------------------------
    @DatamarkField(handlerName = "pexpDatamarkHandler")
    @Column(
            name = "created_datamark",
            length = com.dwarfeng.datamark.util.Constraints.LENGTH_DATAMARK_VALUE,
            updatable = false
    )
    private String createdDatamark;

    @DatamarkField(handlerName = "pexpDatamarkHandler")
    @Column(
            name = "modified_datamark",
            length = com.dwarfeng.datamark.util.Constraints.LENGTH_DATAMARK_VALUE
    )
    private String modifiedDatamark;

    public HibernatePexp() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernatePexpKey getKey() {
        if (Objects.isNull(scopeStringId) || Objects.isNull(roleStringId) || Objects.isNull(pexpStringId)) {
            return null;
        }
        return new HibernatePexpKey(scopeStringId, roleStringId, pexpStringId);
    }

    public void setKey(HibernatePexpKey key) {
        if (Objects.isNull(key)) {
            this.scopeStringId = null;
            this.roleStringId = null;
            this.pexpStringId = null;
        } else {
            this.scopeStringId = key.getScopeStringId();
            this.roleStringId = key.getRoleStringId();
            this.pexpStringId = key.getPexpStringId();
        }
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public String getScopeStringId() {
        return scopeStringId;
    }

    public void setScopeStringId(String scopeStringId) {
        this.scopeStringId = scopeStringId;
    }

    public String getRoleStringId() {
        return roleStringId;
    }

    public void setRoleStringId(String roleStringId) {
        this.roleStringId = roleStringId;
    }

    public String getPexpStringId() {
        return pexpStringId;
    }

    public void setPexpStringId(String pexpStringId) {
        this.pexpStringId = pexpStringId;
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

    public HibernateScope getScope() {
        return scope;
    }

    public void setScope(HibernateScope scope) {
        this.scope = scope;
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
                "scopeStringId = " + scopeStringId + ", " +
                "pexpStringId = " + pexpStringId + ", " +
                "roleStringId = " + roleStringId + ", " +
                "content = " + content + ", " +
                "remark = " + remark + ", " +
                "scope = " + scope + ", " +
                "role = " + role + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}

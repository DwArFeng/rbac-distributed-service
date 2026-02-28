package com.dwarfeng.rbacds.impl.bean.entity;

import com.dwarfeng.datamark.bean.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.bean.jpa.DatamarkField;
import com.dwarfeng.rbacds.impl.bean.key.HibernatePermissionGroupKey;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@IdClass(HibernatePermissionGroupKey.class)
@Table(name = "tbl_permission_group")
@EntityListeners(DatamarkEntityListener.class)
public class HibernatePermissionGroup implements Bean {

    private static final long serialVersionUID = -2219407764326029466L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "scope_id", length = Constraints.LENGTH_ID, nullable = false)
    private String scopeStringId;

    @Id
    @Column(name = "permission_group_id", length = Constraints.LENGTH_ID, nullable = false)
    private String permissionGroupStringId;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "parent_scope_id", length = Constraints.LENGTH_ID)
    private String parentScopeStringId;

    @Column(name = "parent_permission_group_id", length = Constraints.LENGTH_ID)
    private String parentPermissionGroupStringId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "name", length = Constraints.LENGTH_NAME)
    private String name;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateScope.class)
    @JoinColumns({ //
            @JoinColumn(name = "scope_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateScope scope;

    @ManyToOne(targetEntity = HibernatePermissionGroup.class)
    @JoinColumns({ //
            @JoinColumn(
                    name = "parent_scope_id", referencedColumnName = "scope_id", insertable = false, updatable = false
            ), //
            @JoinColumn(
                    name = "parent_permission_group_id", referencedColumnName = "permission_group_id",
                    insertable = false, updatable = false
            ), //
    })
    private HibernatePermissionGroup parent;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernatePermissionGroup.class, mappedBy = "parent")
    private Set<HibernatePermissionGroup> children = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernatePermission.class, mappedBy = "group")
    private Set<HibernatePermission> permissions = new HashSet<>();

    // -----------------------------------------------------------审计-----------------------------------------------------------
    @DatamarkField(handlerName = "permissionGroupDatamarkHandler")
    @Column(
            name = "created_datamark",
            length = com.dwarfeng.datamark.util.Constraints.LENGTH_DATAMARK_VALUE,
            updatable = false
    )
    private String createdDatamark;

    @DatamarkField(handlerName = "permissionGroupDatamarkHandler")
    @Column(
            name = "modified_datamark",
            length = com.dwarfeng.datamark.util.Constraints.LENGTH_DATAMARK_VALUE
    )
    private String modifiedDatamark;

    public HibernatePermissionGroup() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernatePermissionGroupKey getKey() {
        if (Objects.isNull(scopeStringId) || Objects.isNull(permissionGroupStringId)) {
            return null;
        }
        return new HibernatePermissionGroupKey(scopeStringId, permissionGroupStringId);
    }

    public void setKey(HibernatePermissionGroupKey key) {
        if (Objects.isNull(key)) {
            this.scopeStringId = null;
            this.permissionGroupStringId = null;
        } else {
            this.scopeStringId = key.getScopeStringId();
            this.permissionGroupStringId = key.getPermissionGroupStringId();
        }
    }

    public HibernatePermissionGroupKey getParentKey() {
        if (Objects.isNull(parentScopeStringId) || Objects.isNull(parentPermissionGroupStringId)) {
            return null;
        }
        return new HibernatePermissionGroupKey(parentScopeStringId, parentPermissionGroupStringId);
    }

    public void setParentKey(HibernatePermissionGroupKey key) {
        if (Objects.isNull(key)) {
            this.parentScopeStringId = null;
            this.parentPermissionGroupStringId = null;
        } else {
            this.parentScopeStringId = key.getScopeStringId();
            this.parentPermissionGroupStringId = key.getPermissionGroupStringId();
        }
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public String getScopeStringId() {
        return scopeStringId;
    }

    public void setScopeStringId(String scopeStringId) {
        this.scopeStringId = scopeStringId;
    }

    public String getPermissionGroupStringId() {
        return permissionGroupStringId;
    }

    public void setPermissionGroupStringId(String permissionGroupStringId) {
        this.permissionGroupStringId = permissionGroupStringId;
    }

    public String getParentScopeStringId() {
        return parentScopeStringId;
    }

    public void setParentScopeStringId(String parentScopeStringId) {
        this.parentScopeStringId = parentScopeStringId;
    }

    public String getParentPermissionGroupStringId() {
        return parentPermissionGroupStringId;
    }

    public void setParentPermissionGroupStringId(String parentPermissionGroupStringId) {
        this.parentPermissionGroupStringId = parentPermissionGroupStringId;
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

    public HibernateScope getScope() {
        return scope;
    }

    public void setScope(HibernateScope scope) {
        this.scope = scope;
    }

    public HibernatePermissionGroup getParent() {
        return parent;
    }

    public void setParent(HibernatePermissionGroup parent) {
        this.parent = parent;
    }

    public Set<HibernatePermissionGroup> getChildren() {
        return children;
    }

    public void setChildren(Set<HibernatePermissionGroup> children) {
        this.children = children;
    }

    public Set<HibernatePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<HibernatePermission> permissions) {
        this.permissions = permissions;
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
                "permissionGroupStringId = " + permissionGroupStringId + ", " +
                "parentScopeStringId = " + parentScopeStringId + ", " +
                "parentPermissionGroupStringId = " + parentPermissionGroupStringId + ", " +
                "name = " + name + ", " +
                "remark = " + remark + ", " +
                "scope = " + scope + ", " +
                "parent = " + parent + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}

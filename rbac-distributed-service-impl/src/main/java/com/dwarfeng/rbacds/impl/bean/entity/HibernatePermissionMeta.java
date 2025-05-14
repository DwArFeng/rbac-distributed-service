package com.dwarfeng.rbacds.impl.bean.entity;

import com.dwarfeng.datamark.bean.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.bean.jpa.DatamarkField;
import com.dwarfeng.rbacds.impl.bean.key.HibernatePermissionMetaKey;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(HibernatePermissionMetaKey.class)
@Table(name = "tbl_permission_meta")
@EntityListeners(DatamarkEntityListener.class)
public class HibernatePermissionMeta implements Bean {

    private static final long serialVersionUID = -4509368371360638681L;
    
    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "permission_id", length = Constraints.LENGTH_ID, nullable = false)
    private String permissionStringId;

    @Id
    @Column(name = "meta_id", length = Constraints.LENGTH_ID, nullable = false)
    private String metaStringId;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "parent_id", length = Constraints.LENGTH_ID)
    private String parentStringId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "value", length = Constraints.LENGTH_CONTENT)
    private String value;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernatePermission.class)
    @JoinColumns({ //
            @JoinColumn(name = "permission_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernatePermission permission;

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

    public HibernatePermissionMeta() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernatePermissionMetaKey getKey() {
        return new HibernatePermissionMetaKey(permissionStringId, metaStringId);
    }

    public void setKey(HibernatePermissionMetaKey key) {
        if (Objects.isNull(key)) {
            this.permissionStringId = null;
            this.metaStringId = null;
        } else {
            this.permissionStringId = key.getPermissionStringId();
            this.metaStringId = key.getMetaStringId();
        }
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public String getPermissionStringId() {
        return permissionStringId;
    }

    public void setPermissionStringId(String permissionStringId) {
        this.permissionStringId = permissionStringId;
    }

    public String getMetaStringId() {
        return metaStringId;
    }

    public void setMetaStringId(String metaStringId) {
        this.metaStringId = metaStringId;
    }

    public String getParentStringId() {
        return parentStringId;
    }

    public void setParentStringId(String parentStringId) {
        this.parentStringId = parentStringId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HibernatePermission getPermission() {
        return permission;
    }

    public void setPermission(HibernatePermission permission) {
        this.permission = permission;
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
                "permissionStringId = " + permissionStringId + ", " +
                "metaStringId = " + metaStringId + ", " +
                "parentStringId = " + parentStringId + ", " +
                "value = " + value + ", " +
                "remark = " + remark + ", " +
                "permission = " + permission + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}

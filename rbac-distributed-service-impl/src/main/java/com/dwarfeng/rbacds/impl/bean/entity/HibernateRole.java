package com.dwarfeng.rbacds.impl.bean.entity;

import com.dwarfeng.datamark.sdk.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.sdk.jpa.DatamarkField;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;
import com.dwarfeng.subgrade.data.sdk.bean.key.hibernate.HibernateStringIdKey;
import jakarta.persistence.*;

import java.io.Serial;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateStringIdKey.class)
@Table(name = "tbl_role")
@EntityListeners(DatamarkEntityListener.class)
public class HibernateRole implements Bean {

    @Serial
    private static final long serialVersionUID = -6382187798101162387L;

    // region 主键

    @Id
    @Column(name = "id", length = Constraints.LENGTH_ID, nullable = false, unique = true)
    private String stringId;

    // endregion

    // region 主属性字段

    @Column(name = "name", length = Constraints.LENGTH_NAME)
    private String name;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // endregion

    // region 一对多

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateRoleUserRelation.class, mappedBy = "role")
    private Set<HibernateRoleUserRelation> roleUserRelations = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernatePexp.class, mappedBy = "role")
    private Set<HibernatePexp> pexps = new HashSet<>();

    // endregion

    // region 审计

    @DatamarkField(handlerName = "roleDatamarkHandler")
    @Column(
            name = "created_datamark",
            length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE,
            updatable = false
    )
    private String createdDatamark;

    @DatamarkField(handlerName = "roleDatamarkHandler")
    @Column(
            name = "modified_datamark",
            length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE
    )
    private String modifiedDatamark;

    // endregion

    public HibernateRole() {
    }

    // region 映射用属性区

    public HibernateStringIdKey getKey() {
        return Optional.ofNullable(stringId).map(HibernateStringIdKey::new).orElse(null);
    }

    public void setKey(HibernateStringIdKey uuidKey) {
        this.stringId = Optional.ofNullable(uuidKey).map(HibernateStringIdKey::getStringId).orElse(null);
    }

    // endregion

    // region 常规属性区

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
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

    public Set<HibernateRoleUserRelation> getRoleUserRelations() {
        return roleUserRelations;
    }

    public void setRoleUserRelations(Set<HibernateRoleUserRelation> roleUserRelations) {
        this.roleUserRelations = roleUserRelations;
    }

    public Set<HibernatePexp> getPexps() {
        return pexps;
    }

    public void setPexps(Set<HibernatePexp> pexps) {
        this.pexps = pexps;
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

    // endregion

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "stringId = " + stringId + ", " +
                "name = " + name + ", " +
                "enabled = " + enabled + ", " +
                "remark = " + remark + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}

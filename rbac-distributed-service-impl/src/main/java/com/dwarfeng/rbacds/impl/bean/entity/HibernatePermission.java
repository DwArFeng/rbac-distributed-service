package com.dwarfeng.rbacds.impl.bean.entity;

import com.alibaba.fastjson.JSONArray;
import com.dwarfeng.datamark.bean.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.bean.jpa.DatamarkField;
import com.dwarfeng.rbacds.impl.bean.key.HibernatePermissionGroupKey;
import com.dwarfeng.rbacds.impl.bean.key.HibernatePermissionKey;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@IdClass(HibernatePermissionKey.class)
@Table(name = "tbl_permission")
@EntityListeners(DatamarkEntityListener.class)
public class HibernatePermission implements Bean {

    private static final long serialVersionUID = -7279044060587755648L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "scope_id", length = Constraints.LENGTH_ID, nullable = false)
    private String scopeStringId;

    @Id
    @Column(name = "permission_id", length = Constraints.LENGTH_ID, nullable = false)
    private String permissionStringId;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "group_scope_id", length = Constraints.LENGTH_ID)
    private String groupScopeStringId;

    @Column(name = "group_permission_group_id", length = Constraints.LENGTH_ID)
    private String groupPermissionGroupStringId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "name", length = Constraints.LENGTH_NAME)
    private String name;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    @Column(name = "level")
    private int level;

    @Column(name = "group_path", columnDefinition = "TEXT")
    @Convert(converter = StringArrayStringConverter.class)
    private String[] groupPath;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateScope.class)
    @JoinColumns({ //
            @JoinColumn(name = "scope_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateScope scope;

    @ManyToOne(targetEntity = HibernatePermissionGroup.class)
    @JoinColumns({ //
            @JoinColumn(
                    name = "group_scope_id", referencedColumnName = "scope_id", insertable = false, updatable = false
            ), //
            @JoinColumn(
                    name = "group_permission_group_id", referencedColumnName = "permission_group_id",
                    insertable = false, updatable = false
            ), //
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
    public HibernatePermissionKey getKey() {
        if (Objects.isNull(scopeStringId) || Objects.isNull(permissionStringId)) {
            return null;
        }
        return new HibernatePermissionKey(scopeStringId, permissionStringId);
    }

    public void setKey(HibernatePermissionKey key) {
        if (Objects.isNull(key)) {
            this.scopeStringId = null;
            this.permissionStringId = null;
        } else {
            this.scopeStringId = key.getScopeStringId();
            this.permissionStringId = key.getPermissionStringId();
        }
    }

    public HibernatePermissionGroupKey getGroupKey() {
        if (Objects.isNull(groupScopeStringId) || Objects.isNull(groupPermissionGroupStringId)) {
            return null;
        }
        return new HibernatePermissionGroupKey(groupScopeStringId, groupPermissionGroupStringId);
    }

    public void setGroupKey(HibernatePermissionGroupKey key) {
        if (Objects.isNull(key)) {
            this.groupScopeStringId = null;
            this.groupPermissionGroupStringId = null;
        } else {
            this.groupScopeStringId = key.getScopeStringId();
            this.groupPermissionGroupStringId = key.getPermissionGroupStringId();
        }
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public String getScopeStringId() {
        return scopeStringId;
    }

    public void setScopeStringId(String scopeStringId) {
        this.scopeStringId = scopeStringId;
    }

    public String getPermissionStringId() {
        return permissionStringId;
    }

    public void setPermissionStringId(String permissionStringId) {
        this.permissionStringId = permissionStringId;
    }

    public String getGroupScopeStringId() {
        return groupScopeStringId;
    }

    public void setGroupScopeStringId(String groupScopeStringId) {
        this.groupScopeStringId = groupScopeStringId;
    }

    public String getGroupPermissionGroupStringId() {
        return groupPermissionGroupStringId;
    }

    public void setGroupPermissionGroupStringId(String groupPermissionGroupStringId) {
        this.groupPermissionGroupStringId = groupPermissionGroupStringId;
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

    public String[] getGroupPath() {
        return groupPath;
    }

    public void setGroupPath(String[] groupPath) {
        this.groupPath = groupPath;
    }

    public HibernateScope getScope() {
        return scope;
    }

    public void setScope(HibernateScope scope) {
        this.scope = scope;
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
                "scopeStringId = " + scopeStringId + ", " +
                "permissionStringId = " + permissionStringId + ", " +
                "groupScopeStringId = " + groupScopeStringId + ", " +
                "groupPermissionGroupStringId = " + groupPermissionGroupStringId + ", " +
                "name = " + name + ", " +
                "remark = " + remark + ", " +
                "level = " + level + ", " +
                "groupPath = " + Arrays.toString(groupPath) + ", " +
                "scope = " + scope + ", " +
                "group = " + group + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }

    /**
     * 字符串数组与字符串的转换器。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    @Converter
    public static class StringArrayStringConverter implements AttributeConverter<String[], String> {

        @Override
        public String convertToDatabaseColumn(String[] attribute) {
            if (Objects.isNull(attribute)) {
                return null;
            }
            return JSONArray.toJSONString(attribute);
        }

        @Override
        public String[] convertToEntityAttribute(String dbData) {
            if (Objects.isNull(dbData)) {
                return null;
            }
            return JSONArray.parseArray(dbData, String.class).toArray(new String[0]);
        }
    }
}

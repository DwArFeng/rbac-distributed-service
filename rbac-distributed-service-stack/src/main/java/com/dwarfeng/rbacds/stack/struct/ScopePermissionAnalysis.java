package com.dwarfeng.rbacds.stack.struct;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;

import java.util.List;
import java.util.Set;

/**
 * 作用域权限分析结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class ScopePermissionAnalysis {

    /**
     * 权限列表。
     *
     * <p>
     * 按照主键升序排列。
     */
    private final List<Permission> permissions;

    /**
     * 权限主键集合。
     */
    private final Set<PermissionKey> permissionKeySet;

    public ScopePermissionAnalysis(List<Permission> permissions, Set<PermissionKey> permissionKeySet) {
        this.permissions = permissions;
        this.permissionKeySet = permissionKeySet;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public Set<PermissionKey> getPermissionKeySet() {
        return permissionKeySet;
    }

    @Override
    public String toString() {
        return "ScopePermissionAnalysis{" +
                "permissions=" + permissions +
                ", permissionKeySet=" + permissionKeySet +
                '}';
    }
}

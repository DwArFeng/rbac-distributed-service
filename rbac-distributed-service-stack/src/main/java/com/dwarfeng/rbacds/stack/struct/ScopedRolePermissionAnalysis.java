package com.dwarfeng.rbacds.stack.struct;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作用域角色权限分析结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class ScopedRolePermissionAnalysis {

    /**
     * 匹配的权限列表。
     *
     * <p>
     * 按照主键升序排列。
     */
    private final List<Permission> matchedPermissions;

    /**
     * 匹配的权限主键集合。
     */
    private final Set<PermissionKey> matchedPermissionKeySet;

    private final Map<PermissionKey, Permission> acceptedPermissionMap;
    private final Map<PermissionKey, Permission> rejectedPermissionMap;
    private final Map<PermissionKey, Permission> globalRejectedPermissionMap;

    public ScopedRolePermissionAnalysis(
            List<Permission> matchedPermissions, Set<PermissionKey> matchedPermissionKeySet,
            Map<PermissionKey, Permission> acceptedPermissionMap, Map<PermissionKey, Permission> rejectedPermissionMap,
            Map<PermissionKey, Permission> globalRejectedPermissionMap
    ) {
        this.matchedPermissions = matchedPermissions;
        this.matchedPermissionKeySet = matchedPermissionKeySet;
        this.acceptedPermissionMap = acceptedPermissionMap;
        this.rejectedPermissionMap = rejectedPermissionMap;
        this.globalRejectedPermissionMap = globalRejectedPermissionMap;
    }

    public List<Permission> getMatchedPermissions() {
        return matchedPermissions;
    }

    public Set<PermissionKey> getMatchedPermissionKeySet() {
        return matchedPermissionKeySet;
    }

    public Map<PermissionKey, Permission> getAcceptedPermissionMap() {
        return acceptedPermissionMap;
    }

    public Map<PermissionKey, Permission> getRejectedPermissionMap() {
        return rejectedPermissionMap;
    }

    public Map<PermissionKey, Permission> getGlobalRejectedPermissionMap() {
        return globalRejectedPermissionMap;
    }

    @Override
    public String toString() {
        return "ScopedRolePermissionAnalysis{" +
                "matchedPermissions=" + matchedPermissions +
                ", matchedPermissionKeySet=" + matchedPermissionKeySet +
                ", acceptedPermissionMap=" + acceptedPermissionMap +
                ", rejectedPermissionMap=" + rejectedPermissionMap +
                ", globalRejectedPermissionMap=" + globalRejectedPermissionMap +
                '}';
    }
}

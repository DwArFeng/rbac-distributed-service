package com.dwarfeng.rbacds.stack.struct;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;

import java.util.List;
import java.util.Set;

/**
 * 用户权限分析结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class ScopedUserPermissionAnalysis {

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

    private final List<RoleDetail> roleDetails;

    public ScopedUserPermissionAnalysis(
            List<Permission> matchedPermissions, Set<PermissionKey> matchedPermissionKeySet,
            List<RoleDetail> roleDetails
    ) {
        this.matchedPermissions = matchedPermissions;
        this.matchedPermissionKeySet = matchedPermissionKeySet;
        this.roleDetails = roleDetails;
    }

    public List<Permission> getMatchedPermissions() {
        return matchedPermissions;
    }

    public Set<PermissionKey> getMatchedPermissionKeySet() {
        return matchedPermissionKeySet;
    }

    public List<RoleDetail> getRoleDetails() {
        return roleDetails;
    }

    @Override
    public String toString() {
        return "ScopedUserPermissionAnalysis{" +
                "matchedPermissions=" + matchedPermissions +
                ", matchedPermissionKeySet=" + matchedPermissionKeySet +
                ", roleDetails=" + roleDetails +
                '}';
    }

    /**
     * 角色详情。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static final class RoleDetail {

        private final Role role;
        private final List<Permission> acceptedPermissions;
        private final List<Permission> rejectedPermissions;
        private final List<Permission> globalRejectedPermissions;

        public RoleDetail(
                Role role, List<Permission> acceptedPermissions, List<Permission> rejectedPermissions,
                List<Permission> globalRejectedPermissions
        ) {
            this.role = role;
            this.acceptedPermissions = acceptedPermissions;
            this.rejectedPermissions = rejectedPermissions;
            this.globalRejectedPermissions = globalRejectedPermissions;
        }

        public Role getRole() {
            return role;
        }

        public List<Permission> getAcceptedPermissions() {
            return acceptedPermissions;
        }

        public List<Permission> getRejectedPermissions() {
            return rejectedPermissions;
        }

        public List<Permission> getGlobalRejectedPermissions() {
            return globalRejectedPermissions;
        }

        @Override
        public String toString() {
            return "RoleDetail{" +
                    "role=" + role +
                    ", acceptedPermissions=" + acceptedPermissions +
                    ", rejectedPermissions=" + rejectedPermissions +
                    ", globalRejectedPermissions=" + globalRejectedPermissions +
                    '}';
        }
    }
}

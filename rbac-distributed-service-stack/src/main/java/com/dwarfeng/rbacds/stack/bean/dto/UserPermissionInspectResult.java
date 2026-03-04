package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;

/**
 * 用户权限查看结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class UserPermissionInspectResult implements Dto {

    private static final long serialVersionUID = -8609548561068408165L;

    /**
     * 用户匹配的权限列表。
     */
    private List<Permission> matchedPermissions;

    /**
     * 权限匹配标志位。
     */
    private boolean matchedFlag;

    /**
     * 角色详情列表。
     *
     * <p>
     * 只在详细模式下返回。
     */
    private List<RoleDetail> roleDetails;

    public UserPermissionInspectResult() {
    }

    public UserPermissionInspectResult(
            List<Permission> matchedPermissions, boolean matchedFlag, List<RoleDetail> roleDetails
    ) {
        this.matchedPermissions = matchedPermissions;
        this.matchedFlag = matchedFlag;
        this.roleDetails = roleDetails;
    }

    public List<Permission> getMatchedPermissions() {
        return matchedPermissions;
    }

    public void setMatchedPermissions(List<Permission> matchedPermissions) {
        this.matchedPermissions = matchedPermissions;
    }

    public boolean isMatchedFlag() {
        return matchedFlag;
    }

    public void setMatchedFlag(boolean matchedFlag) {
        this.matchedFlag = matchedFlag;
    }

    public List<RoleDetail> getRoleDetails() {
        return roleDetails;
    }

    public void setRoleDetails(List<RoleDetail> roleDetails) {
        this.roleDetails = roleDetails;
    }

    @Override
    public String toString() {
        return "UserPermissionInspectResult{" +
                "matchedPermissions=" + matchedPermissions +
                ", matchedFlag=" + matchedFlag +
                ", roleDetails=" + roleDetails +
                '}';
    }

    /**
     * 角色详情。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class RoleDetail implements Dto {

        private static final long serialVersionUID = -2524467355155007930L;

        /**
         * 角色。
         */
        private Role role;

        /**
         * 角色接受的权限列表。
         */
        private List<Permission> acceptedPermissions;

        /**
         * 角色拒绝的权限列表。
         */
        private List<Permission> rejectedPermissions;

        /**
         * 角色全局拒绝的权限列表。
         */
        private List<Permission> globalRejectedPermissions;

        public RoleDetail() {
        }

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

        public void setRole(Role role) {
            this.role = role;
        }

        public List<Permission> getAcceptedPermissions() {
            return acceptedPermissions;
        }

        public void setAcceptedPermissions(List<Permission> acceptedPermissions) {
            this.acceptedPermissions = acceptedPermissions;
        }

        public List<Permission> getRejectedPermissions() {
            return rejectedPermissions;
        }

        public void setRejectedPermissions(List<Permission> rejectedPermissions) {
            this.rejectedPermissions = rejectedPermissions;
        }

        public List<Permission> getGlobalRejectedPermissions() {
            return globalRejectedPermissions;
        }

        public void setGlobalRejectedPermissions(List<Permission> globalRejectedPermissions) {
            this.globalRejectedPermissions = globalRejectedPermissions;
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

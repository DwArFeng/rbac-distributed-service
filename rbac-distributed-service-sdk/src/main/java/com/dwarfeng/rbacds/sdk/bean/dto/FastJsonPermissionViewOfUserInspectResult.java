package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermission;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonRole;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionViewOfUserInspectResult;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;

import java.io.Serial;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FastJson 以用户为依据的权限视图查看结果。
 *
 * @author DwArFeng
 * @since 2.0.2
 */
public class FastJsonPermissionViewOfUserInspectResult implements Bean {

    @Serial
    private static final long serialVersionUID = 7503416660556396004L;

    public static FastJsonPermissionViewOfUserInspectResult of(
            PermissionViewOfUserInspectResult permissionViewOfUserInspectResult
    ) {
        if (Objects.isNull(permissionViewOfUserInspectResult)) {
            return null;
        } else {
            return new FastJsonPermissionViewOfUserInspectResult(
                    Optional.ofNullable(permissionViewOfUserInspectResult.getMatchedPermissions()).map(
                            f -> f.stream().map(FastJsonPermission::of).collect(Collectors.toList())
                    ).orElse(null),
                    permissionViewOfUserInspectResult.isMatchedFlag(),
                    Optional.ofNullable(permissionViewOfUserInspectResult.getRoleDetails()).map(
                            f -> f.stream().map(FastJsonRoleDetail::of).collect(Collectors.toList())
                    ).orElse(null)
            );
        }
    }

    @JSONField(name = "matched_permissions", ordinal = 1)
    private List<FastJsonPermission> matchedPermissions;

    @JSONField(name = "matched_flag", ordinal = 2)
    private boolean matchedFlag;

    @JSONField(name = "role_details", ordinal = 3)
    private List<FastJsonRoleDetail> roleDetails;

    public FastJsonPermissionViewOfUserInspectResult() {
    }

    public FastJsonPermissionViewOfUserInspectResult(
            List<FastJsonPermission> matchedPermissions, boolean matchedFlag, List<FastJsonRoleDetail> roleDetails
    ) {
        this.matchedPermissions = matchedPermissions;
        this.matchedFlag = matchedFlag;
        this.roleDetails = roleDetails;
    }

    public List<FastJsonPermission> getMatchedPermissions() {
        return matchedPermissions;
    }

    public void setMatchedPermissions(List<FastJsonPermission> matchedPermissions) {
        this.matchedPermissions = matchedPermissions;
    }

    public boolean isMatchedFlag() {
        return matchedFlag;
    }

    public void setMatchedFlag(boolean matchedFlag) {
        this.matchedFlag = matchedFlag;
    }

    public List<FastJsonRoleDetail> getRoleDetails() {
        return roleDetails;
    }

    public void setRoleDetails(List<FastJsonRoleDetail> roleDetails) {
        this.roleDetails = roleDetails;
    }

    @Override
    public String toString() {
        return "FastJsonPermissionViewOfUserInspectResult{" +
                "matchedPermissions=" + matchedPermissions +
                ", matchedFlag=" + matchedFlag +
                ", roleDetails=" + roleDetails +
                '}';
    }

    /**
     * FastJson 角色详情。
     *
     * @author DwArFeng
     * @since 2.0.2
     */
    public static class FastJsonRoleDetail implements Bean {

        @Serial
        private static final long serialVersionUID = -8518306428306264354L;

        @SuppressWarnings("DuplicatedCode")
        public static FastJsonRoleDetail of(PermissionViewOfUserInspectResult.RoleDetail roleDetail) {
            if (Objects.isNull(roleDetail)) {
                return null;
            } else {
                return new FastJsonRoleDetail(
                        FastJsonRole.of(roleDetail.getRole()),
                        Optional.ofNullable(roleDetail.getAcceptedPermissions()).map(
                                f -> f.stream().map(FastJsonPermission::of).collect(Collectors.toList())
                        ).orElse(null),
                        Optional.ofNullable(roleDetail.getRejectedPermissions()).map(
                                f -> f.stream().map(FastJsonPermission::of).collect(Collectors.toList())
                        ).orElse(null),
                        Optional.ofNullable(roleDetail.getGlobalRejectedPermissions()).map(
                                f -> f.stream().map(FastJsonPermission::of).collect(Collectors.toList())
                        ).orElse(null)
                );
            }
        }

        @JSONField(name = "role", ordinal = 1)
        private FastJsonRole role;

        @JSONField(name = "accepted_permissions", ordinal = 2)
        private List<FastJsonPermission> acceptedPermissions;

        @JSONField(name = "rejected_permissions", ordinal = 3)
        private List<FastJsonPermission> rejectedPermissions;

        @JSONField(name = "global_rejected_permissions", ordinal = 4)
        private List<FastJsonPermission> globalRejectedPermissions;

        public FastJsonRoleDetail() {
        }

        public FastJsonRoleDetail(
                FastJsonRole role, List<FastJsonPermission> acceptedPermissions,
                List<FastJsonPermission> rejectedPermissions, List<FastJsonPermission> globalRejectedPermissions
        ) {
            this.role = role;
            this.acceptedPermissions = acceptedPermissions;
            this.rejectedPermissions = rejectedPermissions;
            this.globalRejectedPermissions = globalRejectedPermissions;
        }

        public FastJsonRole getRole() {
            return role;
        }

        public void setRole(FastJsonRole role) {
            this.role = role;
        }

        public List<FastJsonPermission> getAcceptedPermissions() {
            return acceptedPermissions;
        }

        public void setAcceptedPermissions(List<FastJsonPermission> acceptedPermissions) {
            this.acceptedPermissions = acceptedPermissions;
        }

        public List<FastJsonPermission> getRejectedPermissions() {
            return rejectedPermissions;
        }

        public void setRejectedPermissions(List<FastJsonPermission> rejectedPermissions) {
            this.rejectedPermissions = rejectedPermissions;
        }

        public List<FastJsonPermission> getGlobalRejectedPermissions() {
            return globalRejectedPermissions;
        }

        public void setGlobalRejectedPermissions(List<FastJsonPermission> globalRejectedPermissions) {
            this.globalRejectedPermissions = globalRejectedPermissions;
        }

        @Override
        public String toString() {
            return "FastJsonRoleDetail{" +
                    "role=" + role +
                    ", acceptedPermissions=" + acceptedPermissions +
                    ", rejectedPermissions=" + rejectedPermissions +
                    ", globalRejectedPermissions=" + globalRejectedPermissions +
                    '}';
        }
    }
}

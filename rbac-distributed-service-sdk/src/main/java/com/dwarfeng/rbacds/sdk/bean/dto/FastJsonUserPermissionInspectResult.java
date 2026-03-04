package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermission;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonRole;
import com.dwarfeng.rbacds.stack.bean.dto.UserPermissionInspectResult;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FastJson 用户权限查看结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonUserPermissionInspectResult implements Bean {

    private static final long serialVersionUID = 751555302923665629L;

    public static FastJsonUserPermissionInspectResult of(UserPermissionInspectResult userPermissionInspectResult) {
        if (Objects.isNull(userPermissionInspectResult)) {
            return null;
        } else {
            return new FastJsonUserPermissionInspectResult(
                    Optional.ofNullable(userPermissionInspectResult.getMatchedPermissions()).map(
                            f -> f.stream().map(FastJsonPermission::of).collect(Collectors.toList())
                    ).orElse(null),
                    userPermissionInspectResult.isMatchedFlag(),
                    Optional.ofNullable(userPermissionInspectResult.getRoleDetails()).map(
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

    public FastJsonUserPermissionInspectResult() {
    }

    public FastJsonUserPermissionInspectResult(
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
        return "FastJsonUserPermissionInspectResult{" +
                "matchedPermissions=" + matchedPermissions +
                ", matchedFlag=" + matchedFlag +
                ", roleDetails=" + roleDetails +
                '}';
    }

    /**
     * FastJson 角色详情。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class FastJsonRoleDetail implements Bean {

        private static final long serialVersionUID = 376643111973807379L;

        public static FastJsonRoleDetail of(UserPermissionInspectResult.RoleDetail roleDetail) {
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

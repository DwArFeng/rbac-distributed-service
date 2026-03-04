package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermission;
import com.dwarfeng.rbacds.stack.bean.dto.RolePermissionInspectResult;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FastJson 角色权限查看结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonRolePermissionInspectResult implements Bean {

    private static final long serialVersionUID = -9123804592144527920L;

    public static FastJsonRolePermissionInspectResult of(RolePermissionInspectResult rolePermissionInspectResult) {
        if (Objects.isNull(rolePermissionInspectResult)) {
            return null;
        } else {
            return new FastJsonRolePermissionInspectResult(
                    Optional.ofNullable(rolePermissionInspectResult.getMatchedPermissions()).map(
                            f -> f.stream().map(FastJsonPermission::of).collect(Collectors.toList())
                    ).orElse(null),
                    rolePermissionInspectResult.isMatchedFlag(),
                    Optional.ofNullable(rolePermissionInspectResult.getAcceptedPermissions()).map(
                            f -> f.stream().map(FastJsonPermission::of).collect(Collectors.toList())
                    ).orElse(null),
                    Optional.ofNullable(rolePermissionInspectResult.getRejectedPermissions()).map(
                            f -> f.stream().map(FastJsonPermission::of).collect(Collectors.toList())
                    ).orElse(null),
                    Optional.ofNullable(rolePermissionInspectResult.getGlobalRejectedPermissions()).map(
                            f -> f.stream().map(FastJsonPermission::of).collect(Collectors.toList())
                    ).orElse(null)
            );
        }
    }

    @JSONField(name = "matched_permissions", ordinal = 1)
    private List<FastJsonPermission> matchedPermissions;

    @JSONField(name = "matched_flag", ordinal = 2)
    private boolean matchedFlag;

    @JSONField(name = "accepted_permissions", ordinal = 3)
    private List<FastJsonPermission> acceptedPermissions;

    @JSONField(name = "rejected_permissions", ordinal = 4)
    private List<FastJsonPermission> rejectedPermissions;

    @JSONField(name = "global_rejected_permissions", ordinal = 5)
    private List<FastJsonPermission> globalRejectedPermissions;

    public FastJsonRolePermissionInspectResult() {
    }

    public FastJsonRolePermissionInspectResult(
            List<FastJsonPermission> matchedPermissions, boolean matchedFlag,
            List<FastJsonPermission> acceptedPermissions, List<FastJsonPermission> rejectedPermissions,
            List<FastJsonPermission> globalRejectedPermissions
    ) {
        this.matchedPermissions = matchedPermissions;
        this.matchedFlag = matchedFlag;
        this.acceptedPermissions = acceptedPermissions;
        this.rejectedPermissions = rejectedPermissions;
        this.globalRejectedPermissions = globalRejectedPermissions;
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
        return "FastJsonRolePermissionInspectResult{" +
                "matchedPermissions=" + matchedPermissions +
                ", matchedFlag=" + matchedFlag +
                ", acceptedPermissions=" + acceptedPermissions +
                ", rejectedPermissions=" + rejectedPermissions +
                ", globalRejectedPermissions=" + globalRejectedPermissions +
                '}';
    }
}

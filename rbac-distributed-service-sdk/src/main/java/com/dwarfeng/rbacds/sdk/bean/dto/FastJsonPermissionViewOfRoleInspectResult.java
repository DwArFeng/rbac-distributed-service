package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermission;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionViewOfRoleInspectResult;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;

import java.io.Serial;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FastJson 以角色为依据的权限视图查看结果。
 *
 * @author DwArFeng
 * @since 2.0.2
 */
public class FastJsonPermissionViewOfRoleInspectResult implements Bean {

    @Serial
    private static final long serialVersionUID = 7617963718435881933L;

    public static FastJsonPermissionViewOfRoleInspectResult of(
            PermissionViewOfRoleInspectResult permissionViewOfRoleInspectResult
    ) {
        if (Objects.isNull(permissionViewOfRoleInspectResult)) {
            return null;
        } else {
            return new FastJsonPermissionViewOfRoleInspectResult(
                    Optional.ofNullable(permissionViewOfRoleInspectResult.getMatchedPermissions()).map(
                            f -> f.stream().map(FastJsonPermission::of).collect(Collectors.toList())
                    ).orElse(null),
                    permissionViewOfRoleInspectResult.isMatchedFlag(),
                    Optional.ofNullable(permissionViewOfRoleInspectResult.getAcceptedPermissions()).map(
                            f -> f.stream().map(FastJsonPermission::of).collect(Collectors.toList())
                    ).orElse(null),
                    Optional.ofNullable(permissionViewOfRoleInspectResult.getRejectedPermissions()).map(
                            f -> f.stream().map(FastJsonPermission::of).collect(Collectors.toList())
                    ).orElse(null),
                    Optional.ofNullable(permissionViewOfRoleInspectResult.getGlobalRejectedPermissions()).map(
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

    public FastJsonPermissionViewOfRoleInspectResult() {
    }

    public FastJsonPermissionViewOfRoleInspectResult(
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
        return "FastJsonPermissionViewOfRoleInspectResult{" +
                "matchedPermissions=" + matchedPermissions +
                ", matchedFlag=" + matchedFlag +
                ", acceptedPermissions=" + acceptedPermissions +
                ", rejectedPermissions=" + rejectedPermissions +
                ", globalRejectedPermissions=" + globalRejectedPermissions +
                '}';
    }
}

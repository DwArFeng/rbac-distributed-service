package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonRole;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonUser;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionUserInspectResult;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FastJson 权限用户查看结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonPermissionUserInspectResult implements Bean {

    private static final long serialVersionUID = 9122753236679536187L;

    public static FastJsonPermissionUserInspectResult of(PermissionUserInspectResult permissionUserInspectResult) {
        if (Objects.isNull(permissionUserInspectResult)) {
            return null;
        } else {
            return new FastJsonPermissionUserInspectResult(
                    Optional.ofNullable(permissionUserInspectResult.getMatchedUsers()).map(
                            f -> f.stream().map(FastJsonUser::of).collect(Collectors.toList())
                    ).orElse(null),
                    permissionUserInspectResult.isMatchedFlag(),
                    Optional.ofNullable(permissionUserInspectResult.getAcceptedRoles()).map(
                            f -> f.stream().map(FastJsonRole::of).collect(Collectors.toList())
                    ).orElse(null),
                    Optional.ofNullable(permissionUserInspectResult.getRejectedRoles()).map(
                            f -> f.stream().map(FastJsonRole::of).collect(Collectors.toList())
                    ).orElse(null)
            );
        }
    }

    @JSONField(name = "matched_users", ordinal = 1)
    private List<FastJsonUser> matchedUsers;

    @JSONField(name = "matched_flag", ordinal = 2)
    private boolean matchedFlag;

    @JSONField(name = "accepted_roles", ordinal = 3)
    private List<FastJsonRole> acceptedRoles;

    @JSONField(name = "rejected_roles", ordinal = 4)
    private List<FastJsonRole> rejectedRoles;

    public FastJsonPermissionUserInspectResult() {
    }

    public FastJsonPermissionUserInspectResult(
            List<FastJsonUser> matchedUsers, boolean matchedFlag, List<FastJsonRole> acceptedRoles,
            List<FastJsonRole> rejectedRoles
    ) {
        this.matchedUsers = matchedUsers;
        this.matchedFlag = matchedFlag;
        this.acceptedRoles = acceptedRoles;
        this.rejectedRoles = rejectedRoles;
    }

    public List<FastJsonUser> getMatchedUsers() {
        return matchedUsers;
    }

    public void setMatchedUsers(List<FastJsonUser> matchedUsers) {
        this.matchedUsers = matchedUsers;
    }

    public boolean isMatchedFlag() {
        return matchedFlag;
    }

    public void setMatchedFlag(boolean matchedFlag) {
        this.matchedFlag = matchedFlag;
    }

    public List<FastJsonRole> getAcceptedRoles() {
        return acceptedRoles;
    }

    public void setAcceptedRoles(List<FastJsonRole> acceptedRoles) {
        this.acceptedRoles = acceptedRoles;
    }

    public List<FastJsonRole> getRejectedRoles() {
        return rejectedRoles;
    }

    public void setRejectedRoles(List<FastJsonRole> rejectedRoles) {
        this.rejectedRoles = rejectedRoles;
    }

    @Override
    public String toString() {
        return "FastJsonPermissionUserInspectResult{" +
                "matchedUsers=" + matchedUsers +
                ", matchedFlag=" + matchedFlag +
                ", acceptedRoles=" + acceptedRoles +
                ", rejectedRoles=" + rejectedRoles +
                '}';
    }
}

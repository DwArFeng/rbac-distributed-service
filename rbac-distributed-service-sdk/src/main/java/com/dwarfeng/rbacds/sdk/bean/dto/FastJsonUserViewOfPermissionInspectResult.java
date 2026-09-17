package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonRole;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonUser;
import com.dwarfeng.rbacds.stack.bean.dto.UserViewOfPermissionInspectResult;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;

import java.io.Serial;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FastJson 以权限为依据的用户视图查看结果。
 *
 * @author DwArFeng
 * @since 2.0.2
 */
public class FastJsonUserViewOfPermissionInspectResult implements Bean {

    @Serial
    private static final long serialVersionUID = 5861780191340314311L;

    public static FastJsonUserViewOfPermissionInspectResult of(
            UserViewOfPermissionInspectResult userViewOfPermissionInspectResult
    ) {
        if (Objects.isNull(userViewOfPermissionInspectResult)) {
            return null;
        } else {
            return new FastJsonUserViewOfPermissionInspectResult(
                    Optional.ofNullable(userViewOfPermissionInspectResult.getMatchedUsers()).map(
                            f -> f.stream().map(FastJsonUser::of).collect(Collectors.toList())
                    ).orElse(null),
                    userViewOfPermissionInspectResult.isMatchedFlag(),
                    Optional.ofNullable(userViewOfPermissionInspectResult.getAcceptedRoles()).map(
                            f -> f.stream().map(FastJsonRole::of).collect(Collectors.toList())
                    ).orElse(null),
                    Optional.ofNullable(userViewOfPermissionInspectResult.getRejectedRoles()).map(
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

    public FastJsonUserViewOfPermissionInspectResult() {
    }

    public FastJsonUserViewOfPermissionInspectResult(
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
        return "FastJsonUserViewOfPermissionInspectResult{" +
                "matchedUsers=" + matchedUsers +
                ", matchedFlag=" + matchedFlag +
                ", acceptedRoles=" + acceptedRoles +
                ", rejectedRoles=" + rejectedRoles +
                '}';
    }
}

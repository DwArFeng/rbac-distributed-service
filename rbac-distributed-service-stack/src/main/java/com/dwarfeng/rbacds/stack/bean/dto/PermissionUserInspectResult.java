package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;

/**
 * 权限用户查看结果。
 *
 * <p>
 * 该方法命名易引起歧义，已由 {@link UserViewOfPermissionInspectResult} 替代。
 * 新命名采用 {@code inspectXxxViewOfXxx} 格式，使「查询依据」与「查询对象」的关系更加明确。
 *
 * @author DwArFeng
 * @see UserViewOfPermissionInspectResult
 * @since 2.0.0
 * @deprecated 命名歧义，请使用 {@link UserViewOfPermissionInspectResult}
 */
@Deprecated
public class PermissionUserInspectResult implements Dto {

    private static final long serialVersionUID = -2034940803553612000L;

    /**
     * 指定的权限匹配的用户。
     */
    private List<User> matchedUsers;

    /**
     * 权限匹配标志位。
     */
    private boolean matchedFlag;

    /**
     * 指定的权限接受的所有角色。
     *
     * <p>
     * 只在详细模式下返回。
     */
    private List<Role> acceptedRoles;

    /**
     * 指定的权限拒绝的所有角色。
     *
     * <p>
     * 只在详细模式下返回。
     */
    private List<Role> rejectedRoles;

    public PermissionUserInspectResult() {
    }

    public PermissionUserInspectResult(
            List<User> matchedUsers, boolean matchedFlag, List<Role> acceptedRoles, List<Role> rejectedRoles
    ) {
        this.matchedUsers = matchedUsers;
        this.matchedFlag = matchedFlag;
        this.acceptedRoles = acceptedRoles;
        this.rejectedRoles = rejectedRoles;
    }

    public List<User> getMatchedUsers() {
        return matchedUsers;
    }

    public void setMatchedUsers(List<User> matchedUsers) {
        this.matchedUsers = matchedUsers;
    }

    public boolean isMatchedFlag() {
        return matchedFlag;
    }

    public void setMatchedFlag(boolean matchedFlag) {
        this.matchedFlag = matchedFlag;
    }

    public List<Role> getAcceptedRoles() {
        return acceptedRoles;
    }

    public void setAcceptedRoles(List<Role> acceptedRoles) {
        this.acceptedRoles = acceptedRoles;
    }

    public List<Role> getRejectedRoles() {
        return rejectedRoles;
    }

    public void setRejectedRoles(List<Role> rejectedRoles) {
        this.rejectedRoles = rejectedRoles;
    }

    @Override
    public String toString() {
        return "PermissionUserInspectResult{" +
                "matchedUsers=" + matchedUsers +
                ", matchedFlag=" + matchedFlag +
                ", acceptedRoles=" + acceptedRoles +
                ", rejectedRoles=" + rejectedRoles +
                '}';
    }
}

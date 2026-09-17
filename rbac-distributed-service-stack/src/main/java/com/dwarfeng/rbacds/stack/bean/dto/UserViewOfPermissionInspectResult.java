package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;

import java.io.Serial;
import java.util.List;

/**
 * 以权限为依据的用户视图查看结果。
 *
 * @author DwArFeng
 * @since 2.0.2
 */
public class UserViewOfPermissionInspectResult implements Dto {

    @Serial
    private static final long serialVersionUID = 8175770993633209792L;

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

    public UserViewOfPermissionInspectResult() {
    }

    public UserViewOfPermissionInspectResult(
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
        return "UserViewOfPermissionInspectResult{" +
                "matchedUsers=" + matchedUsers +
                ", matchedFlag=" + matchedFlag +
                ", acceptedRoles=" + acceptedRoles +
                ", rejectedRoles=" + rejectedRoles +
                '}';
    }
}

package com.dwarfeng.rbacds.stack.struct;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.List;
import java.util.Set;

/**
 * 权限分析用户分析结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class PermissionUserAnalysis {

    /**
     * 匹配的用户列表。
     *
     * <p>
     * 按照主键升序排列。
     */
    private final List<User> matchedUsers;

    /**
     * 匹配的用户主键集合。
     */
    private final Set<StringIdKey> matchedUserKeySet;

    private final List<Role> acceptedRoles;
    private final List<Role> rejectedRoles;

    public PermissionUserAnalysis(
            List<User> matchedUsers, Set<StringIdKey> matchedUserKeySet, List<Role> acceptedRoles,
            List<Role> rejectedRoles
    ) {
        this.matchedUsers = matchedUsers;
        this.matchedUserKeySet = matchedUserKeySet;
        this.acceptedRoles = acceptedRoles;
        this.rejectedRoles = rejectedRoles;
    }

    public List<User> getMatchedUsers() {
        return matchedUsers;
    }

    public Set<StringIdKey> getMatchedUserKeySet() {
        return matchedUserKeySet;
    }

    public List<Role> getAcceptedRoles() {
        return acceptedRoles;
    }

    public List<Role> getRejectedRoles() {
        return rejectedRoles;
    }

    @Override
    public String toString() {
        return "PermissionUserAnalysis{" +
                "matchedUsers=" + matchedUsers +
                ", matchedUserKeySet=" + matchedUserKeySet +
                ", acceptedRoles=" + acceptedRoles +
                ", rejectedRoles=" + rejectedRoles +
                '}';
    }
}

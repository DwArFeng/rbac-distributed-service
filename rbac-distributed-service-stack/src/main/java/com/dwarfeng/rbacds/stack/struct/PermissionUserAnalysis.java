package com.dwarfeng.rbacds.stack.struct;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

/**
 * 权限分析用户分析结果。
 *
 * @param matchedUsers      匹配的用户列表。
 *
 *                          <p>
 *                          按照主键升序排列。
 * @param matchedUserKeySet 匹配的用户主键集合。
 * @author DwArFeng
 * @since 2.0.0
 */
public record PermissionUserAnalysis(
        List<User> matchedUsers, Set<StringIdKey> matchedUserKeySet, List<Role> acceptedRoles, List<Role> rejectedRoles
) {

    @Override
    public @NotNull String toString() {
        return "PermissionUserAnalysis{" +
                "matchedUsers=" + matchedUsers +
                ", matchedUserKeySet=" + matchedUserKeySet +
                ", acceptedRoles=" + acceptedRoles +
                ", rejectedRoles=" + rejectedRoles +
                '}';
    }
}

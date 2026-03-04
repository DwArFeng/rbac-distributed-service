package com.dwarfeng.rbacds.stack.struct;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.List;
import java.util.Set;

/**
 * 用户权限分析结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class UserRoleAnalysis {

    /**
     * 匹配的角色列表。
     *
     * <p>
     * 按照主键升序排列。
     */
    private final List<Role> matchedRoles;

    /**
     * 匹配的角色主键集合。
     */
    private final Set<StringIdKey> matchedRoleKeySet;

    public UserRoleAnalysis(List<Role> matchedRoles, Set<StringIdKey> matchedRoleKeySet) {
        this.matchedRoles = matchedRoles;
        this.matchedRoleKeySet = matchedRoleKeySet;
    }

    public List<Role> getMatchedRoles() {
        return matchedRoles;
    }

    public Set<StringIdKey> getMatchedRoleKeySet() {
        return matchedRoleKeySet;
    }

    @Override
    public String toString() {
        return "UserRoleAnalysis{" +
                "matchedRoles=" + matchedRoles +
                ", matchedRoleKeySet=" + matchedRoleKeySet +
                '}';
    }
}

package com.dwarfeng.rbacds.stack.struct;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

/**
 * 用户权限分析结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public record UserRoleAnalysis(List<Role> matchedRoles, Set<StringIdKey> matchedRoleKeySet) {

    @Override
    public @NotNull String toString() {
        return "UserRoleAnalysis{" +
                "matchedRoles=" + matchedRoles +
                ", matchedRoleKeySet=" + matchedRoleKeySet +
                '}';
    }
}

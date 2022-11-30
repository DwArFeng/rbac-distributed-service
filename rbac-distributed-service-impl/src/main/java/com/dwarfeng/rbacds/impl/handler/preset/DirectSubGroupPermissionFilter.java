package com.dwarfeng.rbacds.impl.handler.preset;

import com.dwarfeng.rbacds.impl.handler.PermissionFilter;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * 子分组权限过滤器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DirectSubGroupPermissionFilter implements PermissionFilter {

    @Override
    public String getIdentifier() {
        return "DIRECT_SUB_GROUP";
    }

    @Override
    public boolean accept(String pattern, Permission permission) throws Exception {
        String groupId = Optional.ofNullable(permission).map(Permission::getGroupKey).map(StringIdKey::getStringId)
                .orElse(StringUtils.EMPTY);
        return Objects.equals(groupId, pattern);
    }
}

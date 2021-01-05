package com.dwarfeng.rbacds.impl.handler.preset;

import com.dwarfeng.rbacds.impl.handler.PermissionFilter;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import org.springframework.stereotype.Component;

/**
 * NAME 正则匹配的权限过滤器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class NameRegexPermissionFilter implements PermissionFilter {

    @Override
    public String getIdentifier() {
        return "NAME_REGEX";
    }

    @Override
    public boolean accept(String pattern, Permission permission) {
        return permission.getName().matches(pattern);
    }
}

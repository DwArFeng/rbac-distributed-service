package com.dwarfeng.rbacds.impl.handler.preset;

import com.dwarfeng.rbacds.impl.handler.PermissionFilter;
import org.springframework.stereotype.Component;

/**
 * 正则匹配的权限过滤器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class RegexPermissionFilter implements PermissionFilter {

    @Override
    public String getIdentifier() {
        return "REGEX";
    }

    @Override
    public boolean accept(String pattern, String permissionContent) {
        return permissionContent.matches(pattern);
    }
}

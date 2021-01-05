package com.dwarfeng.rbacds.impl.handler.preset;

import com.dwarfeng.rbacds.impl.handler.PermissionFilter;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import org.springframework.stereotype.Component;

/**
 * ID 前缀匹配的权限过滤器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class IdPrefixPermissionFilter implements PermissionFilter {

    @Override
    public String getIdentifier() {
        return "ID_PREFIX";
    }

    @Override
    public boolean accept(String pattern, Permission permission) {
        return permission.getKey().getStringId().startsWith(pattern);
    }
}

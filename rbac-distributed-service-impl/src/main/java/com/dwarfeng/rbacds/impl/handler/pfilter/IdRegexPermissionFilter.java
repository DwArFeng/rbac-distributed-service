package com.dwarfeng.rbacds.impl.handler.pfilter;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import org.springframework.stereotype.Component;

/**
 * ID 正则匹配的权限过滤器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class IdRegexPermissionFilter extends AbstractPermissionFilter {

    public static final String IDENTIFIER = "ID_REGEX";

    public IdRegexPermissionFilter() {
        super(IDENTIFIER);
    }

    @Override
    public boolean accept(String pattern, Permission permission) {
        return permission.getKey().getStringId().matches(pattern);
    }
}

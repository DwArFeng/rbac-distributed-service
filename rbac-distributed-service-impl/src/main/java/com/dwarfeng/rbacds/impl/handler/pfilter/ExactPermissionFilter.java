package com.dwarfeng.rbacds.impl.handler.pfilter;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 精确匹配的权限过滤器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class ExactPermissionFilter extends AbstractPermissionFilter {

    public static final String IDENTIFIER = "EXACT";

    public ExactPermissionFilter() {
        super(IDENTIFIER);
    }

    @Override
    public boolean accept(String pattern, Permission permission) {
        return Objects.equals(pattern, permission.getKey().getStringId());
    }
}

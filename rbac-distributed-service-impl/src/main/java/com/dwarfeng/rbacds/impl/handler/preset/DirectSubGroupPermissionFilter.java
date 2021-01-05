package com.dwarfeng.rbacds.impl.handler.preset;

import com.dwarfeng.rbacds.impl.handler.PermissionFilter;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
        return Objects.equals(permission.getGroupKey().getStringId(), pattern);
    }
}

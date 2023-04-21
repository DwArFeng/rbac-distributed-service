package com.dwarfeng.rbacds.impl.handler.pfilter;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 子分组权限过滤器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class NestedSubGroupPermissionFilter extends AbstractPermissionFilter {

    public static final String IDENTIFIER = "NESTED_SUB_GROUP";

    private final PermissionGroupMaintainService permissionGroupMaintainService;

    public NestedSubGroupPermissionFilter(PermissionGroupMaintainService permissionGroupMaintainService) {
        super(IDENTIFIER);
        this.permissionGroupMaintainService = permissionGroupMaintainService;
    }

    @Override
    public boolean accept(String pattern, Permission permission) throws Exception {
        StringIdKey groupKey = permission.getGroupKey();
        while (Objects.nonNull(groupKey)) {
            if (Objects.equals(groupKey.getStringId(), pattern)) {
                return true;
            }
            groupKey = permissionGroupMaintainService.get(groupKey).getParentKey();
        }
        return false;
    }
}

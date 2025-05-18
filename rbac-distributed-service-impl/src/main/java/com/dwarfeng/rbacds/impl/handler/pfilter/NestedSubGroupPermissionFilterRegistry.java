package com.dwarfeng.rbacds.impl.handler.pfilter;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.exception.PermissionFilterException;
import com.dwarfeng.rbacds.stack.exception.PermissionFilterMakeException;
import com.dwarfeng.rbacds.stack.handler.PermissionFilter;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 子分组权限过滤器注册。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Component
public class NestedSubGroupPermissionFilterRegistry extends AbstractPermissionFilterRegistry {

    public static final String PERMISSION_FILTER_TYPE = "nested_sub_group";

    private final ApplicationContext ctx;

    private final PermissionGroupMaintainService permissionGroupMaintainService;

    public NestedSubGroupPermissionFilterRegistry(
            ApplicationContext ctx,
            PermissionGroupMaintainService permissionGroupMaintainService
    ) {
        super(PERMISSION_FILTER_TYPE);
        this.ctx = ctx;
        this.permissionGroupMaintainService = permissionGroupMaintainService;
    }

    @Override
    public String provideLabel() {
        return "子分组权限过滤器";
    }

    @Override
    public String provideDescription() {
        return "接受属于指定权限组的权限。\n" +
                "如果权限所属的权限组的主键与指定的权限组的主键相同，或者是指定权限组的子分组，则接受该权限。";
    }

    @Override
    public String provideExamplePattern() {
        return "your-permission-group-id-here";
    }

    @Override
    public PermissionFilter makePermissionFilter() throws PermissionFilterException {
        try {
            return ctx.getBean(NestedSubGroupPermissionFilter.class, permissionGroupMaintainService);
        } catch (Exception e) {
            throw new PermissionFilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "NestedSubGroupPermissionFilterRegistry{" +
                "permissionFilterType='" + permissionFilterType + '\'' +
                ", permissionGroupMaintainService=" + permissionGroupMaintainService +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class NestedSubGroupPermissionFilter extends AbstractPermissionFilter {

        private final PermissionGroupMaintainService permissionGroupMaintainService;

        public NestedSubGroupPermissionFilter(PermissionGroupMaintainService permissionGroupMaintainService) {
            this.permissionGroupMaintainService = permissionGroupMaintainService;
        }

        @Override
        protected boolean doAccept(String pattern, Permission permission) throws Exception {
            StringIdKey groupKey = permission.getGroupKey();
            while (Objects.nonNull(groupKey)) {
                if (Objects.equals(groupKey.getStringId(), pattern)) {
                    return true;
                }
                groupKey = permissionGroupMaintainService.get(groupKey).getParentKey();
            }
            return false;
        }

        @Override
        public String toString() {
            return "NestedSubGroupPermissionFilter{" +
                    "permissionGroupMaintainService=" + permissionGroupMaintainService +
                    '}';
        }
    }
}

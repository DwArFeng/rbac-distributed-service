package com.dwarfeng.rbacds.impl.handler.filter;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.exception.FilterException;
import com.dwarfeng.rbacds.stack.exception.FilterMakeException;
import com.dwarfeng.rbacds.stack.handler.Filter;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * 子分组过滤器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class NestedSubGroupFilterRegistry extends AbstractFilterRegistry {

    public static final String FILTER_TYPE = "nested_sub_group";

    private final ApplicationContext ctx;

    private final PermissionGroupMaintainService permissionGroupMaintainService;

    public NestedSubGroupFilterRegistry(
            ApplicationContext ctx,
            PermissionGroupMaintainService permissionGroupMaintainService
    ) {
        super(FILTER_TYPE);
        this.ctx = ctx;
        this.permissionGroupMaintainService = permissionGroupMaintainService;
    }

    @Override
    public String provideLabel() {
        return "子分组过滤器";
    }

    @Override
    public String provideDescription() {
        return "接受属于指定权限组的权限。\n" +
                "如果权限所属的权限组的权限组 ID 与指定的权限组的权限组 ID 相同，或者是指定权限组的子分组，则接受该权限。";
    }

    @Override
    public String provideExamplePattern() {
        return "your-permission-group-identifier-here";
    }

    @Override
    public Filter makeFilter() throws FilterException {
        try {
            return ctx.getBean(NestedSubGroupFilter.class, permissionGroupMaintainService);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "NestedSubGroupFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                ", permissionGroupMaintainService=" + permissionGroupMaintainService +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class NestedSubGroupFilter extends AbstractFilter {

        public NestedSubGroupFilter() {
        }

        @Override
        protected boolean doAccept(String pattern, Permission permission) {
            String[] groupPath = Optional.ofNullable(permission).map(Permission::getGroupPath).orElse(new String[0]);
            for (int i = groupPath.length - 1; i >= 0; i--) {
                String groupIdentifier = groupPath[i];
                if (Objects.equals(groupIdentifier, pattern)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "NestedSubGroupFilter{}";
        }
    }
}

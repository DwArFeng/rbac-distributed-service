package com.dwarfeng.rbacds.impl.handler.filter;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.exception.FilterException;
import com.dwarfeng.rbacds.stack.exception.FilterMakeException;
import com.dwarfeng.rbacds.stack.handler.Filter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * 直接子分组过滤器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class DirectSubGroupFilterRegistry extends AbstractFilterRegistry {

    public static final String FILTER_TYPE = "direct_sub_group";

    private final ApplicationContext ctx;

    public DirectSubGroupFilterRegistry(ApplicationContext ctx) {
        super(FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "直接子分组过滤器";
    }

    @Override
    public String provideDescription() {
        return "接受直接属于指定权限组的权限。\n" +
                "如果权限所属的权限组的权限组 ID 与指定的权限组的权限组 ID 相同，则接受该权限。\n" +
                "只有直接子分组的权限会被接受，即使权限属于指定权限组二级或多级子权限组，也会被拒绝。";
    }

    @Override
    public String provideExamplePattern() {
        return "your-permission-group-identifier-here";
    }

    @Override
    public Filter makeFilter() throws FilterException {
        try {
            return ctx.getBean(DirectSubGroupFilter.class);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "DirectSubGroupFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class DirectSubGroupFilter extends AbstractFilter {

        public DirectSubGroupFilter() {
        }

        @Override
        protected boolean doAccept(String pattern, Permission permission) {
            String permissionGroupStringId = Optional.ofNullable(permission).map(Permission::getGroupKey)
                    .map(PermissionGroupKey::getPermissionGroupStringId).orElse(StringUtils.EMPTY);
            return Objects.equals(permissionGroupStringId, pattern);
        }

        @Override
        public String toString() {
            return "DirectSubGroupFilter{}";
        }
    }
}

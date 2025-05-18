package com.dwarfeng.rbacds.impl.handler.pfilter;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.exception.PermissionFilterException;
import com.dwarfeng.rbacds.stack.exception.PermissionFilterMakeException;
import com.dwarfeng.rbacds.stack.handler.PermissionFilter;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * 直接子分组权限过滤器注册。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Component
public class DirectSubGroupPermissionFilterRegistry extends AbstractPermissionFilterRegistry {

    public static final String PERMISSION_FILTER_TYPE = "direct_sub_group";

    private final ApplicationContext ctx;

    public DirectSubGroupPermissionFilterRegistry(ApplicationContext ctx) {
        super(PERMISSION_FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "直接子分组权限过滤器";
    }

    @Override
    public String provideDescription() {
        return "接受直接属于指定权限组的权限。\n" +
                "如果权限所属的权限组的主键与指定的权限组的主键相同，则接受该权限。\n" +
                "只有直接子分组的权限会被接受，即使权限属于指定权限组二级或多级子权限组，也会被拒绝。";
    }

    @Override
    public String provideExamplePattern() {
        return "your-permission-group-id-here";
    }

    @Override
    public PermissionFilter makePermissionFilter() throws PermissionFilterException {
        try {
            return ctx.getBean(DirectSubGroupPermissionFilter.class);
        } catch (Exception e) {
            throw new PermissionFilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "DirectSubGroupPermissionFilterRegistry{" +
                "permissionFilterType='" + permissionFilterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class DirectSubGroupPermissionFilter extends AbstractPermissionFilter {

        public DirectSubGroupPermissionFilter() {
        }

        @Override
        protected boolean doAccept(String pattern, Permission permission) {
            String groupId = Optional.ofNullable(permission).map(Permission::getGroupKey).map(StringIdKey::getStringId)
                    .orElse(StringUtils.EMPTY);
            return Objects.equals(groupId, pattern);
        }

        @Override
        public String toString() {
            return "DirectSubGroupPermissionFilter{}";
        }
    }
}

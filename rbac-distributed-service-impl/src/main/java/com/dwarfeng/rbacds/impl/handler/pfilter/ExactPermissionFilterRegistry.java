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
 * 精确权限过滤器注册。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Component
public class ExactPermissionFilterRegistry extends AbstractPermissionFilterRegistry {

    public static final String PERMISSION_FILTER_TYPE = "exact";

    private final ApplicationContext ctx;

    public ExactPermissionFilterRegistry(ApplicationContext ctx) {
        super(PERMISSION_FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "精确权限过滤器";
    }

    @Override
    public String provideDescription() {
        return "精确匹配权限的 ID。\n" +
                "如果权限的 ID 与指定的权限 ID 相同，则接受该权限；反之则拒绝。";
    }

    @Override
    public String provideExamplePattern() {
        return "your-permission-id-here";
    }

    @Override
    public PermissionFilter makePermissionFilter() throws PermissionFilterException {
        try {
            return ctx.getBean(ExactPermissionFilter.class);
        } catch (Exception e) {
            throw new PermissionFilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "ExactPermissionFilterRegistry{" +
                "permissionFilterType='" + permissionFilterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class ExactPermissionFilter extends AbstractPermissionFilter {

        public ExactPermissionFilter() {
        }

        @Override
        protected boolean doAccept(String pattern, Permission permission) {
            String permissionId = Optional.ofNullable(permission).map(Permission::getKey).map(StringIdKey::getStringId)
                    .orElse(StringUtils.EMPTY);
            return Objects.equals(permissionId, pattern);
        }

        @Override
        public String toString() {
            return "ExactPermissionFilter{}";
        }
    }
}

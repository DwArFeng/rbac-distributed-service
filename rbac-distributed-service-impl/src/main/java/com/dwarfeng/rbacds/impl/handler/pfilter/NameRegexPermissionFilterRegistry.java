package com.dwarfeng.rbacds.impl.handler.pfilter;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.exception.PermissionFilterException;
import com.dwarfeng.rbacds.stack.exception.PermissionFilterMakeException;
import com.dwarfeng.rbacds.stack.handler.PermissionFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 名称正则匹配权限过滤器注册。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Component
public class NameRegexPermissionFilterRegistry extends AbstractPermissionFilterRegistry {

    public static final String PERMISSION_FILTER_TYPE = "name_regex";

    private final ApplicationContext ctx;

    public NameRegexPermissionFilterRegistry(ApplicationContext ctx) {
        super(PERMISSION_FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "名称正则匹配权限过滤器";
    }

    @Override
    public String provideDescription() {
        return "判断权限的名称是否与指定的正则表达式匹配。\n" +
                "如果权限的名称与指定的正则表达式匹配，则接受该权限；反之则拒绝。";
    }

    @Override
    public String provideExamplePattern() {
        return "your-regex-here";
    }

    @Override
    public PermissionFilter makePermissionFilter() throws PermissionFilterException {
        try {
            return ctx.getBean(NameRegexPermissionFilter.class);
        } catch (Exception e) {
            throw new PermissionFilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "NameRegexPermissionFilterRegistry{" +
                "permissionFilterType='" + permissionFilterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class NameRegexPermissionFilter extends AbstractPermissionFilter {

        public NameRegexPermissionFilter() {
        }

        @Override
        protected boolean doAccept(String pattern, Permission permission) {
            String name = Optional.ofNullable(permission).map(Permission::getName).orElse(StringUtils.EMPTY);
            return name.matches(pattern);
        }

        @Override
        public String toString() {
            return "NameRegexPermissionFilter{}";
        }
    }
}

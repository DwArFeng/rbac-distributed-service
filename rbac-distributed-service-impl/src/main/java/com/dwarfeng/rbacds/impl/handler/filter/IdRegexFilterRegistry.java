package com.dwarfeng.rbacds.impl.handler.filter;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.exception.FilterException;
import com.dwarfeng.rbacds.stack.exception.FilterMakeException;
import com.dwarfeng.rbacds.stack.handler.Filter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * ID 正则匹配权限过滤器注册。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Component
public class IdRegexFilterRegistry extends AbstractFilterRegistry {

    public static final String PERMISSION_FILTER_TYPE = "id_regex";

    private final ApplicationContext ctx;

    public IdRegexFilterRegistry(ApplicationContext ctx) {
        super(PERMISSION_FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "ID 正则匹配权限过滤器";
    }

    @Override
    public String provideDescription() {
        return "判断权限的 ID 是否与指定的正则表达式匹配。\n" +
                "如果权限的 ID 与指定的正则表达式匹配，则接受该权限；反之则拒绝。";
    }

    @Override
    public String provideExamplePattern() {
        return "your-regex-here";
    }

    @Override
    public Filter makeFilter() throws FilterException {
        try {
            return ctx.getBean(IdRegexFilter.class);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "IdRegexFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class IdRegexFilter extends AbstractFilter {

        public IdRegexFilter() {
        }

        @Override
        protected boolean doAccept(String pattern, Permission permission) {
            String permissionStringId = Optional.ofNullable(permission).map(Permission::getKey)
                    .map(PermissionKey::getPermissionStringId).orElse(StringUtils.EMPTY);
            return permissionStringId.matches(pattern);
        }

        @Override
        public String toString() {
            return "IdRegexFilter{}";
        }
    }
}

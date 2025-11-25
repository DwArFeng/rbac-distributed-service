package com.dwarfeng.rbacds.impl.handler.filter;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.exception.FilterException;
import com.dwarfeng.rbacds.stack.exception.FilterMakeException;
import com.dwarfeng.rbacds.stack.handler.Filter;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * 精确过滤器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class ExactFilterRegistry extends AbstractFilterRegistry {

    public static final String FILTER_TYPE = "exact";

    private final ApplicationContext ctx;

    public ExactFilterRegistry(ApplicationContext ctx) {
        super(FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "精确过滤器";
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
    public Filter makeFilter() throws FilterException {
        try {
            return ctx.getBean(ExactFilter.class);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "ExactFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class ExactFilter extends AbstractFilter {

        public ExactFilter() {
        }

        @Override
        protected boolean doAccept(String pattern, Permission permission) {
            String permissionId = Optional.ofNullable(permission).map(Permission::getKey).map(StringIdKey::getStringId)
                    .orElse(StringUtils.EMPTY);
            return Objects.equals(permissionId, pattern);
        }

        @Override
        public String toString() {
            return "ExactFilter{}";
        }
    }
}

package com.dwarfeng.rbacds.impl.handler.filter;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
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
 * 名称正则匹配过滤器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class NameRegexFilterRegistry extends AbstractFilterRegistry {

    public static final String FILTER_TYPE = "name_regex";

    private final ApplicationContext ctx;

    public NameRegexFilterRegistry(ApplicationContext ctx) {
        super(FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "名称正则匹配过滤器";
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
    public Filter makeFilter() throws FilterException {
        try {
            return ctx.getBean(NameRegexFilter.class);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "NameRegexFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class NameRegexFilter extends AbstractFilter {

        public NameRegexFilter() {
        }

        @Override
        protected boolean doAccept(String pattern, Permission permission) {
            String name = Optional.ofNullable(permission).map(Permission::getName).orElse(StringUtils.EMPTY);
            return name.matches(pattern);
        }

        @Override
        public String toString() {
            return "NameRegexFilter{}";
        }
    }
}

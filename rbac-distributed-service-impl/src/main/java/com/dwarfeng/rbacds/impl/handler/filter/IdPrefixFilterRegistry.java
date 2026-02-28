package com.dwarfeng.rbacds.impl.handler.filter;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.exception.FilterException;
import com.dwarfeng.rbacds.stack.exception.FilterMakeException;
import com.dwarfeng.rbacds.stack.handler.Filter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * ID 前缀匹配权限过滤器注册。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Component
public class IdPrefixFilterRegistry extends AbstractFilterRegistry {

    public static final String PERMISSION_FILTER_TYPE = "id_prefix";

    private final ApplicationContext ctx;

    public IdPrefixFilterRegistry(ApplicationContext ctx) {
        super(PERMISSION_FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "ID 前缀匹配权限过滤器注册";
    }

    @Override
    public String provideDescription() {
        return "判断权限的 ID 是否以指定的 ID 前缀开头。\n" +
                "如果权限的 ID 以指定的 ID 前缀开头，则接受该权限；反之则拒绝。";
    }

    @Override
    public String provideExamplePattern() {
        return "your-id-prefix-here";
    }

    @Override
    public Filter makeFilter() throws FilterException {
        try {
            return ctx.getBean(IdPrefixFilter.class);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "IdPrefixFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class IdPrefixFilter extends AbstractFilter {

        public IdPrefixFilter() {
        }

        @Override
        protected boolean doAccept(String pattern, Permission permission) {
            String permissionStringId = Optional.ofNullable(permission).map(Permission::getKey)
                    .map(PermissionKey::getPermissionStringId).orElse(StringUtils.EMPTY);
            return Strings.CS.startsWith(permissionStringId, pattern);
        }

        @Override
        public String toString() {
            return "IdPrefixFilter{}";
        }
    }
}

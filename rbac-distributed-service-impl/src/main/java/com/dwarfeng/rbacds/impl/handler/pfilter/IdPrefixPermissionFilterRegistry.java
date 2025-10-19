package com.dwarfeng.rbacds.impl.handler.pfilter;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.exception.PermissionFilterException;
import com.dwarfeng.rbacds.stack.exception.PermissionFilterMakeException;
import com.dwarfeng.rbacds.stack.handler.PermissionFilter;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
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
public class IdPrefixPermissionFilterRegistry extends AbstractPermissionFilterRegistry {

    public static final String PERMISSION_FILTER_TYPE = "id_prefix";

    private final ApplicationContext ctx;

    public IdPrefixPermissionFilterRegistry(ApplicationContext ctx) {
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
    public PermissionFilter makePermissionFilter() throws PermissionFilterException {
        try {
            return ctx.getBean(IdPrefixPermissionFilter.class);
        } catch (Exception e) {
            throw new PermissionFilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "IdPrefixPermissionFilterRegistry{" +
                "permissionFilterType='" + permissionFilterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class IdPrefixPermissionFilter extends AbstractPermissionFilter {

        public IdPrefixPermissionFilter() {
        }

        @Override
        protected boolean doAccept(String pattern, Permission permission) {
            String permissionId = Optional.ofNullable(permission).map(Permission::getKey).map(StringIdKey::getStringId)
                    .orElse(StringUtils.EMPTY);
            return Strings.CS.startsWith(permissionId, pattern);
        }

        @Override
        public String toString() {
            return "IdPrefixPermissionFilter{}";
        }
    }
}

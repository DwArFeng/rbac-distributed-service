package com.dwarfeng.rbacds.impl.handler.filter;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.exception.FilterException;
import com.dwarfeng.rbacds.stack.exception.FilterMakeException;
import com.dwarfeng.rbacds.stack.handler.Filter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 等级过滤器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class LevelFilterRegistry extends AbstractFilterRegistry {

    public static final String FILTER_TYPE = "level";

    private final ApplicationContext ctx;

    public LevelFilterRegistry(ApplicationContext ctx) {
        super(FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "等级过滤器";
    }

    @Override
    public String provideDescription() {
        return "根据权限的等级进行过滤。如果权限的等级满足指定的条件，则接受该权限；反之则拒绝。\n" +
                "在执行过滤判断时，取前两个字符作为操作符，剩余字符作为等级数值。" +
                "可用的运算符有：\n" +
                "  1. eq : 等于。\n" +
                "  2. ne : 不等于。\n" +
                "  3. gt : 大于。\n" +
                "  4. ge : 大于等于。\n" +
                "  5. lt : 小于。\n" +
                "  6. le : 小于等于。\n" +
                "运算符不区分大小写。";
    }

    @Override
    public String provideExamplePattern() {
        return "lt100";
    }

    @Override
    public Filter makeFilter() throws FilterException {
        try {
            return ctx.getBean(LevelFilter.class);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "LevelFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class LevelFilter extends AbstractFilter {

        /**
         * 操作符：等于。
         */
        public static final String OPERATOR_EQ = "eq";

        /**
         * 操作符：不等于。
         */
        public static final String OPERATOR_NE = "ne";

        /**
         * 操作符：大于。
         */
        public static final String OPERATOR_GT = "gt";

        /**
         * 操作符：大于等于。
         */
        public static final String OPERATOR_GE = "ge";

        /**
         * 操作符：小于。
         */
        public static final String OPERATOR_LT = "lt";

        /**
         * 操作符：小于等于。
         */
        public static final String OPERATOR_LE = "le";

        public LevelFilter() {
        }

        @Override
        protected boolean doAccept(String pattern, Permission permission) {
            String operator = pattern.substring(0, 2).toLowerCase();
            int checkLevel = Integer.parseInt(pattern.substring(2));
            int permissionLevel = permission.getLevel();

            switch (operator) {
                case OPERATOR_EQ:
                    return permissionLevel == checkLevel;
                case OPERATOR_NE:
                    return permissionLevel != checkLevel;
                case OPERATOR_GT:
                    return permissionLevel > checkLevel;
                case OPERATOR_GE:
                    return permissionLevel >= checkLevel;
                case OPERATOR_LT:
                    return permissionLevel < checkLevel;
                case OPERATOR_LE:
                    return permissionLevel <= checkLevel;
                default:
                    throw new IllegalArgumentException("不支持的操作符: " + operator);
            }
        }

        @Override
        public String toString() {
            return "LevelFilter{}";
        }
    }
}

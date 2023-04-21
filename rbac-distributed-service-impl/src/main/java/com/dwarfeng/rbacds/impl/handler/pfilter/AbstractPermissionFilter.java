package com.dwarfeng.rbacds.impl.handler.pfilter;

import com.dwarfeng.rbacds.impl.handler.PermissionFilter;

/**
 * 权限过滤器抽象实现。
 *
 * @author DwArFeng
 * @since 1.5.1
 */
public abstract class AbstractPermissionFilter implements PermissionFilter {

    protected String identifier;

    public AbstractPermissionFilter() {
    }

    public AbstractPermissionFilter(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}

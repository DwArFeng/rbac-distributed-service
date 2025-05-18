package com.dwarfeng.rbacds.impl.handler.pfilter;

import com.dwarfeng.rbacds.sdk.handler.PermissionFilterMaker;
import com.dwarfeng.rbacds.sdk.handler.PermissionFilterSupporter;
import org.apache.commons.lang3.StringUtils;

/**
 * 抽象权限过滤器注册。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public abstract class AbstractPermissionFilterRegistry implements PermissionFilterMaker, PermissionFilterSupporter {

    protected String permissionFilterType;

    public AbstractPermissionFilterRegistry() {
    }

    public AbstractPermissionFilterRegistry(String permissionFilterType) {
        this.permissionFilterType = permissionFilterType;
    }

    @Override
    public boolean supportType(String type) {
        return StringUtils.equalsIgnoreCase(permissionFilterType, type);
    }

    @Override
    public String provideType() {
        return permissionFilterType;
    }

    public String getPermissionFilterType() {
        return permissionFilterType;
    }

    public void setPermissionFilterType(String permissionFilterType) {
        this.permissionFilterType = permissionFilterType;
    }

    @Override
    public String toString() {
        return "AbstractPermissionFilterRegistry{" +
                "permissionFilterType='" + permissionFilterType + '\'' +
                '}';
    }
}

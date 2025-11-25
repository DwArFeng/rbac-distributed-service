package com.dwarfeng.rbacds.impl.handler.filter;

import com.dwarfeng.rbacds.sdk.handler.FilterMaker;
import com.dwarfeng.rbacds.sdk.handler.FilterSupporter;
import org.apache.commons.lang3.Strings;

/**
 * 抽象过滤器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AbstractFilterRegistry implements FilterMaker, FilterSupporter {

    protected String filterType;

    public AbstractFilterRegistry() {
    }

    public AbstractFilterRegistry(String filterType) {
        this.filterType = filterType;
    }

    @Override
    public boolean supportType(String type) {
        return Strings.CI.equals(filterType, type);
    }

    @Override
    public String provideType() {
        return filterType;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    @Override
    public String toString() {
        return "AbstractFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }
}

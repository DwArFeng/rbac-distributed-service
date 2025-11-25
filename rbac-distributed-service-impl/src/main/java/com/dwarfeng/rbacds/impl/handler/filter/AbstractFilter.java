package com.dwarfeng.rbacds.impl.handler.filter;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.exception.FilterException;
import com.dwarfeng.rbacds.stack.exception.FilterExecutionException;
import com.dwarfeng.rbacds.stack.handler.Filter;

/**
 * 过滤器抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AbstractFilter implements Filter {

    @Override
    public boolean accept(String pattern, Permission permission) throws FilterException {
        try {
            return doAccept(pattern, permission);
        } catch (FilterException e) {
            throw e;
        } catch (Exception e) {
            throw new FilterExecutionException(e);
        }
    }

    /**
     * 执行过滤操作。
     *
     * @param pattern    权限表达式的匹配模式。
     * @param permission 待过滤的权限实体。
     * @return 是否接受权限实体。
     * @throws Exception 过滤器执行过程中出现的任何异常。
     */
    protected abstract boolean doAccept(String pattern, Permission permission) throws Exception;
}

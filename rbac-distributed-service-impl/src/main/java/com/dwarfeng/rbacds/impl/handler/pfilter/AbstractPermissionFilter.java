package com.dwarfeng.rbacds.impl.handler.pfilter;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.exception.PermissionFilterException;
import com.dwarfeng.rbacds.stack.exception.PermissionFilterExecutionException;
import com.dwarfeng.rbacds.stack.handler.PermissionFilter;

/**
 * 权限过滤器抽象实现。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public abstract class AbstractPermissionFilter implements PermissionFilter {

    @Override
    public boolean accept(String pattern, Permission permission) throws PermissionFilterException {
        try {
            return doAccept(pattern, permission);
        } catch (PermissionFilterException e) {
            throw e;
        } catch (Exception e) {
            throw new PermissionFilterExecutionException(e);
        }
    }

    /**
     * 执行权限过滤操作。
     *
     * @param pattern    权限表达式的匹配模式。
     * @param permission 待过滤的权限实体。
     * @return 是否接受权限实体。
     * @throws Exception 权限过滤器执行过程中出现的任何异常。
     */
    protected abstract boolean doAccept(String pattern, Permission permission) throws Exception;
}

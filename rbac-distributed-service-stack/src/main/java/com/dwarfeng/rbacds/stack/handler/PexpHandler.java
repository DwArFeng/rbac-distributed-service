package com.dwarfeng.rbacds.stack.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;
import java.util.Map;

/**
 * 权限表达式处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PexpHandler extends Handler {

    /**
     * 测试指定的权限表达式对指定的权限的接受程度。
     *
     * @param pexp       指定的权限表达式。
     * @param permission 指定的权限。
     * @return 指定的权限表达式对指定的权限的接受程度。
     * @throws HandlerException 处理器异常。
     */
    PermissionReception test(Pexp pexp, Permission permission) throws HandlerException;

    /**
     * 测试指定的权限表达式对指定的权限列表的接受程度。
     *
     * @param pexp        指定的权限表达式。
     * @param permissions 指定的权限列表。
     * @return 指定的权限表达式对指定的权限列表的接受程度。
     * @throws HandlerException 处理器异常。
     */
    Map<PermissionReception, List<Permission>> testAll(Pexp pexp, List<Permission> permissions) throws HandlerException;

    /**
     * 权限接受程度枚举。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    enum PermissionReception {

        /**
         * 接受程度：未通过。
         *
         * <p>
         * 该枚举表示权限表达式没有通过判断。
         */
        NOT_ACCEPT,

        /**
         * 接受程度：接受。
         *
         * <p>
         * 该枚举表示权限表达式已经通过判断，且为接受状态。
         */
        ACCEPT,

        /**
         * 接受程度：拒绝。
         *
         * <p>
         * 该枚举表示权限表达式已经通过判断，且为拒绝状态。
         */
        REJECT,

        /**
         * 接受程度：全局拒绝。
         *
         * <p>
         * 该枚举表示权限表达式已经通过判断，且为全局拒绝状态。
         */
        GLOBAL_REJECT
    }
}

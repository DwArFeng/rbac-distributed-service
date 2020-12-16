package com.dwarfeng.rbacds.stack.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
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
     * 分析权限表达式对应的权限。
     *
     * @param pexpsMap       角色与权限表达式列表对应的映射。
     * @param allPermissions 所有权限组成的列表。
     * @return 满足权限表达式的所有权限。
     * @throws HandlerException 处理器异常。
     */
    List<Permission> analysePexpPermissions(Map<Role, List<Pexp>> pexpsMap, List<Permission> allPermissions) throws HandlerException;

    /**
     * 分析权限对应的角色。
     *
     * @param pexpsMap   角色与权限表达式列表对应的映射。
     * @param permission 目标权限。
     * @return 权限对应角色的信息。
     * @throws HandlerException 处理器异常。
     * @since 1.1.0
     */
    PermissionRoleInfo analysePermissionRoles(Map<Role, List<Pexp>> pexpsMap, Permission permission) throws HandlerException;

    class PermissionRoleInfo implements Bean {

        private static final long serialVersionUID = -2838867005495981304L;

        private List<StringIdKey> includeRoleKeys;
        private List<StringIdKey> excludeRoleKeys;

        public PermissionRoleInfo() {
        }

        public PermissionRoleInfo(List<StringIdKey> includeRoleKeys, List<StringIdKey> excludeRoleKeys) {
            this.includeRoleKeys = includeRoleKeys;
            this.excludeRoleKeys = excludeRoleKeys;
        }

        public List<StringIdKey> getIncludeRoleKeys() {
            return includeRoleKeys;
        }

        public void setIncludeRoleKeys(List<StringIdKey> includeRoleKeys) {
            this.includeRoleKeys = includeRoleKeys;
        }

        public List<StringIdKey> getExcludeRoleKeys() {
            return excludeRoleKeys;
        }

        public void setExcludeRoleKeys(List<StringIdKey> excludeRoleKeys) {
            this.excludeRoleKeys = excludeRoleKeys;
        }

        @Override
        public String toString() {
            return "PermissionRoleInfo{" +
                    "includeRoleKeys=" + includeRoleKeys +
                    ", excludeRoleKeys=" + excludeRoleKeys +
                    '}';
        }
    }
}

package com.dwarfeng.rbacds.stack.cache;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.KeyListCache;

/**
 * 角色持有权限的缓存。
 *
 * @author DwArFeng
 * @since 1.2.4
 */
public interface RolePermissionCache extends KeyListCache<StringIdKey, Permission> {

}

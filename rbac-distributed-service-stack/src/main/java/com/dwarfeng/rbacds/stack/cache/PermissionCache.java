package com.dwarfeng.rbacds.stack.cache;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 权限缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PermissionCache extends BatchBaseCache<StringIdKey, Permission> {
}

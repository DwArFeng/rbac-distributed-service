package com.dwarfeng.rbacds.stack.cache;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 权限组缓存。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
public interface PermissionGroupCache extends BatchBaseCache<StringIdKey, PermissionGroup> {
}

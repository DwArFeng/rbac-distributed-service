package com.dwarfeng.rbacds.stack.cache;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionFilterSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 权限过滤器支持缓存。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public interface PermissionFilterSupportCache extends BatchBaseCache<StringIdKey, PermissionFilterSupport> {
}

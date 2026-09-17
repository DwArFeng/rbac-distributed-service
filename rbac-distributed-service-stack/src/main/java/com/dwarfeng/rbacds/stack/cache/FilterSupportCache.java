package com.dwarfeng.rbacds.stack.cache;

import com.dwarfeng.rbacds.stack.bean.entity.FilterSupport;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.data.stack.cache.BatchBaseCache;

/**
 * 过滤器支持缓存。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface FilterSupportCache extends BatchBaseCache<StringIdKey, FilterSupport> {
}

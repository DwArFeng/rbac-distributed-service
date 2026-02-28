package com.dwarfeng.rbacds.stack.cache;

import com.dwarfeng.rbacds.stack.bean.entity.Scope;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 作用域缓存。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface ScopeCache extends BatchBaseCache<StringIdKey, Scope> {
}

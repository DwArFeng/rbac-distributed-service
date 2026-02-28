package com.dwarfeng.rbacds.stack.cache;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 权限表达式缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 * @since 2.0.1
 */
public interface PexpCache extends BatchBaseCache<PexpKey, Pexp> {
}

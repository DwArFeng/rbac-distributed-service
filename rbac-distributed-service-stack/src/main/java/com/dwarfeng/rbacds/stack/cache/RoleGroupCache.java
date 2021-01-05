package com.dwarfeng.rbacds.stack.cache;

import com.dwarfeng.rbacds.stack.bean.entity.RoleGroup;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 角色组缓存。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
public interface RoleGroupCache extends BatchBaseCache<StringIdKey, RoleGroup> {
}

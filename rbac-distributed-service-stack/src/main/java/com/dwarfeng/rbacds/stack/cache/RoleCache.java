package com.dwarfeng.rbacds.stack.cache;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 角色缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface RoleCache extends BatchBaseCache<StringIdKey, Role> {
}

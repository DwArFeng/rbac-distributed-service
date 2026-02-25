package com.dwarfeng.rbacds.stack.cache;

import com.dwarfeng.rbacds.stack.bean.entity.RoleUserRelation;
import com.dwarfeng.rbacds.stack.bean.key.RoleUserRelationKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 角色用户关系缓存。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface RoleUserRelationCache extends BatchBaseCache<RoleUserRelationKey, RoleUserRelation> {
}

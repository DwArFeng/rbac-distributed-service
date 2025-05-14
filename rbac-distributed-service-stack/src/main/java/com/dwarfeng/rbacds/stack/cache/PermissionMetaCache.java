package com.dwarfeng.rbacds.stack.cache;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionMeta;
import com.dwarfeng.rbacds.stack.bean.key.PermissionMetaKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 权限元数据缓存。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public interface PermissionMetaCache extends BatchBaseCache<PermissionMetaKey, PermissionMeta> {
}

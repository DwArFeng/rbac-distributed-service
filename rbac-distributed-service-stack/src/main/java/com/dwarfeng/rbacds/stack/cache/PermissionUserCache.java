package com.dwarfeng.rbacds.stack.cache;

import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.KeyListCache;

/**
 * 权限持有用户的缓存。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface PermissionUserCache extends KeyListCache<StringIdKey, User> {
}

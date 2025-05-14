package com.dwarfeng.rbacds.stack.dao;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionFilterSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 权限过滤器支持数据访问层。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public interface PermissionFilterSupportDao extends BatchBaseDao<StringIdKey, PermissionFilterSupport>, EntireLookupDao<PermissionFilterSupport>,
        PresetLookupDao<PermissionFilterSupport> {
}

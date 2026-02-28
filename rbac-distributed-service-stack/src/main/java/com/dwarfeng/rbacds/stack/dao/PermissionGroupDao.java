package com.dwarfeng.rbacds.stack.dao;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 权限组数据访问层。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
public interface PermissionGroupDao extends BatchBaseDao<PermissionGroupKey, PermissionGroup>,
        PresetLookupDao<PermissionGroup>, EntireLookupDao<PermissionGroup> {
}

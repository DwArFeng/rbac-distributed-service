package com.dwarfeng.rbacds.stack.dao;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 权限数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PermissionDao extends BatchBaseDao<StringIdKey, Permission>, PresetLookupDao<Permission>,
        EntireLookupDao<Permission> {
}

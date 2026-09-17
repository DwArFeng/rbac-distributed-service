package com.dwarfeng.rbacds.stack.dao;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.subgrade.data.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.data.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.data.stack.dao.PresetLookupDao;

/**
 * 权限数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PermissionDao extends BatchBaseDao<PermissionKey, Permission>, PresetLookupDao<Permission>,
        EntireLookupDao<Permission> {
}

package com.dwarfeng.rbacds.stack.dao;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionMeta;
import com.dwarfeng.rbacds.stack.bean.key.PermissionMetaKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 权限元数据数据访问层。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public interface PermissionMetaDao extends BatchBaseDao<PermissionMetaKey, PermissionMeta>,
        PresetLookupDao<PermissionMeta>, EntireLookupDao<PermissionMeta> {
}

package com.dwarfeng.rbacds.stack.dao;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 角色数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface RoleDao extends BatchBaseDao<StringIdKey, Role>, PresetLookupDao<Role>, EntireLookupDao<Role> {
}

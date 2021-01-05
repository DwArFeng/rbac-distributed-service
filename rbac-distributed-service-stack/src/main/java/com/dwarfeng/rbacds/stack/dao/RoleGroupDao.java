package com.dwarfeng.rbacds.stack.dao;

import com.dwarfeng.rbacds.stack.bean.entity.RoleGroup;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 角色组数据访问层。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
public interface RoleGroupDao extends BatchBaseDao<StringIdKey, RoleGroup>, PresetLookupDao<RoleGroup>,
        EntireLookupDao<RoleGroup> {
}

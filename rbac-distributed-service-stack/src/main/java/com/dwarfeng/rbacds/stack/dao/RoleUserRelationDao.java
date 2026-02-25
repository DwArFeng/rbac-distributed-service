package com.dwarfeng.rbacds.stack.dao;

import com.dwarfeng.rbacds.stack.bean.entity.RoleUserRelation;
import com.dwarfeng.rbacds.stack.bean.key.RoleUserRelationKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 角色用户关系数据访问层。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface RoleUserRelationDao extends BatchBaseDao<RoleUserRelationKey, RoleUserRelation>,
        EntireLookupDao<RoleUserRelation>, PresetLookupDao<RoleUserRelation> {
}

package com.dwarfeng.rbacds.stack.dao;

import com.dwarfeng.rbacds.stack.bean.entity.Scope;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.data.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.data.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.data.stack.dao.PresetLookupDao;

/**
 * 作用域数据访问层。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface ScopeDao extends BatchBaseDao<StringIdKey, Scope>, EntireLookupDao<Scope>, PresetLookupDao<Scope> {
}

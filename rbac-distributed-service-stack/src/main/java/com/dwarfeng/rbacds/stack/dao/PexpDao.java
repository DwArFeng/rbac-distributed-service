package com.dwarfeng.rbacds.stack.dao;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 权限表达式数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PexpDao extends BatchBaseDao<LongIdKey, Pexp>, PresetLookupDao<Pexp> {
}

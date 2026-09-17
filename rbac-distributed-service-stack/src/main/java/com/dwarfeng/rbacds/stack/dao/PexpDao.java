package com.dwarfeng.rbacds.stack.dao;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.subgrade.data.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.data.stack.dao.PresetLookupDao;

/**
 * 权限表达式数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PexpDao extends BatchBaseDao<PexpKey, Pexp>, PresetLookupDao<Pexp> {
}

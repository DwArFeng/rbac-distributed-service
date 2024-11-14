package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 权限表达式维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PexpMaintainService extends BatchCrudService<LongIdKey, Pexp>, PresetLookupService<Pexp> {

    String PEXP_FOR_ROLE = "pexp_for_role";
    String PEXP_FOR_ROLE_SET = "pexp_for_role_set";
}

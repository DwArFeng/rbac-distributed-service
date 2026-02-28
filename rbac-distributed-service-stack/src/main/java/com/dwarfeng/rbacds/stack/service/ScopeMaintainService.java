package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.Scope;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 作用域维护服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface ScopeMaintainService extends BatchCrudService<StringIdKey, Scope>, EntireLookupService<Scope>,
        PresetLookupService<Scope> {

    String ID_LIKE = "id_like";
    String NAME_LIKE = "name_like";
}

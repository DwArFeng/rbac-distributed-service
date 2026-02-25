package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 角色维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface RoleMaintainService extends BatchCrudService<StringIdKey, Role>, PresetLookupService<Role>,
        EntireLookupService<Role> {

    String ID_LIKE = "id_like";
    String ENABLED = "enabled";
}

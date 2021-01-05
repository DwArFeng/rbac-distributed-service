package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.RoleGroup;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.CrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 角色组维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface RoleGroupMaintainService extends CrudService<StringIdKey, RoleGroup>, PresetLookupService<RoleGroup>,
        EntireLookupService<RoleGroup> {

    String ID_LIKE = "id_like";
    String CHILD_FOR_PARENT = "child_for_parent";
}

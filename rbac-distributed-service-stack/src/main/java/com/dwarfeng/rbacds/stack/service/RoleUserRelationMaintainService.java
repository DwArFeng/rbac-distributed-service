package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.RoleUserRelation;
import com.dwarfeng.rbacds.stack.bean.key.RoleUserRelationKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 角色用户关系维护服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface RoleUserRelationMaintainService extends BatchCrudService<RoleUserRelationKey, RoleUserRelation>,
        EntireLookupService<RoleUserRelation>, PresetLookupService<RoleUserRelation> {

    String CHILD_FOR_ROLE = "child_for_role";
    String CHILD_FOR_USER = "child_for_user";
}

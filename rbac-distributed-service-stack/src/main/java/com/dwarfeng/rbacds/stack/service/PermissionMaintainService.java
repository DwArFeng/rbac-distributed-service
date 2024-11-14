package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 权限维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PermissionMaintainService extends BatchCrudService<StringIdKey, Permission>, PresetLookupService<Permission>,
        EntireLookupService<Permission> {

    String ID_LIKE = "id_like";
    String CHILD_FOR_GROUP = "child_for_group";
}

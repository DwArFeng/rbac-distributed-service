package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 权限组维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PermissionGroupMaintainService extends BatchCrudService<StringIdKey, PermissionGroup>,
        PresetLookupService<PermissionGroup>, EntireLookupService<PermissionGroup> {

    String CHILD_FOR_PARENT = "child_for_parent";
    String ID_LIKE = "id_like";

    /**
     * @since 1.5.2
     */
    String NAME_LIKE = "name_like";
}

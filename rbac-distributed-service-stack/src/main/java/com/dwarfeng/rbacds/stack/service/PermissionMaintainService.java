package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 权限维护服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface PermissionMaintainService extends BatchCrudService<PermissionKey, Permission>,
        PresetLookupService<Permission>, EntireLookupService<Permission> {

    // region 预设查询 - 级联

    String CHILD_FOR_SCOPE = "child_for_scope";
    String CHILD_FOR_GROUP = "child_for_group";

    // endregion

    // region 预设查询 - 业务逻辑

    String CHILD_FOR_SCOPE_PERMISSION_STRING_ID_ASC = "child_for_scope_permission_string_id_asc";

    // endregion

    // region 预设查询 - UI

    String PERMISSION_STRING_ID_LIKE_PERMISSION_STRING_ID_ASC = "permission_string_id_like_permission_string_id_asc";
    String NAME_LIKE_PERMISSION_STRING_ID_ASC = "name_like_permission_string_id_asc";

    // endregion
}

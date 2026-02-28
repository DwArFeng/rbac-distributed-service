package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 权限组维护服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface PermissionGroupMaintainService extends BatchCrudService<PermissionGroupKey, PermissionGroup>,
        PresetLookupService<PermissionGroup>, EntireLookupService<PermissionGroup> {

    // region 预设查询 - 级联

    String CHILD_FOR_SCOPE = "child_for_scope";
    String CHILD_FOR_PARENT = "child_for_parent";

    // endregion

    // region 预设查询 - UI

    String CHILD_FOR_PARENT_PERMISSION_GROUP_STRING_ID_ASC = "child_for_parent_permission_group_string_id_asc";
    String PERMISSION_GROUP_STRING_ID_LIKE_PERMISSION_GROUP_STRING_ID_ASC =
            "permission_group_string_id_like_permission_group_string_id_asc";
    String CHILD_FOR_PARENT_PERMISSION_GROUP_STRING_ID_LIKE_PERMISSION_GROUP_STRING_ID_ASC =
            "child_for_parent_permission_group_string_id_like_permission_group_string_id_asc";
    String NAME_LIKE_PERMISSION_GROUP_STRING_ID_ASC = "name_like_permission_group_string_id_asc";
    String CHILD_FOR_PARENT_NAME_LIKE_PERMISSION_GROUP_STRING_ID_ASC =
            "child_for_parent_name_like_permission_group_string_id_asc";
    String CHILD_FOR_SCOPE_ROOT_PERMISSION_GROUP_STRING_ID_ASC = "child_for_scope_root_permission_group_string_id_asc";

    // endregion
}

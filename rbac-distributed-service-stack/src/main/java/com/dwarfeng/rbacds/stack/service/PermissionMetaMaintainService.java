package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionMeta;
import com.dwarfeng.rbacds.stack.bean.key.PermissionMetaKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 权限元数据维护服务。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public interface PermissionMetaMaintainService extends BatchCrudService<PermissionMetaKey, PermissionMeta>,
        PresetLookupService<PermissionMeta>, EntireLookupService<PermissionMeta> {

    String CHILD_FOR_PERMISSION = "child_for_permission";
    String CHILD_FOR_PERMISSION_META_STRING_ID_ASC = "child_for_permission_meta_string_id_asc";
}

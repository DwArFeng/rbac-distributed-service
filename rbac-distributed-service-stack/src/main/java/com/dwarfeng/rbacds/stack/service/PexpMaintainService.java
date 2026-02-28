package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 权限表达式维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PexpMaintainService extends BatchCrudService<PexpKey, Pexp>, PresetLookupService<Pexp> {

    // region 预设查询 - 级联

    String CHILD_FOR_SCOPE = "child_for_scope";
    String CHILD_FOR_ROLE = "child_for_role";

    // endregion

    // region 预设查询 - 业务逻辑

    String CHILD_FOR_SCOPE_CHILD_FOR_ROLE = "child_for_scope_child_for_role";

    // endregion

    // region 预设查询 - UI

    String CHILD_FOR_SCOPE_CHILD_FOR_ROLE_PEXP_STRING_ID_ASC = "child_for_scope_child_for_role_identifier_asc";

    // endregion
}

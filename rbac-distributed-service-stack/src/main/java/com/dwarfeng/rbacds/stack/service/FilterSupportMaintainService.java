package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.FilterSupport;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.data.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.data.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.data.stack.service.PresetLookupService;

/**
 * 过滤器支持维护服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface FilterSupportMaintainService extends BatchCrudService<StringIdKey, FilterSupport>,
        EntireLookupService<FilterSupport>, PresetLookupService<FilterSupport> {

    // region 预设查询 - UI

    String ID_LIKE = "id_like";
    String LABEL_LIKE = "label_like";

    // endregion
}

package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.FilterSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 过滤器支持维护服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface FilterSupportMaintainService extends BatchCrudService<StringIdKey, FilterSupport>,
        EntireLookupService<FilterSupport>, PresetLookupService<FilterSupport> {

    String ID_LIKE = "id_like";
    String LABEL_LIKE = "label_like";
}

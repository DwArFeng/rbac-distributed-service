package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionFilterSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 权限过滤器支持维护服务。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public interface PermissionFilterSupportMaintainService extends BatchCrudService<StringIdKey, PermissionFilterSupport>,
        EntireLookupService<PermissionFilterSupport>, PresetLookupService<PermissionFilterSupport> {

    String ID_LIKE = "id_like";
    String LABEL_LIKE = "label_like";

    /**
     * 重置权限过滤器支持。
     *
     * @throws ServiceException 服务异常。
     */
    void reset() throws ServiceException;
}

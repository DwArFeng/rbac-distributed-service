package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupUpdateInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 权限组操作服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface PermissionGroupOperateService extends Service {

    /**
     * 创建权限组。
     *
     * @param info 创建信息。
     * @return 创建结果。
     * @throws ServiceException 服务异常。
     */
    PermissionGroupCreateResult create(PermissionGroupCreateInfo info) throws ServiceException;

    /**
     * 更新权限组。
     *
     * @param info 更新信息。
     * @throws ServiceException 服务异常。
     */
    void update(PermissionGroupUpdateInfo info) throws ServiceException;

    /**
     * 移除权限组。
     *
     * @param info 移除信息。
     * @throws ServiceException 服务异常。
     */
    void remove(PermissionGroupRemoveInfo info) throws ServiceException;
}

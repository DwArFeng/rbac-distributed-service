package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.dto.PermissionCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionUpdateInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 权限操作服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface PermissionOperateService extends Service {

    /**
     * 创建权限。
     *
     * @param info 创建信息。
     * @return 创建结果。
     * @throws ServiceException 服务异常。
     */
    PermissionCreateResult create(PermissionCreateInfo info) throws ServiceException;

    /**
     * 更新权限。
     *
     * @param info 更新信息。
     * @throws ServiceException 服务异常。
     */
    void update(PermissionUpdateInfo info) throws ServiceException;

    /**
     * 移除权限。
     *
     * @param info 移除信息。
     * @throws ServiceException 服务异常。
     */
    void remove(PermissionRemoveInfo info) throws ServiceException;
}

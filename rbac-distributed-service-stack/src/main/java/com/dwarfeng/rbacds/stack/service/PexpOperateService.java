package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.dto.PexpCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PexpCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PexpRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PexpUpdateInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 权限表达式操作服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface PexpOperateService extends Service {

    /**
     * 创建权限表达式。
     *
     * @param info 创建信息。
     * @return 创建结果。
     * @throws ServiceException 服务异常。
     */
    PexpCreateResult create(PexpCreateInfo info) throws ServiceException;

    /**
     * 更新权限表达式。
     *
     * @param info 更新信息。
     * @throws ServiceException 服务异常。
     */
    void update(PexpUpdateInfo info) throws ServiceException;

    /**
     * 移除权限表达式。
     *
     * @param info 移除信息。
     * @throws ServiceException 服务异常。
     */
    void remove(PexpRemoveInfo info) throws ServiceException;
}

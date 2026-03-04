package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.dto.PermissionCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionUpdateInfo;
import com.dwarfeng.rbacds.stack.handler.PermissionOperateHandler;
import com.dwarfeng.rbacds.stack.service.PermissionOperateService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class PermissionOperateServiceImpl implements PermissionOperateService {

    private final PermissionOperateHandler permissionOperateHandler;

    private final ServiceExceptionMapper sem;

    public PermissionOperateServiceImpl(
            PermissionOperateHandler permissionOperateHandler,
            ServiceExceptionMapper sem
    ) {
        this.permissionOperateHandler = permissionOperateHandler;
        this.sem = sem;
    }

    @Override
    public PermissionCreateResult create(PermissionCreateInfo info) throws ServiceException {
        try {
            return permissionOperateHandler.create(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("创建权限时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void update(PermissionUpdateInfo info) throws ServiceException {
        try {
            permissionOperateHandler.update(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("更新权限时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void remove(PermissionRemoveInfo info) throws ServiceException {
        try {
            permissionOperateHandler.remove(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("移除权限时发生异常", LogLevel.WARN, e, sem);
        }
    }
}

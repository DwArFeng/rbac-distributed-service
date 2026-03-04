package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupUpdateInfo;
import com.dwarfeng.rbacds.stack.handler.PermissionGroupOperateHandler;
import com.dwarfeng.rbacds.stack.service.PermissionGroupOperateService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class PermissionGroupOperateServiceImpl implements PermissionGroupOperateService {

    private final PermissionGroupOperateHandler permissionGroupOperateHandler;

    private final ServiceExceptionMapper sem;

    public PermissionGroupOperateServiceImpl(
            PermissionGroupOperateHandler permissionGroupOperateHandler,
            ServiceExceptionMapper sem
    ) {
        this.permissionGroupOperateHandler = permissionGroupOperateHandler;
        this.sem = sem;
    }

    @Override
    public PermissionGroupCreateResult create(PermissionGroupCreateInfo info) throws ServiceException {
        try {
            return permissionGroupOperateHandler.create(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("创建权限组时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void update(PermissionGroupUpdateInfo info) throws ServiceException {
        try {
            permissionGroupOperateHandler.update(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("更新权限组时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void remove(PermissionGroupRemoveInfo info) throws ServiceException {
        try {
            permissionGroupOperateHandler.remove(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("移除权限组时发生异常", LogLevel.WARN, e, sem);
        }
    }
}

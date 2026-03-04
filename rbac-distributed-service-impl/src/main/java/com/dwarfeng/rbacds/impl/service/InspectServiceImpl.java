package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.dto.*;
import com.dwarfeng.rbacds.stack.handler.InspectHandler;
import com.dwarfeng.rbacds.stack.service.InspectService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class InspectServiceImpl implements InspectService {

    private final InspectHandler inspectHandler;

    private final ServiceExceptionMapper sem;

    public InspectServiceImpl(InspectHandler inspectHandler, ServiceExceptionMapper sem) {
        this.inspectHandler = inspectHandler;
        this.sem = sem;
    }

    @BehaviorAnalyse
    @SkipRecord
    @Nullable
    @Override
    public PermissionUserInspectResult inspectPermissionUser(@SkipRecord PermissionUserInspectInfo info)
            throws ServiceException {
        try {
            return inspectHandler.inspectPermissionUser(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询权限的用户信息时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Nullable
    @Override
    public RolePermissionInspectResult inspectRolePermission(@SkipRecord RolePermissionInspectInfo info)
            throws ServiceException {
        try {
            return inspectHandler.inspectRolePermission(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询角色的权限信息时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Nullable
    @Override
    public UserPermissionInspectResult inspectUserPermission(@SkipRecord UserPermissionInspectInfo info)
            throws ServiceException {
        try {
            return inspectHandler.inspectUserPermission(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询用户的权限信息时发生异常", LogLevel.WARN, e, sem);
        }
    }
}

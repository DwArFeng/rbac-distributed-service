package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.dto.*;
import com.dwarfeng.rbacds.stack.handler.InspectHandler;
import com.dwarfeng.rbacds.stack.service.InspectQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class InspectQosServiceImpl implements InspectQosService {

    private final InspectHandler inspectHandler;

    private final ServiceExceptionMapper sem;

    public InspectQosServiceImpl(InspectHandler inspectHandler, ServiceExceptionMapper sem) {
        this.inspectHandler = inspectHandler;
        this.sem = sem;
    }

    @Nullable
    @Override
    @Deprecated
    public PermissionUserInspectResult inspectPermissionUser(PermissionUserInspectInfo info) throws ServiceException {
        try {
            return inspectHandler.inspectPermissionUser(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询权限的用户信息时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Nullable
    @Override
    public UserViewOfPermissionInspectResult inspectUserViewOfPermission(UserViewOfPermissionInspectInfo info)
            throws ServiceException {
        try {
            return inspectHandler.inspectUserViewOfPermission(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("以权限为依据查看用户视图时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Nullable
    @Override
    @Deprecated
    public RolePermissionInspectResult inspectRolePermission(RolePermissionInspectInfo info) throws ServiceException {
        try {
            return inspectHandler.inspectRolePermission(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询角色的权限信息时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Nullable
    @Override
    public PermissionViewOfRoleInspectResult inspectPermissionViewOfRole(PermissionViewOfRoleInspectInfo info)
            throws ServiceException {
        try {
            return inspectHandler.inspectPermissionViewOfRole(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("以角色为依据查看权限视图时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Nullable
    @Override
    @Deprecated
    public UserPermissionInspectResult inspectUserPermission(UserPermissionInspectInfo info) throws ServiceException {
        try {
            return inspectHandler.inspectUserPermission(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询用户的权限信息时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Nullable
    @Override
    public PermissionViewOfUserInspectResult inspectPermissionViewOfUser(PermissionViewOfUserInspectInfo info)
            throws ServiceException {
        try {
            return inspectHandler.inspectPermissionViewOfUser(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("以用户为依据查看权限视图时发生异常", LogLevel.WARN, e, sem);
        }
    }
}

package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.exception.PermissionNotExistsException;
import com.dwarfeng.rbacds.stack.exception.RoleNotExistsException;
import com.dwarfeng.rbacds.stack.exception.UserNotExistsException;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.stereotype.Component;

/**
 * 处理器验证器。
 *
 * <p>
 * 为处理器提供公共的验证方法。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class HandlerValidator {

    private final UserMaintainService userMaintainService;
    private final RoleMaintainService roleMaintainService;
    private final PermissionMaintainService permissionMaintainService;

    public HandlerValidator(
            UserMaintainService userMaintainService,
            RoleMaintainService roleMaintainService,
            PermissionMaintainService permissionMaintainService
    ) {
        this.userMaintainService = userMaintainService;
        this.roleMaintainService = roleMaintainService;
        this.permissionMaintainService = permissionMaintainService;
    }

    public void makeSureUserExists(StringIdKey userKey) throws HandlerException {
        try {
            if (!userMaintainService.exists(userKey)) {
                throw new UserNotExistsException(userKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureRoleExists(StringIdKey roleKey) throws HandlerException {
        try {
            if (!roleMaintainService.exists(roleKey)) {
                throw new RoleNotExistsException(roleKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSurePermissionExists(StringIdKey permissionKey) throws HandlerException {
        try {
            if (!permissionMaintainService.exists(permissionKey)) {
                throw new PermissionNotExistsException(permissionKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }
}

package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.rbacds.stack.exception.*;
import com.dwarfeng.rbacds.stack.service.*;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

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
    private final ScopeMaintainService scopeMaintainService;
    private final PermissionGroupMaintainService permissionGroupMaintainService;
    private final PexpMaintainService pexpMaintainService;

    public HandlerValidator(
            UserMaintainService userMaintainService,
            RoleMaintainService roleMaintainService,
            PermissionMaintainService permissionMaintainService,
            ScopeMaintainService scopeMaintainService,
            PermissionGroupMaintainService permissionGroupMaintainService,
            PexpMaintainService pexpMaintainService
    ) {
        this.userMaintainService = userMaintainService;
        this.roleMaintainService = roleMaintainService;
        this.permissionMaintainService = permissionMaintainService;
        this.scopeMaintainService = scopeMaintainService;
        this.permissionGroupMaintainService = permissionGroupMaintainService;
        this.pexpMaintainService = pexpMaintainService;
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

    public void makeSurePermissionNotExists(PermissionKey permissionKey) throws HandlerException {
        try {
            if (Objects.isNull(permissionKey)) {
                return;
            }
            if (permissionMaintainService.exists(permissionKey)) {
                throw new PermissionAlreadyExistsException(permissionKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSurePermissionExists(PermissionKey permissionKey) throws HandlerException {
        try {
            if (!permissionMaintainService.exists(permissionKey)) {
                throw new PermissionNotExistsException(permissionKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureScopeExists(StringIdKey scopeKey) throws HandlerException {
        try {
            if (!scopeMaintainService.exists(scopeKey)) {
                throw new ScopeNotExistsException(scopeKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSurePermissionGroupInScope(PermissionGroupKey permissionGroupKey, StringIdKey scopeKey)
            throws HandlerException {
        String scopeStringIdAlpha = Optional.ofNullable(permissionGroupKey)
                .map(PermissionGroupKey::getScopeStringId).orElse(null);
        if (Objects.isNull(scopeStringIdAlpha)) {
            throw new PermissionGroupNotExistsException(permissionGroupKey);
        }
        String scopeStringIdBravo = Optional.ofNullable(scopeKey).map(StringIdKey::getStringId).orElse(null);
        if (Objects.isNull(scopeStringIdBravo)) {
            throw new ScopeNotExistsException(scopeKey);
        }
        if (!Objects.equals(scopeStringIdAlpha, scopeStringIdBravo)) {
            throw new PermissionGroupNotInScopeException(permissionGroupKey, scopeKey);
        }
    }

    public void makeSurePermissionGroupNotExists(PermissionGroupKey permissionGroupKey) throws HandlerException {
        try {
            if (Objects.isNull(permissionGroupKey)) {
                return;
            }
            if (permissionGroupMaintainService.exists(permissionGroupKey)) {
                throw new PermissionGroupAlreadyExistsException(permissionGroupKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSurePermissionGroupExists(PermissionGroupKey permissionGroupKey) throws HandlerException {
        try {
            if (!permissionGroupMaintainService.exists(permissionGroupKey)) {
                throw new PermissionGroupNotExistsException(permissionGroupKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSurePexpNotExists(PexpKey pexpKey) throws HandlerException {
        try {
            if (Objects.isNull(pexpKey)) {
                return;
            }
            if (pexpMaintainService.exists(pexpKey)) {
                throw new PexpAlreadyExistsException(pexpKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSurePexpExists(PexpKey pexpKey) throws HandlerException {
        try {
            if (!pexpMaintainService.exists(pexpKey)) {
                throw new PexpNotExistsException(pexpKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }
}

package com.dwarfeng.rbacds.api.integration.subgrade;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.service.PermissionLookupService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.PermissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限处理器的实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class PermissionHandlerImpl implements PermissionHandler {

    @Autowired
    private PermissionLookupService service;

    @Override
    public boolean hasPermission(StringIdKey userKey, String permissionNode) throws HandlerException {
        try {
            List<String> ownedPermissionNodes = service.lookupPermissionsForUser(userKey).stream()
                    .map(Permission::getKey).map(StringIdKey::getStringId).collect(Collectors.toList());
            return ownedPermissionNodes.contains(permissionNode);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public boolean hasPermission(StringIdKey userKey, List<String> permissionNodes) throws HandlerException {
        try {
            List<String> ownedPermissionNodes = service.lookupPermissionsForUser(userKey).stream()
                    .map(Permission::getKey).map(StringIdKey::getStringId).collect(Collectors.toList());
            return ownedPermissionNodes.containsAll(permissionNodes);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public List<String> getMissingPermission(StringIdKey userKey, List<String> permissionNodes)
            throws HandlerException {
        try {
            List<String> ownedPermissionNodes = service.lookupPermissionsForUser(userKey).stream()
                    .map(Permission::getKey).map(StringIdKey::getStringId).collect(Collectors.toList());
            List<String> dejaVu = new ArrayList<>(permissionNodes);
            dejaVu.removeAll(ownedPermissionNodes);
            return dejaVu;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}

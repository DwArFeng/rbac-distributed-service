package com.dwarfeng.rbacds.api.integration.subgrade;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.service.PermissionLookupService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.PermissionHandler;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限处理器的实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class PermissionHandlerImpl implements PermissionHandler {

    private final PermissionLookupService service;

    public PermissionHandlerImpl(PermissionLookupService service) {
        this.service = service;
    }

    @Override
    public boolean hasPermission(String userId, String permission) throws HandlerException {
        try {
            StringIdKey userKey = userIdToKey(userId);
            String permissionNode = permissionToPermissionNode(permission);
            List<String> ownedPermissionNodes = service.lookupForUser(userKey).stream()
                    .map(Permission::getKey).map(StringIdKey::getStringId).collect(Collectors.toList());
            return ownedPermissionNodes.contains(permissionNode);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public boolean hasPermission(String userId, List<String> permissions) throws HandlerException {
        try {
            StringIdKey userKey = userIdToKey(userId);
            List<String> permissionNodes = permissions.stream()
                    .map(this::permissionToPermissionNode)
                    .collect(Collectors.toList());
            List<String> ownedPermissionNodes = service.lookupForUser(userKey).stream()
                    .map(Permission::getKey).map(StringIdKey::getStringId).collect(Collectors.toList());
            return new HashSet<>(ownedPermissionNodes).containsAll(permissionNodes);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public List<String> getMissingPermissions(String userId, List<String> permissions) throws HandlerException {
        try {
            StringIdKey userKey = userIdToKey(userId);
            List<String> permissionNodes = permissions.stream()
                    .map(this::permissionToPermissionNode)
                    .collect(Collectors.toList());
            List<String> ownedPermissionNodes = service.lookupForUser(userKey).stream()
                    .map(Permission::getKey).map(StringIdKey::getStringId).collect(Collectors.toList());
            List<String> dejaVu = new java.util.ArrayList<>(permissionNodes);
            dejaVu.removeAll(ownedPermissionNodes);
            return dejaVu;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    private StringIdKey userIdToKey(String userId) {
        return new StringIdKey(userId);
    }

    private String permissionToPermissionNode(String permission) {
        return permission;
    }
}

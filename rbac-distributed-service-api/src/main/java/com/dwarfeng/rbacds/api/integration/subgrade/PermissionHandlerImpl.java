package com.dwarfeng.rbacds.api.integration.subgrade;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.service.PermissionLookupService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.PermissionHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
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

    private final PermissionLookupService service;

    public PermissionHandlerImpl(PermissionLookupService service) {
        this.service = service;
    }

    @Override
    public boolean hasPermission(StringIdKey userKey, String permissionNode) throws HandlerException {
        try {
            List<String> ownedPermissionNodes = service.lookupForUser(userKey).stream()
                    .map(Permission::getKey).map(StringIdKey::getStringId).collect(Collectors.toList());
            return ownedPermissionNodes.contains(permissionNode);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public boolean hasPermission(StringIdKey userKey, List<String> permissionNodes) throws HandlerException {
        try {
            List<String> ownedPermissionNodes = service.lookupForUser(userKey).stream()
                    .map(Permission::getKey).map(StringIdKey::getStringId).collect(Collectors.toList());
            return new HashSet<>(ownedPermissionNodes).containsAll(permissionNodes);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public List<String> getMissingPermissions(StringIdKey userKey, List<String> permissionNodes)
            throws HandlerException {
        try {
            List<String> ownedPermissionNodes = service.lookupForUser(userKey).stream()
                    .map(Permission::getKey).map(StringIdKey::getStringId).collect(Collectors.toList());
            List<String> dejaVu = new ArrayList<>(permissionNodes);
            dejaVu.removeAll(ownedPermissionNodes);
            return dejaVu;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}

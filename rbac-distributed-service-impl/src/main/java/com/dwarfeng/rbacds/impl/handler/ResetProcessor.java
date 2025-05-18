package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.handler.PermissionFilterLocalCacheHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

/**
 * 重置处理器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Component
public class ResetProcessor {

    private final PermissionFilterLocalCacheHandler permissionFilterLocalCacheHandler;

    public ResetProcessor(
            PermissionFilterLocalCacheHandler permissionFilterLocalCacheHandler
    ) {
        this.permissionFilterLocalCacheHandler = permissionFilterLocalCacheHandler;
    }

    public void resetPermissionFilter() throws HandlerException {
        // 重置权限过滤。
        permissionFilterLocalCacheHandler.clear();
    }
}

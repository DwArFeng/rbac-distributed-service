package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.handler.PermissionFilterLocalCacheHandler;
import com.dwarfeng.rbacds.stack.handler.PushHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 重置处理器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Component
public class ResetProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetProcessor.class);

    private final PermissionFilterLocalCacheHandler permissionFilterLocalCacheHandler;

    private final PushHandler pushHandler;

    public ResetProcessor(
            PermissionFilterLocalCacheHandler permissionFilterLocalCacheHandler,
            PushHandler pushHandler
    ) {
        this.permissionFilterLocalCacheHandler = permissionFilterLocalCacheHandler;
        this.pushHandler = pushHandler;
    }

    public void resetPermissionFilter() throws HandlerException {
        // 重置权限过滤。
        permissionFilterLocalCacheHandler.clear();

        // 推送消息。
        try {
            pushHandler.permissionFilterReset();
        } catch (Exception e) {
            LOGGER.warn("推送权限过滤功能重置消息时发生异常, 本次消息将不会被推送, 异常信息如下: ", e);
        }
    }
}

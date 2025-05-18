package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.handler.PermissionFilter;
import com.dwarfeng.rbacds.stack.handler.PermissionFilterHandler;
import com.dwarfeng.rbacds.stack.handler.PermissionFilterLocalCacheHandler;
import com.dwarfeng.subgrade.impl.handler.Fetcher;
import com.dwarfeng.subgrade.impl.handler.GeneralLocalCacheHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

@Component
public class PermissionFilterLocalCacheHandlerImpl implements PermissionFilterLocalCacheHandler {

    private final GeneralLocalCacheHandler<String, PermissionFilter> handler;

    public PermissionFilterLocalCacheHandlerImpl(PermissionFilterFetcher permissionFilterFetcher) {
        handler = new GeneralLocalCacheHandler<>(permissionFilterFetcher);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(String key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @Override
    public PermissionFilter get(String key) throws HandlerException {
        return handler.get(key);
    }

    @BehaviorAnalyse
    @Override
    public boolean remove(String key) {
        return handler.remove(key);
    }

    @BehaviorAnalyse
    @Override
    public void clear() {
        handler.clear();
    }

    @Component
    public static class PermissionFilterFetcher implements Fetcher<String, PermissionFilter> {

        private final PermissionFilterHandler permissionFilterHandler;

        public PermissionFilterFetcher(PermissionFilterHandler permissionFilterHandler) {
            this.permissionFilterHandler = permissionFilterHandler;
        }

        @Override
        public boolean exists(String key) throws Exception {
            return permissionFilterHandler.supportType(key);
        }

        @Override
        public PermissionFilter fetch(String key) throws Exception {
            return permissionFilterHandler.make(key);
        }
    }
}

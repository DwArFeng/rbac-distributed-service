package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.handler.Filter;
import com.dwarfeng.rbacds.stack.handler.FilterHandler;
import com.dwarfeng.rbacds.stack.handler.FilterLocalCacheHandler;
import com.dwarfeng.subgrade.aop.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;
import com.dwarfeng.subgrade.cache.impl.handler.GeneralLocalCacheHandler;
import com.dwarfeng.subgrade.cache.stack.loader.Fetcher;
import org.springframework.stereotype.Component;

@Component
public class FilterLocalCacheHandlerImpl implements FilterLocalCacheHandler {

    private final GeneralLocalCacheHandler<String, Filter> handler;

    public FilterLocalCacheHandlerImpl(FilterFetcher filterFetcher) {
        handler = new GeneralLocalCacheHandler<>(filterFetcher);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(String key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @Override
    public Filter get(String key) throws HandlerException {
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
    public static class FilterFetcher implements Fetcher<String, Filter> {

        private final FilterHandler filterHandler;

        public FilterFetcher(FilterHandler filterHandler) {
            this.filterHandler = filterHandler;
        }

        @Override
        public boolean exists(String key) throws Exception {
            return filterHandler.supportType(key);
        }

        @Override
        public Filter fetch(String key) throws Exception {
            return filterHandler.make(key);
        }
    }
}

package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.sdk.handler.PermissionFilterMaker;
import com.dwarfeng.rbacds.stack.exception.PermissionFilterException;
import com.dwarfeng.rbacds.stack.exception.UnsupportedPermissionFilterTypeException;
import com.dwarfeng.rbacds.stack.handler.PermissionFilter;
import com.dwarfeng.rbacds.stack.handler.PermissionFilterHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class PermissionFilterHandlerImpl implements PermissionFilterHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionFilterHandlerImpl.class);

    private final List<PermissionFilterMaker> permissionFilterMakers;

    public PermissionFilterHandlerImpl(List<PermissionFilterMaker> permissionFilterMakers) {
        this.permissionFilterMakers = Optional.ofNullable(permissionFilterMakers).orElse(Collections.emptyList());
    }

    @Override
    public boolean supportType(String type) throws HandlerException {
        try {
            return permissionFilterMakers.stream().anyMatch(maker -> maker.supportType(type));
        } catch (Exception e) {
            throw new PermissionFilterException(e);
        }
    }

    @Override
    public PermissionFilter make(String type) throws HandlerException {
        try {
            // 生成过滤器。
            LOGGER.debug("通过过滤器信息构建新的的权限过滤器...");
            PermissionFilterMaker permissionFilterMaker = permissionFilterMakers.stream()
                    .filter(maker -> maker.supportType(type)).findFirst()
                    .orElseThrow(() -> new UnsupportedPermissionFilterTypeException(type));
            PermissionFilter permissionFilter = permissionFilterMaker.makePermissionFilter();
            LOGGER.debug("权限过滤器构建成功!");
            LOGGER.debug("权限过滤器: {}", permissionFilter);
            return permissionFilter;
        } catch (PermissionFilterException e) {
            throw e;
        } catch (Exception e) {
            throw new PermissionFilterException(e);
        }
    }
}

package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupUpdateInfo;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.handler.PermissionGroupOperateHandler;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PermissionGroupOperateHandlerImpl implements PermissionGroupOperateHandler {

    private final PermissionGroupMaintainService permissionGroupMaintainService;

    private final HandlerValidator handlerValidator;

    public PermissionGroupOperateHandlerImpl(
            PermissionGroupMaintainService permissionGroupMaintainService,
            HandlerValidator handlerValidator
    ) {
        this.permissionGroupMaintainService = permissionGroupMaintainService;
        this.handlerValidator = handlerValidator;
    }

    @Override
    public PermissionGroupCreateResult create(PermissionGroupCreateInfo info) throws HandlerException {
        try {
            // 展开参数。
            PermissionGroupKey key = info.getKey();
            PermissionGroupKey parentKey = info.getParentKey();
            String name = info.getName();
            String remark = info.getRemark();

            // 确认作用域存在。
            StringIdKey scopeKey = new StringIdKey(key.getScopeStringId());
            handlerValidator.makeSureScopeExists(scopeKey);
            // 确认父权限组存在。
            if (Objects.nonNull(parentKey)) {
                // 确认父权限组存在。
                handlerValidator.makeSurePermissionGroupExists(parentKey);
                // 确认父权限组所属作用域与当前作用域一致。
                handlerValidator.makeSurePermissionGroupInScope(parentKey, scopeKey);
            }
            // 确认权限组不存在。
            handlerValidator.makeSurePermissionGroupNotExists(key);

            // 根据参数构造权限组实体。
            PermissionGroup permissionGroup = new PermissionGroup(key, parentKey, name, remark);

            // 调用维护服务插入权限组实体。
            permissionGroupMaintainService.insert(permissionGroup);

            // 返回权限组主键。
            return new PermissionGroupCreateResult(key);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public void update(PermissionGroupUpdateInfo info) throws HandlerException {
        try {
            // 展开参数。
            PermissionGroupKey key = info.getKey();
            PermissionGroupKey parentKey = info.getParentKey();
            String name = info.getName();
            String remark = info.getRemark();

            // 确认权限组存在。
            handlerValidator.makeSurePermissionGroupExists(key);
            // 确认父权限组存在。
            if (Objects.nonNull(parentKey)) {
                // 确认父权限组存在。
                handlerValidator.makeSurePermissionGroupExists(parentKey);
                // 确认父权限组所属作用域与当前作用域一致。
                StringIdKey scopeKey = new StringIdKey(key.getScopeStringId());
                handlerValidator.makeSurePermissionGroupInScope(parentKey, scopeKey);
            }

            // 根据参数更新权限实体。
            PermissionGroup permissionGroup = permissionGroupMaintainService.get(key);
            permissionGroup.setParentKey(parentKey);
            permissionGroup.setName(name);
            permissionGroup.setRemark(remark);

            // 调用维护服务更新权限组实体。
            permissionGroupMaintainService.update(permissionGroup);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public void remove(PermissionGroupRemoveInfo info) throws HandlerException {
        try {
            // 展开参数。
            PermissionGroupKey key = info.getKey();

            // 确认权限组存在。
            handlerValidator.makeSurePermissionGroupExists(key);

            // 调用维护服务删除权限组实体。
            permissionGroupMaintainService.delete(key);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }
}

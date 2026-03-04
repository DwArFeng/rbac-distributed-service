package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.dto.PermissionCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionUpdateInfo;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.handler.PermissionOperateHandler;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Component
public class PermissionOperateHandlerImpl implements PermissionOperateHandler {

    private final PermissionMaintainService permissionMaintainService;
    private final PermissionGroupMaintainService permissionGroupMaintainService;

    private final HandlerValidator handlerValidator;

    public PermissionOperateHandlerImpl(
            PermissionMaintainService permissionMaintainService,
            PermissionGroupMaintainService permissionGroupMaintainService,
            HandlerValidator handlerValidator
    ) {
        this.permissionMaintainService = permissionMaintainService;
        this.permissionGroupMaintainService = permissionGroupMaintainService;
        this.handlerValidator = handlerValidator;
    }

    @Override
    public PermissionCreateResult create(PermissionCreateInfo info) throws HandlerException {
        try {
            // 展开参数。
            PermissionKey key = info.getKey();
            PermissionGroupKey groupKey = info.getGroupKey();
            String name = info.getName();
            String remark = info.getRemark();
            int level = info.getLevel();

            // 确认作用域存在。
            StringIdKey scopeKey = new StringIdKey(key.getScopeStringId());
            handlerValidator.makeSureScopeExists(scopeKey);
            // 确认权限组存在。
            if (Objects.nonNull(groupKey)) {
                // 确认权限组存在。
                handlerValidator.makeSurePermissionGroupExists(groupKey);
                // 确认权限组和作用域和指定作用域一致。
                handlerValidator.makeSurePermissionGroupInScope(groupKey, scopeKey);
            }
            // 确认权限不存在。
            handlerValidator.makeSurePermissionNotExists(key);

            // 解析计算参数。
            ComputedParam computedParam = parseComputedParam(groupKey);
            String[] groupPath = computedParam.getGroupPath();

            // 根据参数构造权限实体。
            Permission permission = new Permission(key, groupKey, name, remark, level, groupPath);

            // 调用维护服务插入权限实体。
            permissionMaintainService.insert(permission);

            // 返回权限主键。
            return new PermissionCreateResult(key);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public void update(PermissionUpdateInfo info) throws HandlerException {
        try {
            // 展开参数。
            PermissionKey key = info.getKey();
            PermissionGroupKey groupKey = info.getGroupKey();
            String name = info.getName();
            String remark = info.getRemark();
            int level = info.getLevel();

            // 确认权限存在。
            handlerValidator.makeSurePermissionExists(key);
            // 确认权限组存在。
            if (Objects.nonNull(groupKey)) {
                // 确认权限组存在。
                handlerValidator.makeSurePermissionGroupExists(groupKey);
                // 确认权限组和作用域和指定作用域一致。
                StringIdKey scopeKey = new StringIdKey(key.getScopeStringId());
                handlerValidator.makeSurePermissionGroupInScope(groupKey, scopeKey);
            }

            // 解析计算参数。
            ComputedParam computedParam = parseComputedParam(groupKey);
            String[] groupPath = computedParam.getGroupPath();

            // 根据参数更新权限实体。
            Permission permission = permissionMaintainService.get(key);
            permission.setGroupKey(groupKey);
            permission.setName(name);
            permission.setRemark(remark);
            permission.setLevel(level);
            permission.setGroupPath(groupPath);

            // 调用维护服务更新权限实体。
            permissionMaintainService.update(permission);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public void remove(PermissionRemoveInfo info) throws HandlerException {
        try {
            // 展开参数。
            PermissionKey key = info.getKey();

            // 确认权限存在。
            handlerValidator.makeSurePermissionExists(key);

            // 调用维护服务删除权限实体。
            permissionMaintainService.delete(key);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private ComputedParam parseComputedParam(PermissionGroupKey permissionGroupKey) throws Exception {
        // 特殊值处理：如果权限组主键为 null，则直接返回特殊值。
        if (Objects.isNull(permissionGroupKey)) {
            return new ComputedParam(null);
        }
        // 构造临时字段。
        List<String> groupPathList = new LinkedList<>();
        // 获取权限组实体。
        PermissionGroup permissionGroup = permissionGroupMaintainService.get(permissionGroupKey);
        String permissionGroupStringId = permissionGroup.getKey().getPermissionGroupStringId();
        groupPathList.add(0, permissionGroupStringId);
        // 循环获取权限组的父权限组，直到没有父权限组为止。
        while (Objects.nonNull(permissionGroup.getParentKey())) {
            permissionGroup = permissionGroupMaintainService.get(permissionGroup.getParentKey());
            permissionGroupStringId = permissionGroup.getKey().getPermissionGroupStringId();
            groupPathList.add(0, permissionGroupStringId);
        }
        // 构造计算参数。
        String[] groupIdentifierPath = groupPathList.toArray(new String[0]);
        // 返回计算参数。
        return new ComputedParam(groupIdentifierPath);
    }

    private static final class ComputedParam {

        private final String[] groupPath;

        public ComputedParam(String[] groupPath) {
            this.groupPath = groupPath;
        }

        public String[] getGroupPath() {
            return groupPath;
        }

        @Override
        public String toString() {
            return "ComputedParam{" +
                    "groupPath=" + Arrays.toString(groupPath) +
                    '}';
        }
    }
}

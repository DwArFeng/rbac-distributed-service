package com.dwarfeng.rbacds.api.integration.subgrade;

import com.dwarfeng.rbacds.stack.bean.dto.UserPermissionInspectInfo;
import com.dwarfeng.rbacds.stack.bean.dto.UserPermissionInspectResult;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.bean.key.ScopedUserKey;
import com.dwarfeng.rbacds.stack.service.InspectService;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.PermissionHandler;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 遗留权限处理器。
 *
 * <p>
 * 该处理器用户兼容旧版本的权限系统。
 *
 * <p>
 * 旧版本的权限系统中，没有作用域这个概念，所有的权限都属于同一个作用域。<br>
 * 将旧版本的权限数据迁移至新版本的权限系统时，如果将旧版本的权限数据迁移至一个默认作用域下，便可以使用该处理器进行权限查询。
 *
 * @author DwArFeng
 * @since 2.0.0
 * @deprecated 该类仅用于兼容旧版本的权限系统，建议在新版本的权限系统中使用新的权限处理器进行权限查询。
 */
@Deprecated
@Component
public class LegacyPermissionHandler implements PermissionHandler {

    private InspectService inspectService;

    private String scopeStringId;

    public LegacyPermissionHandler(InspectService inspectService, String scopeStringId) {
        this.inspectService = inspectService;
        this.scopeStringId = scopeStringId;
    }

    @Override
    public boolean hasPermission(String userStringId, String permissionStringId) throws HandlerException {
        try {
            // 构建查询参数。
            ScopedUserKey scopedUserKey = new ScopedUserKey(scopeStringId, userStringId);
            PermissionKey permissionKeyMatched = new PermissionKey(scopeStringId, permissionStringId);
            UserPermissionInspectInfo inspectInfo = new UserPermissionInspectInfo(
                    scopedUserKey,
                    permissionKeyMatched,
                    null,
                    null,
                    false
            );
            // 执行查询。
            UserPermissionInspectResult result = inspectService.inspectUserPermission(inspectInfo);
            // 输出结果。
            if (Objects.isNull(result)) {
                return false;
            }
            return result.isMatchedFlag();
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public boolean hasPermission(String userStringId, List<String> permissionStringIds) throws HandlerException {
        try {
            // 特殊情况判断。
            if (permissionStringIds.isEmpty()) {
                return true;
            }
            // 构建查询参数。
            ScopedUserKey scopedUserKey = new ScopedUserKey(scopeStringId, userStringId);
            List<PermissionKey> allOfPermissionKeysMatched = permissionStringIds.stream()
                    .map(p -> new PermissionKey(scopeStringId, p)).collect(Collectors.toList());
            UserPermissionInspectInfo inspectInfo = new UserPermissionInspectInfo(
                    scopedUserKey,
                    null,
                    allOfPermissionKeysMatched,
                    null,
                    false
            );
            // 执行查询。
            UserPermissionInspectResult result = inspectService.inspectUserPermission(inspectInfo);
            // 输出结果。
            if (Objects.isNull(result)) {
                return false;
            }
            return result.isMatchedFlag();
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public List<String> getMissingPermissions(String userStringId, List<String> permissionStringIds)
            throws HandlerException {
        try {
            // 特殊情况判断。
            if (permissionStringIds.isEmpty()) {
                return Collections.emptyList();
            }
            // 构建查询参数。
            ScopedUserKey scopedUserKey = new ScopedUserKey(scopeStringId, userStringId);
            UserPermissionInspectInfo inspectInfo = new UserPermissionInspectInfo(
                    scopedUserKey,
                    null,
                    null,
                    null,
                    false
            );
            // 执行查询。
            UserPermissionInspectResult result = inspectService.inspectUserPermission(inspectInfo);
            // 计算结果。
            if (Objects.isNull(result)) {
                return permissionStringIds;
            }
            Collection<String> matchedPermissions = result.getMatchedPermissions().stream()
                    .map(p -> p.getKey().getPermissionStringId()).collect(Collectors.toSet());
            return permissionStringIds.stream().filter(p -> !matchedPermissions.contains(p))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    public InspectService getInspectService() {
        return inspectService;
    }

    public void setInspectService(InspectService inspectService) {
        this.inspectService = inspectService;
    }

    public String getScopeStringId() {
        return scopeStringId;
    }

    public void setScopeStringId(String scopeStringId) {
        this.scopeStringId = scopeStringId;
    }

    @Override
    public String toString() {
        return "LegacyPermissionHandler{" +
                "inspectService=" + inspectService +
                ", scopeStringId='" + scopeStringId + '\'' +
                '}';
    }
}

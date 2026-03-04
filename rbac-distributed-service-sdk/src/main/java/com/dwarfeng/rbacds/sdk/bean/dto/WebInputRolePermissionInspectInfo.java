package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPermissionKey;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputScopedRoleKey;
import com.dwarfeng.rbacds.stack.bean.dto.RolePermissionInspectInfo;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * WebInput 角色权限查看信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputRolePermissionInspectInfo implements Bean {

    private static final long serialVersionUID = -4092797872958142381L;

    public static RolePermissionInspectInfo toStackBean(WebInputRolePermissionInspectInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new RolePermissionInspectInfo(
                    WebInputScopedRoleKey.toStackBean(webInput.getScopedRoleKey()),
                    WebInputPermissionKey.toStackBean(webInput.getPermissionKeyMatched()),
                    Optional.ofNullable(webInput.getAllOfPermissionKeysMatched()).map(
                            f -> f.stream().map(WebInputPermissionKey::toStackBean).collect(Collectors.toList())
                    ).orElse(null),
                    Optional.ofNullable(webInput.getAnyOfPermissionKeysMatched()).map(
                            f -> f.stream().map(WebInputPermissionKey::toStackBean).collect(Collectors.toList())
                    ).orElse(null),
                    webInput.isDetailMode()
            );
        }
    }

    @JSONField(name = "scoped_role_key")
    @Valid
    private WebInputScopedRoleKey scopedRoleKey;

    @JSONField(name = "permission_key_matched")
    @Valid
    private WebInputPermissionKey permissionKeyMatched;

    @JSONField(name = "all_of_permission_keys_matched")
    @Valid
    private List<@Valid WebInputPermissionKey> allOfPermissionKeysMatched;

    @JSONField(name = "any_of_permission_keys_matched")
    @Valid
    private List<@Valid WebInputPermissionKey> anyOfPermissionKeysMatched;

    @JSONField(name = "detail_mode")
    private boolean detailMode;

    public WebInputRolePermissionInspectInfo() {
    }

    public WebInputScopedRoleKey getScopedRoleKey() {
        return scopedRoleKey;
    }

    public void setScopedRoleKey(WebInputScopedRoleKey scopedRoleKey) {
        this.scopedRoleKey = scopedRoleKey;
    }

    public WebInputPermissionKey getPermissionKeyMatched() {
        return permissionKeyMatched;
    }

    public void setPermissionKeyMatched(WebInputPermissionKey permissionKeyMatched) {
        this.permissionKeyMatched = permissionKeyMatched;
    }

    public List<WebInputPermissionKey> getAllOfPermissionKeysMatched() {
        return allOfPermissionKeysMatched;
    }

    public void setAllOfPermissionKeysMatched(List<WebInputPermissionKey> allOfPermissionKeysMatched) {
        this.allOfPermissionKeysMatched = allOfPermissionKeysMatched;
    }

    public List<WebInputPermissionKey> getAnyOfPermissionKeysMatched() {
        return anyOfPermissionKeysMatched;
    }

    public void setAnyOfPermissionKeysMatched(List<WebInputPermissionKey> anyOfPermissionKeysMatched) {
        this.anyOfPermissionKeysMatched = anyOfPermissionKeysMatched;
    }

    public boolean isDetailMode() {
        return detailMode;
    }

    public void setDetailMode(boolean detailMode) {
        this.detailMode = detailMode;
    }

    @Override
    public String toString() {
        return "WebInputRolePermissionInspectInfo{" +
                "scopedRoleKey=" + scopedRoleKey +
                ", permissionKeyMatched=" + permissionKeyMatched +
                ", allOfPermissionKeysMatched=" + allOfPermissionKeysMatched +
                ", anyOfPermissionKeysMatched=" + anyOfPermissionKeysMatched +
                ", detailMode=" + detailMode +
                '}';
    }
}

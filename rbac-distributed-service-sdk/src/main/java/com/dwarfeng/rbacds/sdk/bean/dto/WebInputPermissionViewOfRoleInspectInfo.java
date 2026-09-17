package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPermissionKey;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputScopedRoleKey;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionViewOfRoleInspectInfo;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;

import jakarta.validation.Valid;

import java.io.Serial;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * WebInput 以角色为依据的权限视图查看信息。
 *
 * @author DwArFeng
 * @since 2.0.2
 */
public class WebInputPermissionViewOfRoleInspectInfo implements Bean {

    @Serial
    private static final long serialVersionUID = 263685324300039976L;

    public static PermissionViewOfRoleInspectInfo toStackBean(WebInputPermissionViewOfRoleInspectInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PermissionViewOfRoleInspectInfo(
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

    public WebInputPermissionViewOfRoleInspectInfo() {
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
        return "WebInputPermissionViewOfRoleInspectInfo{" +
                "scopedRoleKey=" + scopedRoleKey +
                ", permissionKeyMatched=" + permissionKeyMatched +
                ", allOfPermissionKeysMatched=" + allOfPermissionKeysMatched +
                ", anyOfPermissionKeysMatched=" + anyOfPermissionKeysMatched +
                ", detailMode=" + detailMode +
                '}';
    }
}

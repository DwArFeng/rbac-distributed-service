package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPermissionKey;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputScopedUserKey;
import com.dwarfeng.rbacds.stack.bean.dto.UserPermissionInspectInfo;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;

import jakarta.validation.Valid;

import java.io.Serial;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * WebInput 用户权限查看信息。
 *
 * <p>
 * 该方法命名易引起歧义，已由 {@link WebInputPermissionViewOfUserInspectInfo} 替代。
 *
 * @author DwArFeng
 * @see WebInputPermissionViewOfUserInspectInfo
 * @since 2.0.0
 * @deprecated 命名歧义，请使用 {@link WebInputPermissionViewOfUserInspectInfo}
 */
// BeanMapper 还在使用该类，故忽略相关警告。
@Deprecated
public class WebInputUserPermissionInspectInfo implements Bean {

    @Serial
    private static final long serialVersionUID = 1295613110250496074L;

    public static UserPermissionInspectInfo toStackBean(WebInputUserPermissionInspectInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new UserPermissionInspectInfo(
                    WebInputScopedUserKey.toStackBean(webInput.getScopedUserKey()),
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

    @JSONField(name = "scoped_user_key")
    @Valid
    private WebInputScopedUserKey scopedUserKey;

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

    public WebInputUserPermissionInspectInfo() {
    }

    public WebInputScopedUserKey getScopedUserKey() {
        return scopedUserKey;
    }

    public void setScopedUserKey(WebInputScopedUserKey scopedUserKey) {
        this.scopedUserKey = scopedUserKey;
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
        return "WebInputUserPermissionInspectInfo{" +
                "scopedUserKey=" + scopedUserKey +
                ", permissionKeyMatched=" + permissionKeyMatched +
                ", allOfPermissionKeysMatched=" + allOfPermissionKeysMatched +
                ", anyOfPermissionKeysMatched=" + anyOfPermissionKeysMatched +
                ", detailMode=" + detailMode +
                '}';
    }
}

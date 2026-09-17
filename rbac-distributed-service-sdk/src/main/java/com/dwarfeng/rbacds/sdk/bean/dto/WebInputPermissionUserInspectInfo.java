package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.WebInputPermissionKey;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionUserInspectInfo;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;
import com.dwarfeng.subgrade.web.sdk.bean.key.WebInputStringIdKey;
import jakarta.validation.Valid;

import java.io.Serial;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * WebInput 权限用户查看信息。
 *
 * <p>
 * 该方法命名易引起歧义，已由 {@link WebInputUserViewOfPermissionInspectInfo} 替代。
 *
 * @author DwArFeng
 * @see WebInputUserViewOfPermissionInspectInfo
 * @since 2.0.0
 * @deprecated 命名歧义，请使用 {@link WebInputUserViewOfPermissionInspectInfo}
 */
// BeanMapper 还在使用该类，故忽略相关警告。
@Deprecated
public class WebInputPermissionUserInspectInfo implements Bean {

    @Serial
    private static final long serialVersionUID = 5513572021637527725L;

    public static PermissionUserInspectInfo toStackBean(WebInputPermissionUserInspectInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PermissionUserInspectInfo(
                    WebInputPermissionKey.toStackBean(webInput.getPermissionKey()),
                    WebInputStringIdKey.toStackBean(webInput.getUserKeyMatched()),
                    Optional.ofNullable(webInput.getAllOfUserKeysMatched()).map(
                            f -> f.stream().map(WebInputStringIdKey::toStackBean).collect(Collectors.toList())
                    ).orElse(null),
                    Optional.ofNullable(webInput.getAnyOfUserKeysMatched()).map(
                            f -> f.stream().map(WebInputStringIdKey::toStackBean).collect(Collectors.toList())
                    ).orElse(null),
                    webInput.isDetailMode()
            );
        }
    }

    @JSONField(name = "permission_key")
    @Valid
    private WebInputPermissionKey permissionKey;

    @JSONField(name = "user_key_matched")
    @Valid
    private WebInputStringIdKey userKeyMatched;

    @JSONField(name = "all_of_user_keys_matched")
    @Valid
    private List<@Valid WebInputStringIdKey> allOfUserKeysMatched;

    @JSONField(name = "any_of_user_keys_matched")
    @Valid
    private List<@Valid WebInputStringIdKey> anyOfUserKeysMatched;

    @JSONField(name = "detail_mode")
    private boolean detailMode;

    public WebInputPermissionUserInspectInfo() {
    }

    public WebInputPermissionKey getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(WebInputPermissionKey permissionKey) {
        this.permissionKey = permissionKey;
    }

    public WebInputStringIdKey getUserKeyMatched() {
        return userKeyMatched;
    }

    public void setUserKeyMatched(WebInputStringIdKey userKeyMatched) {
        this.userKeyMatched = userKeyMatched;
    }

    public List<WebInputStringIdKey> getAllOfUserKeysMatched() {
        return allOfUserKeysMatched;
    }

    public void setAllOfUserKeysMatched(List<WebInputStringIdKey> allOfUserKeysMatched) {
        this.allOfUserKeysMatched = allOfUserKeysMatched;
    }

    public List<WebInputStringIdKey> getAnyOfUserKeysMatched() {
        return anyOfUserKeysMatched;
    }

    public void setAnyOfUserKeysMatched(List<WebInputStringIdKey> anyOfUserKeysMatched) {
        this.anyOfUserKeysMatched = anyOfUserKeysMatched;
    }

    public boolean isDetailMode() {
        return detailMode;
    }

    public void setDetailMode(boolean detailMode) {
        this.detailMode = detailMode;
    }

    @Override
    public String toString() {
        return "WebInputPermissionUserInspectInfo{" +
                "permissionKey=" + permissionKey +
                ", userKeyMatched=" + userKeyMatched +
                ", allOfUserKeysMatched=" + allOfUserKeysMatched +
                ", anyOfUserKeysMatched=" + anyOfUserKeysMatched +
                ", detailMode=" + detailMode +
                '}';
    }
}

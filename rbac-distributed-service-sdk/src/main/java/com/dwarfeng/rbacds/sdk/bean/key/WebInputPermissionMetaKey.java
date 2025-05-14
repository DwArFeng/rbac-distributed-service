package com.dwarfeng.rbacds.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.util.Constraints;
import com.dwarfeng.rbacds.stack.bean.key.PermissionMetaKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 权限元数据键。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public class WebInputPermissionMetaKey implements Key {

    private static final long serialVersionUID = -5997514139497407388L;

    public static PermissionMetaKey toStackBean(WebInputPermissionMetaKey webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PermissionMetaKey(
                    webInput.getPermissionStringId(),
                    webInput.getMetaStringId()
            );
        }
    }

    @JSONField(name = "permission_string_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_ID)
    private String permissionStringId;

    @JSONField(name = "meta_string_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_ID)
    private String metaStringId;

    public WebInputPermissionMetaKey() {
    }

    public String getPermissionStringId() {
        return permissionStringId;
    }

    public void setPermissionStringId(String permissionStringId) {
        this.permissionStringId = permissionStringId;
    }

    public String getMetaStringId() {
        return metaStringId;
    }

    public void setMetaStringId(String metaStringId) {
        this.metaStringId = metaStringId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        WebInputPermissionMetaKey that = (WebInputPermissionMetaKey) o;
        return Objects.equals(permissionStringId, that.permissionStringId) && Objects.equals(metaStringId, that.metaStringId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(permissionStringId);
        result = 31 * result + Objects.hashCode(metaStringId);
        return result;
    }

    @Override
    public String toString() {
        return "WebInputPermissionMetaKey{" +
                "permissionStringId='" + permissionStringId + '\'' +
                ", metaStringId='" + metaStringId + '\'' +
                '}';
    }
}

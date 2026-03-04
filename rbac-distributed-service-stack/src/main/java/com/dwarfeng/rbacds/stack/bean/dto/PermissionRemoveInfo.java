package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 权限移除信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionRemoveInfo implements Dto {

    private static final long serialVersionUID = 1388884854699211834L;

    private PermissionKey key;

    public PermissionRemoveInfo() {
    }

    public PermissionRemoveInfo(PermissionKey key) {
        this.key = key;
    }

    public PermissionKey getKey() {
        return key;
    }

    public void setKey(PermissionKey key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "PermissionRemoveInfo{" +
                "key=" + key +
                '}';
    }
}

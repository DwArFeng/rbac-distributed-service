package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;

import java.io.Serial;

/**
 * 权限移除信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionRemoveInfo implements Dto {

    @Serial
    private static final long serialVersionUID = -4940649810400393949L;

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

package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;

import java.io.Serial;

/**
 * 权限组移除信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionGroupRemoveInfo implements Dto {

    @Serial
    private static final long serialVersionUID = -1403975598749251041L;

    private PermissionGroupKey key;

    public PermissionGroupRemoveInfo() {
    }

    public PermissionGroupRemoveInfo(PermissionGroupKey key) {
        this.key = key;
    }

    public PermissionGroupKey getKey() {
        return key;
    }

    public void setKey(PermissionGroupKey key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "PermissionGroupRemoveInfo{" +
                "key=" + key +
                '}';
    }
}

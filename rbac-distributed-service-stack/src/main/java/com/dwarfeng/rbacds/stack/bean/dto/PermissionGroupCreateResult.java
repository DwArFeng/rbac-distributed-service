package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 权限组创建结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionGroupCreateResult implements Dto {

    private static final long serialVersionUID = 9140942830740178444L;

    private PermissionGroupKey key;

    public PermissionGroupCreateResult() {
    }

    public PermissionGroupCreateResult(PermissionGroupKey key) {
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
        return "PermissionGroupCreateResult{" +
                "key=" + key +
                '}';
    }
}

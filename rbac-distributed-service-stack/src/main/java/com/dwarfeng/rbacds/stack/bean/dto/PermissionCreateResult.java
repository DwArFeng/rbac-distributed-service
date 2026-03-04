package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 权限创建结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionCreateResult implements Dto {

    private static final long serialVersionUID = -8139059089403756171L;

    private PermissionKey key;

    public PermissionCreateResult() {
    }

    public PermissionCreateResult(PermissionKey key) {
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
        return "PermissionCreateResult{" +
                "key=" + key +
                '}';
    }
}

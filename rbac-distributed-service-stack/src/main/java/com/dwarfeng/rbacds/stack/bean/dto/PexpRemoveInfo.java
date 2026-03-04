package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 权限表达式移除信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PexpRemoveInfo implements Dto {

    private static final long serialVersionUID = -6197409185073295509L;

    private PexpKey key;

    public PexpRemoveInfo() {
    }

    public PexpRemoveInfo(PexpKey key) {
        this.key = key;
    }

    public PexpKey getKey() {
        return key;
    }

    public void setKey(PexpKey key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "PexpRemoveInfo{" +
                "key=" + key +
                '}';
    }
}

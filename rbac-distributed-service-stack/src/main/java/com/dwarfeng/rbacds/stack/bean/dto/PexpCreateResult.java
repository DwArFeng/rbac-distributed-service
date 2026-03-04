package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 权限表达式创建结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PexpCreateResult implements Dto {

    private static final long serialVersionUID = -9130326564360382836L;

    private PexpKey key;

    public PexpCreateResult() {
    }

    public PexpCreateResult(PexpKey key) {
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
        return "PexpCreateResult{" +
                "key=" + key +
                '}';
    }
}

package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;

import java.io.Serial;

/**
 * 权限表达式创建结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PexpCreateResult implements Dto {

    @Serial
    private static final long serialVersionUID = 9117824023177529858L;

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

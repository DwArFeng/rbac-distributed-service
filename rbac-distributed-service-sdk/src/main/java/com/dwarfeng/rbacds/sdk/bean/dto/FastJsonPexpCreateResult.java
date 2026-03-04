package com.dwarfeng.rbacds.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.FastJsonPexpKey;
import com.dwarfeng.rbacds.stack.bean.dto.PexpCreateResult;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 权限表达式创建结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonPexpCreateResult implements Bean {

    private static final long serialVersionUID = -3415192975562942346L;

    public static FastJsonPexpCreateResult of(PexpCreateResult pexpCreateResult) {
        if (Objects.isNull(pexpCreateResult)) {
            return null;
        } else {
            return new FastJsonPexpCreateResult(
                    FastJsonPexpKey.of(pexpCreateResult.getKey())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonPexpKey key;

    public FastJsonPexpCreateResult() {
    }

    public FastJsonPexpCreateResult(FastJsonPexpKey key) {
        this.key = key;
    }

    public FastJsonPexpKey getKey() {
        return key;
    }

    public void setKey(FastJsonPexpKey key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "FastJsonPexpCreateResult{" +
                "key=" + key +
                '}';
    }
}

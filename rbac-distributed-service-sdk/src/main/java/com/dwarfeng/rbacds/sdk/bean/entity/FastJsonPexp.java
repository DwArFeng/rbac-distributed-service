package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.sdk.bean.key.FastJsonPexpKey;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 权限表达式。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonPexp implements Bean {

    private static final long serialVersionUID = 1894206726148410082L;

    public static FastJsonPexp of(Pexp pexp) {
        if (Objects.isNull(pexp)) {
            return null;
        } else {
            return new FastJsonPexp(
                    FastJsonPexpKey.of(pexp.getKey()),
                    pexp.getContent(),
                    pexp.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonPexpKey key;

    @JSONField(name = "content", ordinal = 2)
    private String content;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    public FastJsonPexp() {
    }

    public FastJsonPexp(FastJsonPexpKey key, String content, String remark) {
        this.key = key;
        this.content = content;
        this.remark = remark;
    }

    public FastJsonPexpKey getKey() {
        return key;
    }

    public void setKey(FastJsonPexpKey key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonPexp{" +
                "key=" + key +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

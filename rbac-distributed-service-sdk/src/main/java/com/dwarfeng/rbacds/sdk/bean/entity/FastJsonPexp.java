package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 权限表达式。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonPexp implements Bean {

    private static final long serialVersionUID = 6913527582734758032L;

    public static FastJsonPexp of(Pexp pexp) {
        if (Objects.isNull(pexp)) {
            return null;
        }
        return new FastJsonPexp(
                FastJsonLongIdKey.of(pexp.getKey()),
                FastJsonStringIdKey.of(pexp.getRoleKey()),
                pexp.getContent(),
                pexp.getRemark()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "role_key", ordinal = 2)
    private FastJsonStringIdKey roleKey;

    @JSONField(name = "content", ordinal = 3)
    private String content;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    public FastJsonPexp() {
    }

    public FastJsonPexp(FastJsonLongIdKey key, FastJsonStringIdKey roleKey, String content, String remark) {
        this.key = key;
        this.roleKey = roleKey;
        this.content = content;
        this.remark = remark;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonStringIdKey getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(FastJsonStringIdKey roleKey) {
        this.roleKey = roleKey;
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
        return "JsonPexp{" +
                "key=" + key +
                ", roleKey=" + roleKey +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

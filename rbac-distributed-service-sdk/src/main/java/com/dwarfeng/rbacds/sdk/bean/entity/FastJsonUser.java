package com.dwarfeng.rbacds.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 用户。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonUser implements Bean {

    private static final long serialVersionUID = 9199394085072121152L;

    public static FastJsonUser of(User user) {
        if (Objects.isNull(user)) {
            return null;
        }
        return new FastJsonUser(
                FastJsonStringIdKey.of(user.getKey()),
                user.getRemark()
        );
    }


    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "remark", ordinal = 2)
    private String remark;

    public FastJsonUser() {
    }

    public FastJsonUser(FastJsonStringIdKey key, String remark) {
        this.key = key;
        this.remark = remark;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
        this.key = key;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonUser{" +
                "key=" + key +
                ", remark='" + remark + '\'' +
                '}';
    }
}

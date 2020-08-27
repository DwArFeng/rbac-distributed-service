package com.dwarfeng.rbacds.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 权限表达式。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Pexp implements Entity<LongIdKey> {

    private static final long serialVersionUID = -7246102861329325491L;

    private LongIdKey key;
    private StringIdKey roleKey;
    private String content;
    private String remark;

    public Pexp() {
    }

    public Pexp(LongIdKey key, StringIdKey roleKey, String content, String remark) {
        this.key = key;
        this.roleKey = roleKey;
        this.content = content;
        this.remark = remark;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public StringIdKey getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(StringIdKey roleKey) {
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
        return "Pexp{" +
                "key=" + key +
                ", roleKey=" + roleKey +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

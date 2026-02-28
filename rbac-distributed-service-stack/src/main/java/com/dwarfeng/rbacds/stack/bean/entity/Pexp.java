package com.dwarfeng.rbacds.stack.bean.entity;

import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

/**
 * 权限表达式。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class Pexp implements Entity<PexpKey> {

    private static final long serialVersionUID = 4614717080523655380L;

    private PexpKey key;
    private String content;
    private String remark;

    public Pexp() {
    }

    public Pexp(PexpKey key, String content, String remark) {
        this.key = key;
        this.content = content;
        this.remark = remark;
    }

    @Override
    public PexpKey getKey() {
        return key;
    }

    @Override
    public void setKey(PexpKey key) {
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
        return "Pexp{" +
                "key=" + key +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

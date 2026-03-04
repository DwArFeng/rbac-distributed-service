package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 权限表达式创建信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PexpCreateInfo implements Dto {

    private static final long serialVersionUID = 5370194572097749406L;

    private PexpKey key;
    private String content;
    private String remark;

    public PexpCreateInfo() {
    }

    public PexpCreateInfo(PexpKey key, String content, String remark) {
        this.key = key;
        this.content = content;
        this.remark = remark;
    }

    public PexpKey getKey() {
        return key;
    }

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
        return "PexpCreateInfo{" +
                "key=" + key +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

package com.dwarfeng.rbacds.sdk.bean.key.formatter;

import com.dwarfeng.rbacds.stack.bean.key.RoleUserRelationKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * RoleUserRelationKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class RoleUserRelationStringKeyFormatter implements StringKeyFormatter<RoleUserRelationKey> {

    private String prefix;

    public RoleUserRelationStringKeyFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(RoleUserRelationKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getRoleStringId() + "_" + key.getUserStringId();
    }

    @Override
    public String generalFormat() {
        return prefix + Constants.REDIS_KEY_WILDCARD_CHARACTER;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return "RoleUserRelationStringKeyFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}

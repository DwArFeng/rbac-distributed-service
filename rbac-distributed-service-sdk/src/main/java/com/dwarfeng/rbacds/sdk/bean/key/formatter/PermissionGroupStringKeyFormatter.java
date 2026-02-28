package com.dwarfeng.rbacds.sdk.bean.key.formatter;

import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * PermissionGroupKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionGroupStringKeyFormatter implements StringKeyFormatter<PermissionGroupKey> {

    private String prefix;

    public PermissionGroupStringKeyFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(PermissionGroupKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getScopeStringId() + "_" + key.getPermissionGroupStringId();
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
        return "PermissionGroupStringKeyFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}

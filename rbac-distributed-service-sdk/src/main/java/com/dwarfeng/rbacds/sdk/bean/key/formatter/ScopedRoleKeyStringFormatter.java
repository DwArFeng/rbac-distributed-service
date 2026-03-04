package com.dwarfeng.rbacds.sdk.bean.key.formatter;

import com.dwarfeng.rbacds.stack.bean.key.ScopedRoleKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * ScopedRoleKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class ScopedRoleKeyStringFormatter implements StringKeyFormatter<ScopedRoleKey> {

    private String prefix;

    public ScopedRoleKeyStringFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(ScopedRoleKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getScopeStringId() + "_" + key.getRoleStringId();
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
        return "ScopedRoleKeyStringFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}

package com.dwarfeng.rbacds.sdk.bean.key.formatter;

import com.dwarfeng.rbacds.stack.bean.key.ScopedUserKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * ScopedUserKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class ScopedUserKeyStringFormatter implements StringKeyFormatter<ScopedUserKey> {

    private String prefix;

    public ScopedUserKeyStringFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(ScopedUserKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getScopeStringId() + "_" + key.getUserStringId();
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
        return "ScopedUserKeyStringFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}

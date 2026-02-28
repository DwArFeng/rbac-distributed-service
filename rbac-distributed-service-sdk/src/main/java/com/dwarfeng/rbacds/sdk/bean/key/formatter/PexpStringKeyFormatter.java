package com.dwarfeng.rbacds.sdk.bean.key.formatter;

import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * PexpKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PexpStringKeyFormatter implements StringKeyFormatter<PexpKey> {

    private String prefix;

    public PexpStringKeyFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(PexpKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getScopeStringId() + "_" + key.getRoleStringId() + "_" + key.getPexpStringId();
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
        return "PexpStringKeyFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}

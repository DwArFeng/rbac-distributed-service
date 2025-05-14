package com.dwarfeng.rbacds.sdk.bean.key.formatter;

import com.dwarfeng.rbacds.stack.bean.key.PermissionMetaKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * PermissionMetaKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public class PermissionMetaStringKeyFormatter implements StringKeyFormatter<PermissionMetaKey> {

    private String prefix;

    public PermissionMetaStringKeyFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(PermissionMetaKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getPermissionStringId() + "_" + key.getMetaStringId();
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
        return "PermissionMetaStringKeyFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}

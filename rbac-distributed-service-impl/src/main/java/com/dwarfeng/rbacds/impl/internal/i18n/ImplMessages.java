package com.dwarfeng.rbacds.impl.internal.i18n;

import com.dwarfeng.rbacds.base.sdk.i18n.Messages;
import com.dwarfeng.rbacds.base.stack.i18n.MessageCatalog;

import java.util.Locale;

/**
 * Impl 模块私有消息入口。
 *
 * <p>
 * 该工具隐藏资源路径和项目内部国际化协议，不属于公共 API。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public final class ImplMessages {

    /**
     * 使用当前语言环境解析消息。
     *
     * @param key  消息键。
     * @param args 格式化参数。
     * @return 解析后的消息。
     */
    public static String message(ImplMessageKey key, Object... args) {
        return Messages.resolve(key.catalog().messageCatalog(), key.key(), args);
    }

    /**
     * 使用指定语言环境解析消息。
     *
     * @param locale 语言环境。
     * @param key    消息键。
     * @param args   格式化参数。
     * @return 解析后的消息。
     */
    public static String message(Locale locale, ImplMessageKey key, Object... args) {
        return Messages.resolve(key.catalog().messageCatalog(), key.key(), locale, args);
    }

    private ImplMessages() {
        throw new AssertionError("No instances");
    }

    /**
     * Impl 模块消息目录。
     */
    enum Catalog {

        IMPL("com.dwarfeng.rbacds.impl.i18n.messages");

        private final MessageCatalog messageCatalog;

        Catalog(String baseName) {
            this.messageCatalog = MessageCatalog.of(ImplMessages.class, baseName);
        }

        MessageCatalog messageCatalog() {
            return messageCatalog;
        }
    }
}

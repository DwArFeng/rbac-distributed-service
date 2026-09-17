package com.dwarfeng.rbacds.node.all.he.internal.i18n;

import com.dwarfeng.rbacds.base.sdk.i18n.Messages;
import com.dwarfeng.rbacds.base.stack.i18n.MessageCatalog;

import java.util.Locale;

/**
 * Node All HE 模块私有消息入口。
 *
 * <p>
 * 该工具隐藏资源路径和项目内部国际化协议，不属于公共 API。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public final class NodeMessages {

    /**
     * 使用当前语言环境解析消息。
     *
     * @param key  消息键。
     * @param args 格式化参数。
     * @return 解析后的消息。
     */
    public static String message(NodeMessageKey key, Object... args) {
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
    public static String message(Locale locale, NodeMessageKey key, Object... args) {
        return Messages.resolve(key.catalog().messageCatalog(), key.key(), locale, args);
    }

    private NodeMessages() {
        throw new AssertionError("No instances");
    }

    /**
     * Node All HE 模块消息目录。
     */
    enum Catalog {

        NODE("com.dwarfeng.rbacds.node.all.he.i18n.messages");

        private final MessageCatalog messageCatalog;

        Catalog(String baseName) {
            this.messageCatalog = MessageCatalog.of(NodeMessages.class, baseName);
        }

        MessageCatalog messageCatalog() {
            return messageCatalog;
        }
    }
}

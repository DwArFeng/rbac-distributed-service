package com.dwarfeng.rbacds.node.all.he.internal.i18n;

import static com.dwarfeng.rbacds.node.all.he.internal.i18n.NodeMessages.Catalog.NODE;

/**
 * Node All HE 模块消息键。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public enum NodeMessageKey {

    LOG_LAUNCHER_RESET_FILTER_SUPPORT_FAILED(NODE, "log.launcher_reset_filter_support_failed"),
    LOG_LAUNCHER_RESETTING_FILTER_SUPPORT(NODE, "log.launcher_resetting_filter_support"),
    LOG_LAUNCHER_START_RESET_FAILED(NODE, "log.launcher_start_reset_failed"),
    LOG_LAUNCHER_STARTING_RESET(NODE, "log.launcher_starting_reset"),
    LOG_LAUNCHER_STARTING_RESET_DELAYED(NODE, "log.launcher_starting_reset_delayed"),
    LOG_LAUNCHER_STARTING_RESET_IMMEDIATELY(NODE, "log.launcher_starting_reset_immediately");

    private final NodeMessages.Catalog catalog;
    private final String key;

    NodeMessageKey(NodeMessages.Catalog catalog, String key) {
        this.catalog = catalog;
        this.key = key;
    }

    NodeMessages.Catalog catalog() {
        return catalog;
    }

    /**
     * 获取消息键。
     *
     * @return 消息键。
     */
    public String key() {
        return key;
    }
}

package com.dwarfeng.rbacds.node.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${launcher.reset_filter_support}")
    private boolean resetFilterSupport;

    @Value("${launcher.start_reset_delay}")
    private long startResetDelay;

    public boolean isResetFilterSupport() {
        return resetFilterSupport;
    }

    public long getStartResetDelay() {
        return startResetDelay;
    }

    @Override
    public String toString() {
        return "LauncherSettingHandler{" +
                "resetFilterSupport=" + resetFilterSupport +
                ", startResetDelay=" + startResetDelay +
                '}';
    }
}

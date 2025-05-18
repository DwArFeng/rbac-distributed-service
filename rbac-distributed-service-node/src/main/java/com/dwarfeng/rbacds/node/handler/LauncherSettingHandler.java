package com.dwarfeng.rbacds.node.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${launcher.reset_permission_filter_support}")
    private boolean resetPermissionFilterSupport;

    @Value("${launcher.start_reset_delay}")
    private long startResetDelay;

    public boolean isResetPermissionFilterSupport() {
        return resetPermissionFilterSupport;
    }

    public long getStartResetDelay() {
        return startResetDelay;
    }

    @Override
    public String toString() {
        return "LauncherSettingHandler{" +
                "resetPermissionFilterSupport=" + resetPermissionFilterSupport +
                ", startResetDelay=" + startResetDelay +
                '}';
    }
}

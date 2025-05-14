package com.dwarfeng.rbacds.node.launcher;

import com.dwarfeng.rbacds.node.handler.LauncherSettingHandler;
import com.dwarfeng.rbacds.stack.service.PermissionFilterSupportMaintainService;
import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * 程序启动器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public class Launcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    // 为了保证代码的可扩展性，此处代码不做简化。
    @SuppressWarnings("Convert2MethodRef")
    public static void main(String[] args) {
        ApplicationUtil.launch(new String[]{
                "classpath:spring/application-context*.xml",
                "file:opt/opt*.xml",
                "file:optext/opt*.xml"
        }, ctx -> {
            // 根据启动器设置处理器的设置，选择性重置权限过滤器。
            mayResetPermissionFilter(ctx);
        });
    }

    private static void mayResetPermissionFilter(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        //判断是否重置权限过滤器。
        if (launcherSettingHandler.isResetPermissionFilterSupport()) {
            LOGGER.info("重置权限过滤器支持...");
            PermissionFilterSupportMaintainService maintainService = ctx.getBean(PermissionFilterSupportMaintainService.class);
            try {
                maintainService.reset();
            } catch (ServiceException e) {
                LOGGER.warn("权限过滤器支持重置失败，异常信息如下", e);
            }
        }
    }
}

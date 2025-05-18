package com.dwarfeng.rbacds.node.launcher;

import com.dwarfeng.rbacds.node.handler.LauncherSettingHandler;
import com.dwarfeng.rbacds.stack.service.PermissionFilterSupportMaintainService;
import com.dwarfeng.rbacds.stack.service.ResetQosService;
import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;

/**
 * 程序启动器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public class Launcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        ApplicationUtil.launch(new String[]{
                "classpath:spring/application-context*.xml",
                "file:opt/opt*.xml",
                "file:optext/opt*.xml"
        }, ctx -> {
            // 根据启动器设置处理器的设置，选择性重置权限过滤器。
            mayResetPermissionFilter(ctx);

            // 根据启动器设置处理器的设置，选择性启动重置服务。
            mayStartReset(ctx);
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

    private static void mayStartReset(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取重置 QOS 服务。
        ResetQosService resetQosService = ctx.getBean(ResetQosService.class);

        // 判断重置处理器是否启动重置服务，并按条件执行不同的操作。
        long startResetDelay = launcherSettingHandler.getStartResetDelay();
        if (startResetDelay == 0) {
            LOGGER.info("立即启动重置服务...");
            try {
                resetQosService.start();
            } catch (ServiceException e) {
                LOGGER.error("无法启动重置服务，异常原因如下", e);
            }
        } else if (startResetDelay > 0) {
            LOGGER.info("{} 毫秒后启动重置服务...", startResetDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("启动重置服务...");
                        try {
                            resetQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error("无法启动重置服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + startResetDelay)
            );
        }
    }
}

package com.dwarfeng.rbacds.node.all.he.launcher;

import com.dwarfeng.rbacds.node.all.he.handler.LauncherSettingHandler;
import com.dwarfeng.rbacds.node.all.he.internal.i18n.NodeMessageKey;
import com.dwarfeng.rbacds.node.all.he.internal.i18n.NodeMessages;
import com.dwarfeng.rbacds.stack.service.ResetQosService;
import com.dwarfeng.rbacds.stack.service.SupportQosService;
import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.Instant;

/**
 * 程序启动器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public class Launcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    static void main(String[] args) {
        ApplicationUtil.launch(new String[]{
                "classpath:com/dwarfeng/rbacds/node/all/he/spring/application-context*.xml",
                "file:opt/opt*.xml",
                "file:optext/opt*.xml"
        }, ctx -> {
            // 根据启动器设置处理器的设置，选择性重置过滤器。
            mayResetFilter(ctx);

            // 根据启动器设置处理器的设置，选择性启动重置服务。
            mayStartReset(ctx);
        });
    }

    private static void mayResetFilter(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 如果不重置过滤器，则返回。
        if (!launcherSettingHandler.isResetFilterSupport()) {
            return;
        }

        // 重置过滤器支持。
        LOGGER.info(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_RESETTING_FILTER_SUPPORT));
        SupportQosService supportQosService = ctx.getBean(SupportQosService.class);
        try {
            supportQosService.resetFilter();
        } catch (ServiceException e) {
            LOGGER.warn(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_RESET_FILTER_SUPPORT_FAILED), e);
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
            LOGGER.info(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_STARTING_RESET_IMMEDIATELY));
            try {
                resetQosService.start();
            } catch (ServiceException e) {
                LOGGER.error(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_START_RESET_FAILED), e);
            }
        } else if (startResetDelay > 0) {
            LOGGER.info(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_STARTING_RESET_DELAYED, startResetDelay));
            scheduler.schedule(
                    () -> {
                        LOGGER.info(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_STARTING_RESET));
                        try {
                            resetQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_START_RESET_FAILED), e);
                        }
                    },
                    Instant.now().plusMillis(startResetDelay)
            );
        }
    }
}

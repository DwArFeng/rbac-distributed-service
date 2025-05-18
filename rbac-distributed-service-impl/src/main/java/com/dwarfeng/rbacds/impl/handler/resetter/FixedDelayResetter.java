package com.dwarfeng.rbacds.impl.handler.resetter;

import com.dwarfeng.rbacds.sdk.handler.resetter.AbstractResetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

/**
 * 固定间隔的重置器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Component
public class FixedDelayResetter extends AbstractResetter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixedDelayResetter.class);

    private final ThreadPoolTaskScheduler scheduler;

    @Value("${resetter.fixed_delay.delay}")
    private long delay;

    private final ResetTask resetTask = new ResetTask();

    private ScheduledFuture<?> resetTaskFuture;

    public FixedDelayResetter(ThreadPoolTaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void doStart() {
        resetTaskFuture = scheduler.scheduleWithFixedDelay(resetTask, delay);
    }

    @Override
    protected void doStop() {
        resetTaskFuture.cancel(true);
    }

    @Override
    public String toString() {
        return "FixedDelayResetter{" +
                "delay=" + delay +
                '}';
    }

    private class ResetTask implements Runnable {

        @Override
        public void run() {
            try {
                LOGGER.info("计划时间已到, 重置权限过滤功能...");
                context.resetPermissionFilter();
            } catch (Exception e) {
                String message = "重置器 " + FixedDelayResetter.this +
                        " 执行重置调度时发生异常, 权限过滤功能将不会重置, 异常信息如下: ";
                LOGGER.warn(message, e);
            }
        }
    }
}

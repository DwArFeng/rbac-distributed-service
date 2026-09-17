package com.dwarfeng.rbacds.impl.handler.resetter;

import com.dwarfeng.rbacds.sdk.handler.resetter.AbstractResetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

import static com.dwarfeng.rbacds.impl.internal.i18n.ImplMessageKey.LOG_RESET_FILTER_SCHEDULED;
import static com.dwarfeng.rbacds.impl.internal.i18n.ImplMessageKey.LOG_RESET_SCHEDULE_FAILED;
import static com.dwarfeng.rbacds.impl.internal.i18n.ImplMessages.message;

/**
 * 使用 CRON 表达式定时重置的重置器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Component
public class CronResetter extends AbstractResetter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CronResetter.class);

    private final ThreadPoolTaskScheduler scheduler;

    @Value("${com.dwarfeng.rbacds.resetter.cron.cron}")
    private String cron;

    private final ResetTask resetTask = new ResetTask();

    private ScheduledFuture<?> resetTaskFuture;

    public CronResetter(ThreadPoolTaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void doStart() {
        resetTaskFuture = scheduler.schedule(resetTask, new CronTrigger(cron));
    }

    @Override
    protected void doStop() {
        resetTaskFuture.cancel(true);
    }

    @Override
    public String toString() {
        return "CronResetter{" +
                "cron='" + cron + '\'' +
                '}';
    }

    private class ResetTask implements Runnable {

        @Override
        public void run() {
            try {
                LOGGER.info(message(LOG_RESET_FILTER_SCHEDULED));
                context.resetFilter();
                context.resetAnalysis();
            } catch (Exception e) {
                LOGGER.warn(message(LOG_RESET_SCHEDULE_FAILED, CronResetter.this), e);
            }
        }
    }
}

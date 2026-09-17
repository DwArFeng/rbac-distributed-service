package com.dwarfeng.rbacds.impl.handler.resetter;

import com.dwarfeng.rbacds.sdk.handler.resetter.AbstractResetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

import static com.dwarfeng.rbacds.impl.internal.i18n.ImplMessageKey.LOG_RESET_FILTER_SCHEDULED;
import static com.dwarfeng.rbacds.impl.internal.i18n.ImplMessageKey.LOG_RESET_SCHEDULE_FAILED;
import static com.dwarfeng.rbacds.impl.internal.i18n.ImplMessages.message;

/**
 * 固定间隔的重置器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Component
public class FixedRateResetter extends AbstractResetter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixedRateResetter.class);

    private final ThreadPoolTaskScheduler scheduler;

    @Value("${com.dwarfeng.rbacds.resetter.fixed_rate.rate}")
    private long rate;

    private final ResetTask resetTask = new ResetTask();

    private ScheduledFuture<?> resetTaskFuture;

    public FixedRateResetter(ThreadPoolTaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void doStart() {
        resetTaskFuture = scheduler.scheduleAtFixedRate(resetTask, Duration.ofMillis(rate));
    }

    @Override
    protected void doStop() {
        resetTaskFuture.cancel(true);
    }

    @Override
    public String toString() {
        return "FixedRateResetter{" +
                "rate=" + rate +
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
                LOGGER.warn(message(LOG_RESET_SCHEDULE_FAILED, FixedRateResetter.this), e);
            }
        }
    }
}

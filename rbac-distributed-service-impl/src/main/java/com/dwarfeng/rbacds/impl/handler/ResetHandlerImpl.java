package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.handler.ResetHandler;
import com.dwarfeng.rbacds.stack.handler.Resetter;
import com.dwarfeng.rbacds.stack.handler.ResetterHandler;
import com.dwarfeng.subgrade.aop.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;
import com.dwarfeng.subgrade.lifecycle.impl.handler.GeneralStartableHandler;
import com.dwarfeng.subgrade.lifecycle.stack.handler.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.dwarfeng.rbacds.impl.internal.i18n.ImplMessageKey.*;
import static com.dwarfeng.rbacds.impl.internal.i18n.ImplMessages.message;

@Component
public class ResetHandlerImpl implements ResetHandler {

    private final GeneralStartableHandler startableHandler;

    private final ResetProcessor resetProcessor;

    private final Lock lock = new ReentrantLock();

    public ResetHandlerImpl(ResetWorker resetWorker, ResetProcessor resetProcessor) {
        this.startableHandler = new GeneralStartableHandler(resetWorker);
        this.resetProcessor = resetProcessor;
    }

    @BehaviorAnalyse
    @Override
    public boolean isStarted() {
        lock.lock();
        try {
            return startableHandler.isStarted();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void start() throws HandlerException {
        lock.lock();
        try {
            startableHandler.start();
        } finally {
            lock.unlock();
        }

    }

    @BehaviorAnalyse
    @Override
    public void stop() throws HandlerException {
        lock.lock();
        try {
            startableHandler.stop();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void resetFilter() throws HandlerException {
        lock.lock();
        try {
            resetProcessor.resetFilter();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void resetAnalysis() throws HandlerException {
        lock.lock();
        try {
            resetProcessor.resetAnalysis();
        } finally {
            lock.unlock();
        }
    }

    @Component
    public static class ResetWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(ResetWorker.class);

        private final ResetterHandler resetterHandler;

        public ResetWorker(ResetterHandler resetterHandler) {
            this.resetterHandler = resetterHandler;
        }

        @Override
        public void work() throws Exception {
            List<Resetter> resetters = resetterHandler.all();
            LOGGER.info(message(LOG_RESETTERS_STARTING, resetters.size()));
            for (Resetter resetter : resetters) {
                try {
                    resetter.start();
                } catch (Exception e) {
                    LOGGER.warn(message(LOG_RESETTER_START_FAILED, resetter), e);
                }
            }
        }

        @Override
        public void rest() throws Exception {
            List<Resetter> resetters = resetterHandler.all();
            LOGGER.info(message(LOG_RESETTERS_STOPPING, resetters.size()));
            for (Resetter resetter : resetters) {
                try {
                    resetter.stop();
                } catch (Exception e) {
                    LOGGER.warn(message(LOG_RESETTER_STOP_FAILED, resetter), e);
                }
            }
        }
    }
}

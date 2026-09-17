package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.handler.Resetter;
import com.dwarfeng.rbacds.stack.handler.ResetterHandler;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.dwarfeng.rbacds.impl.internal.i18n.ImplMessageKey.LOG_RESETTER_HANDLER_INITIALIZING;
import static com.dwarfeng.rbacds.impl.internal.i18n.ImplMessages.message;

@Component
public class ResetterHandlerImpl implements ResetterHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetterHandlerImpl.class);

    private final ResetProcessor resetProcessor;

    private final List<Resetter> resetters;

    private final InternalResetterContext resetterContext = new InternalResetterContext();

    public ResetterHandlerImpl(ResetProcessor resetProcessor, List<Resetter> resetters) {
        this.resetProcessor = resetProcessor;
        this.resetters = Optional.ofNullable(resetters).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() {
        LOGGER.info(message(LOG_RESETTER_HANDLER_INITIALIZING));
        resetters.forEach(resetter -> resetter.init(resetterContext));
    }

    @Override
    public List<Resetter> all() {
        return resetters;
    }

    private class InternalResetterContext implements Resetter.Context {

        @Override
        public void resetFilter() throws Exception {
            resetProcessor.resetFilter();
        }

        @Override
        public void resetAnalysis() throws Exception {
            resetProcessor.resetAnalysis();
        }
    }
}

package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.handler.Resetter;
import com.dwarfeng.rbacds.stack.handler.ResetterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        LOGGER.info("初始化权限过滤器...");
        resetters.forEach(resetter -> resetter.init(resetterContext));
    }

    @Override
    public List<Resetter> all() {
        return resetters;
    }

    private class InternalResetterContext implements Resetter.Context {

        @Override
        public void resetPermissionFilter() throws Exception {
            resetProcessor.resetPermissionFilter();
        }
    }
}

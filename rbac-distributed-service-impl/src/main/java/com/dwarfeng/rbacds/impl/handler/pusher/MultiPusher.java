package com.dwarfeng.rbacds.impl.handler.pusher;

import com.dwarfeng.rbacds.sdk.handler.Pusher;
import com.dwarfeng.rbacds.sdk.handler.pusher.AbstractPusher;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.dwarfeng.rbacds.impl.internal.i18n.ImplMessageKey.LOG_PROXY_PUSHER_PUSH_FAILED;
import static com.dwarfeng.rbacds.impl.internal.i18n.ImplMessages.message;

/**
 * 同时将消息推送给所有代理的多重推送器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Component
public class MultiPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "multi";
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiPusher.class);

    private final List<Pusher> pushers;

    @Value("${com.dwarfeng.rbacds.pusher.multi.delegate_types}")
    private String delegateTypes;

    private final List<Pusher> delegates = new ArrayList<>();

    public MultiPusher(List<Pusher> pushers) {
        super(PUSHER_TYPE);
        this.pushers = Optional.ofNullable(pushers).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws HandlerException {
        StringTokenizer st = new StringTokenizer(delegateTypes, ",");
        while (st.hasMoreTokens()) {
            String delegateType = st.nextToken();
            delegates.add(pushers.stream().filter(p -> p.supportType(delegateType)).findAny()
                    .orElseThrow(() -> new HandlerException("未知的 pusher 类型: " + delegateType)));
        }
    }

    @Override
    public void filterReset() {
        for (Pusher delegate : delegates) {
            try {
                delegate.filterReset();
            } catch (Exception e) {
                LOGGER.warn(message(LOG_PROXY_PUSHER_PUSH_FAILED), e);
            }
        }
    }

    @Override
    public void analysisReset() {
        for (Pusher delegate : delegates) {
            try {
                delegate.analysisReset();
            } catch (Exception e) {
                LOGGER.warn(message(LOG_PROXY_PUSHER_PUSH_FAILED), e);
            }
        }
    }

    @Override
    public String toString() {
        return "MultiPusher{" +
                "delegateTypes='" + delegateTypes + '\'' +
                ", pusherType='" + pusherType + '\'' +
                '}';
    }
}

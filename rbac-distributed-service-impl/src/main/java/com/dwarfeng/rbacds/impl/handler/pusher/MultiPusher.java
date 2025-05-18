package com.dwarfeng.rbacds.impl.handler.pusher;

import com.dwarfeng.rbacds.sdk.handler.Pusher;
import com.dwarfeng.rbacds.sdk.handler.pusher.AbstractPusher;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

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

    @Value("${pusher.multi.delegate_types}")
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
    public void permissionFilterReset() {
        for (Pusher delegate : delegates) {
            try {
                delegate.permissionFilterReset();
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
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

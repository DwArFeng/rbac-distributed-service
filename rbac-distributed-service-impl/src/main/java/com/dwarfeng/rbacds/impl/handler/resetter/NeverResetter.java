package com.dwarfeng.rbacds.impl.handler.resetter;

import com.dwarfeng.rbacds.sdk.handler.resetter.AbstractResetter;
import org.springframework.stereotype.Component;

/**
 * 永远不执行重置的重置器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Component
public class NeverResetter extends AbstractResetter {

    @Override
    protected void doStart() {
    }

    @Override
    protected void doStop() {
    }

    @Override
    public String toString() {
        return "NeverResetter{}";
    }
}

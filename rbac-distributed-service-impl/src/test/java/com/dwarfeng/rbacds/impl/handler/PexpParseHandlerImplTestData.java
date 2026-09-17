package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.handler.PexpParseHandler;
import org.jetbrains.annotations.NotNull;

/**
 * 权限表达式解析处理器测试数据。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
record PexpParseHandlerImplTestData(
        Pexp pexp, PexpParseHandler.ParseResult expectedResult, boolean expectedExceptionThrow
) {

    @Override
    public @NotNull String toString() {
        return "TestData{" +
                "pexp=" + pexp +
                ", expectedResult=" + expectedResult +
                ", expectedExceptionThrow=" + expectedExceptionThrow +
                '}';
    }
}

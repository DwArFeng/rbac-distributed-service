package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.handler.PexpParseHandler;

/**
 * 权限表达式解析处理器测试数据。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
final class PexpParseHandlerImplTestData {

    private final Pexp pexp;
    private final PexpParseHandler.ParseResult expectedResult;
    private final boolean expectedExceptionThrow;

    PexpParseHandlerImplTestData(Pexp pexp, PexpParseHandler.ParseResult expectedResult, boolean expectedExceptionThrow) {
        this.pexp = pexp;
        this.expectedResult = expectedResult;
        this.expectedExceptionThrow = expectedExceptionThrow;
    }

    public Pexp getPexp() {
        return pexp;
    }

    public PexpParseHandler.ParseResult getExpectedResult() {
        return expectedResult;
    }

    public boolean isExpectedExceptionThrow() {
        return expectedExceptionThrow;
    }

    @Override
    public String toString() {
        return "TestData{" +
                "pexp=" + pexp +
                ", expectedResult=" + expectedResult +
                ", expectedExceptionThrow=" + expectedExceptionThrow +
                '}';
    }
}

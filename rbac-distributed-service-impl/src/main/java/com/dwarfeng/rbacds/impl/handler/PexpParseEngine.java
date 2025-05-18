package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.handler.PexpParseHandler.ParseResult;

/**
 * 权限表达式解析引擎。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public interface PexpParseEngine {

    /**
     * 解析权限表达式。
     *
     * @return 解析结果。
     * @throws Exception 解析过程中发生的任何异常。
     */
    ParseResult parse() throws Exception;
}

package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.handler.PexpParseHandler.ParseResult;
import com.dwarfeng.rbacds.stack.handler.PexpParseHandler.PipeModifier;
import com.dwarfeng.rbacds.stack.handler.PexpParseHandler.PipeUnit;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 权限表达式 V2 解析引擎。
 *
 * <p>
 * 按照权限表达式 V2 规则解析权限表达式。<br>
 * 权限表达式 V2 规则如下可以参照 Wiki 中的相关文档。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class V2PexpParseEngine implements PexpParseEngine {

    /**
     * 修饰符：接受。
     */
    public static final char MODIFIER_CHAR_ACCEPT = '+';

    /**
     * 修饰符：拒绝。
     */
    public static final char MODIFIER_CHAR_REJECT = '-';

    /**
     * 修饰符：全局拒绝。
     */
    public static final char MODIFIER_CHAR_GLOBAL_REJECT = '!';

    /**
     * 控制符：匹配模式分隔。
     */
    public static final char CONTROLLER_CHAR_PATTERN_DELIMITER = '@';

    /**
     * 控制符：管道。
     */
    public static final char CONTROLLER_CHAR_PIPE = '|';

    /**
     * 转义符。
     */
    public static final char ESCAPE_CHAR = '\\';

    private static final Logger LOGGER = LoggerFactory.getLogger(V2PexpParseEngine.class);

    private final String pexpContent;

    private ParserState state = ParserState.START;
    private int index = 0;

    private List<PipeUnit> parsingPipe;
    private StringBuilder parsingPipeUnitFilterTypeBuilder;
    private StringBuilder parsingPipeUnitFilterPatternBuilder;

    private PipeModifier parsedPipeModifier;
    private List<PipeUnit> parsedPipe;
    private String parsedPipeUnitFilterType;
    private String parsedPipeUnitFilterPattern;

    public V2PexpParseEngine(String pexpContent) {
        this.pexpContent = pexpContent;
    }

    @BehaviorAnalyse
    @Override
    public ParseResult parse() throws Exception {

        /*
         * 解析方法说明：
         * 权限表达式 V2 的语法规则较为复杂，具有转义、管道等多个功能。在解析时，使用一个变种状态机，管理内部的解析状态。
         * 解析状态一共有：
         *   1. START：开始解析状态，初始化解析器。
         *      状态可转移到 PARSE_MODIFIER。
         *   2. PARSE_MODIFIER：解析修饰符状态，解析修饰符。
         *      状态可转移到 PARSE_FILTER_TYPE。
         *   3. PARSE_FILTER_TYPE：解析过滤器类型状态，解析过滤器类型。
         *      状态可转移到 PARSE_FILTER_PATTERN。
         *   4. PARSE_FILTER_PATTERN：解析过滤器匹配模式状态，解析过滤器匹配模式。
         *      状态可转移到 PARSE_FILTER_TYPE 或者 END。
         *   5. END：结束状态，解析结束。
         *      执行完毕后退出主循环。
         */

        LOGGER.debug("解析开始...");
        int loopCount = 0;
        outer:
        while (true) {
            loopCount++;
            LOGGER.debug("循环解析, 计数: {}, 当前状态: {}", loopCount, state);
            switch (state) {
                case START:
                    parseStart();
                    break;
                case PARSE_MODIFIER:
                    parseModifier();
                    break;
                case PARSE_FILTER_TYPE:
                    parseFilterType();
                    break;
                case PARSE_FILTER_PATTERN:
                    parseFilterPattern();
                    break;
                case END:
                    parseEnd();
                    break outer;
                default:
                    throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
            }
        }
        ParseResult parseResult = new ParseResult(parsedPipeModifier, parsedPipe);
        LOGGER.debug("解析结束, 结果: {}", parseResult);
        return parseResult;
    }

    private void parseStart() throws Exception {
        LOGGER.debug("基于 START 状态开始解析...");
        if (StringUtils.isEmpty(pexpContent)) {
            LOGGER.debug("字符串 pexpContent 为空, 将抛出异常...");
            throw new EmptyPexpContentException();
        }
        LOGGER.debug(
                "初始化 parsingPipe, parsingPipeUnitFilterTypeBuilder, parsingPipeUnitFilterPatternBuilder..."
        );
        parsingPipe = new ArrayList<>();
        parsingPipeUnitFilterTypeBuilder = new StringBuilder();
        parsingPipeUnitFilterPatternBuilder = new StringBuilder();
        LOGGER.debug("状态转移, 新状态: {}", ParserState.PARSE_MODIFIER);
        state = ParserState.PARSE_MODIFIER;
    }

    private void parseModifier() throws Exception {
        LOGGER.debug("基于 PARSE_MODIFIER 状态开始解析...");
        LOGGER.debug("获取当前字符...");
        char modifierChar = pexpContent.charAt(index++);
        LOGGER.debug("当前字符: {}", modifierChar);
        if (Objects.equals(modifierChar, MODIFIER_CHAR_ACCEPT)) {
            LOGGER.debug("当前字符为接受修饰符, 设置修饰符, 值: {}", PipeModifier.ACCEPT);
            parsedPipeModifier = PipeModifier.ACCEPT;
        } else if (Objects.equals(modifierChar, MODIFIER_CHAR_REJECT)) {
            LOGGER.debug("当前字符为拒绝修饰符, 设置修饰符, 值: {}", PipeModifier.REJECT);
            parsedPipeModifier = PipeModifier.REJECT;
        } else if (Objects.equals(modifierChar, MODIFIER_CHAR_GLOBAL_REJECT)) {
            LOGGER.debug("当前字符为全局拒绝修饰符, 设置修饰符, 值: {}", PipeModifier.GLOBAL_REJECT);
            parsedPipeModifier = PipeModifier.GLOBAL_REJECT;
        } else {
            LOGGER.debug("当前字符不属于任何修饰符, 将抛出异常...");
            String detailMessage = "非法的修饰符: " + modifierChar;
            throw new MalformedPexpContentException(pexpContent, index - 1, detailMessage);
        }
        LOGGER.debug("状态转移, 新状态: {}", ParserState.PARSE_FILTER_TYPE);
        state = ParserState.PARSE_FILTER_TYPE;
    }

    @SuppressWarnings("DuplicatedCode")
    private void parseFilterType() throws Exception {
        LOGGER.debug("基于 PARSE_FILTER_TYPE 状态开始解析...");
        if (pexpContent.length() <= index) {
            LOGGER.debug("字符串 pexpContent 提前结束, 将抛出异常...");
            throw new MalformedPexpContentException(pexpContent, pexpContent.length(), "表达式提前结束");
        }
        LOGGER.debug("获取当前字符...");
        char anchorChar = pexpContent.charAt(index++);
        LOGGER.debug("当前字符: {}", anchorChar);
        boolean escapeFlag = false;
        if (Objects.equals(anchorChar, ESCAPE_CHAR)) {
            LOGGER.debug("当前字符为转义字符, 设置 escapeFlag 为 true...");
            escapeFlag = true;
            if (pexpContent.length() <= index) {
                LOGGER.debug("字符串 pexpContent 提前结束, 将抛出异常...");
                throw new MalformedPexpContentException(pexpContent, pexpContent.length(), "表达式提前结束");
            }
            LOGGER.debug("获取下一个字符作为当前字符...");
            anchorChar = pexpContent.charAt(index++);
            LOGGER.debug("当前字符: {}", anchorChar);
        }
        if (escapeFlag) {
            if (Objects.equals(anchorChar, ESCAPE_CHAR)) {
                LOGGER.debug("当前状态为 转义符+转义符, 向 parsingPipeUnitFilterTypeBuilder 中追加当转义符...");
                parsingPipeUnitFilterTypeBuilder.append(ESCAPE_CHAR);
            } else if (Objects.equals(anchorChar, CONTROLLER_CHAR_PATTERN_DELIMITER)) {
                LOGGER.debug(
                        "当前状态为 转义符+匹配模式分隔符, 向 parsingPipeUnitFilterTypeBuilder 中追加匹配模式分隔符..."
                );
                parsingPipeUnitFilterTypeBuilder.append(CONTROLLER_CHAR_PATTERN_DELIMITER);
            } else if (Objects.equals(anchorChar, CONTROLLER_CHAR_PIPE)) {
                LOGGER.debug("当前状态为 转义符+管道符, 向 parsingPipeUnitFilterTypeBuilder 中追加管道符...");
                parsingPipeUnitFilterTypeBuilder.append(CONTROLLER_CHAR_PIPE);
            } else {
                LOGGER.debug("当前状态为 转义符+非法字符, 将抛出异常...");
                String detailMessage = "非法的转义字符: " + ESCAPE_CHAR + anchorChar;
                throw new MalformedPexpContentException(pexpContent, index - 2, detailMessage);
            }
            return;
        } else {
            if (Objects.equals(anchorChar, CONTROLLER_CHAR_PIPE)) {
                LOGGER.debug("管道符不允许单独出现, 将抛出异常...");
                throw new MalformedPexpContentException(pexpContent, index - 1, "过滤器类型中不允许出现管道符, 请转义");
            }
        }
        if (Objects.equals(anchorChar, CONTROLLER_CHAR_PATTERN_DELIMITER)) {
            LOGGER.debug(
                    "当前字符为匹配模式分隔符, 设置 parsedPipeUnitFilterType 的值为 " +
                            "parsingPipeUnitFilterTypeBuilder 中的内容..."
            );
            parsedPipeUnitFilterType = parsingPipeUnitFilterTypeBuilder.toString();
            LOGGER.debug("当前 parsedPipeUnitFilterType: {}", parsedPipeUnitFilterType);
            if (StringUtils.isEmpty(parsedPipeUnitFilterType)) {
                LOGGER.debug("当前 parsedPipeUnitFilterType 为空, 将抛出异常...");
                throw new MalformedPexpContentException(pexpContent, index - 1, "过滤器类型不能为空");
            }
            LOGGER.debug("重置 parsingPipeUnitFilterTypeBuilder...");
            parsingPipeUnitFilterTypeBuilder = new StringBuilder();
            LOGGER.debug("状态转移, 新状态: {}", ParserState.PARSE_FILTER_PATTERN);
            state = ParserState.PARSE_FILTER_PATTERN;
            return;
        }
        LOGGER.debug("当前字符属于一般字符, 向 parsingPipeUnitFilterTypeBuilder 中追加当前字符...");
        parsingPipeUnitFilterTypeBuilder.append(anchorChar);
    }

    @SuppressWarnings("DuplicatedCode")
    private void parseFilterPattern() throws Exception {
        LOGGER.debug("基于 PARSE_FILTER_PATTERN 状态开始解析...");
        if (pexpContent.length() <= index) {
            LOGGER.debug(
                    "字符串 pexpContent 结束, 设置 parsedPipeUnitFilterPattern 的值为 " +
                            "parsingPipeUnitFilterPatternBuilder 中的内容..."
            );
            parsedPipeUnitFilterPattern = parsingPipeUnitFilterPatternBuilder.toString();
            LOGGER.debug("当前 parsedPipeUnitFilterPattern: {}", parsedPipeUnitFilterPattern);
            LOGGER.debug("基于 parsedPipeUnitFilterType, parsedPipeUnitFilterPattern 构造 pipeUnit...");
            PipeUnit pipeUnit = new PipeUnit(parsedPipeUnitFilterType, parsedPipeUnitFilterPattern);
            LOGGER.debug("构造的 pipeUnit: {}", pipeUnit);
            parsingPipe.add(pipeUnit);
            LOGGER.debug("状态转移, 新状态: {}", ParserState.END);
            state = ParserState.END;
            return;
        }
        LOGGER.debug("获取当前字符...");
        char anchorChar = pexpContent.charAt(index++);
        LOGGER.debug("当前字符: {}", anchorChar);
        boolean escapeFlag = false;
        if (Objects.equals(anchorChar, ESCAPE_CHAR)) {
            LOGGER.debug("当前字符为转义字符, 设置 escapeFlag 为 true...");
            escapeFlag = true;
            if (pexpContent.length() <= index) {
                LOGGER.debug("字符串 pexpContent 提前结束, 将抛出异常...");
                throw new MalformedPexpContentException(pexpContent, pexpContent.length(), "表达式提前结束");
            }
            LOGGER.debug("获取下一个字符作为当前字符...");
            anchorChar = pexpContent.charAt(index++);
            LOGGER.debug("当前字符: {}", anchorChar);
        }
        if (escapeFlag) {
            if (Objects.equals(anchorChar, ESCAPE_CHAR)) {
                LOGGER.debug("当前状态为 转义符+转义符, 向 parsingPipeUnitFilterPatternBuilder 中追加当转义符...");
                parsingPipeUnitFilterPatternBuilder.append(ESCAPE_CHAR);
            } else if (Objects.equals(anchorChar, CONTROLLER_CHAR_PATTERN_DELIMITER)) {
                LOGGER.debug(
                        "当前状态为 转义符+匹配模式分隔符, 向 parsingPipeUnitFilterPatternBuilder 中追加匹配模式分隔符..."
                );
                parsingPipeUnitFilterPatternBuilder.append(CONTROLLER_CHAR_PATTERN_DELIMITER);
            } else if (Objects.equals(anchorChar, CONTROLLER_CHAR_PIPE)) {
                LOGGER.debug("当前状态为 转义符+管道符, 向 parsingPipeUnitFilterPatternBuilder 中追加管道符...");
                parsingPipeUnitFilterPatternBuilder.append(CONTROLLER_CHAR_PIPE);
            } else {
                LOGGER.debug("当前状态为 转义符+非法字符, 将抛出异常...");
                String detailMessage = "非法的转义字符: " + ESCAPE_CHAR + anchorChar;
                throw new MalformedPexpContentException(pexpContent, index - 2, detailMessage);
            }
            return;
        } else {
            if (Objects.equals(anchorChar, CONTROLLER_CHAR_PATTERN_DELIMITER)) {
                LOGGER.debug("匹配模式分隔符不允许单独出现, 将抛出异常...");
                throw new MalformedPexpContentException(
                        pexpContent, index - 1, "过滤器匹配模式中不允许出现匹配模式分隔符, 请转义"
                );
            }
        }
        if (Objects.equals(anchorChar, CONTROLLER_CHAR_PIPE)) {
            LOGGER.debug(
                    "当前字符为管道符, 设置 parsedPipeUnitFilterPattern 的值为 " +
                            "parsingPipeUnitFilterPatternBuilder 中的内容..."
            );
            parsedPipeUnitFilterPattern = parsingPipeUnitFilterPatternBuilder.toString();
            LOGGER.debug("当前 parsedPipeUnitFilterPattern: {}", parsedPipeUnitFilterPattern);
            LOGGER.debug("重置 parsingPipeUnitFilterPatternBuilder...");
            parsingPipeUnitFilterPatternBuilder = new StringBuilder();
            LOGGER.debug("基于 parsedPipeUnitFilterType, parsedPipeUnitFilterPattern 构造 pipeUnit...");
            PipeUnit pipeUnit = new PipeUnit(parsedPipeUnitFilterType, parsedPipeUnitFilterPattern);
            parsingPipe.add(pipeUnit);
            LOGGER.debug("状态转移, 新状态: {}", ParserState.PARSE_FILTER_TYPE);
            state = ParserState.PARSE_FILTER_TYPE;
            return;
        }
        LOGGER.debug("当前字符属于一般字符, 向 parsingPipeUnitFilterPatternBuilder 中追加当前字符...");
        parsingPipeUnitFilterPatternBuilder.append(anchorChar);
    }

    private void parseEnd() {
        LOGGER.debug("基于 END 状态开始解析...");
        LOGGER.debug(
                "设置 parsedPipeUnitFilterPattern 的值为 " +
                        "parsingPipeUnitFilterPatternBuilder 中的内容, 使用不可变列表..."
        );
        parsedPipe = Collections.unmodifiableList(parsingPipe);
    }

    @Override
    public String toString() {
        return "V2PexpParseEngine{" +
                "pexpContent='" + pexpContent + '\'' +
                ", state=" + state +
                ", index=" + index +
                ", parsingPipe=" + parsingPipe +
                ", parsingPipeUnitFilterTypeBuilder=" + parsingPipeUnitFilterTypeBuilder +
                ", parsingPipeUnitFilterPatternBuilder=" + parsingPipeUnitFilterPatternBuilder +
                ", parsedPipeModifier=" + parsedPipeModifier +
                ", parsedPipe=" + parsedPipe +
                ", parsedPipeUnitFilterType='" + parsedPipeUnitFilterType + '\'' +
                ", parsedPipeUnitFilterPattern='" + parsedPipeUnitFilterPattern + '\'' +
                '}';
    }

    /**
     * 解析状态。
     *
     * <p>
     * 用于权限表达式解析器。
     *
     * @author DwArFeng
     * @since 1.8.0
     */
    private enum ParserState {
        START,
        PARSE_MODIFIER,
        PARSE_FILTER_TYPE,
        PARSE_FILTER_PATTERN,
        END,
    }

    /**
     * 空的权限表达式内容异常。
     *
     * @author DwArFeng
     * @since 1.8.0
     */
    private static class EmptyPexpContentException extends Exception {

        private static final long serialVersionUID = 3779956237595708201L;

        public EmptyPexpContentException() {
        }

        @Override
        public String getMessage() {
            return "权限表达式内容为空";
        }
    }

    /**
     * 畸形权限表达式内容异常。
     *
     * @author DwArFeng
     * @since 1.8.0
     */
    private static class MalformedPexpContentException extends Exception {

        private static final long serialVersionUID = -1672083737202377830L;

        private final String pexpContent;
        private final int index;
        private final String detailMessage;

        public MalformedPexpContentException(String pexpContent, int index, String detailMessage) {
            this.pexpContent = pexpContent;
            this.index = index;
            this.detailMessage = detailMessage;
        }

        @Override
        public String getMessage() {
            return "畸形的权限表达式内容, 内容: " + pexpContent + ", 索引: " + index + ", 详细信息: " + detailMessage;
        }
    }
}

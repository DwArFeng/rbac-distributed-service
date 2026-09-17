package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.handler.PexpParseHandler;
import com.dwarfeng.rbacds.stack.handler.PexpParseHandler.Modifier;
import com.dwarfeng.rbacds.stack.handler.PexpParseHandler.ParseResult;
import com.dwarfeng.rbacds.stack.handler.PexpParseHandler.PipeUnit;
import com.dwarfeng.subgrade.aop.sdk.interceptor.analyse.BehaviorAnalyse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.dwarfeng.rbacds.impl.internal.i18n.ImplMessageKey.*;
import static com.dwarfeng.rbacds.impl.internal.i18n.ImplMessages.message;

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

    private Modifier parsedModifier;
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

        LOGGER.debug(message(LOG_PEXP_PARSE_STARTING));
        int loopCount = 0;
        outer:
        while (true) {
            loopCount++;
            LOGGER.debug(message(LOG_PEXP_PARSE_LOOP, loopCount, state));
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
        ParseResult parseResult = new ParseResult(parsedModifier, parsedPipe);
        LOGGER.debug(message(LOG_PEXP_PARSE_FINISHED, parseResult));
        return parseResult;
    }

    private void parseStart() throws Exception {
        LOGGER.debug(message(LOG_PEXP_PARSE_STATE_PROCESSING, ParserState.START));
        if (StringUtils.isEmpty(pexpContent)) {
            LOGGER.debug(message(LOG_PEXP_PARSE_EMPTY_CONTENT));
            throw new EmptyPexpContentException();
        }
        LOGGER.debug(message(LOG_PEXP_PARSE_INITIALIZING));
        parsingPipe = new ArrayList<>();
        parsingPipeUnitFilterTypeBuilder = new StringBuilder();
        parsingPipeUnitFilterPatternBuilder = new StringBuilder();
        LOGGER.debug(message(LOG_PEXP_PARSE_STATE_TRANSITION, ParserState.PARSE_MODIFIER));
        state = ParserState.PARSE_MODIFIER;
    }

    private void parseModifier() throws Exception {
        LOGGER.debug(message(LOG_PEXP_PARSE_STATE_PROCESSING, ParserState.PARSE_MODIFIER));
        LOGGER.debug(message(LOG_PEXP_PARSE_READ_CHARACTER));
        char modifierChar = pexpContent.charAt(index++);
        LOGGER.debug(message(LOG_PEXP_PARSE_CURRENT_CHARACTER, modifierChar));
        if (Objects.equals(modifierChar, MODIFIER_CHAR_ACCEPT)) {
            LOGGER.debug(message(LOG_PEXP_PARSE_MODIFIER_ACCEPT, Modifier.ACCEPT));
            parsedModifier = PexpParseHandler.Modifier.ACCEPT;
        } else if (Objects.equals(modifierChar, MODIFIER_CHAR_REJECT)) {
            LOGGER.debug(message(LOG_PEXP_PARSE_MODIFIER_REJECT, Modifier.REJECT));
            parsedModifier = Modifier.REJECT;
        } else if (Objects.equals(modifierChar, MODIFIER_CHAR_GLOBAL_REJECT)) {
            LOGGER.debug(message(LOG_PEXP_PARSE_MODIFIER_GLOBAL_REJECT, Modifier.GLOBAL_REJECT));
            parsedModifier = Modifier.GLOBAL_REJECT;
        } else {
            LOGGER.debug(message(LOG_PEXP_PARSE_INVALID_MODIFIER));
            String detailMessage = "非法的修饰符: " + modifierChar;
            throw new MalformedPexpContentException(pexpContent, index - 1, detailMessage);
        }
        LOGGER.debug(message(LOG_PEXP_PARSE_STATE_TRANSITION, ParserState.PARSE_FILTER_TYPE));
        state = ParserState.PARSE_FILTER_TYPE;
    }

    @SuppressWarnings("DuplicatedCode")
    private void parseFilterType() throws Exception {
        LOGGER.debug(message(LOG_PEXP_PARSE_STATE_PROCESSING, ParserState.PARSE_FILTER_TYPE));
        if (pexpContent.length() <= index) {
            LOGGER.debug(message(LOG_PEXP_PARSE_PREMATURE_END));
            throw new MalformedPexpContentException(pexpContent, pexpContent.length(), "表达式提前结束");
        }
        LOGGER.debug(message(LOG_PEXP_PARSE_READ_CHARACTER));
        char anchorChar = pexpContent.charAt(index++);
        LOGGER.debug(message(LOG_PEXP_PARSE_CURRENT_CHARACTER, anchorChar));
        boolean escapeFlag = false;
        if (Objects.equals(anchorChar, ESCAPE_CHAR)) {
            LOGGER.debug(message(LOG_PEXP_PARSE_ESCAPE_DETECTED));
            escapeFlag = true;
            if (pexpContent.length() <= index) {
                LOGGER.debug(message(LOG_PEXP_PARSE_PREMATURE_END));
                throw new MalformedPexpContentException(pexpContent, pexpContent.length(), "表达式提前结束");
            }
            LOGGER.debug(message(LOG_PEXP_PARSE_NEXT_CHARACTER));
            anchorChar = pexpContent.charAt(index++);
            LOGGER.debug(message(LOG_PEXP_PARSE_CURRENT_CHARACTER, anchorChar));
        }
        if (escapeFlag) {
            if (Objects.equals(anchorChar, ESCAPE_CHAR)) {
                LOGGER.debug(message(LOG_PEXP_PARSE_ESCAPE_ESCAPE));
                parsingPipeUnitFilterTypeBuilder.append(ESCAPE_CHAR);
            } else if (Objects.equals(anchorChar, CONTROLLER_CHAR_PATTERN_DELIMITER)) {
                LOGGER.debug(message(LOG_PEXP_PARSE_ESCAPE_PATTERN_DELIMITER));
                parsingPipeUnitFilterTypeBuilder.append(CONTROLLER_CHAR_PATTERN_DELIMITER);
            } else if (Objects.equals(anchorChar, CONTROLLER_CHAR_PIPE)) {
                LOGGER.debug(message(LOG_PEXP_PARSE_ESCAPE_PIPE));
                parsingPipeUnitFilterTypeBuilder.append(CONTROLLER_CHAR_PIPE);
            } else {
                LOGGER.debug(message(LOG_PEXP_PARSE_ESCAPE_INVALID));
                String detailMessage = "非法的转义字符: " + ESCAPE_CHAR + anchorChar;
                throw new MalformedPexpContentException(pexpContent, index - 2, detailMessage);
            }
            return;
        } else {
            if (Objects.equals(anchorChar, CONTROLLER_CHAR_PIPE)) {
                LOGGER.debug(message(LOG_PEXP_PARSE_PIPE_WITHOUT_FILTER_TYPE));
                throw new MalformedPexpContentException(pexpContent, index - 1, "过滤器类型中不允许出现管道符, 请转义");
            }
        }
        if (Objects.equals(anchorChar, CONTROLLER_CHAR_PATTERN_DELIMITER)) {
            LOGGER.debug(message(LOG_PEXP_PARSE_SET_FILTER_TYPE));
            parsedPipeUnitFilterType = parsingPipeUnitFilterTypeBuilder.toString();
            LOGGER.debug(message(LOG_PEXP_PARSE_CURRENT_FILTER_TYPE, parsedPipeUnitFilterType));
            if (StringUtils.isEmpty(parsedPipeUnitFilterType)) {
                LOGGER.debug(message(LOG_PEXP_PARSE_EMPTY_FILTER_TYPE));
                throw new MalformedPexpContentException(pexpContent, index - 1, "过滤器类型不能为空");
            }
            LOGGER.debug(message(LOG_PEXP_PARSE_RESET_FILTER_TYPE_BUILDER));
            parsingPipeUnitFilterTypeBuilder = new StringBuilder();
            LOGGER.debug(message(LOG_PEXP_PARSE_STATE_TRANSITION, ParserState.PARSE_FILTER_PATTERN));
            state = ParserState.PARSE_FILTER_PATTERN;
            return;
        }
        LOGGER.debug(message(LOG_PEXP_PARSE_APPEND_CHARACTER));
        parsingPipeUnitFilterTypeBuilder.append(anchorChar);
    }

    @SuppressWarnings("DuplicatedCode")
    private void parseFilterPattern() throws Exception {
        LOGGER.debug(message(LOG_PEXP_PARSE_STATE_PROCESSING, ParserState.PARSE_FILTER_PATTERN));
        if (pexpContent.length() <= index) {
            LOGGER.debug(message(LOG_PEXP_PARSE_SET_FILTER_PATTERN));
            parsedPipeUnitFilterPattern = parsingPipeUnitFilterPatternBuilder.toString();
            LOGGER.debug(message(LOG_PEXP_PARSE_CURRENT_FILTER_PATTERN, parsedPipeUnitFilterPattern));
            LOGGER.debug(message(LOG_PEXP_PARSE_BUILD_PIPE_UNIT));
            PipeUnit pipeUnit = new PipeUnit(parsedPipeUnitFilterType, parsedPipeUnitFilterPattern);
            LOGGER.debug(message(LOG_PEXP_PARSE_PIPE_UNIT_DETAIL, pipeUnit));
            parsingPipe.add(pipeUnit);
            LOGGER.debug(message(LOG_PEXP_PARSE_STATE_TRANSITION, ParserState.END));
            state = ParserState.END;
            return;
        }
        LOGGER.debug(message(LOG_PEXP_PARSE_READ_CHARACTER));
        char anchorChar = pexpContent.charAt(index++);
        LOGGER.debug(message(LOG_PEXP_PARSE_CURRENT_CHARACTER, anchorChar));
        boolean escapeFlag = false;
        if (Objects.equals(anchorChar, ESCAPE_CHAR)) {
            LOGGER.debug(message(LOG_PEXP_PARSE_ESCAPE_DETECTED));
            escapeFlag = true;
            if (pexpContent.length() <= index) {
                LOGGER.debug(message(LOG_PEXP_PARSE_PREMATURE_END));
                throw new MalformedPexpContentException(pexpContent, pexpContent.length(), "表达式提前结束");
            }
            LOGGER.debug(message(LOG_PEXP_PARSE_NEXT_CHARACTER));
            anchorChar = pexpContent.charAt(index++);
            LOGGER.debug(message(LOG_PEXP_PARSE_CURRENT_CHARACTER, anchorChar));
        }
        if (escapeFlag) {
            if (Objects.equals(anchorChar, ESCAPE_CHAR)) {
                LOGGER.debug(message(LOG_PEXP_PARSE_ESCAPE_ESCAPE));
                parsingPipeUnitFilterPatternBuilder.append(ESCAPE_CHAR);
            } else if (Objects.equals(anchorChar, CONTROLLER_CHAR_PATTERN_DELIMITER)) {
                LOGGER.debug(message(LOG_PEXP_PARSE_ESCAPE_PATTERN_DELIMITER));
                parsingPipeUnitFilterPatternBuilder.append(CONTROLLER_CHAR_PATTERN_DELIMITER);
            } else if (Objects.equals(anchorChar, CONTROLLER_CHAR_PIPE)) {
                LOGGER.debug(message(LOG_PEXP_PARSE_ESCAPE_PIPE));
                parsingPipeUnitFilterPatternBuilder.append(CONTROLLER_CHAR_PIPE);
            } else {
                LOGGER.debug(message(LOG_PEXP_PARSE_ESCAPE_INVALID));
                String detailMessage = "非法的转义字符: " + ESCAPE_CHAR + anchorChar;
                throw new MalformedPexpContentException(pexpContent, index - 2, detailMessage);
            }
            return;
        } else {
            if (Objects.equals(anchorChar, CONTROLLER_CHAR_PATTERN_DELIMITER)) {
                LOGGER.debug(message(LOG_PEXP_PARSE_PATTERN_SEPARATOR_WITHOUT_FILTER));
                throw new MalformedPexpContentException(
                        pexpContent, index - 1, "过滤器匹配模式中不允许出现匹配模式分隔符, 请转义"
                );
            }
        }
        if (Objects.equals(anchorChar, CONTROLLER_CHAR_PIPE)) {
            LOGGER.debug(message(LOG_PEXP_PARSE_SET_FILTER_PATTERN));
            parsedPipeUnitFilterPattern = parsingPipeUnitFilterPatternBuilder.toString();
            LOGGER.debug(message(LOG_PEXP_PARSE_CURRENT_FILTER_PATTERN, parsedPipeUnitFilterPattern));
            LOGGER.debug(message(LOG_PEXP_PARSE_RESET_FILTER_PATTERN_BUILDER));
            parsingPipeUnitFilterPatternBuilder = new StringBuilder();
            LOGGER.debug(message(LOG_PEXP_PARSE_BUILD_PIPE_UNIT));
            PipeUnit pipeUnit = new PipeUnit(parsedPipeUnitFilterType, parsedPipeUnitFilterPattern);
            parsingPipe.add(pipeUnit);
            LOGGER.debug(message(LOG_PEXP_PARSE_STATE_TRANSITION, ParserState.PARSE_FILTER_TYPE));
            state = ParserState.PARSE_FILTER_TYPE;
            return;
        }
        LOGGER.debug(message(LOG_PEXP_PARSE_APPEND_CHARACTER));
        parsingPipeUnitFilterPatternBuilder.append(anchorChar);
    }

    private void parseEnd() {
        LOGGER.debug(message(LOG_PEXP_PARSE_STATE_PROCESSING, ParserState.END));
        LOGGER.debug(message(LOG_PEXP_PARSE_FINALIZING));
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
                ", parsedModifier=" + parsedModifier +
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

        @Serial
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

        @Serial
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

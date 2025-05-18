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

import java.util.Collections;
import java.util.Objects;

/**
 * 权限表达式 V1 解析引擎。
 *
 * <p>
 * 按照权限表达式 V1 规则解析权限表达式。<br>
 * 权限表达式 V1 规则如下可以参照 Wiki 中的相关文档。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class V1PexpParseEngine implements PexpParseEngine {

    /**
     * 修饰符：接受。
     */
    public static final String MODIFIER_STRING_ACCEPT = "+";

    /**
     * 修饰符：拒绝。
     */
    public static final String MODIFIER_STRING_REJECT = "-";

    /**
     * 修饰符：全局拒绝。
     */
    public static final String MODIFIER_STRING_GLOBAL_REJECT = "!";

    /**
     * 控制符：匹配模式分隔。
     */
    public static final String CONTROLLER_STRING_PATTERN_DELIMITER = "@";

    private static final Logger LOGGER = LoggerFactory.getLogger(V1PexpParseEngine.class);

    private final String pexpContent;

    public V1PexpParseEngine(String pexpContent) {
        this.pexpContent = pexpContent;
    }

    @BehaviorAnalyse
    @Override
    public ParseResult parse() throws Exception {
        // 定义解析结果相关字段。
        String modifier;
        String filterType;
        String filterPattern;

        // 使用匹配模式分隔符分割权限表达式。
        // split[0] 为修饰符 + 过滤器类型，split[1] 为过滤器参数。
        String[] split = pexpContent.split(CONTROLLER_STRING_PATTERN_DELIMITER, 2);
        // 在大多数情况下，权限表达式都是合法的（即 split[0].length() > 1），优先判断此条件，以提高性能。
        if (split[0].length() > 1) {
            modifier = split[0].substring(0, 1);
            filterType = split[0].substring(1);
        }
        // split[0] 为空字符串的处理逻辑。
        else if (split[0].isEmpty()) {
            modifier = StringUtils.EMPTY;
            filterType = StringUtils.EMPTY;
        }
        // split[0] 只有一个字符的处理逻辑。
        else {
            modifier = String.valueOf(pexpContent.charAt(0));
            filterType = StringUtils.EMPTY;
        }
        filterPattern = split[1];

        // 记录日志。
        LOGGER.debug(
                "解析结果相关字段解析完成, modifier: {}, filterType: {}, filterPattern: {}",
                modifier, filterType, filterPattern
        );

        // 判断相关信息的合法性。
        if (!Objects.equals(modifier, MODIFIER_STRING_ACCEPT) &&
                !Objects.equals(modifier, MODIFIER_STRING_REJECT) &&
                !Objects.equals(modifier, MODIFIER_STRING_GLOBAL_REJECT)) {
            throw new IllegalModifierException(modifier);
        }
        if (StringUtils.isEmpty(filterType)) {
            throw new EmptyFilterTypeException();
        }

        // 解析 pipeModifier。
        PipeModifier pipeModifier;
        switch (modifier) {
            case MODIFIER_STRING_ACCEPT:
                pipeModifier = PipeModifier.ACCEPT;
                break;
            case MODIFIER_STRING_REJECT:
                pipeModifier = PipeModifier.REJECT;
                break;
            case MODIFIER_STRING_GLOBAL_REJECT:
                pipeModifier = PipeModifier.GLOBAL_REJECT;
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }

        // 构造结果并返回。
        return new ParseResult(pipeModifier, Collections.singletonList(new PipeUnit(filterType, filterPattern)));
    }

    @Override
    public String toString() {
        return "V1PexpParseEngine{" +
                "pexpContent='" + pexpContent + '\'' +
                '}';
    }

    private static class IllegalModifierException extends Exception {

        private static final long serialVersionUID = 7941039818447762511L;

        private final String modifier;

        public IllegalModifierException(String modifier) {
            this.modifier = modifier;
        }

        @Override
        public String getMessage() {
            return String.format(
                    "权限表达式的修饰符只能为 %s, %s, %s, 而当前为 %s",
                    MODIFIER_STRING_ACCEPT, MODIFIER_STRING_REJECT, MODIFIER_STRING_GLOBAL_REJECT, modifier
            );
        }
    }

    private static class EmptyFilterTypeException extends Exception {

        private static final long serialVersionUID = -7787865833992151692L;

        @Override
        public String getMessage() {
            return "权限表达式的过滤器类型不能为空";
        }
    }
}

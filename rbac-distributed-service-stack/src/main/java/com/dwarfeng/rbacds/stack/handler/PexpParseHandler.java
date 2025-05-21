package com.dwarfeng.rbacds.stack.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;
import java.util.Objects;

/**
 * 权限表达式解析处理器。
 *
 * <p>
 * 该处理器线程安全。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public interface PexpParseHandler extends Handler {

    /**
     * 解析权限表达式。
     *
     * @param pexp 权限表达式。
     * @return 解析结果。
     * @throws HandlerException 处理器异常。
     */
    ParseResult parse(Pexp pexp) throws HandlerException;

    /**
     * 解析结果。
     *
     * @author DwArFeng
     * @since 1.8.0
     */
    final class ParseResult {

        private final Modifier modifier;
        private final List<PipeUnit> pipe;

        public ParseResult(Modifier modifier, List<PipeUnit> pipe) {
            this.modifier = modifier;
            this.pipe = pipe;
        }

        public Modifier getModifier() {
            return modifier;
        }

        public List<PipeUnit> getPipe() {
            return pipe;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;

            ParseResult that = (ParseResult) o;
            return modifier == that.modifier && Objects.equals(pipe, that.pipe);
        }

        @Override
        public int hashCode() {
            int result = Objects.hashCode(modifier);
            result = 31 * result + Objects.hashCode(pipe);
            return result;
        }

        @Override
        public String toString() {
            return "ParseResult{" +
                    "modifier=" + modifier +
                    ", pipe=" + pipe +
                    '}';
        }
    }

    /**
     * 管道修饰符。
     *
     * @author DwArFeng
     * @since 1.8.0
     */
    enum Modifier {

        /**
         * 修饰符：接受。
         */
        ACCEPT,

        /**
         * 修饰符：拒绝。
         */
        REJECT,

        /**
         * 修饰符：全局拒绝。
         */
        GLOBAL_REJECT,
    }

    /**
     * 管道单元。
     *
     * @author DwArFeng
     * @since 1.8.0
     */
    final class PipeUnit {

        /**
         * 管道单元过滤器类型。
         */
        private final String filterType;

        /**
         * 管道单元过滤器匹配模式。
         */
        private final String filterPattern;

        public PipeUnit(String filterType, String filterPattern) {
            this.filterType = filterType;
            this.filterPattern = filterPattern;
        }

        public String getFilterType() {
            return filterType;
        }

        public String getFilterPattern() {
            return filterPattern;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;

            PipeUnit pipeUnit = (PipeUnit) o;
            return Objects.equals(filterType, pipeUnit.filterType) &&
                    Objects.equals(filterPattern, pipeUnit.filterPattern);
        }

        @Override
        public int hashCode() {
            int result = Objects.hashCode(filterType);
            result = 31 * result + Objects.hashCode(filterPattern);
            return result;
        }

        @Override
        public String toString() {
            return "PipeUnit{" +
                    "filterType='" + filterType + '\'' +
                    ", filterPattern='" + filterPattern + '\'' +
                    '}';
        }
    }
}

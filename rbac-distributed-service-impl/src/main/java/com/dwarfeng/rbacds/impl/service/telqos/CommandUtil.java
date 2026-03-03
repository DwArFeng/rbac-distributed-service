package com.dwarfeng.rbacds.impl.service.telqos;

import com.dwarfeng.springtelqos.stack.command.Context;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.StringJoiner;

/**
 * 指令工具类。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
final class CommandUtil {

    /**
     * 拼接选项的前缀，用于生成选项说明书。
     *
     * <p>
     * online -> -online<br>
     * dump-file -> --dump-file。
     *
     * @param commandOption 指定的选项。
     * @return 拼接前缀之后的选项。
     */
    public static String concatOptionPrefix(@Nonnull String commandOption) {
        if (commandOption.contains("-")) {
            return "--" + commandOption;
        }
        return "-" + commandOption;
    }

    public static String syntax(@Nonnull String... patterns) {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        for (String pattern : patterns) {
            sj.add(pattern);
        }
        return sj.toString();
    }

    public static Pair<String, Integer> analyseCommand(
            @Nonnull CommandLine commandLine, @Nonnull String... commandOptions
    ) {
        int i = 0;
        String subCmd = null;
        for (String commandOption : commandOptions) {
            if (commandLine.hasOption(commandOption)) {
                i++;
                subCmd = commandOption;
            }
        }
        return Pair.of(subCmd, i);
    }

    public static String optionMismatchMessage(@Nonnull String... patterns) {
        StringJoiner sj = new StringJoiner(", ", "下列选项必须且只能含有一个: ", "");
        for (String pattern : patterns) {
            sj.add(concatOptionPrefix(pattern));
        }
        return sj.toString();
    }

    /**
     * 计算一个整数的位数。
     *
     * @param num 指定的整数。
     * @return 指定整数的位数。
     */
    public static int digits(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("num 不能为负数");
        }
        if (num == 0) {
            return 1;
        }
        int count = 0;
        while (num != 0) {
            num /= 10;
            count++;
        }
        return count;
    }

    /**
     * 分片数据输出。
     *
     * @param context       上下文。
     * @param originData    原始数据列表。
     * @param bannerMessage 横幅消息。
     * @param quitPrompt    退出提示。
     * @param <T>           数据类型。
     * @return 分片结果。
     * @throws Exception 处理过程中发生的异常。
     * @since 2.0.0
     */
    public static <T> CropResult cropData(
            @Nonnull Context context, @Nonnull List<T> originData, @Nonnull String bannerMessage,
            @Nonnull String quitPrompt
    ) throws Exception {
        int beginIndex;
        int endIndex;

        while (true) {
            context.sendMessage(bannerMessage);
            context.sendMessage("");
            context.sendMessage("输入 all 查看所有数据");
            context.sendMessage("输入 begin-end 查看指定范围的数据(开始于 0)");
            context.sendMessage(quitPrompt);
            context.sendMessage("");

            String message = context.receiveMessage();

            if (Strings.CI.equals(message, "q")) {
                return new CropResult(-1, -1, true);
            } else if (Strings.CI.equals(message, "all")) {
                beginIndex = 0;
                endIndex = originData.size();
            } else {
                String[] split = message.split("-");
                if (split.length != 2) {
                    context.sendMessage("输入格式错误");
                    context.sendMessage("");
                    continue;
                }
                try {
                    beginIndex = Integer.parseInt(split[0]);
                    endIndex = Integer.parseInt(split[1]);
                } catch (NumberFormatException e) {
                    context.sendMessage("输入格式错误");
                    context.sendMessage("");
                    continue;
                }
                if (beginIndex < 0 || endIndex > originData.size() || beginIndex >= endIndex) {
                    String errorMessage = "输入范围错误，begin 和 end 均应介于 [0, " + originData.size() + "] 之间，" +
                            "且 begin 应小于 end";
                    context.sendMessage(errorMessage);
                    context.sendMessage("");
                    continue;
                }
            }
            break;
        }

        return new CropResult(beginIndex, endIndex, false);
    }

    /**
     * 分片数据输出结果。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    static final class CropResult {

        private final int beginIndex;
        private final int endIndex;
        private final boolean exitFlag;

        public CropResult(int beginIndex, int endIndex, boolean exitFlag) {
            this.beginIndex = beginIndex;
            this.endIndex = endIndex;
            this.exitFlag = exitFlag;
        }

        public int getBeginIndex() {
            return beginIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public boolean isExitFlag() {
            return exitFlag;
        }

        @Override
        public String toString() {
            return "CropResult{" +
                    "beginIndex=" + beginIndex +
                    ", endIndex=" + endIndex +
                    ", exitFlag=" + exitFlag +
                    '}';
        }
    }

    private CommandUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}

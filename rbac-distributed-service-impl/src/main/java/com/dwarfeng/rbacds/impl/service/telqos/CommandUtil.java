package com.dwarfeng.rbacds.impl.service.telqos;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
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

    private CommandUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}

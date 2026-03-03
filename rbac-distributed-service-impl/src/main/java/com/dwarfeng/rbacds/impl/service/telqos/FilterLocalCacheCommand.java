package com.dwarfeng.rbacds.impl.service.telqos;

import com.dwarfeng.rbacds.stack.handler.Filter;
import com.dwarfeng.rbacds.stack.service.FilterQosService;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 过滤器本地缓存指令。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@TelqosCommand
public class FilterLocalCacheCommand extends CliCommand {

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_CLEAR = "c";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_CLEAR
    };

    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
    private static final String IDENTITY = "flc";
    private static final String DESCRIPTION = "过滤器本地缓存操作";
    private static final String CMD_LINE_SYNTAX_LOOKUP = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " filter-type";
    private static final String CMD_LINE_SYNTAX_CLEAR = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEAR);

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_LOOKUP,
            CMD_LINE_SYNTAX_CLEAR
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final FilterQosService filterQosService;

    public FilterLocalCacheCommand(FilterQosService filterQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.filterQosService = filterQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(
                Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(true).type(Number.class)
                        .argName("filter-type")
                        .desc("查看指定过滤类型的过滤器，如果本地缓存中不存在，则尝试抓取")
                        .build()
        );
        list.add(Option.builder(COMMAND_OPTION_CLEAR).optionalArg(true).hasArg(false).desc("清除缓存").build());
        return list;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = CommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
            if (pair.getRight() != 1) {
                context.sendMessage(CommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            switch (pair.getLeft()) {
                case COMMAND_OPTION_LOOKUP:
                    handleLookup(context, cmd);
                    break;
                case COMMAND_OPTION_CLEAR:
                    handleClear(context);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleLookup(Context context, CommandLine cmd) throws Exception {
        String filterType = cmd.getOptionValue(COMMAND_OPTION_LOOKUP);
        Filter filter = filterQosService.getFilter(filterType);
        if (Objects.isNull(filter)) {
            context.sendMessage("not exists!");
            return;
        }
        context.sendMessage(String.format("filter: %s", filter));
    }

    private void handleClear(Context context) throws Exception {
        filterQosService.clearLocalCache();
        context.sendMessage("缓存已清空");
    }
}

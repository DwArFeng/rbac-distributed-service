package com.dwarfeng.rbacds.impl.service.telqos;

import com.alibaba.fastjson2.JSON;
import com.dwarfeng.rbacds.impl.internal.i18n.ImplMessageKey;
import com.dwarfeng.rbacds.impl.internal.i18n.ImplMessages;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPexpCreateInfo;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPexpRemoveInfo;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPexpUpdateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PexpCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PexpCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PexpRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PexpUpdateInfo;
import com.dwarfeng.rbacds.stack.service.PexpOperateService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 权限表达式操作服务指令。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@TelqosCommand
public class PexpCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "pexp";

    // region 指令选项

    private static final String COMMAND_OPTION_CREATE = "create";
    private static final String COMMAND_OPTION_UPDATE = "update";
    private static final String COMMAND_OPTION_REMOVE = "remove";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_CREATE,
            COMMAND_OPTION_UPDATE,
            COMMAND_OPTION_REMOVE
    };

    private static final String COMMAND_SUB_OPTION_JSON = "json";
    private static final String COMMAND_SUB_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_SUB_OPTION_JSON_FILE_LONG_OPT = "json-file";

    // endregion

    private final PexpOperateService pexpOperateService;

    public PexpCommand(PexpOperateService pexpOperateService) {
        super(IDENTITY);
        this.pexpOperateService = pexpOperateService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return _ -> ImplMessages.message(ImplMessageKey.TELQOS_PEXP_DESCRIPTION);
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    @SuppressWarnings("DuplicatedCode")
    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_CREATE) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_UPDATE) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_REMOVE) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(
                Option.builder(COMMAND_OPTION_CREATE)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_PEXP_OPTION_CREATE))
                        .get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_UPDATE)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_PEXP_OPTION_UPDATE))
                        .get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_REMOVE)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_PEXP_OPTION_REMOVE))
                        .get()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_JSON)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_OPTION_JSON_STRING)).hasArg()
                        .type(String.class).get()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_JSON_FILE).longOpt(COMMAND_SUB_OPTION_JSON_FILE_LONG_OPT)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_OPTION_JSON_FILE))
                        .hasArg().type(File.class).get()
        );
        return list;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void executeWithCmd(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Pair<String, Integer> pair = CliCommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
        if (pair.getRight() != 1) {
            context.sendMessage(CliCommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
            return;
        }
        switch (pair.getLeft()) {
            case COMMAND_OPTION_CREATE:
                handleCreate(context, cmd);
                break;
            case COMMAND_OPTION_UPDATE:
                handleUpdate(context, cmd);
                break;
            case COMMAND_OPTION_REMOVE:
                handleRemove(context, cmd);
                break;
            default:
                throw new IllegalStateException(
                        ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_INTERNAL_UNREACHABLE)
                );
        }
    }

    private void handleCreate(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        PexpCreateInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PexpCreateInfo。
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            info = WebInputPexpCreateInfo.toStackBean(
                    JSON.parseObject(json, WebInputPexpCreateInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PexpCreateInfo。
        else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPexpCreateInfo.toStackBean(
                        JSON.parseObject(in, WebInputPexpCreateInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 创建权限表达式。
        PexpCreateResult result = pexpOperateService.create(info);

        // 输出结果。
        if (Objects.isNull(result)) {
            context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_CREATE_RESULT_NULL));
        } else {
            context.sendMessage(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_CREATE_SUCCEEDED, result.getKey())
            );
        }
    }

    private void handleUpdate(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        PexpUpdateInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PexpUpdateInfo。
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            info = WebInputPexpUpdateInfo.toStackBean(
                    JSON.parseObject(json, WebInputPexpUpdateInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PexpUpdateInfo。
        else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPexpUpdateInfo.toStackBean(
                        JSON.parseObject(in, WebInputPexpUpdateInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 更新权限表达式。
        pexpOperateService.update(info);

        // 输出结果。
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UPDATE_SUCCEEDED));
    }

    private void handleRemove(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        PexpRemoveInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PexpRemoveInfo。
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            info = WebInputPexpRemoveInfo.toStackBean(
                    JSON.parseObject(json, WebInputPexpRemoveInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PexpRemoveInfo。
        else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPexpRemoveInfo.toStackBean(
                        JSON.parseObject(in, WebInputPexpRemoveInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 移除权限表达式。
        pexpOperateService.remove(info);

        // 输出结果。
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_REMOVE_SUCCEEDED));
    }
}

package com.dwarfeng.rbacds.impl.service.telqos;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPermissionGroupCreateInfo;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPermissionGroupRemoveInfo;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPermissionGroupUpdateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupUpdateInfo;
import com.dwarfeng.rbacds.stack.service.PermissionGroupOperateService;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 权限组操作服务指令。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@TelqosCommand
public class PermissionGroupCommand extends CliCommand {

    private static final String COMMAND_OPTION_CREATE = "create";
    private static final String COMMAND_OPTION_UPDATE = "update";
    private static final String COMMAND_OPTION_REMOVE = "remove";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_CREATE,
            COMMAND_OPTION_UPDATE,
            COMMAND_OPTION_REMOVE
    };

    private static final String COMMAND_OPTION_JSON = "json";
    private static final String COMMAND_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_OPTION_JSON_FILE_LONG_OPT = "json-file";

    private static final String IDENTITY = "pg";
    private static final String DESCRIPTION = "权限组操作服务";

    private static final String CMD_LINE_SYNTAX_CREATE = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_CREATE) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
    private static final String CMD_LINE_SYNTAX_UPDATE = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_UPDATE) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
    private static final String CMD_LINE_SYNTAX_REMOVE = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_REMOVE) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_CREATE,
            CMD_LINE_SYNTAX_UPDATE,
            CMD_LINE_SYNTAX_REMOVE
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final PermissionGroupOperateService permissionGroupOperateService;

    public PermissionGroupCommand(PermissionGroupOperateService permissionGroupOperateService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.permissionGroupOperateService = permissionGroupOperateService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_CREATE).desc("创建权限组").build());
        list.add(Option.builder(COMMAND_OPTION_UPDATE).desc("更新权限组").build());
        list.add(Option.builder(COMMAND_OPTION_REMOVE).desc("移除权限组").build());
        list.add(
                Option.builder(COMMAND_OPTION_JSON).desc("JSON 字符串").hasArg().type(String.class).build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_JSON_FILE).longOpt(COMMAND_OPTION_JSON_FILE_LONG_OPT).desc("JSON 文件")
                        .hasArg().type(File.class).build()
        );
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
                case COMMAND_OPTION_CREATE:
                    handleCreate(context, cmd);
                    break;
                case COMMAND_OPTION_UPDATE:
                    handleUpdate(context, cmd);
                    break;
                case COMMAND_OPTION_REMOVE:
                    handleRemove(context, cmd);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleCreate(Context context, CommandLine cmd) throws Exception {
        PermissionGroupCreateInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PermissionGroupCreateInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputPermissionGroupCreateInfo.toStackBean(
                    JSON.parseObject(json, WebInputPermissionGroupCreateInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PermissionGroupCreateInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPermissionGroupCreateInfo.toStackBean(
                        JSON.parseObject(in, WebInputPermissionGroupCreateInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 创建权限组。
        PermissionGroupCreateResult result = permissionGroupOperateService.create(info);

        // 输出结果。
        if (Objects.isNull(result)) {
            context.sendMessage("创建结果: null");
        } else {
            context.sendMessage("创建成功, key: " + result.getKey());
        }
    }

    private void handleUpdate(Context context, CommandLine cmd) throws Exception {
        PermissionGroupUpdateInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PermissionGroupUpdateInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputPermissionGroupUpdateInfo.toStackBean(
                    JSON.parseObject(json, WebInputPermissionGroupUpdateInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PermissionGroupUpdateInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPermissionGroupUpdateInfo.toStackBean(
                        JSON.parseObject(in, WebInputPermissionGroupUpdateInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 更新权限组。
        permissionGroupOperateService.update(info);

        // 输出结果。
        context.sendMessage("更新成功");
    }

    private void handleRemove(Context context, CommandLine cmd) throws Exception {
        PermissionGroupRemoveInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PermissionGroupRemoveInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputPermissionGroupRemoveInfo.toStackBean(
                    JSON.parseObject(json, WebInputPermissionGroupRemoveInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PermissionGroupRemoveInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPermissionGroupRemoveInfo.toStackBean(
                        JSON.parseObject(in, WebInputPermissionGroupRemoveInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 移除权限组。
        permissionGroupOperateService.remove(info);

        // 输出结果。
        context.sendMessage("移除成功");
    }
}

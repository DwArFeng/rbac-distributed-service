package com.dwarfeng.rbacds.impl.service.telqos;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPermissionCreateInfo;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPermissionRemoveInfo;
import com.dwarfeng.rbacds.sdk.bean.dto.WebInputPermissionUpdateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionUpdateInfo;
import com.dwarfeng.rbacds.stack.service.PermissionOperateService;
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
 * 权限操作服务指令。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@TelqosCommand
public class PermissionCommand extends CliCommand {

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

    private static final String IDENTITY = "pm";
    private static final String DESCRIPTION = "权限操作服务";

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

    private final PermissionOperateService permissionOperateService;

    public PermissionCommand(PermissionOperateService permissionOperateService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.permissionOperateService = permissionOperateService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_CREATE).desc("创建权限").build());
        list.add(Option.builder(COMMAND_OPTION_UPDATE).desc("更新权限").build());
        list.add(Option.builder(COMMAND_OPTION_REMOVE).desc("移除权限").build());
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
        PermissionCreateInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PermissionCreateInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputPermissionCreateInfo.toStackBean(
                    JSON.parseObject(json, WebInputPermissionCreateInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PermissionCreateInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPermissionCreateInfo.toStackBean(
                        JSON.parseObject(in, WebInputPermissionCreateInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 创建权限。
        PermissionCreateResult result = permissionOperateService.create(info);

        // 输出结果。
        if (Objects.isNull(result)) {
            context.sendMessage("创建结果: null");
        } else {
            context.sendMessage("创建成功, key: " + result.getKey());
        }
    }

    private void handleUpdate(Context context, CommandLine cmd) throws Exception {
        PermissionUpdateInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PermissionUpdateInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputPermissionUpdateInfo.toStackBean(
                    JSON.parseObject(json, WebInputPermissionUpdateInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PermissionUpdateInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPermissionUpdateInfo.toStackBean(
                        JSON.parseObject(in, WebInputPermissionUpdateInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 更新权限。
        permissionOperateService.update(info);

        // 输出结果。
        context.sendMessage("更新成功");
    }

    private void handleRemove(Context context, CommandLine cmd) throws Exception {
        PermissionRemoveInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PermissionRemoveInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputPermissionRemoveInfo.toStackBean(
                    JSON.parseObject(json, WebInputPermissionRemoveInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PermissionRemoveInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPermissionRemoveInfo.toStackBean(
                        JSON.parseObject(in, WebInputPermissionRemoveInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 移除权限。
        permissionOperateService.remove(info);

        // 输出结果。
        context.sendMessage("移除成功");
    }
}

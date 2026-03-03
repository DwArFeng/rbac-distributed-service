package com.dwarfeng.rbacds.impl.service.telqos;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.service.ScopePermissionAnalysisLocalCacheQosService;
import com.dwarfeng.rbacds.stack.struct.ScopePermissionAnalysis;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 作用域权限分析本地缓存指令。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@TelqosCommand
public class ScopePermissionAnalysisLocalCacheCommand extends CliCommand {

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_CLEAR = "c";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_CLEAR
    };

    private static final String COMMAND_OPTION_SCOPE = "ls";

    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
    private static final String IDENTITY = "spalc";
    private static final String DESCRIPTION = "作用域权限分析本地缓存操作";
    private static final String CMD_LINE_SYNTAX_LOOKUP = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_SCOPE) + " scopeId";
    private static final String CMD_LINE_SYNTAX_CLEAR = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEAR);

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_LOOKUP,
            CMD_LINE_SYNTAX_CLEAR
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final ScopePermissionAnalysisLocalCacheQosService qosService;

    public ScopePermissionAnalysisLocalCacheCommand(ScopePermissionAnalysisLocalCacheQosService qosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.qosService = qosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(false)
                .desc("查看指定作用域键的作用域权限分析，如果本地缓存中不存在，则尝试抓取").build());
        list.add(Option.builder(COMMAND_OPTION_CLEAR).optionalArg(true).hasArg(false).desc("清除缓存").build());
        list.add(
                Option.builder(COMMAND_OPTION_SCOPE).optionalArg(true).hasArg(true)
                        .argName("scopeId").desc("作用域 ID").build()
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
        String scopeId = cmd.getOptionValue(COMMAND_OPTION_SCOPE);
        if (Objects.isNull(scopeId) || scopeId.isEmpty()) {
            context.sendMessage("lookup 操作需要指定 " + CommandUtil.concatOptionPrefix(COMMAND_OPTION_SCOPE) + " 选项");
            return;
        }
        StringIdKey key = new StringIdKey(scopeId);
        ScopePermissionAnalysis analysis = qosService.get(key);
        if (Objects.isNull(analysis)) {
            context.sendMessage("not exists!");
            return;
        }
        interactScopePermissionAnalysis(context, analysis);
    }

    private void handleClear(Context context) throws Exception {
        qosService.clearLocalCache();
        context.sendMessage("缓存已清空");
    }

    @SuppressWarnings("DuplicatedCode")
    private void printPermission(int i, int endIndex, Permission permission, Context context) throws Exception {
        context.sendMessage(String.format("索引: %d/%d", i, endIndex));
        printPermissionKey(context, permission.getKey());
        printPermissionGroupKey(context, permission.getGroupKey());
        context.sendMessage("  name: " + permission.getName());
        context.sendMessage("  level: " + permission.getLevel());
        context.sendMessage(
                "  groupPath: " + (Objects.nonNull(permission.getGroupPath()) ?
                        Arrays.toString(permission.getGroupPath()) : "null")
        );
        context.sendMessage("");
    }

    private void printPermissionKey(Context context, PermissionKey key) throws Exception {
        context.sendMessage("  key:");
        if (Objects.isNull(key)) {
            context.sendMessage("    null");
            return;
        }
        context.sendMessage("    scopeStringId: " + key.getScopeStringId());
        context.sendMessage("    permissionStringId: " + key.getPermissionStringId());
    }

    private void printPermissionGroupKey(Context context, PermissionGroupKey key) throws Exception {
        context.sendMessage("  group:");
        if (Objects.isNull(key)) {
            context.sendMessage("    null");
            return;
        }
        context.sendMessage("    scopeStringId: " + key.getScopeStringId());
        context.sendMessage("    permissionGroupStringId: " + key.getPermissionGroupStringId());
    }

    @SuppressWarnings("DuplicatedCode")
    private void interactScopePermissionAnalysis(Context context, ScopePermissionAnalysis analysis) throws Exception {
        sendScopePermissionMenu(context, analysis);

        while (true) {
            String message = context.receiveMessage();

            if (Strings.CI.equals(message, "q")) {
                break;
            } else if (Strings.CI.equals(message, "p")) {
                List<Permission> list = analysis.getPermissions();
                if (Objects.isNull(list)) {
                    context.sendMessage("该列表未设置，无法查看");
                    context.sendMessage("");
                    continue;
                }
                while (true) {
                    CommandUtil.CropResult cropResult = CommandUtil.cropData(
                            context, list, "数据总数: " + list.size(), "输入 q 返回至主菜单"
                    );
                    if (cropResult.isExitFlag()) break;
                    context.sendMessage("");
                    for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                        printPermission(i, cropResult.getEndIndex(), list.get(i), context);
                    }
                }
                sendScopePermissionMenu(context, analysis);
            } else {
                context.sendMessage("输入格式错误");
                context.sendMessage("");
            }
        }
    }

    private void sendScopePermissionMenu(Context context, ScopePermissionAnalysis analysis)
            throws Exception {
        context.sendMessage("");
        context.sendMessage("permissions: " + formatListCount(analysis.getPermissions()));
        context.sendMessage("");
        context.sendMessage("输入 p 查看 permissions");
        context.sendMessage("输入 q 退出查询");
        context.sendMessage("");
    }

    private String formatListCount(List<?> list) {
        if (Objects.isNull(list)) return "null";
        if (list.isEmpty()) return "0 (空)";
        return String.valueOf(list.size());
    }
}

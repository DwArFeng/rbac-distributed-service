package com.dwarfeng.rbacds.impl.service.telqos;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.service.PermissionUserAnalysisLocalCacheQosService;
import com.dwarfeng.rbacds.stack.struct.PermissionUserAnalysis;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 权限用户分析本地缓存指令。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@TelqosCommand
public class PermissionUserAnalysisLocalCacheCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "pualc";

    // region 指令选项

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_CLEAR = "c";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_CLEAR
    };

    private static final String COMMAND_SUB_OPTION_SCOPE = "ls";
    private static final String COMMAND_SUB_OPTION_PERMISSION = "lp";

    // endregion

    private final PermissionUserAnalysisLocalCacheQosService qosService;

    public PermissionUserAnalysisLocalCacheCommand(PermissionUserAnalysisLocalCacheQosService qosService) {
        super(IDENTITY);
        this.qosService = qosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "权限用户分析本地缓存操作";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " " +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_SCOPE) + " scopeId " +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_PERMISSION) + " permissionId",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEAR)
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(false)
                .desc("查看指定权限键的权限用户分析，如果本地缓存中不存在，则尝试抓取").build());
        list.add(Option.builder(COMMAND_OPTION_CLEAR).optionalArg(true).hasArg(false).desc("清除缓存").build());
        list.add(
                Option.builder(COMMAND_SUB_OPTION_SCOPE).optionalArg(true).hasArg(true)
                        .argName("scopeId").desc("作用域 ID").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_PERMISSION).optionalArg(true).hasArg(true)
                        .argName("permissionId").desc("权限 ID").build()
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
            case COMMAND_OPTION_LOOKUP:
                handleLookup(context, cmd);
                break;
            case COMMAND_OPTION_CLEAR:
                handleClear(context);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleLookup(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        String scopeId = cmd.getOptionValue(COMMAND_SUB_OPTION_SCOPE);
        String permissionId = cmd.getOptionValue(COMMAND_SUB_OPTION_PERMISSION);
        if (
                Objects.isNull(scopeId) || scopeId.isEmpty() ||
                        Objects.isNull(permissionId) || permissionId.isEmpty()
        ) {
            context.sendMessage(
                    "lookup 操作需要指定 " + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_SCOPE) +
                            " 和 " + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_PERMISSION) + " 选项"
            );
            return;
        }
        PermissionKey key = new PermissionKey(scopeId, permissionId);
        PermissionUserAnalysis analysis = qosService.get(key);
        if (Objects.isNull(analysis)) {
            context.sendMessage("not exists!");
            return;
        }
        interactPermissionUserAnalysis(context, analysis);
    }

    private void handleClear(CommandExecutor.Context context) throws Exception {
        qosService.clearLocalCache();
        context.sendMessage("缓存已清空");
    }

    private void printUser(int i, int endIndex, User user, CommandExecutor.Context context) throws Exception {
        context.sendMessage(String.format("索引: %d/%d", i, endIndex));
        printStringIdKey(context, user.getKey());
        context.sendMessage("  remark: " + user.getRemark());
        context.sendMessage("");
    }

    @SuppressWarnings("DuplicatedCode")
    private void printRole(int i, int endIndex, Role role, CommandExecutor.Context context) throws Exception {
        context.sendMessage(String.format("索引: %d/%d", i, endIndex));
        printStringIdKey(context, role.getKey());
        context.sendMessage("  name: " + role.getName());
        context.sendMessage("  enabled: " + role.isEnabled());
        context.sendMessage("  remark: " + role.getRemark());
        context.sendMessage("");
    }

    private void printStringIdKey(CommandExecutor.Context context, StringIdKey key) throws Exception {
        context.sendMessage("  key:");
        if (Objects.isNull(key)) {
            context.sendMessage("    null");
            return;
        }
        context.sendMessage("    stringId: " + key.getStringId());
    }

    @SuppressWarnings("DuplicatedCode")
    private void interactPermissionUserAnalysis(CommandExecutor.Context context, PermissionUserAnalysis analysis)
            throws Exception {
        sendPermissionUserMenu(context, analysis);

        while (true) {
            String message = context.receiveMessage();

            if (Strings.CI.equals(message, "q")) {
                break;
            } else if (Strings.CI.equals(message, "u")) {
                List<User> list = analysis.getMatchedUsers();
                if (Objects.isNull(list)) {
                    context.sendMessage("该列表未设置，无法查看");
                    context.sendMessage("");
                    continue;
                }
                while (true) {
                    CliCommandUtil.CropResult cropResult = CliCommandUtil.cropData(
                            context, list, "数据总数: " + list.size(), command -> "输入 " + command + " 返回至主菜单"
                    );
                    if (cropResult.isExitFlag()) break;
                    context.sendMessage("");
                    for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                        printUser(i, cropResult.getEndIndex(), list.get(i), context);
                    }
                }
                sendPermissionUserMenu(context, analysis);
            } else if (Strings.CI.equals(message, "a")) {
                List<Role> list = analysis.getAcceptedRoles();
                if (Objects.isNull(list)) {
                    context.sendMessage("该列表未设置，无法查看");
                    context.sendMessage("");
                    continue;
                }
                while (true) {
                    CliCommandUtil.CropResult cropResult = CliCommandUtil.cropData(
                            context, list, "数据总数: " + list.size(), command -> "输入 " + command + " 返回至主菜单"
                    );
                    if (cropResult.isExitFlag()) break;
                    context.sendMessage("");
                    for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                        printRole(i, cropResult.getEndIndex(), list.get(i), context);
                    }
                }
                sendPermissionUserMenu(context, analysis);
            } else if (Strings.CI.equals(message, "r")) {
                List<Role> list = analysis.getRejectedRoles();
                if (Objects.isNull(list)) {
                    context.sendMessage("该列表未设置，无法查看");
                    context.sendMessage("");
                    continue;
                }
                while (true) {
                    CliCommandUtil.CropResult cropResult = CliCommandUtil.cropData(
                            context, list, "数据总数: " + list.size(), command -> "输入 " + command + " 返回至主菜单"
                    );
                    if (cropResult.isExitFlag()) break;
                    context.sendMessage("");
                    for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                        printRole(i, cropResult.getEndIndex(), list.get(i), context);
                    }
                }
                sendPermissionUserMenu(context, analysis);
            } else {
                context.sendMessage("输入格式错误");
                context.sendMessage("");
            }
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void sendPermissionUserMenu(CommandExecutor.Context context, PermissionUserAnalysis analysis)
            throws Exception {
        context.sendMessage("");
        context.sendMessage("matchedUsers: " + formatListCount(analysis.getMatchedUsers()));
        context.sendMessage("acceptedRoles: " + formatListCount(analysis.getAcceptedRoles()));
        context.sendMessage("rejectedRoles: " + formatListCount(analysis.getRejectedRoles()));
        context.sendMessage("");
        context.sendMessage("输入 u 查看 matchedUsers");
        context.sendMessage("输入 a 查看 acceptedRoles");
        context.sendMessage("输入 r 查看 rejectedRoles");
        context.sendMessage("输入 q 退出查询");
        context.sendMessage("");
    }

    private String formatListCount(List<?> list) {
        if (Objects.isNull(list)) return "null";
        if (list.isEmpty()) return "0 (空)";
        return String.valueOf(list.size());
    }
}

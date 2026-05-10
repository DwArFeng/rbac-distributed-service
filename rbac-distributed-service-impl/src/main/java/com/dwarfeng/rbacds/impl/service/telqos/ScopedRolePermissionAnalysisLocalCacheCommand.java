package com.dwarfeng.rbacds.impl.service.telqos;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.bean.key.ScopedRoleKey;
import com.dwarfeng.rbacds.stack.service.ScopedRolePermissionAnalysisLocalCacheQosService;
import com.dwarfeng.rbacds.stack.struct.ScopedRolePermissionAnalysis;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * 作用域角色权限分析本地缓存指令。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@TelqosCommand
public class ScopedRolePermissionAnalysisLocalCacheCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "srpalc";

    // region 指令选项

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_CLEAR = "c";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_CLEAR
    };

    private static final String COMMAND_SUB_OPTION_SCOPE = "ls";
    private static final String COMMAND_SUB_OPTION_ROLE = "lr";

    // endregion

    private final ScopedRolePermissionAnalysisLocalCacheQosService qosService;

    public ScopedRolePermissionAnalysisLocalCacheCommand(ScopedRolePermissionAnalysisLocalCacheQosService qosService) {
        super(IDENTITY);
        this.qosService = qosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "作用域角色权限分析本地缓存操作";
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
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_ROLE) + " roleId",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEAR)
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(false)
                .desc("查看指定作用域角色键的作用域角色权限分析，如果本地缓存中不存在，则尝试抓取").build());
        list.add(Option.builder(COMMAND_OPTION_CLEAR).optionalArg(true).hasArg(false).desc("清除缓存").build());
        list.add(
                Option.builder(COMMAND_SUB_OPTION_SCOPE).optionalArg(true).hasArg(true)
                        .argName("scopeId").desc("作用域 ID").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_ROLE).optionalArg(true).hasArg(true)
                        .argName("roleId").desc("角色 ID").build()
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
        String roleId = cmd.getOptionValue(COMMAND_SUB_OPTION_ROLE);
        if (Objects.isNull(scopeId) || scopeId.isEmpty() || Objects.isNull(roleId) || roleId.isEmpty()) {
            context.sendMessage(
                    "lookup 操作需要指定 " + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_SCOPE) +
                            " 和 " + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_ROLE) + " 选项"
            );
            return;
        }
        ScopedRoleKey key = new ScopedRoleKey(scopeId, roleId);
        ScopedRolePermissionAnalysis analysis = qosService.get(key);
        if (Objects.isNull(analysis)) {
            context.sendMessage("not exists!");
            return;
        }
        interactScopedRolePermissionAnalysis(context, analysis);
    }

    private void handleClear(CommandExecutor.Context context) throws Exception {
        qosService.clearLocalCache();
        context.sendMessage("缓存已清空");
    }

    @SuppressWarnings("DuplicatedCode")
    private void printPermission(int i, int endIndex, Permission permission, CommandExecutor.Context context)
            throws Exception {
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

    private void printPermissionKey(CommandExecutor.Context context, PermissionKey key) throws Exception {
        context.sendMessage("  key:");
        if (Objects.isNull(key)) {
            context.sendMessage("    null");
            return;
        }
        context.sendMessage("    scopeStringId: " + key.getScopeStringId());
        context.sendMessage("    permissionStringId: " + key.getPermissionStringId());
    }

    private void printPermissionGroupKey(CommandExecutor.Context context, PermissionGroupKey key) throws Exception {
        context.sendMessage("  group:");
        if (Objects.isNull(key)) {
            context.sendMessage("    null");
            return;
        }
        context.sendMessage("    scopeStringId: " + key.getScopeStringId());
        context.sendMessage("    permissionGroupStringId: " + key.getPermissionGroupStringId());
    }

    private static List<Permission> toList(Map<?, Permission> map) {
        if (Objects.isNull(map)) return null;
        return new ArrayList<>(map.values());
    }

    @SuppressWarnings("DuplicatedCode")
    private void interactScopedRolePermissionAnalysis(CommandExecutor.Context context,
                                                      ScopedRolePermissionAnalysis analysis)
            throws Exception {
        sendScopedRolePermissionMenu(context, analysis);

        while (true) {
            String message = context.receiveMessage();

            if (Strings.CI.equals(message, "q")) {
                break;
            } else if (Strings.CI.equals(message, "m")) {
                List<Permission> list = analysis.getMatchedPermissions();
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
                        printPermission(i, cropResult.getEndIndex(), list.get(i), context);
                    }
                }
                sendScopedRolePermissionMenu(context, analysis);
            } else if (Strings.CI.equals(message, "a")) {
                List<Permission> list = toList(analysis.getAcceptedPermissionMap());
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
                        printPermission(i, cropResult.getEndIndex(), list.get(i), context);
                    }
                }
                sendScopedRolePermissionMenu(context, analysis);
            } else if (Strings.CI.equals(message, "r")) {
                List<Permission> list = toList(analysis.getRejectedPermissionMap());
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
                        printPermission(i, cropResult.getEndIndex(), list.get(i), context);
                    }
                }
                sendScopedRolePermissionMenu(context, analysis);
            } else if (Strings.CI.equals(message, "g")) {
                List<Permission> list = toList(analysis.getGlobalRejectedPermissionMap());
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
                        printPermission(i, cropResult.getEndIndex(), list.get(i), context);
                    }
                }
                sendScopedRolePermissionMenu(context, analysis);
            } else {
                context.sendMessage("输入格式错误");
                context.sendMessage("");
            }
        }
    }

    private void sendScopedRolePermissionMenu(CommandExecutor.Context context, ScopedRolePermissionAnalysis analysis)
            throws Exception {
        context.sendMessage("");
        context.sendMessage("matchedPermissions: " + formatListCount(analysis.getMatchedPermissions()));
        context.sendMessage("acceptedPermissions: " + formatMapCount(analysis.getAcceptedPermissionMap()));
        context.sendMessage("rejectedPermissions: " + formatMapCount(analysis.getRejectedPermissionMap()));
        context.sendMessage("globalRejectedPermissions: " + formatMapCount(analysis.getGlobalRejectedPermissionMap()));
        context.sendMessage("");
        context.sendMessage("输入 m 查看 matchedPermissions");
        context.sendMessage("输入 a 查看 acceptedPermissions");
        context.sendMessage("输入 r 查看 rejectedPermissions");
        context.sendMessage("输入 g 查看 globalRejectedPermissions");
        context.sendMessage("输入 q 退出查询");
        context.sendMessage("");
    }

    private String formatListCount(List<?> list) {
        if (Objects.isNull(list)) return "null";
        if (list.isEmpty()) return "0 (空)";
        return String.valueOf(list.size());
    }

    private String formatMapCount(Map<?, ?> map) {
        if (Objects.isNull(map)) return "null";
        if (map.isEmpty()) return "0 (空)";
        return String.valueOf(map.size());
    }
}

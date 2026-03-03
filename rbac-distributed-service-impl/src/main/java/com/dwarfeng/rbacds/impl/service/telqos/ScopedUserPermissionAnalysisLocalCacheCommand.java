package com.dwarfeng.rbacds.impl.service.telqos;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.bean.key.ScopedUserKey;
import com.dwarfeng.rbacds.stack.service.ScopedUserPermissionAnalysisLocalCacheQosService;
import com.dwarfeng.rbacds.stack.struct.ScopedUserPermissionAnalysis;
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
 * 作用域用户权限分析本地缓存指令。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@TelqosCommand
public class ScopedUserPermissionAnalysisLocalCacheCommand extends CliCommand {

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_CLEAR = "c";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_CLEAR
    };

    private static final String COMMAND_OPTION_SCOPE = "ls";
    private static final String COMMAND_OPTION_USER = "lu";

    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
    private static final String IDENTITY = "supalc";
    private static final String DESCRIPTION = "作用域用户权限分析本地缓存操作";
    private static final String CMD_LINE_SYNTAX_LOOKUP = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_SCOPE) + " scopeId " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_USER) + " userId";
    private static final String CMD_LINE_SYNTAX_CLEAR = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEAR);

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_LOOKUP,
            CMD_LINE_SYNTAX_CLEAR
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final ScopedUserPermissionAnalysisLocalCacheQosService qosService;

    public ScopedUserPermissionAnalysisLocalCacheCommand(ScopedUserPermissionAnalysisLocalCacheQosService qosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.qosService = qosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(false)
                .desc("查看指定作用域用户键的作用域用户权限分析，如果本地缓存中不存在，则尝试抓取").build());
        list.add(Option.builder(COMMAND_OPTION_CLEAR).optionalArg(true).hasArg(false).desc("清除缓存").build());
        list.add(
                Option.builder(COMMAND_OPTION_SCOPE).optionalArg(true).hasArg(true)
                        .argName("scopeId").desc("作用域 ID").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_USER).optionalArg(true).hasArg(true)
                        .argName("userId").desc("用户 ID").build()
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

    @SuppressWarnings("DuplicatedCode")
    private void handleLookup(Context context, CommandLine cmd) throws Exception {
        String scopeId = cmd.getOptionValue(COMMAND_OPTION_SCOPE);
        String userId = cmd.getOptionValue(COMMAND_OPTION_USER);
        if (Objects.isNull(scopeId) || scopeId.isEmpty() || Objects.isNull(userId) || userId.isEmpty()) {
            context.sendMessage(
                    "lookup 操作需要指定 " + CommandUtil.concatOptionPrefix(COMMAND_OPTION_SCOPE) +
                            " 和 " + CommandUtil.concatOptionPrefix(COMMAND_OPTION_USER) + " 选项"
            );
            return;
        }
        ScopedUserKey key = new ScopedUserKey(scopeId, userId);
        ScopedUserPermissionAnalysis analysis = qosService.get(key);
        if (Objects.isNull(analysis)) {
            context.sendMessage("not exists!");
            return;
        }
        interactScopedUserPermissionAnalysis(context, analysis);
    }

    private void handleClear(Context context) throws Exception {
        qosService.clearLocalCache();
        context.sendMessage("缓存已清空");
    }

    @SuppressWarnings("DuplicatedCode")
    private void interactScopedUserPermissionAnalysis(Context context, ScopedUserPermissionAnalysis analysis)
            throws Exception {
        sendScopedUserPermissionMenu(context, analysis);

        while (true) {
            String message = context.receiveMessage();

            if (Strings.CI.equals(message, "q")) {
                break;
            } else if (Strings.CI.equals(message, "p")) {
                List<Permission> list = analysis.getMatchedPermissions();
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
                sendScopedUserPermissionMenu(context, analysis);
            } else if (Strings.CI.equals(message, "r")) {
                interactRoleDetails(context, analysis.getRoleDetails());
                sendScopedUserPermissionMenu(context, analysis);
            } else {
                context.sendMessage("输入格式错误");
                context.sendMessage("");
            }
        }
    }

    private void sendScopedUserPermissionMenu(Context context, ScopedUserPermissionAnalysis analysis)
            throws Exception {
        context.sendMessage("");
        context.sendMessage("matchedPermissions: " + formatListCount(analysis.getMatchedPermissions()));
        context.sendMessage("roleDetails: " + formatListCount(analysis.getRoleDetails()));
        context.sendMessage("");
        context.sendMessage("输入 p 查看 matchedPermissions");
        context.sendMessage("输入 r 查看 roleDetails");
        context.sendMessage("输入 q 退出查询");
        context.sendMessage("");
    }

    @SuppressWarnings("DuplicatedCode")
    private void interactRoleDetails(Context context, List<ScopedUserPermissionAnalysis.RoleDetail> roleDetails)
            throws Exception {
        if (Objects.isNull(roleDetails)) {
            context.sendMessage("该列表未设置，无法查看");
            return;
        }
        while (true) {
            context.sendMessage("角色详情总数: " + roleDetails.size());
            context.sendMessage("");
            context.sendMessage("输入索引查看角色详情");
            context.sendMessage("输入 q 返回至主菜单");
            context.sendMessage("");

            String message = context.receiveMessage();

            if (Strings.CI.equals(message, "q")) {
                break;
            }
            int index;
            try {
                index = Integer.parseInt(message);
            } catch (NumberFormatException e) {
                context.sendMessage("输入格式错误");
                context.sendMessage("");
                continue;
            }
            if (index < 0 || index >= roleDetails.size()) {
                context.sendMessage("输入范围错误");
                context.sendMessage("");
                continue;
            }

            ScopedUserPermissionAnalysis.RoleDetail roleDetail = roleDetails.get(index);
            sendScopedRoleDetailMenu(context, roleDetail);

            while (true) {
                String subMessage = context.receiveMessage();

                if (Strings.CI.equals(subMessage, "q")) {
                    break;
                } else if (Strings.CI.equals(subMessage, "a")) {
                    List<Permission> list = roleDetail.getAcceptedPermissions();
                    if (Objects.isNull(list)) {
                        context.sendMessage("该列表未设置，无法查看");
                        context.sendMessage("");
                        continue;
                    }
                    while (true) {
                        CommandUtil.CropResult cropResult = CommandUtil.cropData(
                                context, list, "数据总数: " + list.size(), "输入 q 返回至角色详情"
                        );
                        if (cropResult.isExitFlag()) break;
                        context.sendMessage("");
                        for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                            printPermission(i, cropResult.getEndIndex(), list.get(i), context);
                        }
                    }
                    sendScopedRoleDetailMenu(context, roleDetail);
                } else if (Strings.CI.equals(subMessage, "r")) {
                    List<Permission> list = roleDetail.getRejectedPermissions();
                    if (Objects.isNull(list)) {
                        context.sendMessage("该列表未设置，无法查看");
                        context.sendMessage("");
                        continue;
                    }
                    while (true) {
                        CommandUtil.CropResult cropResult = CommandUtil.cropData(
                                context, list, "数据总数: " + list.size(), "输入 q 返回至角色详情"
                        );
                        if (cropResult.isExitFlag()) break;
                        context.sendMessage("");
                        for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                            printPermission(i, cropResult.getEndIndex(), list.get(i), context);
                        }
                    }
                    sendScopedRoleDetailMenu(context, roleDetail);
                } else if (Strings.CI.equals(subMessage, "g")) {
                    List<Permission> list = roleDetail.getGlobalRejectedPermissions();
                    if (Objects.isNull(list)) {
                        context.sendMessage("该列表未设置，无法查看");
                        context.sendMessage("");
                        continue;
                    }
                    while (true) {
                        CommandUtil.CropResult cropResult = CommandUtil.cropData(
                                context, list, "数据总数: " + list.size(), "输入 q 返回至角色详情"
                        );
                        if (cropResult.isExitFlag()) break;
                        context.sendMessage("");
                        for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                            printPermission(i, cropResult.getEndIndex(), list.get(i), context);
                        }
                    }
                    sendScopedRoleDetailMenu(context, roleDetail);
                } else {
                    context.sendMessage("输入格式错误");
                    context.sendMessage("");
                }
            }
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void sendScopedRoleDetailMenu(Context context, ScopedUserPermissionAnalysis.RoleDetail roleDetail)
            throws Exception {
        context.sendMessage("");
        context.sendMessage("角色信息:");
        printRole(roleDetail.getRole(), context);
        context.sendMessage("  acceptedPermissions: " + formatListCount(roleDetail.getAcceptedPermissions()));
        context.sendMessage("  rejectedPermissions: " + formatListCount(roleDetail.getRejectedPermissions()));
        context.sendMessage(
                "  globalRejectedPermissions: " + formatListCount(roleDetail.getGlobalRejectedPermissions())
        );
        context.sendMessage("");
        context.sendMessage("输入 a 查看 acceptedPermissions");
        context.sendMessage("输入 r 查看 rejectedPermissions");
        context.sendMessage("输入 g 查看 globalRejectedPermissions");
        context.sendMessage("输入 q 返回至角色选择");
        context.sendMessage("");
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

    private void printRole(Role role, Context context) throws Exception {
        printStringIdKey(context, role.getKey());
        context.sendMessage("  name: " + role.getName());
        context.sendMessage("  enabled: " + role.isEnabled());
        context.sendMessage("  remark: " + role.getRemark());
    }

    private void printStringIdKey(Context context, StringIdKey key) throws Exception {
        context.sendMessage("  key:");
        if (Objects.isNull(key)) {
            context.sendMessage("    null");
            return;
        }
        context.sendMessage("    stringId: " + key.getStringId());
    }

    private String formatListCount(List<?> list) {
        if (Objects.isNull(list)) return "null";
        if (list.isEmpty()) return "0 (空)";
        return String.valueOf(list.size());
    }
}

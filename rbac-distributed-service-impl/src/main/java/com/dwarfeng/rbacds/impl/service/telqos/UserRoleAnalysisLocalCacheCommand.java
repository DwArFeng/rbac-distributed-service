package com.dwarfeng.rbacds.impl.service.telqos;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.service.UserRoleAnalysisLocalCacheQosService;
import com.dwarfeng.rbacds.stack.struct.UserRoleAnalysis;
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
import java.util.List;
import java.util.Objects;

/**
 * 用户角色分析本地缓存指令。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@TelqosCommand
public class UserRoleAnalysisLocalCacheCommand extends CliCommand {

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_CLEAR = "c";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_CLEAR
    };

    private static final String COMMAND_OPTION_USER = "lu";

    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
    private static final String IDENTITY = "uralc";
    private static final String DESCRIPTION = "用户角色分析本地缓存操作";
    private static final String CMD_LINE_SYNTAX_LOOKUP = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_USER) + " userId";
    private static final String CMD_LINE_SYNTAX_CLEAR = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEAR);

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_LOOKUP,
            CMD_LINE_SYNTAX_CLEAR
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final UserRoleAnalysisLocalCacheQosService qosService;

    public UserRoleAnalysisLocalCacheCommand(UserRoleAnalysisLocalCacheQosService qosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.qosService = qosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(false)
                .desc("查看指定用户键的用户角色分析，如果本地缓存中不存在，则尝试抓取").build());
        list.add(Option.builder(COMMAND_OPTION_CLEAR).optionalArg(true).hasArg(false).desc("清除缓存").build());
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

    private void handleLookup(Context context, CommandLine cmd) throws Exception {
        String userId = cmd.getOptionValue(COMMAND_OPTION_USER);
        if (Objects.isNull(userId) || userId.isEmpty()) {
            context.sendMessage("lookup 操作需要指定 " + CommandUtil.concatOptionPrefix(COMMAND_OPTION_USER) + " 选项");
            return;
        }
        StringIdKey key = new StringIdKey(userId);
        UserRoleAnalysis analysis = qosService.get(key);
        if (Objects.isNull(analysis)) {
            context.sendMessage("not exists!");
            return;
        }
        interactUserRoleAnalysis(context, analysis);
    }

    private void handleClear(Context context) throws Exception {
        qosService.clearLocalCache();
        context.sendMessage("缓存已清空");
    }

    @SuppressWarnings("DuplicatedCode")
    private void printRole(int i, int endIndex, Role role, Context context) throws Exception {
        context.sendMessage(String.format("索引: %d/%d", i, endIndex));
        printStringIdKey(context, role.getKey());
        context.sendMessage("  name: " + role.getName());
        context.sendMessage("  enabled: " + role.isEnabled());
        context.sendMessage("  remark: " + role.getRemark());
        context.sendMessage("");
    }

    private void printStringIdKey(Context context, StringIdKey key) throws Exception {
        context.sendMessage("  key:");
        if (Objects.isNull(key)) {
            context.sendMessage("    null");
            return;
        }
        context.sendMessage("    stringId: " + key.getStringId());
    }

    @SuppressWarnings("DuplicatedCode")
    private void interactUserRoleAnalysis(Context context, UserRoleAnalysis analysis) throws Exception {
        sendUserRoleMenu(context, analysis);

        while (true) {
            String message = context.receiveMessage();

            if (Strings.CI.equals(message, "q")) {
                break;
            } else if (Strings.CI.equals(message, "r")) {
                List<Role> list = analysis.getMatchedRoles();
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
                        printRole(i, cropResult.getEndIndex(), list.get(i), context);
                    }
                }
                sendUserRoleMenu(context, analysis);
            } else {
                context.sendMessage("输入格式错误");
                context.sendMessage("");
            }
        }
    }

    private void sendUserRoleMenu(Context context, UserRoleAnalysis analysis) throws Exception {
        context.sendMessage("");
        context.sendMessage("matchedRoles: " + formatListCount(analysis.getMatchedRoles()));
        context.sendMessage("");
        context.sendMessage("输入 r 查看 matchedRoles");
        context.sendMessage("输入 q 退出查询");
        context.sendMessage("");
    }

    private String formatListCount(List<?> list) {
        if (Objects.isNull(list)) return "null";
        if (list.isEmpty()) return "0 (空)";
        return String.valueOf(list.size());
    }
}

package com.dwarfeng.rbacds.impl.service.telqos;

import com.dwarfeng.rbacds.impl.internal.i18n.ImplMessageKey;
import com.dwarfeng.rbacds.impl.internal.i18n.ImplMessages;

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
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
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
        return _ -> ImplMessages.message(ImplMessageKey.TELQOS_PERMISSION_USER_ANALYSIS_LOCAL_CACHE_DESCRIPTION);
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
                .desc(ImplMessages.message(ImplMessageKey.TELQOS_PERMISSION_USER_ANALYSIS_LOCAL_CACHE_OPTION_LOOKUP))
                .get());
        list.add(
                Option.builder(COMMAND_OPTION_CLEAR).optionalArg(true).hasArg(false)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_OPTION_CLEAR)).get()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_SCOPE).optionalArg(true).hasArg(true)
                        .argName("scopeId")
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_SCOPE_ID)).get()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_PERMISSION).optionalArg(true).hasArg(true)
                        .argName("permissionId")
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_PERMISSION_ID)).get()
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
                throw new IllegalStateException(
                        ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_INTERNAL_UNREACHABLE)
                );
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
                    ImplMessages.message(
                            ImplMessageKey.TELQOS_COMMON_RESULT_LOOKUP_REQUIRES_OPTIONS,
                            CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_SCOPE),
                            CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_PERMISSION)
                    )
            );
            return;
        }
        PermissionKey key = new PermissionKey(scopeId, permissionId);
        PermissionUserAnalysis analysis = qosService.get(key);
        if (Objects.isNull(analysis)) {
            context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_NOT_EXISTS));
            return;
        }
        interactPermissionUserAnalysis(context, analysis);
    }

    private void handleClear(CommandExecutor.Context context) throws Exception {
        qosService.clearLocalCache();
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_CACHE_CLEARED));
    }

    private void printUser(int i, int endIndex, User user, CommandExecutor.Context context) throws Exception {
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_INDEX, i, endIndex));
        printStringIdKey(context, user.getKey());
        context.sendMessage("  remark: " + user.getRemark());
        context.sendMessage("");
    }

    @SuppressWarnings("DuplicatedCode")
    private void printRole(int i, int endIndex, Role role, CommandExecutor.Context context) throws Exception {
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_INDEX, i, endIndex));
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
                List<User> list = analysis.matchedUsers();
                if (Objects.isNull(list)) {
                    context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_LIST_NOT_SET));
                    context.sendMessage("");
                    continue;
                }
                while (true) {
                    CliCommandUtil.CropResult cropResult = CliCommandUtil.cropData(
                            context, list,
                            ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_DATA_TOTAL, list.size()),
                            command -> ImplMessages.message(
                                    ImplMessageKey.TELQOS_COMMON_RESULT_RETURN_MAIN_MENU, command
                            )
                    );
                    if (cropResult.exitFlag()) break;
                    context.sendMessage("");
                    for (int i = cropResult.beginIndex(); i < cropResult.endIndex(); i++) {
                        printUser(i, cropResult.endIndex(), list.get(i), context);
                    }
                }
                sendPermissionUserMenu(context, analysis);
            } else if (Strings.CI.equals(message, "a")) {
                List<Role> list = analysis.acceptedRoles();
                if (Objects.isNull(list)) {
                    context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_LIST_NOT_SET));
                    context.sendMessage("");
                    continue;
                }
                while (true) {
                    CliCommandUtil.CropResult cropResult = CliCommandUtil.cropData(
                            context, list,
                            ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_DATA_TOTAL, list.size()),
                            command -> ImplMessages.message(
                                    ImplMessageKey.TELQOS_COMMON_RESULT_RETURN_MAIN_MENU, command
                            )
                    );
                    if (cropResult.exitFlag()) break;
                    context.sendMessage("");
                    for (int i = cropResult.beginIndex(); i < cropResult.endIndex(); i++) {
                        printRole(i, cropResult.endIndex(), list.get(i), context);
                    }
                }
                sendPermissionUserMenu(context, analysis);
            } else if (Strings.CI.equals(message, "r")) {
                List<Role> list = analysis.rejectedRoles();
                if (Objects.isNull(list)) {
                    context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_LIST_NOT_SET));
                    context.sendMessage("");
                    continue;
                }
                while (true) {
                    CliCommandUtil.CropResult cropResult = CliCommandUtil.cropData(
                            context, list,
                            ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_DATA_TOTAL, list.size()),
                            command -> ImplMessages.message(
                                    ImplMessageKey.TELQOS_COMMON_RESULT_RETURN_MAIN_MENU, command
                            )
                    );
                    if (cropResult.exitFlag()) break;
                    context.sendMessage("");
                    for (int i = cropResult.beginIndex(); i < cropResult.endIndex(); i++) {
                        printRole(i, cropResult.endIndex(), list.get(i), context);
                    }
                }
                sendPermissionUserMenu(context, analysis);
            } else {
                context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_INPUT_FORMAT_ERROR));
                context.sendMessage("");
            }
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void sendPermissionUserMenu(CommandExecutor.Context context, PermissionUserAnalysis analysis)
            throws Exception {
        context.sendMessage("");
        context.sendMessage("matchedUsers: " + formatListCount(analysis.matchedUsers()));
        context.sendMessage("acceptedRoles: " + formatListCount(analysis.acceptedRoles()));
        context.sendMessage("rejectedRoles: " + formatListCount(analysis.rejectedRoles()));
        context.sendMessage("");
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_PROMPT_VIEW_MATCHED_USERS));
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_PROMPT_VIEW_ACCEPTED_ROLES));
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_PROMPT_VIEW_REJECTED_ROLES));
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_PROMPT_EXIT_QUERY));
        context.sendMessage("");
    }

    private String formatListCount(List<?> list) {
        if (Objects.isNull(list)) return "null";
        if (list.isEmpty()) return ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_EMPTY_LIST_SIZE);
        return String.valueOf(list.size());
    }
}

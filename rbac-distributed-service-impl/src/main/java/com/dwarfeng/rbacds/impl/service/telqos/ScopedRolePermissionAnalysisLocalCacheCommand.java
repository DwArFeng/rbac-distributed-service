package com.dwarfeng.rbacds.impl.service.telqos;

import com.dwarfeng.rbacds.impl.internal.i18n.ImplMessageKey;
import com.dwarfeng.rbacds.impl.internal.i18n.ImplMessages;

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
        return _ -> ImplMessages.message(ImplMessageKey.TELQOS_SCOPED_ROLE_PERMISSION_ANALYSIS_LOCAL_CACHE_DESCRIPTION);
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
                .desc(
                        ImplMessages.message(
                                ImplMessageKey.TELQOS_SCOPED_ROLE_PERMISSION_ANALYSIS_LOCAL_CACHE_OPTION_LOOKUP
                        )
                )
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
                Option.builder(COMMAND_SUB_OPTION_ROLE).optionalArg(true).hasArg(true)
                        .argName("roleId")
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_ROLE_ID)).get()
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
        String roleId = cmd.getOptionValue(COMMAND_SUB_OPTION_ROLE);
        if (Objects.isNull(scopeId) || scopeId.isEmpty() || Objects.isNull(roleId) || roleId.isEmpty()) {
            context.sendMessage(
                    ImplMessages.message(
                            ImplMessageKey.TELQOS_COMMON_RESULT_LOOKUP_REQUIRES_OPTIONS,
                            CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_SCOPE),
                            CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_ROLE)
                    )
            );
            return;
        }
        ScopedRoleKey key = new ScopedRoleKey(scopeId, roleId);
        ScopedRolePermissionAnalysis analysis = qosService.get(key);
        if (Objects.isNull(analysis)) {
            context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_NOT_EXISTS));
            return;
        }
        interactScopedRolePermissionAnalysis(context, analysis);
    }

    private void handleClear(CommandExecutor.Context context) throws Exception {
        qosService.clearLocalCache();
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_CACHE_CLEARED));
    }

    @SuppressWarnings("DuplicatedCode")
    private void printPermission(int i, int endIndex, Permission permission, CommandExecutor.Context context)
            throws Exception {
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_INDEX, i, endIndex));
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
                        printPermission(i, cropResult.endIndex(), list.get(i), context);
                    }
                }
                sendScopedRolePermissionMenu(context, analysis);
            } else if (Strings.CI.equals(message, "a")) {
                List<Permission> list = toList(analysis.getAcceptedPermissionMap());
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
                        printPermission(i, cropResult.endIndex(), list.get(i), context);
                    }
                }
                sendScopedRolePermissionMenu(context, analysis);
            } else if (Strings.CI.equals(message, "r")) {
                List<Permission> list = toList(analysis.getRejectedPermissionMap());
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
                        printPermission(i, cropResult.endIndex(), list.get(i), context);
                    }
                }
                sendScopedRolePermissionMenu(context, analysis);
            } else if (Strings.CI.equals(message, "g")) {
                List<Permission> list = toList(analysis.getGlobalRejectedPermissionMap());
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
                        printPermission(i, cropResult.endIndex(), list.get(i), context);
                    }
                }
                sendScopedRolePermissionMenu(context, analysis);
            } else {
                context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_INPUT_FORMAT_ERROR));
                context.sendMessage("");
            }
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void sendScopedRolePermissionMenu(CommandExecutor.Context context, ScopedRolePermissionAnalysis analysis)
            throws Exception {
        context.sendMessage("");
        context.sendMessage("matchedPermissions: " + formatListCount(analysis.getMatchedPermissions()));
        context.sendMessage("acceptedPermissions: " + formatMapCount(analysis.getAcceptedPermissionMap()));
        context.sendMessage("rejectedPermissions: " + formatMapCount(analysis.getRejectedPermissionMap()));
        context.sendMessage("globalRejectedPermissions: " + formatMapCount(analysis.getGlobalRejectedPermissionMap()));
        context.sendMessage("");
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_PROMPT_VIEW_MATCHED_PERMISSIONS_M)
        );
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_PROMPT_VIEW_ACCEPTED_PERMISSIONS));
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_PROMPT_VIEW_REJECTED_PERMISSIONS));
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_PROMPT_VIEW_GLOBAL_REJECTED_PERMISSIONS)
        );
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_PROMPT_EXIT_QUERY));
        context.sendMessage("");
    }

    private String formatListCount(List<?> list) {
        if (Objects.isNull(list)) return "null";
        if (list.isEmpty()) return ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_EMPTY_LIST_SIZE);
        return String.valueOf(list.size());
    }

    private String formatMapCount(Map<?, ?> map) {
        if (Objects.isNull(map)) return "null";
        if (map.isEmpty()) return ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_EMPTY_LIST_SIZE);
        return String.valueOf(map.size());
    }
}

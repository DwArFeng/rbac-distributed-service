package com.dwarfeng.rbacds.impl.service.telqos;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.rbacds.sdk.bean.dto.*;
import com.dwarfeng.rbacds.stack.bean.dto.*;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.service.InspectQosService;
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

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 查看服务指令。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@TelqosCommand
public class InspectCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "inspect";

    // region 指令选项

    private static final String COMMAND_OPTION_PERMISSION_USER = "inspect-permission-user";
    private static final String COMMAND_OPTION_ROLE_PERMISSION = "inspect-role-permission";
    private static final String COMMAND_OPTION_USER_PERMISSION = "inspect-user-permission";

    private static final String COMMAND_OPTION_USER_VIEW_OF_PERMISSION = "uvp";
    private static final String COMMAND_OPTION_USER_VIEW_OF_PERMISSION_LONG_OPT = "inspect-user-view-of-permission";
    private static final String COMMAND_OPTION_PERMISSION_VIEW_OF_ROLE = "pvr";
    private static final String COMMAND_OPTION_PERMISSION_VIEW_OF_ROLE_LONG_OPT = "inspect-permission-view-of-role";
    private static final String COMMAND_OPTION_PERMISSION_VIEW_OF_USER = "pvu";
    private static final String COMMAND_OPTION_PERMISSION_VIEW_OF_USER_LONG_OPT = "inspect-permission-view-of-user";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_PERMISSION_USER,
            COMMAND_OPTION_ROLE_PERMISSION,
            COMMAND_OPTION_USER_PERMISSION,
            COMMAND_OPTION_USER_VIEW_OF_PERMISSION,
            COMMAND_OPTION_PERMISSION_VIEW_OF_ROLE,
            COMMAND_OPTION_PERMISSION_VIEW_OF_USER
    };

    private static final String COMMAND_SUB_OPTION_JSON = "json";
    private static final String COMMAND_SUB_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_SUB_OPTION_JSON_FILE_LONG_OPT = "json-file";

    // endregion

    private final InspectQosService inspectQosService;

    public InspectCommand(InspectQosService inspectQosService) {
        super(IDENTITY);
        this.inspectQosService = inspectQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "查看服务";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_PERMISSION_USER) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_ROLE_PERMISSION) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_USER_PERMISSION) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_USER_VIEW_OF_PERMISSION) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_PERMISSION_VIEW_OF_ROLE) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_PERMISSION_VIEW_OF_USER) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder().longOpt(COMMAND_OPTION_PERMISSION_USER)
                .desc("(已废弃) 查询权限的用户信息，请使用 inspect-user-view-of-permission").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_ROLE_PERMISSION)
                .desc("(已废弃) 查询角色的权限信息，请使用 inspect-permission-view-of-role").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_USER_PERMISSION)
                .desc("(已废弃) 查询用户的权限信息，请使用 inspect-permission-view-of-user").build());
        list.add(
                Option.builder(COMMAND_OPTION_USER_VIEW_OF_PERMISSION)
                        .longOpt(COMMAND_OPTION_USER_VIEW_OF_PERMISSION_LONG_OPT)
                        .desc("以权限为依据查看用户视图").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_PERMISSION_VIEW_OF_ROLE)
                        .longOpt(COMMAND_OPTION_PERMISSION_VIEW_OF_ROLE_LONG_OPT)
                        .desc("以角色为依据查看权限视图").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_PERMISSION_VIEW_OF_USER)
                        .longOpt(COMMAND_OPTION_PERMISSION_VIEW_OF_USER_LONG_OPT)
                        .desc("以用户为依据查看权限视图").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_JSON).desc("JSON 字符串").hasArg().type(String.class).build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_JSON_FILE).longOpt(COMMAND_SUB_OPTION_JSON_FILE_LONG_OPT)
                        .desc("JSON 文件")
                        .hasArg().type(File.class).build()
        );
        return list;
    }

    @Override
    protected void executeWithCmd(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Pair<String, Integer> pair = CliCommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
        if (pair.getRight() != 1) {
            context.sendMessage(CliCommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
            return;
        }
        switch (pair.getLeft()) {
            case COMMAND_OPTION_PERMISSION_USER:
                handleInspectPermissionUser(context, cmd);
                break;
            case COMMAND_OPTION_ROLE_PERMISSION:
                handleInspectRolePermission(context, cmd);
                break;
            case COMMAND_OPTION_USER_PERMISSION:
                handleInspectUserPermission(context, cmd);
                break;
            case COMMAND_OPTION_USER_VIEW_OF_PERMISSION:
                handleInspectUserViewOfPermission(context, cmd);
                break;
            case COMMAND_OPTION_PERMISSION_VIEW_OF_ROLE:
                handleInspectPermissionViewOfRole(context, cmd);
                break;
            case COMMAND_OPTION_PERMISSION_VIEW_OF_USER:
                handleInspectPermissionViewOfUser(context, cmd);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    // 仍然需要保留旧指令选项，故忽略相关警告。
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    private void handleInspectPermissionUser(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        PermissionUserInspectInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PermissionUserInspectInfo。
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            info = WebInputPermissionUserInspectInfo.toStackBean(
                    JSON.parseObject(json, WebInputPermissionUserInspectInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PermissionUserInspectInfo。
        else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPermissionUserInspectInfo.toStackBean(
                        JSON.parseObject(in, WebInputPermissionUserInspectInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 查询权限的用户信息。
        PermissionUserInspectResult result = inspectQosService.inspectPermissionUser(info);

        // 输出结果（转换为新 DTO 进行交互）。
        if (Objects.isNull(result)) {
            context.sendMessage("查询结果: null");
        } else {
            context.sendMessage("查询成功:");
            interactUserViewOfPermissionResult(
                    context, permissionUserInspectResultToUserViewOfPermissionInspectResult(result)
            );
        }
    }

    private void handleInspectUserViewOfPermission(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        UserViewOfPermissionInspectInfo info;

        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            info = WebInputUserViewOfPermissionInspectInfo.toStackBean(
                    JSON.parseObject(json, WebInputUserViewOfPermissionInspectInfo.class)
            );
        } else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputUserViewOfPermissionInspectInfo.toStackBean(
                        JSON.parseObject(in, WebInputUserViewOfPermissionInspectInfo.class)
                );
            }
        } else {
            throw new UnsupportedOperationException("not supported yet");
        }

        UserViewOfPermissionInspectResult result = inspectQosService.inspectUserViewOfPermission(info);

        if (Objects.isNull(result)) {
            context.sendMessage("查询结果: null");
        } else {
            context.sendMessage("查询成功:");
            interactUserViewOfPermissionResult(context, result);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void interactUserViewOfPermissionResult(
            CommandExecutor.Context context, UserViewOfPermissionInspectResult result
    ) throws Exception {
        sendUserViewOfPermissionMenu(context, result);

        while (true) {
            String message = context.receiveMessage();

            if (Strings.CI.equals(message, "q")) {
                break;
            } else if (Strings.CI.equals(message, "u")) {
                List<User> list = result.getMatchedUsers();
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
                sendUserViewOfPermissionMenu(context, result);
            } else if (Strings.CI.equals(message, "a")) {
                List<Role> list = result.getAcceptedRoles();
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
                        context.sendMessage("");
                    }
                }
                sendUserViewOfPermissionMenu(context, result);
            } else if (Strings.CI.equals(message, "r")) {
                List<Role> list = result.getRejectedRoles();
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
                        context.sendMessage("");
                    }
                }
                sendUserViewOfPermissionMenu(context, result);
            } else {
                context.sendMessage("输入格式错误");
                context.sendMessage("");
            }
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void sendUserViewOfPermissionMenu(CommandExecutor.Context context, UserViewOfPermissionInspectResult result)
            throws Exception {
        context.sendMessage("");
        context.sendMessage("matchedFlag: " + result.isMatchedFlag());
        context.sendMessage("matchedUsers: " + formatListCount(result.getMatchedUsers()));
        context.sendMessage("acceptedRoles: " + formatListCount(result.getAcceptedRoles()));
        context.sendMessage("rejectedRoles: " + formatListCount(result.getRejectedRoles()));
        context.sendMessage("");
        context.sendMessage("输入 u 查看 matchedUsers");
        context.sendMessage("输入 a 查看 acceptedRoles");
        context.sendMessage("输入 r 查看 rejectedRoles");
        context.sendMessage("输入 q 退出查询");
        context.sendMessage("");
    }

    // 仍然需要保留旧指令选项，故忽略相关警告。
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    private void handleInspectRolePermission(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        RolePermissionInspectInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 RolePermissionInspectInfo。
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            info = WebInputRolePermissionInspectInfo.toStackBean(
                    JSON.parseObject(json, WebInputRolePermissionInspectInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 RolePermissionInspectInfo。
        else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputRolePermissionInspectInfo.toStackBean(
                        JSON.parseObject(in, WebInputRolePermissionInspectInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 查询角色的权限信息。
        RolePermissionInspectResult result = inspectQosService.inspectRolePermission(info);

        // 输出结果（转换为新 DTO 进行交互）。
        if (Objects.isNull(result)) {
            context.sendMessage("查询结果: null");
        } else {
            context.sendMessage("查询成功:");
            interactPermissionViewOfRoleResult(
                    context, rolePermissionInspectResultToPermissionViewOfRoleInspectResult(result)
            );
        }
    }

    private void handleInspectPermissionViewOfRole(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        PermissionViewOfRoleInspectInfo info;

        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            info = WebInputPermissionViewOfRoleInspectInfo.toStackBean(
                    JSON.parseObject(json, WebInputPermissionViewOfRoleInspectInfo.class)
            );
        } else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPermissionViewOfRoleInspectInfo.toStackBean(
                        JSON.parseObject(in, WebInputPermissionViewOfRoleInspectInfo.class)
                );
            }
        } else {
            throw new UnsupportedOperationException("not supported yet");
        }

        PermissionViewOfRoleInspectResult result = inspectQosService.inspectPermissionViewOfRole(info);

        if (Objects.isNull(result)) {
            context.sendMessage("查询结果: null");
        } else {
            context.sendMessage("查询成功:");
            interactPermissionViewOfRoleResult(context, result);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void interactPermissionViewOfRoleResult(
            CommandExecutor.Context context, PermissionViewOfRoleInspectResult result
    ) throws Exception {
        sendPermissionViewOfRoleMenu(context, result);

        while (true) {
            String message = context.receiveMessage();

            if (Strings.CI.equals(message, "q")) {
                break;
            } else if (Strings.CI.equals(message, "m")) {
                List<Permission> list = result.getMatchedPermissions();
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
                sendPermissionViewOfRoleMenu(context, result);
            } else if (Strings.CI.equals(message, "a")) {
                List<Permission> list = result.getAcceptedPermissions();
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
                sendPermissionViewOfRoleMenu(context, result);
            } else if (Strings.CI.equals(message, "r")) {
                List<Permission> list = result.getRejectedPermissions();
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
                sendPermissionViewOfRoleMenu(context, result);
            } else if (Strings.CI.equals(message, "g")) {
                List<Permission> list = result.getGlobalRejectedPermissions();
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
                sendPermissionViewOfRoleMenu(context, result);
            } else {
                context.sendMessage("输入格式错误");
                context.sendMessage("");
            }
        }
    }

    private void sendPermissionViewOfRoleMenu(CommandExecutor.Context context, PermissionViewOfRoleInspectResult result)
            throws Exception {
        context.sendMessage("");
        context.sendMessage("matchedFlag: " + result.isMatchedFlag());
        context.sendMessage("matchedPermissions: " + formatListCount(result.getMatchedPermissions()));
        context.sendMessage("acceptedPermissions: " + formatListCount(result.getAcceptedPermissions()));
        context.sendMessage("rejectedPermissions: " + formatListCount(result.getRejectedPermissions()));
        context.sendMessage("globalRejectedPermissions: " + formatListCount(result.getGlobalRejectedPermissions()));
        context.sendMessage("");
        context.sendMessage("输入 m 查看 matchedPermissions");
        context.sendMessage("输入 a 查看 acceptedPermissions");
        context.sendMessage("输入 r 查看 rejectedPermissions");
        context.sendMessage("输入 g 查看 globalRejectedPermissions");
        context.sendMessage("输入 q 退出查询");
        context.sendMessage("");
    }

    // 仍然需要保留旧指令选项，故忽略相关警告。
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    private void handleInspectUserPermission(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        UserPermissionInspectInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 UserPermissionInspectInfo。
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            info = WebInputUserPermissionInspectInfo.toStackBean(
                    JSON.parseObject(json, WebInputUserPermissionInspectInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 UserPermissionInspectInfo。
        else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputUserPermissionInspectInfo.toStackBean(
                        JSON.parseObject(in, WebInputUserPermissionInspectInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 查询用户的权限信息。
        UserPermissionInspectResult result = inspectQosService.inspectUserPermission(info);

        // 输出结果（转换为新 DTO 进行交互）。
        if (Objects.isNull(result)) {
            context.sendMessage("查询结果: null");
        } else {
            context.sendMessage("查询成功:");
            interactPermissionViewOfUserResult(
                    context, userPermissionInspectResultToPermissionViewOfUserInspectResult(result)
            );
        }
    }

    private void handleInspectPermissionViewOfUser(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        PermissionViewOfUserInspectInfo info;

        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            info = WebInputPermissionViewOfUserInspectInfo.toStackBean(
                    JSON.parseObject(json, WebInputPermissionViewOfUserInspectInfo.class)
            );
        } else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPermissionViewOfUserInspectInfo.toStackBean(
                        JSON.parseObject(in, WebInputPermissionViewOfUserInspectInfo.class)
                );
            }
        } else {
            throw new UnsupportedOperationException("not supported yet");
        }

        PermissionViewOfUserInspectResult result = inspectQosService.inspectPermissionViewOfUser(info);

        if (Objects.isNull(result)) {
            context.sendMessage("查询结果: null");
        } else {
            context.sendMessage("查询成功:");
            interactPermissionViewOfUserResult(context, result);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void interactPermissionViewOfUserResult(
            CommandExecutor.Context context, PermissionViewOfUserInspectResult result
    ) throws Exception {
        sendPermissionViewOfUserMenu(context, result);

        while (true) {
            String message = context.receiveMessage();

            if (Strings.CI.equals(message, "q")) {
                break;
            } else if (Strings.CI.equals(message, "p")) {
                List<Permission> list = result.getMatchedPermissions();
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
                sendPermissionViewOfUserMenu(context, result);
            } else if (Strings.CI.equals(message, "r")) {
                interactPermissionViewOfUserRoleDetails(context, result.getRoleDetails());
                sendPermissionViewOfUserMenu(context, result);
            } else {
                context.sendMessage("输入格式错误");
                context.sendMessage("");
            }
        }
    }

    private void sendPermissionViewOfUserMenu(CommandExecutor.Context context, PermissionViewOfUserInspectResult result)
            throws Exception {
        context.sendMessage("");
        context.sendMessage("matchedFlag: " + result.isMatchedFlag());
        context.sendMessage("matchedPermissions: " + formatListCount(result.getMatchedPermissions()));
        context.sendMessage("roleDetails: " + formatListCount(result.getRoleDetails()));
        context.sendMessage("");
        context.sendMessage("输入 p 查看 matchedPermissions");
        context.sendMessage("输入 r 查看 roleDetails");
        context.sendMessage("输入 q 退出查询");
        context.sendMessage("");
    }

    @SuppressWarnings("DuplicatedCode")
    private void interactPermissionViewOfUserRoleDetails(
            CommandExecutor.Context context, List<PermissionViewOfUserInspectResult.RoleDetail> roleDetails
    ) throws Exception {
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

            PermissionViewOfUserInspectResult.RoleDetail roleDetail = roleDetails.get(index);
            sendPermissionViewOfUserRoleDetailMenu(context, roleDetail);

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
                        CliCommandUtil.CropResult cropResult = CliCommandUtil.cropData(
                                context, list, "数据总数: " + list.size(), command -> "输入 " + command + " 返回至角色详情"
                        );
                        if (cropResult.isExitFlag()) break;
                        context.sendMessage("");
                        for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                            printPermission(i, cropResult.getEndIndex(), list.get(i), context);
                        }
                    }
                    sendPermissionViewOfUserRoleDetailMenu(context, roleDetail);
                } else if (Strings.CI.equals(subMessage, "r")) {
                    List<Permission> list = roleDetail.getRejectedPermissions();
                    if (Objects.isNull(list)) {
                        context.sendMessage("该列表未设置，无法查看");
                        context.sendMessage("");
                        continue;
                    }
                    while (true) {
                        CliCommandUtil.CropResult cropResult = CliCommandUtil.cropData(
                                context, list, "数据总数: " + list.size(), command -> "输入 " + command + " 返回至角色详情"
                        );
                        if (cropResult.isExitFlag()) break;
                        context.sendMessage("");
                        for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                            printPermission(i, cropResult.getEndIndex(), list.get(i), context);
                        }
                    }
                    sendPermissionViewOfUserRoleDetailMenu(context, roleDetail);
                } else if (Strings.CI.equals(subMessage, "g")) {
                    List<Permission> list = roleDetail.getGlobalRejectedPermissions();
                    if (Objects.isNull(list)) {
                        context.sendMessage("该列表未设置，无法查看");
                        context.sendMessage("");
                        continue;
                    }
                    while (true) {
                        CliCommandUtil.CropResult cropResult = CliCommandUtil.cropData(
                                context, list, "数据总数: " + list.size(), command -> "输入 " + command + " 返回至角色详情"
                        );
                        if (cropResult.isExitFlag()) break;
                        context.sendMessage("");
                        for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                            printPermission(i, cropResult.getEndIndex(), list.get(i), context);
                        }
                    }
                    sendPermissionViewOfUserRoleDetailMenu(context, roleDetail);
                } else {
                    context.sendMessage("输入格式错误");
                    context.sendMessage("");
                }
            }
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void sendPermissionViewOfUserRoleDetailMenu(
            CommandExecutor.Context context, PermissionViewOfUserInspectResult.RoleDetail roleDetail
    ) throws Exception {
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

    private void printUser(int i, int endIndex, User user, CommandExecutor.Context context) throws Exception {
        context.sendMessage(String.format("索引: %d/%d", i, endIndex));
        context.sendMessage("  key: " + user.getKey());
        context.sendMessage("  remark: " + user.getRemark());
        context.sendMessage("");
    }

    private void printRole(int i, int endIndex, Role role, CommandExecutor.Context context) throws Exception {
        context.sendMessage(String.format("索引: %d/%d", i, endIndex));
        printStringIdKey(context, role.getKey());
        context.sendMessage("  name: " + role.getName());
        context.sendMessage("  enabled: " + role.isEnabled());
        context.sendMessage("  remark: " + role.getRemark());
    }

    private void printRole(Role role, CommandExecutor.Context context) throws Exception {
        printStringIdKey(context, role.getKey());
        context.sendMessage("  name: " + role.getName());
        context.sendMessage("  enabled: " + role.isEnabled());
        context.sendMessage("  remark: " + role.getRemark());
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
    private void printPermission(int i, int endIndex, Permission permission, CommandExecutor.Context context)
            throws Exception {
        context.sendMessage(String.format("索引: %d/%d", i, endIndex));
        printPermissionKey(context, permission.getKey());
        printPermissionGroupKey(context, permission.getGroupKey());
        context.sendMessage("  name: " + permission.getName());
        context.sendMessage("  level: " + permission.getLevel());
        context.sendMessage("  groupPath: " + (Objects.nonNull(permission.getGroupPath()) ?
                Arrays.toString(permission.getGroupPath()) : "null"));
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

    private String formatListCount(List<?> list) {
        if (Objects.isNull(list)) return "null";
        if (list.isEmpty()) return "0 (空)";
        return String.valueOf(list.size());
    }

    @Deprecated
    private UserViewOfPermissionInspectResult permissionUserInspectResultToUserViewOfPermissionInspectResult(
            PermissionUserInspectResult result
    ) {
        if (Objects.isNull(result)) {
            return null;
        }
        return new UserViewOfPermissionInspectResult(
                result.getMatchedUsers(),
                result.isMatchedFlag(),
                result.getAcceptedRoles(),
                result.getRejectedRoles()
        );
    }

    @Deprecated
    private PermissionViewOfRoleInspectResult rolePermissionInspectResultToPermissionViewOfRoleInspectResult(
            RolePermissionInspectResult result
    ) {
        if (Objects.isNull(result)) {
            return null;
        }
        return new PermissionViewOfRoleInspectResult(
                result.getMatchedPermissions(),
                result.isMatchedFlag(),
                result.getAcceptedPermissions(),
                result.getRejectedPermissions(),
                result.getGlobalRejectedPermissions()
        );
    }

    @Deprecated
    private PermissionViewOfUserInspectResult userPermissionInspectResultToPermissionViewOfUserInspectResult(
            UserPermissionInspectResult result
    ) {
        if (Objects.isNull(result)) {
            return null;
        }
        List<PermissionViewOfUserInspectResult.RoleDetail> roleDetails = null;
        if (Objects.nonNull(result.getRoleDetails())) {
            roleDetails = new ArrayList<>(result.getRoleDetails().size());
            for (UserPermissionInspectResult.RoleDetail rd : result.getRoleDetails()) {
                roleDetails.add(new PermissionViewOfUserInspectResult.RoleDetail(
                        rd.getRole(),
                        rd.getAcceptedPermissions(),
                        rd.getRejectedPermissions(),
                        rd.getGlobalRejectedPermissions()
                ));
            }
        }
        return new PermissionViewOfUserInspectResult(
                result.getMatchedPermissions(),
                result.isMatchedFlag(),
                roleDetails
        );
    }
}

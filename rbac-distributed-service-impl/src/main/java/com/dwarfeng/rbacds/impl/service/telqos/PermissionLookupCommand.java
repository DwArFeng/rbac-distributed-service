package com.dwarfeng.rbacds.impl.service.telqos;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.service.PermissionLookupQosService;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@TelqosCommand
public class PermissionLookupCommand extends CliCommand {

    private static final String COMMAND_OPTION_LOOKUP_FOR_USER = "u";
    private static final String COMMAND_OPTION_LOOKUP_FOR_ROLE = "r";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP_FOR_USER,
            COMMAND_OPTION_LOOKUP_FOR_ROLE
    };

    private static final String IDENTITY = "pl";
    private static final String DESCRIPTION = "查询权限信息";

    private static final String CMD_LINE_SYNTAX_LOOKUP_FOR_USER = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP_FOR_USER) + " user-id";
    private static final String CMD_LINE_SYNTAX_LOOKUP_FOR_ROLE = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP_FOR_ROLE) + " role-id";

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_LOOKUP_FOR_USER,
            CMD_LINE_SYNTAX_LOOKUP_FOR_ROLE
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final PermissionLookupQosService permissionLookupQosService;

    public PermissionLookupCommand(PermissionLookupQosService permissionLookupQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.permissionLookupQosService = permissionLookupQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(
                Option.builder(COMMAND_OPTION_LOOKUP_FOR_USER).desc("查询用户")
                        .hasArg(true).type(String.class).build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_LOOKUP_FOR_ROLE).desc("查询角色")
                        .hasArg(true).type(String.class).build()
        );
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine commandLine) throws TelqosException {
        try {
            Pair<String, Integer> pair = CommandUtil.analyseCommand(commandLine, COMMAND_OPTION_ARRAY);
            if (pair.getRight() != 1) {
                context.sendMessage(CommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
                context.sendMessage(super.cmdLineSyntax);
                return;
            }
            switch (pair.getLeft()) {
                case COMMAND_OPTION_LOOKUP_FOR_USER:
                    handleLookupForUser(context, commandLine);
                    break;
                case COMMAND_OPTION_LOOKUP_FOR_ROLE:
                    handleLookupForRole(context, commandLine);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleLookupForUser(Context context, CommandLine commandLine) throws Exception {
        String userId = commandLine.getOptionValue(COMMAND_OPTION_LOOKUP_FOR_USER);
        List<Permission> permissions = permissionLookupQosService.lookupForUser(new StringIdKey(userId));
        printPermissions(context, permissions);
    }

    private void handleLookupForRole(Context context, CommandLine commandLine) throws Exception {
        String roleId = commandLine.getOptionValue(COMMAND_OPTION_LOOKUP_FOR_ROLE);
        List<Permission> permissions = permissionLookupQosService.lookupForRole(new StringIdKey(roleId));
        printPermissions(context, permissions);
    }

    private static void printPermissions(Context context, List<Permission> permissions) throws TelqosException {
        int sizeDigits = CommandUtil.digits(permissions.size());
        for (int i = 0; i < permissions.size(); i++) {
            context.sendMessage(String.format("%-" + sizeDigits + "d. %s", i + 1, permissions.get(i)));
        }
    }
}

package com.dwarfeng.rbacds.impl.service.telqos;

import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.service.QosService;
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
public class UserLookupCommand extends CliCommand {

    private static final String COMMAND_OPTION_LOOKUP_FOR_PERMISSION = "p";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP_FOR_PERMISSION
    };

    private static final String IDENTITY = "ul";
    private static final String DESCRIPTION = "查询用户信息";


    private static final String CMD_LINE_SYNTAX_LOOKUP_FOR_PERMISSION = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP_FOR_PERMISSION) + " permission-id";

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_LOOKUP_FOR_PERMISSION
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final QosService qosService;

    public UserLookupCommand(QosService qosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.qosService = qosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(
                Option.builder(COMMAND_OPTION_LOOKUP_FOR_PERMISSION).desc("查询权限")
                        .hasArg(true).type(String.class).build()
        );
        return list;
    }

    // 为了程序代码的可维护型，此处忽略 Switch 语句中的警告。
    @SuppressWarnings("SwitchStatementWithTooFewBranches")
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
                case COMMAND_OPTION_LOOKUP_FOR_PERMISSION:
                    handleLookupForPermission(context, commandLine);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleLookupForPermission(Context context, CommandLine commandLine) throws Exception {
        String permissionId = commandLine.getOptionValue(COMMAND_OPTION_LOOKUP_FOR_PERMISSION);
        List<User> users = qosService.lookupUsersForPermission(new StringIdKey(permissionId));
        printUsers(context, users);
    }

    private void printUsers(Context context, List<User> users) throws Exception {
        int sizeDigits = CommandUtil.digits(users.size());
        for (int i = 0; i < users.size(); i++) {
            context.sendMessage(String.format("%-" + sizeDigits + "d. %s", i + 1, users.get(i)));
        }
    }
}

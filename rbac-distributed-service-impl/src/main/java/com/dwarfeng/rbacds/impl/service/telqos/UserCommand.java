package com.dwarfeng.rbacds.impl.service.telqos;

import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.service.QosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserCommand extends CliCommand {

    private static final String IDENTITY = "user";
    private static final String DESCRIPTION = "查询用户信息";
    private static final String CMD_LINE_SYNTAX = "user -p permission";

    private final QosService qosService;

    public UserCommand(QosService qosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.qosService = qosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder("p").required().hasArg(true).desc("查询权限").build());
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            if (!cmd.hasOption("p")) {
                context.sendMessage("命令行格式不符合规范，规范的格式为:");
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            String permissionId = cmd.getOptionValue("p");
            List<User> users = qosService.lookupUsersForPermission(new StringIdKey(permissionId));
            int sizeLength = TelqosUtil.numLength(users.size());
            for (int i = 0; i < users.size(); i++) {
                context.sendMessage(String.format("%-" + sizeLength + "d. %s", i + 1, users.get(i)));
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }
}

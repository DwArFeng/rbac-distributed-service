package com.dwarfeng.rbacds.impl.service.telqos;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.service.QosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PermissionCommand extends CliCommand {

    private static final String IDENTITY = "permission";
    private static final String DESCRIPTION = "查询权限信息";
    private static final String CMD_LINE_SYNTAX_USER = "permission -u user";
    private static final String CMD_LINE_SYNTAX_ROLE = "permission -r role";
    private static final String CMD_LINE_SYNTAX = CMD_LINE_SYNTAX_USER + System.lineSeparator() +
            CMD_LINE_SYNTAX_ROLE;

    public PermissionCommand() {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
    }

    @Autowired
    private QosService qosService;

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder("u").hasArg(true).desc("查询用户").build());
        list.add(Option.builder("r").hasArg(true).desc("查询角色").build());
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            if (cmd.hasOption("u")) {
                String userId = cmd.getOptionValue("u");
                List<Permission> permissions = qosService.lookupPermissionsForUser(new StringIdKey(userId));
                int sizeLength = TelqosUtil.numLength(permissions.size());
                for (int i = 0; i < permissions.size(); i++) {
                    context.sendMessage(String.format("%-" + sizeLength + "d. %s", i + 1, permissions.get(i)));
                }
                return;
            }

            if (cmd.hasOption("r")) {
                String roleId = cmd.getOptionValue("r");
                List<Permission> permissions = qosService.lookupPermissionsForRole(new StringIdKey(roleId));
                int sizeLength = TelqosUtil.numLength(permissions.size());
                for (int i = 0; i < permissions.size(); i++) {
                    context.sendMessage(String.format("%-" + sizeLength + "d. %s", i + 1, permissions.get(i)));
                }
                return;
            }

            context.sendMessage("命令行格式不符合规范，规范的格式为:");
            context.sendMessage(CMD_LINE_SYNTAX);
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }
}

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
    private static final String CMD_LINE_SYNTAX = "permission -u user";

    public PermissionCommand() {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
    }

    @Autowired
    private QosService qosService;

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder("u").required().hasArg(true).desc("查询用户").build());
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            if (!cmd.hasOption("u")) {
                context.sendMessage("命令行格式不符合规范，规范的格式为:");
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            String permissionId = cmd.getOptionValue("u");
            List<Permission> permissions = qosService.lookupPermissions(new StringIdKey(permissionId));
            int sizeLength = TelqosUtil.numLength(permissions.size());
            for (int i = 0; i < permissions.size(); i++) {
                context.sendMessage(String.format("%-" + sizeLength + "d. %s", i + 1, permissions.get(i)));
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }
}

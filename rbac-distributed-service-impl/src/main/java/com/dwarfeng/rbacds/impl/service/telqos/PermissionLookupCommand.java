package com.dwarfeng.rbacds.impl.service.telqos;

import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
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
        // 查询数据，并计时。
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        String userId = commandLine.getOptionValue(COMMAND_OPTION_LOOKUP_FOR_USER);
        List<Permission> permissions = permissionLookupQosService.lookupForUser(new StringIdKey(userId));
        tm.stop();

        // 输出执行时间。
        context.sendMessage("");
        context.sendMessage("执行时间：" + tm.getTimeMs() + "ms");
        context.sendMessage("");

        // 输出数据。
        while (true) {
            CropResult cropResult = cropPermissions(context, permissions);
            if (cropResult.exitFlag) {
                break;
            }
            context.sendMessage("");
            printPermissions(context, permissions, cropResult.getBeginIndex(), cropResult.getEndIndex());
        }
    }

    private void handleLookupForRole(Context context, CommandLine commandLine) throws Exception {
        // 查询数据，并计时。
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        String roleId = commandLine.getOptionValue(COMMAND_OPTION_LOOKUP_FOR_ROLE);
        List<Permission> permissions = permissionLookupQosService.lookupForRole(new StringIdKey(roleId));
        tm.stop();

        // 输出执行时间。
        context.sendMessage("");
        context.sendMessage("执行时间：" + tm.getTimeMs() + "ms");
        context.sendMessage("");

        // 输出数据。
        while (true) {
            CropResult cropResult = cropPermissions(context, permissions);
            if (cropResult.isExitFlag()) {
                break;
            }
            context.sendMessage("");
            printPermissions(context, permissions, cropResult.getBeginIndex(), cropResult.getEndIndex());
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private static CropResult cropPermissions(Context context, List<Permission> permissions) throws Exception {
        // 如果查询到的数据为空，做特殊处理。
        if (permissions.isEmpty()) {
            context.sendMessage("查询到 0 条权限信息");
            context.sendMessage("");
            return new CropResult(-1, -1, true);
        }

        // 正常处理。
        int beginIndex;
        int endIndex;

        while (true) {
            context.sendMessage("查询到 " + permissions.size() + " 条权限信息");
            context.sendMessage("");
            context.sendMessage("输入 all 查看所有数据");
            context.sendMessage("输入 begin-end 查看指定范围的数据(开始于 0)");
            context.sendMessage("输入 q 退出查询");
            context.sendMessage("");

            String message = context.receiveMessage();

            if (message.equalsIgnoreCase("q")) {
                return new CropResult(-1, -1, true);
            } else if (message.equalsIgnoreCase("all")) {
                beginIndex = 0;
                endIndex = permissions.size();
            } else {
                String[] split = message.split("-");
                if (split.length != 2) {
                    context.sendMessage("输入格式错误");
                    context.sendMessage("");
                    continue;
                }
                try {
                    beginIndex = Integer.parseInt(split[0]);
                    endIndex = Integer.parseInt(split[1]);
                } catch (NumberFormatException e) {
                    context.sendMessage("输入格式错误");
                    context.sendMessage("");
                    continue;
                }
                if (beginIndex < 0 || endIndex > permissions.size() || beginIndex >= endIndex) {
                    String errorMessage = "输入范围错误，begin 和 end 均应介于 [0, " + permissions.size() + "] 之间，" +
                            "且 begin 应小于 end";
                    context.sendMessage(errorMessage);
                    context.sendMessage("");
                    continue;
                }
            }
            break;
        }

        return new CropResult(beginIndex, endIndex, false);
    }

    @SuppressWarnings("DuplicatedCode")
    private void printPermissions(Context context, List<Permission> permissions, int beginIndex, int endIndex)
            throws Exception {
        // 如果数据为空，不进行打印，直接返回。
        if (permissions.isEmpty()) {
            return;
        }
        // 正常打印。
        int sizeDigits = CommandUtil.digits(permissions.size() - 1);
        for (int i = beginIndex; i < endIndex; i++) {
            context.sendMessage(String.format("%" + sizeDigits + "d. %s", i, permissions.get(i)));
        }
    }

    private static final class CropResult {

        private final int beginIndex;
        private final int endIndex;
        private final boolean exitFlag;

        public CropResult(int beginIndex, int endIndex, boolean exitFlag) {
            this.beginIndex = beginIndex;
            this.endIndex = endIndex;
            this.exitFlag = exitFlag;
        }

        public int getBeginIndex() {
            return beginIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public boolean isExitFlag() {
            return exitFlag;
        }

        @Override
        public String toString() {
            return "CropResult{" +
                    "beginIndex=" + beginIndex +
                    ", endIndex=" + endIndex +
                    ", exitFlag=" + exitFlag +
                    '}';
        }
    }
}

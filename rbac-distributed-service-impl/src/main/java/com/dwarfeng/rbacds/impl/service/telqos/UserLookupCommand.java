package com.dwarfeng.rbacds.impl.service.telqos;

import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.service.UserLookupQosService;
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

    private final UserLookupQosService userLookupQosService;

    public UserLookupCommand(UserLookupQosService userLookupQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.userLookupQosService = userLookupQosService;
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
        // 查询数据，并计时。
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        String permissionId = commandLine.getOptionValue(COMMAND_OPTION_LOOKUP_FOR_PERMISSION);
        List<User> users = userLookupQosService.lookupForPermission(new StringIdKey(permissionId));
        tm.stop();

        // 输出执行时间。
        context.sendMessage("");
        context.sendMessage("执行时间：" + tm.getTimeMs() + "ms");
        context.sendMessage("");

        // 输出数据。
        while (true) {
            CropResult cropResult = cropUsers(context, users);
            if (cropResult.isExitFlag()) {
                break;
            }
            context.sendMessage("");
            printUsers(context, users, cropResult.getBeginIndex(), cropResult.getEndIndex());
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private static CropResult cropUsers(Context context, List<User> users) throws Exception {
        // 如果查询到的数据为空，做特殊处理。
        if (users.isEmpty()) {
            context.sendMessage("查询到 0 条用户信息");
            context.sendMessage("");
            return new UserLookupCommand.CropResult(-1, -1, true);
        }

        // 正常处理。
        int beginIndex;
        int endIndex;

        while (true) {
            context.sendMessage("查询到 " + users.size() + " 条用户信息");
            context.sendMessage("");
            context.sendMessage("输入 all 查看所有数据");
            context.sendMessage("输入 begin-end 查看指定范围的数据(开始于 0)");
            context.sendMessage("输入 q 退出查询");
            context.sendMessage("");

            String message = context.receiveMessage();

            if (message.equalsIgnoreCase("q")) {
                return new UserLookupCommand.CropResult(-1, -1, true);
            } else if (message.equalsIgnoreCase("all")) {
                beginIndex = 0;
                endIndex = users.size();
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
                if (beginIndex < 0 || endIndex > users.size() || beginIndex >= endIndex) {
                    String errorMessage = "输入范围错误，begin 和 end 均应介于 [0, " + users.size() + "] 之间，" +
                            "且 begin 应小于 end";
                    context.sendMessage(errorMessage);
                    context.sendMessage("");
                    continue;
                }
            }
            break;
        }

        return new UserLookupCommand.CropResult(beginIndex, endIndex, false);
    }

    @SuppressWarnings("DuplicatedCode")
    private void printUsers(Context context, List<User> users, int beginIndex, int endIndex) throws Exception {
        // 如果数据为空，不进行打印，直接返回。
        if (users.isEmpty()) {
            return;
        }
        // 正常打印。
        int sizeDigits = CommandUtil.digits(users.size() - 1);
        for (int i = beginIndex; i < endIndex; i++) {
            context.sendMessage(String.format("%" + sizeDigits + "d. %s", i, users.get(i)));
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

package com.dwarfeng.rbacds.api.integration.subgrade;

import com.dwarfeng.rbacds.stack.bean.dto.UserPermissionInspectInfo;
import com.dwarfeng.rbacds.stack.bean.dto.UserPermissionInspectResult;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.bean.key.ScopedUserKey;
import com.dwarfeng.rbacds.stack.service.InspectService;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.PermissionHandler;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 作用域权限处理器。
 *
 * <p>
 * 该处理器支持带作用域的权限格式：<code>[scope-string-id];[permission-string-id]</code>。<br>
 * 其中分号作为分隔符。如果 scope-string-id 或 permissionNode-string-id 中含有分号，必须使用反斜杠转义。
 *
 * <p>
 * 转义规则：
 * <ul>
 *     <li><code>\;</code> 表示字面量分号</li>
 *     <li><code>\\</code> 表示字面量反斜杠</li>
 * </ul>
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class ScopedPermissionHandler implements PermissionHandler {

    private static final char SEPARATOR = ';';
    private static final char ESCAPE = '\\';

    private final InspectService inspectService;

    public ScopedPermissionHandler(InspectService inspectService) {
        this.inspectService = inspectService;
    }

    @Override
    public boolean hasPermission(String userStringId, String permissionNode) throws HandlerException {
        try {
            PermissionKey permissionKey = parsePermissionNode(permissionNode);
            ScopedUserKey scopedUserKey = new ScopedUserKey(permissionKey.getScopeStringId(), userStringId);
            UserPermissionInspectInfo inspectInfo = new UserPermissionInspectInfo(
                    scopedUserKey,
                    permissionKey,
                    null,
                    null,
                    false
            );
            UserPermissionInspectResult result = inspectService.inspectUserPermission(inspectInfo);
            return Objects.nonNull(result) && result.isMatchedFlag();
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    // 为了保证代码的可读性，此处代码不做简化。
    @SuppressWarnings("ExtractMethodRecommender")
    @Override
    public boolean hasPermission(String userStringId, List<String> permissionNodes) throws HandlerException {
        try {
            if (Objects.isNull(permissionNodes) || permissionNodes.isEmpty()) {
                return true;
            }
            List<PermissionNodeParseResult> permissionNodeParseResults = parsePermissionNodes(permissionNodes);
            // 按作用域分组。
            Map<String, List<PermissionKey>> permissionKeysMap = new HashMap<>();
            for (PermissionNodeParseResult permissionNodeParseResult : permissionNodeParseResults) {
                String scopeStringId = permissionNodeParseResult.getPermissionKey().getScopeStringId();
                PermissionKey permissionKey = permissionNodeParseResult.getPermissionKey();
                permissionKeysMap.computeIfAbsent(scopeStringId, k -> new ArrayList<>()).add(permissionKey);
            }
            // 对每个作用域执行查询。
            for (Map.Entry<String, List<PermissionKey>> entry : permissionKeysMap.entrySet()) {
                String scopeStringId = entry.getKey();
                List<PermissionKey> scopePermissionKeys = entry.getValue();
                ScopedUserKey scopeScopedUserKey = new ScopedUserKey(scopeStringId, userStringId);
                UserPermissionInspectInfo scopeInspectInfo = new UserPermissionInspectInfo(
                        scopeScopedUserKey,
                        null,
                        scopePermissionKeys,
                        null,
                        false
                );
                UserPermissionInspectResult scopeResult = inspectService.inspectUserPermission(scopeInspectInfo);
                // 有任何一个作用域的查询结果不匹配，整个结果即为不匹配。
                if (Objects.isNull(scopeResult) || !scopeResult.isMatchedFlag()) {
                    return false;
                }
            }
            // 所有作用域的查询结果均匹配。
            return true;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public List<String> getMissingPermissions(String userStringId, List<String> permissionNodes)
            throws HandlerException {
        try {
            if (Objects.isNull(permissionNodes) || permissionNodes.isEmpty()) {
                return Collections.emptyList();
            }
            List<PermissionNodeParseResult> permissionNodeParseResults = parsePermissionNodes(permissionNodes);
            // 按作用域分组。
            Map<String, List<PermissionNodeParseResult>> parseResultsMap = new HashMap<>();
            for (PermissionNodeParseResult permissionNodeParseResult : permissionNodeParseResults) {
                String scopeStringId = permissionNodeParseResult.getPermissionKey().getScopeStringId();
                parseResultsMap.computeIfAbsent(scopeStringId, k -> new ArrayList<>()).add(permissionNodeParseResult);
            }
            List<String> missingPermissions = new ArrayList<>();
            // 对每个作用域执行查询。
            for (Map.Entry<String, List<PermissionNodeParseResult>> entry : parseResultsMap.entrySet()) {
                String scopeStringId = entry.getKey();
                List<PermissionNodeParseResult> scopePermissionNodeParseResults = entry.getValue();
                ScopedUserKey scopeScopedUserKey = new ScopedUserKey(scopeStringId, userStringId);
                UserPermissionInspectInfo inspectInfo = new UserPermissionInspectInfo(
                        scopeScopedUserKey,
                        null,
                        null,
                        null,
                        false
                );
                UserPermissionInspectResult scopeResult = inspectService.inspectUserPermission(inspectInfo);
                // 计算结果，将单个作用域中缺失的权限添加到结果列表中。
                if (Objects.isNull(scopeResult)) {
                    missingPermissions.addAll(
                            scopePermissionNodeParseResults.stream().map(PermissionNodeParseResult::getPermissionNode)
                                    .collect(Collectors.toList())
                    );
                    continue;
                }
                List<PermissionKey> scopeMatchedPermissionKeys =
                        Optional.ofNullable(scopeResult.getMatchedPermissions()).map(
                                f -> f.stream().map(Permission::getKey).collect(Collectors.toList())
                        ).orElse(Collections.emptyList());
                List<PermissionNodeParseResult> scopeMissingPermissionNodeParseResults =
                        scopePermissionNodeParseResults.stream()
                                .filter(p -> !scopeMatchedPermissionKeys.contains(p.getPermissionKey()))
                                .collect(Collectors.toList());
                missingPermissions.addAll(
                        scopeMissingPermissionNodeParseResults.stream()
                                .map(PermissionNodeParseResult::getPermissionNode).collect(Collectors.toList())
                );
            }
            // 返回结果。
            return missingPermissions;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    /**
     * 使用状态机解析权限字符串，将其转化为 PermissionKey。
     *
     * @param permission 权限字符串，格式为 [scope-string-id];[permissionNode-string-id]
     * @return 解析后的 PermissionKey
     * @throws IllegalArgumentException 解析失败时
     */
    private PermissionKey parsePermissionNode(String permission) {
        return parsePermissionNodeToResult(permission).getPermissionKey();
    }

    /**
     * 解析权限字符串列表。
     *
     * @param permissions 权限字符串列表
     * @return 解析结果列表
     * @throws IllegalArgumentException 任一权限解析失败时
     */
    private List<PermissionNodeParseResult> parsePermissionNodes(List<String> permissions) {
        if (Objects.isNull(permissions)) {
            return Collections.emptyList();
        }
        return permissions.stream().map(this::parsePermissionNodeToResult).collect(Collectors.toList());
    }

    /**
     * 使用状态机解析权限字符串。
     *
     * <p>
     * 状态机状态：
     * <ul>
     *     <li>READING_SCOPE: 读取 scope 部分，遇到未转义的分号时切换到 READING_PERMISSION</li>
     *     <li>READING_PERMISSION: 读取 permissionNode 部分</li>
     * </ul>
     *
     * <p>
     * 转义处理：遇到反斜杠时，将下一个字符作为字面量追加到当前部分。
     *
     * @param permissionNode 权限字符串
     * @return 解析结果
     * @throws IllegalArgumentException 解析失败时（格式无效、缺少分隔符、scope 或 permissionNode 为空、转义符作为字符结尾）。
     */
    private PermissionNodeParseResult parsePermissionNodeToResult(String permissionNode) {
        if (Objects.isNull(permissionNode) || permissionNode.isEmpty()) {
            throw new IllegalArgumentException("权限字符串不能为空: " + permissionNode);
        }
        StringBuilder scopeStringIdBuilder = new StringBuilder();
        StringBuilder permissionStringIdBuilder = new StringBuilder();
        PermissionNodeParseState state = PermissionNodeParseState.READING_SCOPE;
        int i = 0;
        while (i < permissionNode.length()) {
            char c = permissionNode.charAt(i);
            switch (state) {
                case READING_SCOPE:
                    if (c == ESCAPE) {
                        if (i + 1 >= permissionNode.length()) {
                            throw new IllegalArgumentException(
                                    "权限字符串格式无效, 转义符不能作为字符结尾: " + permissionNode
                            );
                        }
                        scopeStringIdBuilder.append(permissionNode.charAt(i + 1));
                        i += 2;
                    } else if (c == SEPARATOR) {
                        state = PermissionNodeParseState.READING_PERMISSION;
                        i++;
                    } else {
                        scopeStringIdBuilder.append(c);
                        i++;
                    }
                    break;
                case READING_PERMISSION:
                    if (c == ESCAPE) {
                        if (i + 1 >= permissionNode.length()) {
                            throw new IllegalArgumentException(
                                    "权限字符串格式无效, 转义符不能作为字符结尾: " + permissionNode
                            );
                        }
                        permissionStringIdBuilder.append(permissionNode.charAt(i + 1));
                        i += 2;
                    } else {
                        permissionStringIdBuilder.append(c);
                        i++;
                    }
                    break;
                default:
                    i++;
            }
        }
        String scopeStringId = scopeStringIdBuilder.toString();
        String permissionStringId = permissionStringIdBuilder.toString();
        if (scopeStringId.isEmpty()) {
            throw new IllegalArgumentException("权限字符串格式无效, 缺少作用域: " + permissionNode);
        }
        if (permissionStringId.isEmpty()) {
            throw new IllegalArgumentException("权限字符串格式无效, 缺少权限标识: " + permissionNode);
        }
        return new PermissionNodeParseResult(permissionNode, new PermissionKey(scopeStringId, permissionStringId));
    }

    private enum PermissionNodeParseState {
        READING_SCOPE,
        READING_PERMISSION
    }

    private static final class PermissionNodeParseResult {

        private final String permissionNode;
        private final PermissionKey permissionKey;

        public PermissionNodeParseResult(String permissionNode, PermissionKey permissionKey) {
            this.permissionNode = permissionNode;
            this.permissionKey = permissionKey;
        }

        public String getPermissionNode() {
            return permissionNode;
        }

        public PermissionKey getPermissionKey() {
            return permissionKey;
        }

        @Override
        public String toString() {
            return "PermissionNodeParseResult{" +
                    "permissionNode='" + permissionNode + '\'' +
                    ", permissionKey=" + permissionKey +
                    '}';
        }
    }
}

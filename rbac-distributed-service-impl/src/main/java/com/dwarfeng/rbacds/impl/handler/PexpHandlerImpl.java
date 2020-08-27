package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.handler.PexpHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PexpHandlerImpl implements PexpHandler {

    public static final String DELIMITER = "@";
    public static final String ACCEPT_MODIFIER = "+";
    public static final String REJECT_MODIFIER = "-";
    public static final String GLOBAL_REJECT_MODIFIER = "!";

    @SuppressWarnings("FieldMayBeFinal")
    @Autowired(required = false)
    private Set<PermissionFilter> permissionFilters = new HashSet<>();

    @Override
    @BehaviorAnalyse
    public List<Permission> analysePermissions(Map<Role, List<Pexp>> pexpsMap, List<Permission> allPermissions) throws HandlerException {
        try {
            final Map<Role, Set<Permission>> acceptedPermissionsMap = new HashMap<>();
            final Map<Role, Set<Permission>> rejectedPermissionsMap = new HashMap<>();
            final Set<Permission> globalRejectedPermissions = new HashSet<>();
            for (Map.Entry<Role, List<Pexp>> entry : pexpsMap.entrySet()) {
                Role role = entry.getKey();
                List<Pexp> pexps = entry.getValue();
                for (Pexp pexp : pexps) {
                    resolveSinglePermission(role, pexp, allPermissions, acceptedPermissionsMap, rejectedPermissionsMap, globalRejectedPermissions);
                }
            }
            List<Permission> permissions = new ArrayList<>();
            for (Role role : pexpsMap.keySet()) {
                List<Permission> rolePermissions = new ArrayList<>(acceptedPermissionsMap.getOrDefault(role, Collections.emptySet()));
                rolePermissions.removeAll(rejectedPermissionsMap.getOrDefault(role, Collections.emptySet()));
                permissions.addAll(rolePermissions);
            }
            permissions.removeAll(globalRejectedPermissions);
            permissions.sort(new PermissionComparator());
            return permissions;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    private void resolveSinglePermission(
            Role role, Pexp pexp, List<Permission> allPermissions,
            Map<Role, Set<Permission>> acceptedPermissionsMap,
            Map<Role, Set<Permission>> rejectedPermissionsMap,
            Set<Permission> globalRejectedPermissions) {
        // 取出权限表达式和权限中的相关有用的信息。
        String content = pexp.getContent();
        String[] split = content.split(DELIMITER, 2);
        String modifier = split[0].substring(0, 1);
        String identifier = split[0].substring(1);
        String pattern = split[1];

        // 判断权限表达式的修饰符是否合法。
        if (!Objects.equals(modifier, ACCEPT_MODIFIER) &&
                !Objects.equals(modifier, REJECT_MODIFIER) &&
                !Objects.equals(modifier, GLOBAL_REJECT_MODIFIER)) {
            String format = String.format("权限表达式的修饰符只能为 %s, %s, %s, 而当前为 %s",
                    ACCEPT_MODIFIER, REJECT_MODIFIER, GLOBAL_REJECT_MODIFIER, modifier);
            throw new IllegalArgumentException(format);
        }

        PermissionFilter permissionFilter = permissionFilters.stream()
                .filter(f -> StringUtils.equalsIgnoreCase(identifier, f.getIdentifier())).findAny()
                .orElseThrow(() -> new IllegalArgumentException("未能找到标识符为 " + identifier + " 的权限过滤器"));
        Set<Permission> permissions = allPermissions.stream()
                .filter(permission -> permissionFilter.accept(pattern, permission.getKey().getStringId()))
                .collect(Collectors.toSet());

        if (Objects.equals(modifier, ACCEPT_MODIFIER)) {
            acceptedPermissionsMap.put(role, permissions);
        } else if (Objects.equals(modifier, REJECT_MODIFIER)) {
            rejectedPermissionsMap.put(role, permissions);
        } else {
            globalRejectedPermissions.addAll(permissions);
        }
    }

    private final static class PermissionComparator implements Comparator<Permission> {

        @Override
        public int compare(Permission o1, Permission o2) {
            return o1.getKey().getStringId().compareTo(o2.getKey().getStringId());
        }
    }
}

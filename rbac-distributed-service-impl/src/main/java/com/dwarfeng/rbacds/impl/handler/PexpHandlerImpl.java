package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.handler.PexpHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
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
    public List<Permission> analysePexpPermissions(Map<Role, List<Pexp>> pexpsMap, List<Permission> allPermissions) throws HandlerException {
        try {
            final Map<Role, Set<Permission>> acceptedPermissionsMap = new HashMap<>();
            final Map<Role, Set<Permission>> rejectedPermissionsMap = new HashMap<>();
            final Set<Permission> globalRejectedPermissions = new HashSet<>();
            for (Map.Entry<Role, List<Pexp>> entry : pexpsMap.entrySet()) {
                Role role = entry.getKey();
                List<Pexp> pexps = entry.getValue();
                for (Pexp pexp : pexps) {
                    PexpInfo pexpInfo = analysePexpInfo(pexp);

                    Set<Permission> permissions = allPermissions.stream()
                            .filter(permission -> pexpInfo.getPermissionFilter().accept(
                                    pexpInfo.getPattern(), permission.getKey().getStringId()))
                            .collect(Collectors.toSet());

                    if (Objects.equals(pexpInfo.getModifier(), ACCEPT_MODIFIER)) {
                        acceptedPermissionsMap.put(role, permissions);
                    } else if (Objects.equals(pexpInfo.getModifier(), REJECT_MODIFIER)) {
                        rejectedPermissionsMap.put(role, permissions);
                    } else {
                        globalRejectedPermissions.addAll(permissions);
                    }
                }
            }
            List<Permission> permissions = new ArrayList<>();
            for (Role role : pexpsMap.keySet()) {
                List<Permission> rolePermissions = new ArrayList<>(acceptedPermissionsMap.getOrDefault(role, Collections.emptySet()));
                rolePermissions.removeAll(rejectedPermissionsMap.getOrDefault(role, Collections.emptySet()));
                permissions.addAll(rolePermissions);
            }
            permissions.removeAll(globalRejectedPermissions);
            permissions.sort(PermissionComparator.INSTANCE);
            return permissions;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public PermissionRoleInfo analysePermissionRoles(Map<Role, List<Pexp>> pexpsMap, Permission permission) throws HandlerException {
        try {
            List<StringIdKey> includeRoleKeys = new ArrayList<>();
            List<StringIdKey> excludeRoleKeys = new ArrayList<>();
            for (Map.Entry<Role, List<Pexp>> entry : pexpsMap.entrySet()) {
                Role role = entry.getKey();
                List<Pexp> pexps = entry.getValue();

                boolean acceptedPermissionFlag = false;
                boolean rejectedPermissionFlag = false;
                boolean globalRejectedPermissionFlag = false;

                for (Pexp pexp : pexps) {
                    PexpInfo pexpInfo = analysePexpInfo(pexp);
                    boolean acceptFlag = pexpInfo.getPermissionFilter().accept(pexpInfo.getPattern(), permission.getKey().getStringId());
                    if (!acceptFlag) {
                        continue;
                    }
                    if (Objects.equals(pexpInfo.getModifier(), ACCEPT_MODIFIER)) {
                        acceptedPermissionFlag = true;
                    } else if (Objects.equals(pexpInfo.getModifier(), REJECT_MODIFIER)) {
                        rejectedPermissionFlag = true;
                    } else {
                        globalRejectedPermissionFlag = true;
                    }
                }

                if (globalRejectedPermissionFlag) {
                    excludeRoleKeys.add(role.getKey());
                } else if (acceptedPermissionFlag && !rejectedPermissionFlag) {
                    includeRoleKeys.add(role.getKey());
                }
            }
            return new PermissionRoleInfo(includeRoleKeys, excludeRoleKeys);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    private PexpInfo analysePexpInfo(Pexp pexp) {
        // 取出权限表达式和权限中的相关有用的信息。
        String content = pexp.getContent();
        String[] split = content.split(DELIMITER, 2);
        String modifier = split[0].substring(0, 1);
        String identifier = split[0].substring(1);
        String pattern = split[1];

        // 判断相关信息的合法性。
        if (!Objects.equals(modifier, ACCEPT_MODIFIER) &&
                !Objects.equals(modifier, REJECT_MODIFIER) &&
                !Objects.equals(modifier, GLOBAL_REJECT_MODIFIER)) {
            String format = String.format("权限表达式的修饰符只能为 %s, %s, %s, 而当前为 %s",
                    ACCEPT_MODIFIER, REJECT_MODIFIER, GLOBAL_REJECT_MODIFIER, modifier);
            throw new IllegalArgumentException(format);
        }

        // 根据相关信息构造权限过滤器。
        PermissionFilter permissionFilter = permissionFilters.stream()
                .filter(f -> StringUtils.equalsIgnoreCase(identifier, f.getIdentifier())).findAny()
                .orElseThrow(() -> new IllegalArgumentException("未能找到标识符为 " + identifier + " 的权限过滤器"));

        // 返回权限表达式信息。
        return new PexpInfo(modifier, pattern, permissionFilter);
    }

    private static final class PermissionComparator implements Comparator<Permission> {

        public static PermissionComparator INSTANCE = new PermissionComparator();

        @Override
        public int compare(Permission o1, Permission o2) {
            return o1.getKey().getStringId().compareTo(o2.getKey().getStringId());
        }
    }

    private static class PexpInfo implements Bean {

        private static final long serialVersionUID = 4275024550886016080L;

        private String modifier;
        private String pattern;
        private PermissionFilter permissionFilter;

        public PexpInfo() {
        }

        public PexpInfo(String modifier, String pattern, PermissionFilter permissionFilter) {
            this.modifier = modifier;
            this.pattern = pattern;
            this.permissionFilter = permissionFilter;
        }

        public String getModifier() {
            return modifier;
        }

        public void setModifier(String modifier) {
            this.modifier = modifier;
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public PermissionFilter getPermissionFilter() {
            return permissionFilter;
        }

        public void setPermissionFilter(PermissionFilter permissionFilter) {
            this.permissionFilter = permissionFilter;
        }

        @Override
        public String toString() {
            return "PexpInfo{" +
                    "modifier='" + modifier + '\'' +
                    ", pattern='" + pattern + '\'' +
                    ", permissionFilter=" + permissionFilter +
                    '}';
        }
    }
}

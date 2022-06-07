package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.exception.PexpTestException;
import com.dwarfeng.rbacds.stack.handler.PexpHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class PexpHandlerImpl implements PexpHandler {

    public static final String DELIMITER = "@";
    public static final String ACCEPT_MODIFIER = "+";
    public static final String REJECT_MODIFIER = "-";
    public static final String GLOBAL_REJECT_MODIFIER = "!";

    private final Set<PermissionFilter> permissionFilters;

    private final Map<String, PermissionFilter> permissionFilterMap = new HashMap<>();

    public PexpHandlerImpl(Set<PermissionFilter> permissionFilters) {
        if (Objects.isNull(permissionFilters)) {
            this.permissionFilters = new HashSet<>();
        } else {
            this.permissionFilters = permissionFilters;
        }
    }

    @PostConstruct
    public void init() {
        for (PermissionFilter permissionFilter : permissionFilters) {
            permissionFilterMap.put(permissionFilter.getIdentifier().toUpperCase(), permissionFilter);
        }
    }

    @Override
    @BehaviorAnalyse
    public PermissionReception test(Pexp pexp, Permission permission) throws HandlerException {
        try {
            // 分析并取得权限表达式的信息。
            PexpInfo pexpInfo = analysePexpInfo(pexp);
            PermissionFilter permissionFilter = pexpInfo.getPermissionFilter();
            String pattern = pexpInfo.getPattern();
            String modifier = pexpInfo.getModifier();

            // 判断权限表达式对应的过滤器是否接受指定的权限文本。
            boolean acceptFlag = permissionFilter.accept(pattern, permission);

            if (!acceptFlag) {
                return PermissionReception.NOT_ACCEPT;
            } else if (Objects.equals(modifier, ACCEPT_MODIFIER)) {
                return PermissionReception.ACCEPT;
            } else if (Objects.equals(modifier, REJECT_MODIFIER)) {
                return PermissionReception.REJECT;
            } else {
                return PermissionReception.GLOBAL_REJECT;
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public Map<PermissionReception, List<Permission>> testAll(Pexp pexp, @SkipRecord List<Permission> permissions)
            throws HandlerException {
        try {
            // 分析并取得权限表达式的信息。
            PexpInfo pexpInfo = analysePexpInfo(pexp);
            PermissionFilter permissionFilter = pexpInfo.getPermissionFilter();
            String pattern = pexpInfo.getPattern();
            String modifier = pexpInfo.getModifier();

            List<Permission> acceptedPermissions = new ArrayList<>();
            List<Permission> notAcceptedPermissions = new ArrayList<>();

            // 遍历所有权限，确认权限过滤器的接收情况。
            for (Permission permission : permissions) {
                // 判断权限表达式对应的过滤器是否接受指定的权限文本。
                try {
                    boolean acceptFlag = permissionFilter.accept(pattern, permission);
                    if (acceptFlag) {
                        acceptedPermissions.add(permission);
                    } else {
                        notAcceptedPermissions.add(permission);
                    }
                } catch (Exception e) {
                    throw new PexpTestException(e, pexp, permission);
                }
            }

            if (acceptedPermissions.isEmpty()) {
                acceptedPermissions = Collections.emptyList();
            }
            if (notAcceptedPermissions.isEmpty()) {
                notAcceptedPermissions = Collections.emptyList();
            }

            // 构造结果并返回。
            Map<PermissionReception, List<Permission>> result = new EnumMap<>(PermissionReception.class);
            result.put(PermissionReception.NOT_ACCEPT, notAcceptedPermissions);
            if (Objects.equals(modifier, ACCEPT_MODIFIER)) {
                result.put(PermissionReception.ACCEPT, acceptedPermissions);
                result.put(PermissionReception.REJECT, Collections.emptyList());
                result.put(PermissionReception.GLOBAL_REJECT, Collections.emptyList());
            } else if (Objects.equals(modifier, REJECT_MODIFIER)) {
                result.put(PermissionReception.ACCEPT, Collections.emptyList());
                result.put(PermissionReception.REJECT, acceptedPermissions);
                result.put(PermissionReception.GLOBAL_REJECT, Collections.emptyList());
            } else {
                result.put(PermissionReception.ACCEPT, Collections.emptyList());
                result.put(PermissionReception.REJECT, Collections.emptyList());
                result.put(PermissionReception.GLOBAL_REJECT, acceptedPermissions);
            }
            return result;
        } catch (HandlerException e) {
            throw e;
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

        // 根据相关信息查找权限过滤器。
        PermissionFilter permissionFilter = permissionFilterMap.get(identifier.toUpperCase());
        if (Objects.isNull(permissionFilter)) {
            throw new IllegalArgumentException("未能找到标识符为 " + identifier.toUpperCase() + " 的权限过滤器");
        }

        // 返回权限表达式信息。
        return new PexpInfo(modifier, pattern, permissionFilter);
    }

    private static class PexpInfo implements Bean {

        private static final long serialVersionUID = 4275024550886016080L;

        private String modifier;
        private String pattern;
        private PermissionFilter permissionFilter;

        // 由于实体继承了 Bean 接口，因此必须有无参数构造器。
        @SuppressWarnings("unused")
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

package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;

/**
 * 角色权限查看结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class RolePermissionInspectResult implements Dto {

    private static final long serialVersionUID = 1556849230788951483L;

    /**
     * 角色匹配的权限列表。
     */
    private List<Permission> matchedPermissions;

    /**
     * 权限匹配标志位。
     */
    private boolean matchedFlag;

    /**
     * 角色接受的权限列表。
     *
     * <p>
     * 只在详细模式下返回。
     */
    private List<Permission> acceptedPermissions;

    /**
     * 角色拒绝的权限列表。
     *
     * <p>
     * 只在详细模式下返回。
     */
    private List<Permission> rejectedPermissions;

    /**
     * 角色全局拒绝的权限列表。
     *
     * <p>
     * 只在详细模式下返回。
     */
    private List<Permission> globalRejectedPermissions;

    public RolePermissionInspectResult() {
    }

    public RolePermissionInspectResult(
            List<Permission> matchedPermissions, boolean matchedFlag, List<Permission> acceptedPermissions,
            List<Permission> rejectedPermissions, List<Permission> globalRejectedPermissions
    ) {
        this.matchedPermissions = matchedPermissions;
        this.matchedFlag = matchedFlag;
        this.acceptedPermissions = acceptedPermissions;
        this.rejectedPermissions = rejectedPermissions;
        this.globalRejectedPermissions = globalRejectedPermissions;
    }

    public List<Permission> getMatchedPermissions() {
        return matchedPermissions;
    }

    public void setMatchedPermissions(List<Permission> matchedPermissions) {
        this.matchedPermissions = matchedPermissions;
    }

    public boolean isMatchedFlag() {
        return matchedFlag;
    }

    public void setMatchedFlag(boolean matchedFlag) {
        this.matchedFlag = matchedFlag;
    }

    public List<Permission> getAcceptedPermissions() {
        return acceptedPermissions;
    }

    public void setAcceptedPermissions(List<Permission> acceptedPermissions) {
        this.acceptedPermissions = acceptedPermissions;
    }

    public List<Permission> getRejectedPermissions() {
        return rejectedPermissions;
    }

    public void setRejectedPermissions(List<Permission> rejectedPermissions) {
        this.rejectedPermissions = rejectedPermissions;
    }

    public List<Permission> getGlobalRejectedPermissions() {
        return globalRejectedPermissions;
    }

    public void setGlobalRejectedPermissions(List<Permission> globalRejectedPermissions) {
        this.globalRejectedPermissions = globalRejectedPermissions;
    }

    @Override
    public String toString() {
        return "RolePermissionInspectResult{" +
                "matchedPermissions=" + matchedPermissions +
                ", matchedFlag=" + matchedFlag +
                ", acceptedPermissions=" + acceptedPermissions +
                ", rejectedPermissions=" + rejectedPermissions +
                ", globalRejectedPermissions=" + globalRejectedPermissions +
                '}';
    }
}

package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.bean.key.ScopedUserKey;
import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;

import java.io.Serial;
import java.util.List;

/**
 * 以用户为依据的权限视图查看信息。
 *
 * @author DwArFeng
 * @since 2.0.2
 */
public class PermissionViewOfUserInspectInfo implements Dto {

    @Serial
    private static final long serialVersionUID = -1220733829707091995L;

    /**
     * 查看的作用域用户键主键。
     */
    private ScopedUserKey scopedUserKey;

    /**
     * 查询指定的权限主键是否匹配。
     */
    private PermissionKey permissionKeyMatched;

    /**
     * 查询指定的权限主键列表是否全部匹配。
     */
    private List<PermissionKey> allOfPermissionKeysMatched;

    /**
     * 查询指定的权限主键列表是否至少有一个匹配。
     */
    private List<PermissionKey> anyOfPermissionKeysMatched;

    /**
     * 是否处于详细模式。
     */
    private boolean detailMode;

    public PermissionViewOfUserInspectInfo() {
    }

    public PermissionViewOfUserInspectInfo(
            ScopedUserKey scopedUserKey, PermissionKey permissionKeyMatched,
            List<PermissionKey> allOfPermissionKeysMatched, List<PermissionKey> anyOfPermissionKeysMatched,
            boolean detailMode
    ) {
        this.scopedUserKey = scopedUserKey;
        this.permissionKeyMatched = permissionKeyMatched;
        this.allOfPermissionKeysMatched = allOfPermissionKeysMatched;
        this.anyOfPermissionKeysMatched = anyOfPermissionKeysMatched;
        this.detailMode = detailMode;
    }

    public ScopedUserKey getScopedUserKey() {
        return scopedUserKey;
    }

    public void setScopedUserKey(ScopedUserKey scopedUserKey) {
        this.scopedUserKey = scopedUserKey;
    }

    public PermissionKey getPermissionKeyMatched() {
        return permissionKeyMatched;
    }

    public void setPermissionKeyMatched(PermissionKey permissionKeyMatched) {
        this.permissionKeyMatched = permissionKeyMatched;
    }

    public List<PermissionKey> getAllOfPermissionKeysMatched() {
        return allOfPermissionKeysMatched;
    }

    public void setAllOfPermissionKeysMatched(List<PermissionKey> allOfPermissionKeysMatched) {
        this.allOfPermissionKeysMatched = allOfPermissionKeysMatched;
    }

    public List<PermissionKey> getAnyOfPermissionKeysMatched() {
        return anyOfPermissionKeysMatched;
    }

    public void setAnyOfPermissionKeysMatched(List<PermissionKey> anyOfPermissionKeysMatched) {
        this.anyOfPermissionKeysMatched = anyOfPermissionKeysMatched;
    }

    public boolean isDetailMode() {
        return detailMode;
    }

    public void setDetailMode(boolean detailMode) {
        this.detailMode = detailMode;
    }

    @Override
    public String toString() {
        return "PermissionViewOfUserInspectInfo{" +
                "scopedUserKey=" + scopedUserKey +
                ", permissionKeyMatched=" + permissionKeyMatched +
                ", allOfPermissionKeysMatched=" + allOfPermissionKeysMatched +
                ", anyOfPermissionKeysMatched=" + anyOfPermissionKeysMatched +
                ", detailMode=" + detailMode +
                '}';
    }
}

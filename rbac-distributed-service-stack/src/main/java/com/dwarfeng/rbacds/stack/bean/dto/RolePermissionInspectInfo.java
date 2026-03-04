package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.bean.key.ScopedRoleKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;

/**
 * 角色权限查看信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class RolePermissionInspectInfo implements Dto {

    private static final long serialVersionUID = 6960106385663348940L;

    /**
     * 查看的作用域角色键主键。
     */
    private ScopedRoleKey scopedRoleKey;

    /**
     * 查询指定的权限主键是否匹配。
     *
     * <p>
     * 用户匹配参数顺序如下：
     * <ol>
     *     <li><code>permissionKeyMatched</code></li>
     *     <li><code>allOfPermissionKeysMatched</code></li>
     *     <li><code>anyOfPermissionKeysMatched</code></li>
     * </ol>
     *
     * <p>
     * 服务将会按照上述顺序搜索第一个非 <code>null</code> 的参数，并使用该参数进行查询。
     * 并将匹配结果写入 {@link RolePermissionInspectResult#isMatchedFlag()} 字段中。
     *
     * <p>
     * 如果所有的用户匹配参数都为 <code>null</code>，则查询结果中的匹配标志没有意义。
     */
    private PermissionKey permissionKeyMatched;

    /**
     * 查询指定的权限主键列表是否全部匹配。
     *
     * <p>
     * 用户匹配参数顺序如下：
     * <ol>
     *     <li><code>permissionKeyMatched</code></li>
     *     <li><code>allOfPermissionKeysMatched</code></li>
     *     <li><code>anyOfPermissionKeysMatched</code></li>
     * </ol>
     *
     * <p>
     * 服务将会按照上述顺序搜索第一个非 <code>null</code> 的参数，并使用该参数进行查询。
     * 并将匹配结果写入 {@link RolePermissionInspectResult#isMatchedFlag()} 字段中。
     *
     * <p>
     * 如果所有的用户匹配参数都为 <code>null</code>，则查询结果中的匹配标志没有意义。
     */
    private List<PermissionKey> allOfPermissionKeysMatched;

    /**
     * 查询指定的权限主键列表是否至少有一个匹配。
     *
     * <p>
     * 用户匹配参数顺序如下：
     * <ol>
     *     <li><code>permissionKeyMatched</code></li>
     *     <li><code>allOfPermissionKeysMatched</code></li>
     *     <li><code>anyOfPermissionKeysMatched</code></li>
     * </ol>
     *
     * <p>
     * 服务将会按照上述顺序搜索第一个非 <code>null</code> 的参数，并使用该参数进行查询。
     * 并将匹配结果写入 {@link RolePermissionInspectResult#isMatchedFlag()} 字段中。
     *
     * <p>
     * 如果所有的用户匹配参数都为 <code>null</code>，则查询结果中的匹配标志没有意义。
     */
    private List<PermissionKey> anyOfPermissionKeysMatched;

    /**
     * 是否处于详细模式。
     *
     * <p>
     * 该参数为 <code>true</code> 时，查询结果会包含更多的细节信息，可用于调试。
     */
    private boolean detailMode;

    public RolePermissionInspectInfo() {
    }

    public RolePermissionInspectInfo(
            ScopedRoleKey scopedRoleKey, PermissionKey permissionKeyMatched,
            List<PermissionKey> allOfPermissionKeysMatched, List<PermissionKey> anyOfPermissionKeysMatched,
            boolean detailMode
    ) {
        this.scopedRoleKey = scopedRoleKey;
        this.permissionKeyMatched = permissionKeyMatched;
        this.allOfPermissionKeysMatched = allOfPermissionKeysMatched;
        this.anyOfPermissionKeysMatched = anyOfPermissionKeysMatched;
        this.detailMode = detailMode;
    }

    public ScopedRoleKey getScopedRoleKey() {
        return scopedRoleKey;
    }

    public void setScopedRoleKey(ScopedRoleKey scopedRoleKey) {
        this.scopedRoleKey = scopedRoleKey;
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
        return "RolePermissionInspectInfo{" +
                "scopedRoleKey=" + scopedRoleKey +
                ", permissionKeyMatched=" + permissionKeyMatched +
                ", allOfPermissionKeysMatched=" + allOfPermissionKeysMatched +
                ", anyOfPermissionKeysMatched=" + anyOfPermissionKeysMatched +
                ", detailMode=" + detailMode +
                '}';
    }
}

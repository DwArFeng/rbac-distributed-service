package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.bean.key.ScopedUserKey;
import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;

import java.io.Serial;
import java.util.List;

/**
 * 用户权限查看信息。
 *
 * <p>
 * 该方法命名易引起歧义，已由 {@link PermissionViewOfUserInspectInfo} 替代。
 * 新命名采用 {@code inspectXxxViewOfXxx} 格式，使「查询依据」与「查询对象」的关系更加明确。
 *
 * @author DwArFeng
 * @see PermissionViewOfUserInspectInfo
 * @since 2.0.0
 * @deprecated 命名歧义，请使用 {@link PermissionViewOfUserInspectInfo}
 */
@Deprecated
public class UserPermissionInspectInfo implements Dto {

    @Serial
    private static final long serialVersionUID = 5470825134179465544L;

    /**
     * 查看的作用域用户键主键。
     */
    private ScopedUserKey scopedUserKey;

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
     */
    private boolean detailMode;

    public UserPermissionInspectInfo() {
    }

    public UserPermissionInspectInfo(
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
        return "UserPermissionInspectInfo{" +
                "scopedUserKey=" + scopedUserKey +
                ", permissionKeyMatched=" + permissionKeyMatched +
                ", allOfPermissionKeysMatched=" + allOfPermissionKeysMatched +
                ", anyOfPermissionKeysMatched=" + anyOfPermissionKeysMatched +
                ", detailMode=" + detailMode +
                '}';
    }
}

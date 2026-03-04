package com.dwarfeng.rbacds.stack.bean.dto;

import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.List;

/**
 * 权限用户查看信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PermissionUserInspectInfo implements Dto {

    private static final long serialVersionUID = 8697246840708625786L;

    /**
     * 查看的权限主键。
     */
    private PermissionKey permissionKey;

    /**
     * 查询指定的用户主键是否匹配。
     *
     * <p>
     * 用户匹配参数顺序如下：
     * <ol>
     *     <li><code>userKeyMatched</code></li>
     *     <li><code>allOfUserKeysMatched</code></li>
     *     <li><code>anyOfUserKeysMatched</code></li>
     * </ol>
     *
     * <p>
     * 服务将会按照上述顺序搜索第一个非 <code>null</code> 的参数，并使用该参数进行查询。
     * 并将匹配结果写入 {@link PermissionUserInspectResult#isMatchedFlag()} 字段中。
     *
     * <p>
     * 如果所有的用户匹配参数都为 <code>null</code>，则查询结果中的匹配标志没有意义。
     */
    private StringIdKey userKeyMatched;

    /**
     * 查询指定的用户主键列表是否全部匹配。
     *
     * <p>
     * 用户匹配参数顺序如下：
     * <ol>
     *     <li><code>userKeyMatched</code></li>
     *     <li><code>allOfUserKeysMatched</code></li>
     *     <li><code>anyOfUserKeysMatched</code></li>
     * </ol>
     *
     * <p>
     * 服务将会按照上述顺序搜索第一个非 <code>null</code> 的参数，并使用该参数进行查询。
     * 并将匹配结果写入 {@link PermissionUserInspectResult#isMatchedFlag()} 字段中。
     *
     * <p>
     * 如果所有的用户匹配参数都为 <code>null</code>，则查询结果中的匹配标志没有意义。
     */
    private List<StringIdKey> allOfUserKeysMatched;

    /**
     * 查询指定的用户主键列表是否至少有一个匹配。
     *
     * <p>
     * 用户匹配参数顺序如下：
     * <ol>
     *     <li><code>userKeyMatched</code></li>
     *     <li><code>allOfUserKeysMatched</code></li>
     *     <li><code>anyOfUserKeysMatched</code></li>
     * </ol>
     *
     * <p>
     * 服务将会按照上述顺序搜索第一个非 <code>null</code> 的参数，并使用该参数进行查询。
     * 并将匹配结果写入 {@link PermissionUserInspectResult#isMatchedFlag()} 字段中。
     *
     * <p>
     * 如果所有的用户匹配参数都为 <code>null</code>，则查询结果中的匹配标志没有意义。
     */
    private List<StringIdKey> anyOfUserKeysMatched;

    /**
     * 是否处于详细模式。
     *
     * <p>
     * 该参数为 <code>true</code> 时，查询结果会包含更多的细节信息，可用于调试。
     */
    private boolean detailMode;

    public PermissionUserInspectInfo() {
    }

    public PermissionUserInspectInfo(
            PermissionKey permissionKey, StringIdKey userKeyMatched, List<StringIdKey> allOfUserKeysMatched,
            List<StringIdKey> anyOfUserKeysMatched, boolean detailMode
    ) {
        this.permissionKey = permissionKey;
        this.userKeyMatched = userKeyMatched;
        this.allOfUserKeysMatched = allOfUserKeysMatched;
        this.anyOfUserKeysMatched = anyOfUserKeysMatched;
        this.detailMode = detailMode;
    }

    public PermissionKey getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(PermissionKey permissionKey) {
        this.permissionKey = permissionKey;
    }

    public StringIdKey getUserKeyMatched() {
        return userKeyMatched;
    }

    public void setUserKeyMatched(StringIdKey userKeyMatched) {
        this.userKeyMatched = userKeyMatched;
    }

    public List<StringIdKey> getAllOfUserKeysMatched() {
        return allOfUserKeysMatched;
    }

    public void setAllOfUserKeysMatched(List<StringIdKey> allOfUserKeysMatched) {
        this.allOfUserKeysMatched = allOfUserKeysMatched;
    }

    public List<StringIdKey> getAnyOfUserKeysMatched() {
        return anyOfUserKeysMatched;
    }

    public void setAnyOfUserKeysMatched(List<StringIdKey> anyOfUserKeysMatched) {
        this.anyOfUserKeysMatched = anyOfUserKeysMatched;
    }

    public boolean isDetailMode() {
        return detailMode;
    }

    public void setDetailMode(boolean detailMode) {
        this.detailMode = detailMode;
    }

    @Override
    public String toString() {
        return "PermissionUserInspectInfo{" +
                "permissionKey=" + permissionKey +
                ", userKeyMatched=" + userKeyMatched +
                ", allOfUserKeysMatched=" + allOfUserKeysMatched +
                ", anyOfUserKeysMatched=" + anyOfUserKeysMatched +
                ", detailMode=" + detailMode +
                '}';
    }
}

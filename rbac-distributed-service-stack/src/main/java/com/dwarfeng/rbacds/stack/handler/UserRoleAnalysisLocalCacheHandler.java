package com.dwarfeng.rbacds.stack.handler;

import com.dwarfeng.rbacds.stack.struct.UserRoleAnalysis;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

/**
 * 用户角色分析本地缓存处理器。
 *
 * <p>
 * 该处理器实现了 <code>LocalCacheHandler&lt;StringIdKey, UserRoleAnalysis&gt;</code> 接口，
 * 用于处理与用户角色分析相关的本地缓存。<br>
 * 对于 <code>LocalCacheHandler&lt;K, V&gt;</code> 接口中的泛型参数:
 * <table>
 *     <tr>
 *         <th></th>
 *         <th>类型</th>
 *         <th>含义</th>
 *     </tr>
 *     <tr>
 *         <td>K</td>
 *         <td>StringIdKey</td>
 *         <td>用户键</td>
 *     </tr>
 *     <tr>
 *         <td>V</td>
 *         <td>UserRoleAnalysis</td>
 *         <td>用户角色分析结果</td>
 *     </tr>
 * </table>
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface UserRoleAnalysisLocalCacheHandler extends LocalCacheHandler<StringIdKey, UserRoleAnalysis> {
}

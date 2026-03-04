package com.dwarfeng.rbacds.stack.handler;

import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.struct.PermissionUserAnalysis;
import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

/**
 * 权限用户分析本地缓存处理器。
 *
 * <p>
 * 该处理器实现了 <code>LocalCacheHandler&lt;PermissionKey, PermissionUserAnalysis&gt;</code> 接口，
 * 用于处理与权限用户分析相关的本地缓存。<br>
 * 对于 <code>LocalCacheHandler&lt;K, V&gt;</code> 接口中的泛型参数:
 * <table>
 *     <tr>
 *         <th></th>
 *         <th>类型</th>
 *         <th>含义</th>
 *     </tr>
 *     <tr>
 *         <td>K</td>
 *         <td>PermissionKey</td>
 *         <td>权限键</td>
 *     </tr>
 *     <tr>
 *         <td>V</td>
 *         <td>PermissionUserAnalysis</td>
 *         <td>权限用户分析结果</td>
 *     </tr>
 * </table>
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface PermissionUserAnalysisLocalCacheHandler extends
        LocalCacheHandler<PermissionKey, PermissionUserAnalysis> {
}

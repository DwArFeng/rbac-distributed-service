package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 用户维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface UserMaintainService extends BatchCrudService<StringIdKey, User>, PresetLookupService<User>,
        EntireLookupService<User> {

    /**
     * 查询在指定角色集合，且不在另一个指定角色集合的用户。
     *
     * <p>
     * 该预设查询会关联 角色用户关系 实体进行查询，使用 <code>in</code> 和 <code>not in</code> 进行查询。
     *
     * <p>
     * 返回的结果按照主键升序排序。
     *
     * <p>
     * 预设参数表：
     * <table>
     *     <tr>
     *         <th>索引</th>
     *         <th>参数类型</th>
     *         <th>说明</th>
     *     </tr>
     *     <tr>
     *         <td>0</td>
     *         <td>Collection&lt;StringIdKey&gt;</td>
     *         <td>指定角色集合（<code>in</code>）</td>
     *     </tr>
     *     <tr>
     *         <td>1</td>
     *         <td>Collection&lt;StringIdKey&gt;</td>
     *         <td>另一个指定角色集合（<code>not in</code>）</td>
     *     </tr>
     * </table>
     */
    String ROLE_IN_ROLE_NOT_IN_KEY_ASC = "role_in_role_not_in_key_asc";

    String ID_LIKE = "id_like";
}

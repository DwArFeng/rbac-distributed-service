package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.data.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.data.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.data.stack.service.PresetLookupService;

/**
 * 角色维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface RoleMaintainService extends BatchCrudService<StringIdKey, Role>, PresetLookupService<Role>,
        EntireLookupService<Role> {

    // region 预设查询 - 业务逻辑

    /**
     * 用户的可用子角色。
     *
     * <p>
     * 该预设查询会查找给定的用户主键对应的 角色用户关系 实体，并查找这些实体对应的角色。
     * 并保证 角色用户关系 的 <code>enabled</code> 字段为 <code>true</code>，
     * 以及 角色 的 <code>enabled</code> 字段为 <code>true</code>。
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
     *         <td>{@link StringIdKey}</td>
     *         <td>用户主键</td>
     *     </tr>
     * </table>
     */
    String CHILD_FOR_USER_AVAILABLE_KEY_ASC = "child_for_user_available_key_asc";

    // endregion

    // region 预设查询 - UI

    String ENABLED = "enabled";
    String ID_LIKE = "id_like";

    /**
     * 非子项于用户。
     *
     * <p>
     * 该预设查询会查找给定的用户主键对应的、用户还未关联的所有角色。
     * 即：在 角色用户关系 表中不存在该用户与角色的关联记录的角色。
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
     *         <td>{@link StringIdKey}</td>
     *         <td>用户主键</td>
     *     </tr>
     * </table>
     *
     * @since 2.0.2
     */
    String NOT_CHILD_FOR_USER_KEY_ASC = "not_child_for_user_key_asc";

    // endregion
}

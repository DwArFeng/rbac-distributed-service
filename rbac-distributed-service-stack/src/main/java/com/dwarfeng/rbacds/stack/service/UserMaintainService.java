package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.CrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

import java.util.List;

/**
 * 用户维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface UserMaintainService extends CrudService<StringIdKey, User>, PresetLookupService<User>,
        EntireLookupService<User> {

    String ID_LIKE = "id_like";
    String CHILD_FOR_ROLE = "child_for_role";

    /**
     * 添加用户与角色的关联。
     *
     * @param userIdKey 指定的用户主键。
     * @param roleIdKey 指定的角色主键。
     * @throws ServiceException 服务异常。
     */
    void addRoleRelation(StringIdKey userIdKey, StringIdKey roleIdKey) throws ServiceException;

    /**
     * 删除用户与角色的关联。
     *
     * @param userIdKey 指定的用户主键。
     * @param roleIdKey 指定的角色主键。
     * @throws ServiceException 服务异常。
     */
    void deleteRoleRelation(StringIdKey userIdKey, StringIdKey roleIdKey) throws ServiceException;

    /**
     * 添加用户与角色的关联。
     *
     * @param userIdKey  指定的用户主键。
     * @param roleIdKeys 指定的角色主键组成的列表。
     * @throws ServiceException 服务异常。
     */
    void batchAddRoleRelations(StringIdKey userIdKey, List<StringIdKey> roleIdKeys) throws ServiceException;

    /**
     * 删除用户与角色的关联。
     *
     * @param userIdKey  指定的用户主键。
     * @param roleIdKeys 指定的角色主键组成的列表。
     * @throws ServiceException 服务异常。
     */
    void batchDeleteRoleRelations(StringIdKey userIdKey, List<StringIdKey> roleIdKeys) throws ServiceException;
}

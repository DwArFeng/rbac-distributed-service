package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.CrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

import java.util.List;

/**
 * 角色维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface RoleMaintainService extends CrudService<StringIdKey, Role>, PresetLookupService<Role>,
        EntireLookupService<Role> {

    String ROLE_FOR_USER = "role_for_user";
    String ENABLED_ROLE_FOR_USER = "enabled_role_for_user";
    String ID_LIKE = "id_like";
    String ENABLED = "enabled";

    /**
     * 添加角色与用户的关联。
     *
     * @param roleIdKey 指定的角色主键。
     * @param userIdKey 指定的用户主键。
     * @throws ServiceException 服务异常。
     */
    void addUserRelation(StringIdKey roleIdKey, StringIdKey userIdKey) throws ServiceException;

    /**
     * 添加角色与用户的关联。
     *
     * @param roleIdKey 指定的角色主键。
     * @param userIdKey 指定的用户主键。
     * @throws ServiceException 服务异常。
     */
    void deleteUserRelation(StringIdKey roleIdKey, StringIdKey userIdKey) throws ServiceException;

    /**
     * 添加角色与用户的关联。
     *
     * @param roleIdKey  指定的角色主键。
     * @param userIdKeys 指定的用户主键组成的列表。
     * @throws ServiceException 服务异常。
     */
    void batchAddUserRelations(StringIdKey roleIdKey, List<StringIdKey> userIdKeys) throws ServiceException;

    /**
     * 添加角色与用户的关联。
     *
     * @param roleIdKey  指定的角色主键。
     * @param userIdKeys 指定的用户主键组成的列表。
     * @throws ServiceException 服务异常。
     */
    void batchDeleteUserRelations(StringIdKey roleIdKey, List<StringIdKey> userIdKeys) throws ServiceException;
}

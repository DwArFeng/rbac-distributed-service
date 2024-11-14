package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

import java.util.List;

/**
 * 角色维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface RoleMaintainService extends BatchCrudService<StringIdKey, Role>, PresetLookupService<Role>,
        EntireLookupService<Role> {

    String ROLE_FOR_USER = "role_for_user";
    String ENABLED_ROLE_FOR_USER = "enabled_role_for_user";
    String ID_LIKE = "id_like";
    String ENABLED = "enabled";

    /**
     * 判断指定的角色和用户是否存在关联。
     *
     * @param roleKey 指定的角色主键。
     * @param userKey 指定的用户主键。
     * @return 指定的角色和用户是否存在关联。
     * @throws ServiceException 服务异常。
     * @since 1.6.0
     */
    boolean existsUserRelation(StringIdKey roleKey, StringIdKey userKey) throws ServiceException;

    /**
     * 添加角色与用户的关联。
     *
     * @param roleKey 指定的角色主键。
     * @param userKey 指定的用户主键。
     * @throws ServiceException 服务异常。
     */
    void addUserRelation(StringIdKey roleKey, StringIdKey userKey) throws ServiceException;

    /**
     * 删除角色与用户的关联。
     *
     * @param roleKey 指定的角色主键。
     * @param userKey 指定的用户主键。
     * @throws ServiceException 服务异常。
     */
    void deleteUserRelation(StringIdKey roleKey, StringIdKey userKey) throws ServiceException;

    /**
     * 判断指定的角色和指定的所有用户是否全部存在关联。
     *
     * @param roleKey  指定的角色主键。
     * @param userKeys 指定的用户主键组成的列表。
     * @return 指定的角色和指定的所有用户是否全部存在关联。
     * @throws ServiceException 服务异常。
     * @since 1.6.0
     */
    boolean existsAllUserRelations(StringIdKey roleKey, List<StringIdKey> userKeys) throws ServiceException;

    /**
     * 判断指定的角色和指定的所有用户是否全部不存在关联。
     *
     * @param roleKey  指定的角色主键。
     * @param userKeys 指定的用户主键组成的列表。
     * @return 指定的角色和指定的所有用户是否全部不存在关联。
     * @throws ServiceException 服务异常。
     * @since 1.6.0
     */
    boolean existsNonUserRelations(StringIdKey roleKey, List<StringIdKey> userKeys) throws ServiceException;

    /**
     * 添加角色与用户的关联。
     *
     * @param roleKey 指定的角色主键。
     * @param userKey 指定的用户主键组成的列表。
     * @throws ServiceException 服务异常。
     */
    void batchAddUserRelations(StringIdKey roleKey, List<StringIdKey> userKey) throws ServiceException;

    /**
     * 删除角色与用户的关联。
     *
     * @param roleKey 指定的角色主键。
     * @param userKey 指定的用户主键组成的列表。
     * @throws ServiceException 服务异常。
     */
    void batchDeleteUserRelations(StringIdKey roleKey, List<StringIdKey> userKey) throws ServiceException;
}

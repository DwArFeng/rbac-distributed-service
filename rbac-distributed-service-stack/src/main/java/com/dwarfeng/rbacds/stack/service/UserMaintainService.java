package com.dwarfeng.rbacds.stack.service;

import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

import java.util.List;

/**
 * 用户维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface UserMaintainService extends BatchCrudService<StringIdKey, User>, PresetLookupService<User>,
        EntireLookupService<User> {

    String ID_LIKE = "id_like";
    String CHILD_FOR_ROLE = "child_for_role";
    String CHILD_FOR_ROLE_SET = "child_for_role_set";

    /**
     * 判断指定的用户和角色是否存在关联。
     *
     * @param userKey 指定的用户主键。
     * @param roleKey 指定的角色主键。
     * @return 指定的用户和角色是否存在关联。
     * @throws ServiceException 服务异常。
     * @since 1.6.0
     */
    boolean existsRoleRelation(StringIdKey userKey, StringIdKey roleKey) throws ServiceException;

    /**
     * 添加用户与角色的关联。
     *
     * @param userKey 指定的用户主键。
     * @param roleKey 指定的角色主键。
     * @throws ServiceException 服务异常。
     */
    void addRoleRelation(StringIdKey userKey, StringIdKey roleKey) throws ServiceException;

    /**
     * 删除用户与角色的关联。
     *
     * @param userKey 指定的用户主键。
     * @param roleKey 指定的角色主键。
     * @throws ServiceException 服务异常。
     */
    void deleteRoleRelation(StringIdKey userKey, StringIdKey roleKey) throws ServiceException;

    /**
     * 判断指定的用户和指定的所有角色是否全部存在关联。
     *
     * @param userKey  指定的用户主键。
     * @param roleKeys 指定的角色主键组成的列表。
     * @return 指定的用户和指定的所有角色是否全部存在关联。
     * @throws ServiceException 服务异常。
     * @since 1.6.0
     */
    boolean existsAllRoleRelations(StringIdKey userKey, List<StringIdKey> roleKeys) throws ServiceException;

    /**
     * 判断指定的用户和指定的所有角色是否全部不存在关联。
     *
     * @param userKey  指定的用户主键。
     * @param roleKeys 指定的角色主键组成的列表。
     * @return 指定的用户和指定的所有角色是否全部不存在关联。
     * @throws ServiceException 服务异常。
     * @since 1.6.0
     */
    boolean existsNonRoleRelations(StringIdKey userKey, List<StringIdKey> roleKeys) throws ServiceException;

    /**
     * 添加用户与角色的关联。
     *
     * @param userKey 指定的用户主键。
     * @param roleKey 指定的角色主键组成的列表。
     * @throws ServiceException 服务异常。
     */
    void batchAddRoleRelations(StringIdKey userKey, List<StringIdKey> roleKey) throws ServiceException;

    /**
     * 删除用户与角色的关联。
     *
     * @param userKey 指定的用户主键。
     * @param roleKey 指定的角色主键组成的列表。
     * @throws ServiceException 服务异常。
     */
    void batchDeleteRoleRelations(StringIdKey userKey, List<StringIdKey> roleKey) throws ServiceException;
}

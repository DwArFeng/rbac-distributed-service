package com.dwarfeng.rbacds.stack.dao;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;
import com.dwarfeng.subgrade.stack.exception.DaoException;

import java.util.List;

/**
 * 角色数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface RoleDao extends BatchBaseDao<StringIdKey, Role>, PresetLookupDao<Role>, EntireLookupDao<Role> {

    /**
     * 判断指定的角色和用户是否存在关联。
     *
     * @param roleKey 指定的角色主键。
     * @param userKey 指定的用户主键。
     * @return 指定的角色和用户是否存在关联。
     * @throws DaoException 数据访问层异常。
     * @since 1.6.0
     */
    boolean existsUserRelation(StringIdKey roleKey, StringIdKey userKey) throws DaoException;

    /**
     * 添加角色与用户的关联。
     *
     * @param roleKey 指定的角色主键。
     * @param userKey 指定的用户主键。
     * @throws DaoException 数据访问层异常。
     */
    void addUserRelation(StringIdKey roleKey, StringIdKey userKey) throws DaoException;

    /**
     * 删除角色与用户的关联。
     *
     * @param roleKey 指定的角色主键。
     * @param userKey 指定的用户主键。
     * @throws DaoException 数据访问层异常。
     */
    void deleteUserRelation(StringIdKey roleKey, StringIdKey userKey) throws DaoException;

    /**
     * 判断指定的角色和指定的所有用户是否全部存在关联。
     *
     * @param roleKey  指定的角色主键。
     * @param userKeys 指定的用户主键组成的列表。
     * @return 指定的角色和指定的所有用户是否全部存在关联。
     * @throws DaoException 数据访问层异常。
     * @since 1.6.0
     */
    boolean existsAllUserRelations(StringIdKey roleKey, List<StringIdKey> userKeys) throws DaoException;

    /**
     * 判断指定的角色和指定的所有用户是否全部不存在关联。
     *
     * @param roleKey  指定的角色主键。
     * @param userKeys 指定的用户主键组成的列表。
     * @return 指定的角色和指定的所有用户是否全部不存在关联。
     * @throws DaoException 数据访问层异常。
     * @since 1.6.0
     */
    boolean existsNonUserRelations(StringIdKey roleKey, List<StringIdKey> userKeys) throws DaoException;

    /**
     * 添加角色与用户的关联。
     *
     * @param roleKey 指定的角色主键。
     * @param userKey 指定的用户主键组成的列表。
     * @throws DaoException 数据访问层异常。
     */
    void batchAddUserRelations(StringIdKey roleKey, List<StringIdKey> userKey) throws DaoException;

    /**
     * 删除角色与用户的关联。
     *
     * @param roleKey 指定的角色主键。
     * @param userKey 指定的用户主键组成的列表。
     * @throws DaoException 数据访问层异常。
     */
    void batchDeleteUserRelations(StringIdKey roleKey, List<StringIdKey> userKey) throws DaoException;
}

package com.dwarfeng.rbacds.stack.dao;

import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;
import com.dwarfeng.subgrade.stack.exception.DaoException;

import java.util.List;

/**
 * 用户数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface UserDao extends BatchBaseDao<StringIdKey, User>, PresetLookupDao<User>, EntireLookupDao<User> {

    /**
     * 判断指定的用户和角色是否存在关联。
     *
     * @param userKey 指定的用户主键。
     * @param roleKey 指定的角色主键。
     * @return 指定的用户和角色是否存在关联。
     * @throws DaoException 数据访问层异常。
     * @since 1.6.0
     */
    boolean existsRoleRelation(StringIdKey userKey, StringIdKey roleKey) throws DaoException;

    /**
     * 添加用户与角色的关联。
     *
     * @param userKey 指定的用户主键。
     * @param roleKey 指定的角色主键。
     * @throws DaoException 数据访问层异常。
     */
    void addRoleRelation(StringIdKey userKey, StringIdKey roleKey) throws DaoException;

    /**
     * 删除用户与角色的关联。
     *
     * @param userKey 指定的用户主键。
     * @param roleKey 指定的角色主键。
     * @throws DaoException 数据访问层异常。
     */
    void deleteRoleRelation(StringIdKey userKey, StringIdKey roleKey) throws DaoException;

    /**
     * 判断指定的用户和指定的所有角色是否全部存在关联。
     *
     * @param userKey  指定的用户主键。
     * @param roleKeys 指定的角色主键组成的列表。
     * @return 指定的用户和指定的所有角色是否全部存在关联。
     * @throws DaoException 数据访问层异常。
     * @since 1.6.0
     */
    boolean existsAllRoleRelations(StringIdKey userKey, List<StringIdKey> roleKeys) throws DaoException;

    /**
     * 判断指定的用户和指定的所有角色是否全部不存在关联。
     *
     * @param userKey  指定的用户主键。
     * @param roleKeys 指定的角色主键组成的列表。
     * @return 指定的用户和指定的所有角色是否全部不存在关联。
     * @throws DaoException 数据访问层异常。
     * @since 1.6.0
     */
    boolean existsNonRoleRelations(StringIdKey userKey, List<StringIdKey> roleKeys) throws DaoException;

    /**
     * 添加用户与角色的关联。
     *
     * @param userKey 指定的用户主键。
     * @param roleKey 指定的角色主键组成的列表。
     * @throws DaoException 数据访问层异常。
     */
    void batchAddRoleRelations(StringIdKey userKey, List<StringIdKey> roleKey) throws DaoException;

    /**
     * 删除用户与角色的关联。
     *
     * @param userKey 指定的用户主键。
     * @param roleKey 指定的角色主键组成的列表。
     * @throws DaoException 数据访问层异常。
     */
    void batchDeleteRoleRelations(StringIdKey userKey, List<StringIdKey> roleKey) throws DaoException;
}

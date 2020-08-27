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
     * 添加用户与角色的关联。
     *
     * @param userIdKey 指定的用户主键。
     * @param roleIdKey 指定的角色主键。
     * @throws DaoException 服务异常。
     */
    void addRoleRelation(StringIdKey userIdKey, StringIdKey roleIdKey) throws DaoException;

    /**
     * 删除用户与角色的关联。
     *
     * @param userIdKey 指定的用户主键。
     * @param roleIdKey 指定的角色主键。
     * @throws DaoException 服务异常。
     */
    void deleteRoleRelation(StringIdKey userIdKey, StringIdKey roleIdKey) throws DaoException;

    /**
     * 添加用户与角色的关联。
     *
     * @param userIdKey  指定的用户主键。
     * @param roleIdKeys 指定的角色主键组成的列表。
     * @throws DaoException 服务异常。
     */
    void batchAddRoleRelations(StringIdKey userIdKey, List<StringIdKey> roleIdKeys) throws DaoException;

    /**
     * 删除用户与角色的关联。
     *
     * @param userIdKey  指定的用户主键。
     * @param roleIdKeys 指定的角色主键组成的列表。
     * @throws DaoException 服务异常。
     */
    void batchDeleteRoleRelations(StringIdKey userIdKey, List<StringIdKey> roleIdKeys) throws DaoException;
}

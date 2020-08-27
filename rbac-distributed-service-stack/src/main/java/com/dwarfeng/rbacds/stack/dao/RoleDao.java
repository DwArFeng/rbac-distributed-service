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
     * 添加角色与用户的关联。
     *
     * @param roleIdKey 指定的角色主键。
     * @param userIdKey 指定的用户主键。
     * @throws DaoException 数据访问层异常。
     */
    void addUserRelation(StringIdKey roleIdKey, StringIdKey userIdKey) throws DaoException;

    /**
     * 添加角色与用户的关联。
     *
     * @param roleIdKey 指定的角色主键。
     * @param userIdKey 指定的用户主键。
     * @throws DaoException 数据访问层异常。
     */
    void deleteUserRelation(StringIdKey roleIdKey, StringIdKey userIdKey) throws DaoException;

    /**
     * 添加角色与用户的关联。
     *
     * @param roleIdKey  指定的角色主键。
     * @param userIdKeys 指定的用户主键组成的列表。
     * @throws DaoException 数据访问层异常。
     */
    void batchAddUserRelations(StringIdKey roleIdKey, List<StringIdKey> userIdKeys) throws DaoException;

    /**
     * 添加角色与用户的关联。
     *
     * @param roleIdKey  指定的角色主键。
     * @param userIdKeys 指定的用户主键组成的列表。
     * @throws DaoException 数据访问层异常。
     */
    void batchDeleteUserRelations(StringIdKey roleIdKey, List<StringIdKey> userIdKeys) throws DaoException;
}

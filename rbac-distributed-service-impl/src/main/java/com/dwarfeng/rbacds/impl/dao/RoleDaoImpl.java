package com.dwarfeng.rbacds.impl.dao;

import com.dwarfeng.rbacds.impl.bean.entity.HibernateRole;
import com.dwarfeng.rbacds.impl.bean.entity.HibernateUser;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.dao.RoleDao;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchRelationDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, Role, HibernateRole> batchDelegate;
    @Autowired
    private HibernatePresetLookupDao<Role, HibernateRole> presetDelegate;
    @Autowired
    private HibernateEntireLookupDao<Role, HibernateRole> entireLookupDelegate;
    @Autowired
    private HibernateBatchRelationDao<StringIdKey, Role, StringIdKey, User, HibernateStringIdKey, HibernateRole,
            HibernateStringIdKey, HibernateUser> relationDelegate;

    @Autowired
    private HibernateTemplate template;
    @Autowired
    private Mapper mapper;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public StringIdKey insert(Role element) throws DaoException {
        return batchDelegate.insert(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void update(Role element) throws DaoException {
        batchDelegate.update(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(StringIdKey key) throws DaoException {
        batchDelegate.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(StringIdKey key) throws DaoException {
        return batchDelegate.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public Role get(StringIdKey key) throws DaoException {
        return batchDelegate.get(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public List<StringIdKey> batchInsert(List<Role> elements) throws DaoException {
        return batchDelegate.batchInsert(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchUpdate(List<Role> elements) throws DaoException {
        batchDelegate.batchUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchDelete(List<StringIdKey> keys) throws DaoException {
        batchDelegate.batchDelete(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean allExists(List<StringIdKey> keys) throws DaoException {
        return batchDelegate.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean nonExists(List<StringIdKey> keys) throws DaoException {
        return batchDelegate.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<Role> batchGet(List<StringIdKey> keys) throws DaoException {
        return batchDelegate.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<Role> lookup(String preset, Object[] objs) throws DaoException {
        return presetDelegate.lookup(preset, objs);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<Role> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws DaoException {
        return presetDelegate.lookup(preset, objs, pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public int lookupCount(String preset, Object[] objs) throws DaoException {
        return presetDelegate.lookupCount(preset, objs);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<Role> lookup() throws DaoException {
        return entireLookupDelegate.lookup();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<Role> lookup(PagingInfo pagingInfo) throws DaoException {
        return entireLookupDelegate.lookup(pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public int lookupCount() throws DaoException {
        return entireLookupDelegate.lookupCount();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void addUserRelation(StringIdKey roleIdKey, StringIdKey userIdKey) throws DaoException {
        relationDelegate.addRelation(roleIdKey, userIdKey);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void deleteUserRelation(StringIdKey roleIdKey, StringIdKey userIdKey) throws DaoException {
        relationDelegate.deleteRelation(roleIdKey, userIdKey);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchAddUserRelations(StringIdKey roleIdKey, List<StringIdKey> userIdKeys) throws DaoException {
        relationDelegate.batchAddRelations(roleIdKey, userIdKeys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchDeleteUserRelations(StringIdKey roleIdKey, List<StringIdKey> userIdKeys) throws DaoException {
        relationDelegate.batchDeleteRelations(roleIdKey, userIdKeys);
    }
}

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
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    private final HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, Role, HibernateRole> batchDelegate;
    private final HibernatePresetLookupDao<Role, HibernateRole> presetDelegate;
    private final HibernateEntireLookupDao<Role, HibernateRole> entireLookupDelegate;
    private final HibernateBatchRelationDao<StringIdKey, Role, StringIdKey, User, HibernateStringIdKey, HibernateRole,
            HibernateStringIdKey, HibernateUser> relationDelegate;

    public RoleDaoImpl(
            HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, Role, HibernateRole> batchDelegate,
            HibernatePresetLookupDao<Role, HibernateRole> presetDelegate,
            HibernateEntireLookupDao<Role, HibernateRole> entireLookupDelegate
            , HibernateBatchRelationDao<StringIdKey, Role, StringIdKey, User, HibernateStringIdKey, HibernateRole,
                    HibernateStringIdKey, HibernateUser> relationDelegate
    ) {
        this.batchDelegate = batchDelegate;
        this.presetDelegate = presetDelegate;
        this.entireLookupDelegate = entireLookupDelegate;
        this.relationDelegate = relationDelegate;
    }

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
    @SkipRecord
    public List<StringIdKey> batchInsert(@SkipRecord List<Role> elements) throws DaoException {
        return batchDelegate.batchInsert(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchUpdate(@SkipRecord List<Role> elements) throws DaoException {
        batchDelegate.batchUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchDelete(@SkipRecord List<StringIdKey> keys) throws DaoException {
        batchDelegate.batchDelete(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean allExists(@SkipRecord List<StringIdKey> keys) throws DaoException {
        return batchDelegate.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean nonExists(@SkipRecord List<StringIdKey> keys) throws DaoException {
        return batchDelegate.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public List<Role> batchGet(@SkipRecord List<StringIdKey> keys) throws DaoException {
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
    @SkipRecord
    public List<Role> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws DaoException {
        return presetDelegate.lookup(preset, objs, pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public int lookupCount(String preset, Object[] objs) throws DaoException {
        return presetDelegate.lookupCount(preset, objs);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
    public List<Role> lookup() throws DaoException {
        return entireLookupDelegate.lookup();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    @SkipRecord
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
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean existsUserRelation(StringIdKey roleKey, StringIdKey userKey) throws DaoException {
        return relationDelegate.existsRelation(roleKey, userKey);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void addUserRelation(StringIdKey roleKey, StringIdKey userKey) throws DaoException {
        relationDelegate.addRelation(roleKey, userKey);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void deleteUserRelation(StringIdKey roleKey, StringIdKey userKey) throws DaoException {
        relationDelegate.deleteRelation(roleKey, userKey);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean existsAllUserRelations(StringIdKey roleKey, @SkipRecord List<StringIdKey> userKeys)
            throws DaoException {
        return relationDelegate.existsAllRelations(roleKey, userKeys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean existsNonUserRelations(StringIdKey roleKey, @SkipRecord List<StringIdKey> userKeys)
            throws DaoException {
        return relationDelegate.existsNonRelations(roleKey, userKeys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchAddUserRelations(StringIdKey roleKey, @SkipRecord List<StringIdKey> userKeys) throws DaoException {
        relationDelegate.batchAddRelations(roleKey, userKeys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchDeleteUserRelations(StringIdKey roleKey, @SkipRecord List<StringIdKey> userKeys) throws DaoException {
        relationDelegate.batchDeleteRelations(roleKey, userKeys);
    }
}

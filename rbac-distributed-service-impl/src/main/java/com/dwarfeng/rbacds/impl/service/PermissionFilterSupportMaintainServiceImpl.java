package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionFilterSupport;
import com.dwarfeng.rbacds.stack.service.PermissionFilterSupportMaintainService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.impl.service.GeneralBatchCrudService;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionFilterSupportMaintainServiceImpl implements PermissionFilterSupportMaintainService {

    private final GeneralBatchCrudService<StringIdKey, PermissionFilterSupport> crudService;
    private final DaoOnlyEntireLookupService<PermissionFilterSupport> entireLookupService;
    private final DaoOnlyPresetLookupService<PermissionFilterSupport> presetLookupService;

    public PermissionFilterSupportMaintainServiceImpl(
            GeneralBatchCrudService<StringIdKey, PermissionFilterSupport> crudService,
            DaoOnlyEntireLookupService<PermissionFilterSupport> entireLookupService,
            DaoOnlyPresetLookupService<PermissionFilterSupport> presetLookupService
    ) {
        this.crudService = crudService;
        this.entireLookupService = entireLookupService;
        this.presetLookupService = presetLookupService;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(StringIdKey key) throws ServiceException {
        return crudService.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public PermissionFilterSupport get(StringIdKey key) throws ServiceException {
        return crudService.get(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public StringIdKey insert(PermissionFilterSupport element) throws ServiceException {
        return crudService.insert(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void update(PermissionFilterSupport element) throws ServiceException {
        crudService.update(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(StringIdKey key) throws ServiceException {
        crudService.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public PermissionFilterSupport getIfExists(StringIdKey key) throws ServiceException {
        return crudService.getIfExists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public StringIdKey insertIfNotExists(PermissionFilterSupport element) throws ServiceException {
        return crudService.insertIfNotExists(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void updateIfExists(PermissionFilterSupport element) throws ServiceException {
        crudService.updateIfExists(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void deleteIfExists(StringIdKey key) throws ServiceException {
        crudService.deleteIfExists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public StringIdKey insertOrUpdate(PermissionFilterSupport element) throws ServiceException {
        return crudService.insertOrUpdate(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean allExists(@SkipRecord List<StringIdKey> keys) throws ServiceException {
        return crudService.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean nonExists(@SkipRecord List<StringIdKey> keys) throws ServiceException {
        return crudService.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<PermissionFilterSupport> batchGet(@SkipRecord List<StringIdKey> keys) throws ServiceException {
        return crudService.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public List<StringIdKey> batchInsert(@SkipRecord List<PermissionFilterSupport> elements) throws ServiceException {
        return crudService.batchInsert(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchUpdate(@SkipRecord List<PermissionFilterSupport> elements) throws ServiceException {
        crudService.batchUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchDelete(@SkipRecord List<StringIdKey> keys) throws ServiceException {
        crudService.batchDelete(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<PermissionFilterSupport> batchGetIfExists(@SkipRecord List<StringIdKey> keys) throws ServiceException {
        return crudService.batchGetIfExists(keys);
    }

    @Deprecated
    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public List<StringIdKey> batchInsertIfExists(@SkipRecord List<PermissionFilterSupport> elements) throws ServiceException {
        return crudService.batchInsertIfExists(elements);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public List<StringIdKey> batchInsertIfNotExists(@SkipRecord List<PermissionFilterSupport> elements) throws ServiceException {
        return crudService.batchInsertIfNotExists(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchUpdateIfExists(@SkipRecord List<PermissionFilterSupport> elements) throws ServiceException {
        crudService.batchUpdateIfExists(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchDeleteIfExists(@SkipRecord List<StringIdKey> keys) throws ServiceException {
        crudService.batchDeleteIfExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public List<StringIdKey> batchInsertOrUpdate(@SkipRecord List<PermissionFilterSupport> elements) throws ServiceException {
        return crudService.batchInsertOrUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public PagedData<PermissionFilterSupport> lookup() throws ServiceException {
        return entireLookupService.lookup();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public PagedData<PermissionFilterSupport> lookup(PagingInfo pagingInfo) throws ServiceException {
        return entireLookupService.lookup(pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<PermissionFilterSupport> lookupAsList() throws ServiceException {
        return entireLookupService.lookupAsList();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<PermissionFilterSupport> lookupAsList(PagingInfo pagingInfo) throws ServiceException {
        return entireLookupService.lookupAsList(pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public PagedData<PermissionFilterSupport> lookup(String preset, Object[] objs) throws ServiceException {
        return presetLookupService.lookup(preset, objs);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public PagedData<PermissionFilterSupport> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        return presetLookupService.lookup(preset, objs, pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<PermissionFilterSupport> lookupAsList(String preset, Object[] objs) throws ServiceException {
        return presetLookupService.lookupAsList(preset, objs);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<PermissionFilterSupport> lookupAsList(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        return presetLookupService.lookupAsList(preset, objs, pagingInfo);
    }

    /**
     * @since 1.8.0
     */
    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public PermissionFilterSupport lookupFirst() throws ServiceException {
        return entireLookupService.lookupFirst();
    }

    /**
     * @since 1.8.0
     */
    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public int lookupCount() throws ServiceException {
        return entireLookupService.lookupCount();
    }

    /**
     * @since 1.8.0
     */
    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public PermissionFilterSupport lookupFirst(String preset, Object[] objs) throws ServiceException {
        return presetLookupService.lookupFirst(preset, objs);
    }

    /**
     * @since 1.8.0
     */
    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public int lookupCount(String preset, Object[] objs) throws ServiceException {
        return presetLookupService.lookupCount(preset, objs);
    }
}

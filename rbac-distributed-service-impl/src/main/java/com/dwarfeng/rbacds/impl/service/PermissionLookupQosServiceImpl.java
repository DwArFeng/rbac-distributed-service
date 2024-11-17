package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.handler.PermissionLookupHandler;
import com.dwarfeng.rbacds.stack.service.PermissionLookupQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionLookupQosServiceImpl implements PermissionLookupQosService {

    private final PermissionLookupHandler permissionLookupHandler;

    private final ServiceExceptionMapper sem;

    public PermissionLookupQosServiceImpl(PermissionLookupHandler permissionLookupHandler, ServiceExceptionMapper sem) {
        this.permissionLookupHandler = permissionLookupHandler;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<Permission> lookupForUser(StringIdKey userKey) throws ServiceException {
        try {
            return permissionLookupHandler.lookupForUser(userKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询指定的用户对应的权限时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<Permission> lookupForRole(StringIdKey roleKey) throws ServiceException {
        try {
            return permissionLookupHandler.lookupForRole(roleKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询指定的角色对应的权限时发生异常", LogLevel.WARN, e, sem);
        }
    }
}

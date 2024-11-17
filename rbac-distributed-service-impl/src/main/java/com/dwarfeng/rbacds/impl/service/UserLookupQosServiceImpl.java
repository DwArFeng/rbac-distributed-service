package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.handler.UserLookupHandler;
import com.dwarfeng.rbacds.stack.service.UserLookupQosService;
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
public class UserLookupQosServiceImpl implements UserLookupQosService {

    private final UserLookupHandler userLookupHandler;

    private final ServiceExceptionMapper sem;

    public UserLookupQosServiceImpl(UserLookupHandler userLookupHandler, ServiceExceptionMapper sem) {
        this.userLookupHandler = userLookupHandler;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<User> lookupForPermission(StringIdKey permissionKey) throws ServiceException {
        try {
            return userLookupHandler.lookupForPermission(permissionKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询指定的权限对应的所有用户时发生异常", LogLevel.WARN, e, sem);
        }
    }
}

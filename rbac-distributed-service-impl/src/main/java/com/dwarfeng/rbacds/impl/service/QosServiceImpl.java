package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.service.PermissionLookupService;
import com.dwarfeng.rbacds.stack.service.QosService;
import com.dwarfeng.rbacds.stack.service.UserLookupService;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QosServiceImpl implements QosService {

    @Autowired
    private PermissionLookupService permissionLookupService;
    @Autowired
    private UserLookupService userLookupService;

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<Permission> lookupPermissions(StringIdKey userKey) throws ServiceException {
        return permissionLookupService.lookupPermissions(userKey);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<User> lookupUsers(StringIdKey permissionKey) throws ServiceException {
        return userLookupService.lookupUsers(permissionKey);
    }
}

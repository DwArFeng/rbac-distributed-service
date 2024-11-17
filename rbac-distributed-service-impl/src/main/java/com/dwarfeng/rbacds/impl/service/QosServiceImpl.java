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
import org.springframework.stereotype.Service;

import java.util.List;

@Deprecated
@Service
public class QosServiceImpl implements QosService {

    private final PermissionLookupService permissionLookupService;
    private final UserLookupService userLookupService;

    public QosServiceImpl(
            PermissionLookupService permissionLookupService,
            UserLookupService userLookupService
    ) {
        this.permissionLookupService = permissionLookupService;
        this.userLookupService = userLookupService;
    }

    // 由于实现 QosService 接口时必须实现该方法，故忽略相关警告。
    @SuppressWarnings("deprecation")
    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Deprecated
    public List<Permission> lookupPermissions(StringIdKey userKey) throws ServiceException {
        return permissionLookupService.lookupPermissions(userKey);
    }

    // 由于实现 QosService 接口时必须实现该方法，故忽略相关警告。
    @SuppressWarnings("deprecation")
    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Deprecated
    public List<User> lookupUsers(StringIdKey permissionKey) throws ServiceException {
        return userLookupService.lookupUsers(permissionKey);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<Permission> lookupPermissionsForUser(StringIdKey userKey) throws ServiceException {
        return permissionLookupService.lookupForUser(userKey);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<Permission> lookupPermissionsForRole(StringIdKey roleKey) throws ServiceException {
        return permissionLookupService.lookupForRole(roleKey);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<User> lookupUsersForPermission(StringIdKey permissionKey) throws ServiceException {
        return userLookupService.lookupForPermission(permissionKey);
    }
}

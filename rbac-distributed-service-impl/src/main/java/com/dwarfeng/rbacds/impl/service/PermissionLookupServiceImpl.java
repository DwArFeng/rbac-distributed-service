package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.cache.UserPermissionCache;
import com.dwarfeng.rbacds.stack.handler.PexpHandler;
import com.dwarfeng.rbacds.stack.service.*;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionLookupServiceImpl implements PermissionLookupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionLookupServiceImpl.class);
    @Autowired
    PexpHandler pexpHandler;
    @Autowired
    private UserMaintainService userMaintainService;
    @Autowired
    private RoleMaintainService roleMaintainService;
    @Autowired
    private PexpMaintainService pexpMaintainService;
    @Autowired
    private PermissionMaintainService permissionMaintainService;
    @Autowired
    private UserPermissionCache userPermissionCache;
    @Autowired
    private ServiceExceptionMapper sem;

    @Value("${cache.timeout.list.user_has_permission}")
    private long userHasPermissionTimeout;

    @Override
    @BehaviorAnalyse
    public List<Permission> lookupPermissions(StringIdKey userKey) throws ServiceException {
        try {
            if (userPermissionCache.exists(userKey)) {
                return userPermissionCache.get(userKey);
            }
            List<Permission> permissions = lookupPermission(userKey);
            userPermissionCache.set(userKey, permissions, userHasPermissionTimeout);
            return permissions;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询用户对应的权限时发生异常", LogLevel.WARN, sem, e);
        }
    }

    private List<Permission> lookupPermission(StringIdKey userKey) throws Exception {
        // 判断用户是否存在。
        if (!userMaintainService.exists(userKey)) {
            LOGGER.warn("指定的用户 " + userKey.toString() + " 不存在, 将抛出异常...");
            throw new ServiceException(ServiceExceptionCodes.USER_NOT_EXISTS);
        }
        // 获取用户有效的权限表达式。
        List<Role> roles = roleMaintainService.lookup(RoleMaintainService.ENABLED_ROLE_FOR_USER, new Object[]{userKey}).getData();
        Map<Role, List<Pexp>> pexpsMap = new HashMap<>();
        for (Role role : roles) {
            List<Pexp> pexps = pexpMaintainService.lookup(PexpMaintainService.PEXP_FOR_ROLE, new Object[]{role.getKey()}).getData();
            pexpsMap.put(role, pexps);
        }
        // 查询所有的权限。
        List<Permission> permissions = permissionMaintainService.lookup().getData();
        // 通过所有的权限表达式和所有的权限解析用户拥有的所有权限。
        permissions = pexpHandler.analysePermissions(pexpsMap, permissions);
        // Debug输出用户获得的所有权限表达式。
        LOGGER.debug("查询获得用户 " + userKey.toString() + " 的权限:");
        permissions.forEach(permission -> LOGGER.debug("\t" + permission));
        return permissions;
    }
}

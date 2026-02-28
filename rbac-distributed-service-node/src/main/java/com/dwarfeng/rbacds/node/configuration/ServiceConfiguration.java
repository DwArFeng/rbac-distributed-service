package com.dwarfeng.rbacds.node.configuration;

import com.dwarfeng.rbacds.impl.service.operation.*;
import com.dwarfeng.rbacds.stack.bean.entity.*;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.rbacds.stack.bean.key.RoleUserRelationKey;
import com.dwarfeng.rbacds.stack.cache.FilterSupportCache;
import com.dwarfeng.rbacds.stack.cache.RoleUserRelationCache;
import com.dwarfeng.rbacds.stack.dao.*;
import com.dwarfeng.subgrade.impl.generation.ExceptionKeyGenerator;
import com.dwarfeng.subgrade.impl.service.CustomBatchCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.impl.service.GeneralBatchCrudService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    private final ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;

    private final UserCrudOperation userCrudOperation;
    private final UserDao userDao;
    private final RoleCrudOperation roleCrudOperation;
    private final RoleDao roleDao;
    private final PermissionCrudOperation permissionCrudOperation;
    private final PermissionDao permissionDao;
    private final PexpCrudOperation pexpCrudOperation;
    private final PexpDao pexpDao;
    private final PermissionGroupCrudOperation permissionGroupCrudOperation;
    private final PermissionGroupDao permissionGroupDao;
    private final FilterSupportCache filterSupportCache;
    private final FilterSupportDao filterSupportDao;
    private final RoleUserRelationDao roleUserRelationDao;
    private final RoleUserRelationCache roleUserRelationCache;
    private final ScopeCrudOperation scopeCrudOperation;
    private final ScopeDao scopeDao;

    @Value("${cache.timeout.entity.filter_support}")
    private long filterSupportTimeout;
    @Value("${cache.timeout.entity.role_user_relation}")
    private long roleUserRelationTimeout;

    public ServiceConfiguration(
            ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration,
            UserCrudOperation userCrudOperation,
            UserDao userDao,
            RoleCrudOperation roleCrudOperation,
            RoleDao roleDao,
            PermissionCrudOperation permissionCrudOperation,
            PermissionDao permissionDao,
            PexpCrudOperation pexpCrudOperation,
            PexpDao pexpDao,
            PermissionGroupCrudOperation permissionGroupCrudOperation,
            PermissionGroupDao permissionGroupDao,
            FilterSupportCache filterSupportCache,
            FilterSupportDao filterSupportDao,
            RoleUserRelationDao roleUserRelationDao,
            RoleUserRelationCache roleUserRelationCache,
            ScopeCrudOperation scopeCrudOperation,
            ScopeDao scopeDao
    ) {
        this.serviceExceptionMapperConfiguration = serviceExceptionMapperConfiguration;
        this.userCrudOperation = userCrudOperation;
        this.userDao = userDao;
        this.roleCrudOperation = roleCrudOperation;
        this.roleDao = roleDao;
        this.permissionCrudOperation = permissionCrudOperation;
        this.permissionDao = permissionDao;
        this.pexpCrudOperation = pexpCrudOperation;
        this.pexpDao = pexpDao;
        this.permissionGroupCrudOperation = permissionGroupCrudOperation;
        this.permissionGroupDao = permissionGroupDao;
        this.filterSupportCache = filterSupportCache;
        this.filterSupportDao = filterSupportDao;
        this.roleUserRelationDao = roleUserRelationDao;
        this.roleUserRelationCache = roleUserRelationCache;
        this.scopeCrudOperation = scopeCrudOperation;
        this.scopeDao = scopeDao;
    }

    @Bean
    public CustomBatchCrudService<StringIdKey, User> userCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                userCrudOperation,
                new ExceptionKeyGenerator<>()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<User> userDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                userDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<User> userDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                userDao
        );
    }

    @Bean
    public CustomBatchCrudService<StringIdKey, Role> roleCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                roleCrudOperation,
                new ExceptionKeyGenerator<>()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<Role> roleDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                roleDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<Role> roleDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                roleDao
        );
    }

    @Bean
    public CustomBatchCrudService<PermissionKey, Permission> permissionCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                permissionCrudOperation,
                new ExceptionKeyGenerator<>()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<Permission> permissionDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                permissionDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<Permission> permissionDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                permissionDao
        );
    }

    @Bean
    public CustomBatchCrudService<PexpKey, Pexp> pexpCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                pexpCrudOperation,
                new ExceptionKeyGenerator<>()
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<Pexp> pexpDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                pexpDao
        );
    }

    @Bean
    public CustomBatchCrudService<PermissionGroupKey, PermissionGroup> permissionGroupCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                permissionGroupCrudOperation,
                new ExceptionKeyGenerator<>()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<PermissionGroup> permissionGroupDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                permissionGroupDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<PermissionGroup> permissionGroupDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                permissionGroupDao
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, FilterSupport>
    filterSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                filterSupportDao,
                filterSupportCache,
                new ExceptionKeyGenerator<>(),
                filterSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<FilterSupport> filterSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                filterSupportDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<FilterSupport> filterSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                filterSupportDao
        );
    }

    @Bean
    public GeneralBatchCrudService<RoleUserRelationKey, RoleUserRelation> roleUserRelationGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                roleUserRelationDao,
                roleUserRelationCache,
                new ExceptionKeyGenerator<>(),
                roleUserRelationTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<RoleUserRelation> roleUserRelationDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                roleUserRelationDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<RoleUserRelation> roleUserRelationDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                roleUserRelationDao
        );
    }

    @Bean
    public CustomBatchCrudService<StringIdKey, Scope> scopeCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                scopeCrudOperation,
                new ExceptionKeyGenerator<>()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<Scope> scopeDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                scopeDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<Scope> scopeDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                scopeDao
        );
    }
}

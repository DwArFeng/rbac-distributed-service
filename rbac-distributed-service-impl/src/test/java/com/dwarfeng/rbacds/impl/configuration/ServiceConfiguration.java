package com.dwarfeng.rbacds.impl.configuration;

import com.dwarfeng.rbacds.impl.service.operation.*;
import com.dwarfeng.rbacds.stack.bean.entity.*;
import com.dwarfeng.rbacds.stack.dao.*;
import com.dwarfeng.subgrade.impl.generation.ExceptionKeyGenerator;
import com.dwarfeng.subgrade.impl.service.CustomBatchCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyBatchRelationService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    private final ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;
    private final GenerateConfiguration generateConfiguration;
    private final DaoConfiguration daoConfiguration;

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

    public ServiceConfiguration(
            ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration,
            GenerateConfiguration generateConfiguration,
            DaoConfiguration daoConfiguration,
            UserCrudOperation userCrudOperation,
            UserDao userDao,
            RoleCrudOperation roleCrudOperation,
            RoleDao roleDao,
            PermissionCrudOperation permissionCrudOperation,
            PermissionDao permissionDao,
            PexpCrudOperation pexpCrudOperation,
            PexpDao pexpDao,
            PermissionGroupCrudOperation permissionGroupCrudOperation,
            PermissionGroupDao permissionGroupDao
    ) {
        this.serviceExceptionMapperConfiguration = serviceExceptionMapperConfiguration;
        this.generateConfiguration = generateConfiguration;
        this.daoConfiguration = daoConfiguration;
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
    public DaoOnlyBatchRelationService<StringIdKey, StringIdKey> userDaoOnlyBatchRelationService() {
        return new DaoOnlyBatchRelationService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                daoConfiguration.userRoleBatchRelationDao()
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
    public DaoOnlyBatchRelationService<StringIdKey, StringIdKey> roleDaoOnlyBatchRelationService() {
        return new DaoOnlyBatchRelationService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                daoConfiguration.roleUserBatchRelationDao()
        );
    }

    @Bean
    public CustomBatchCrudService<StringIdKey, Permission> permissionCustomBatchCrudService() {
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
    public CustomBatchCrudService<LongIdKey, Pexp> pexpCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                pexpCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
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
    public CustomBatchCrudService<StringIdKey, PermissionGroup> permissionGroupCustomBatchCrudService() {
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
}

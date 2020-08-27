package com.dwarfeng.rbacds.api.configuration;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.sfds.api.integration.subgrade.SnowFlakeLongIdKeyFetcher;
import com.dwarfeng.subgrade.impl.bean.key.ExceptionKeyFetcher;
import com.dwarfeng.subgrade.impl.service.GeneralCrudService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Autowired
    private DaoConfiguration daoConfiguration;
    @Autowired
    private CacheConfiguration cacheConfiguration;
    @Autowired
    private SnowFlakeLongIdKeyFetcher keyFetcher;
    @Autowired
    private ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;

    @Value("${cache.timeout.entity.permission}")
    private long permissionTimeout;
    @Value("${cache.timeout.entity.pexp}")
    private long pexpTimeout;
    @Value("${cache.timeout.entity.role}")
    private long roleTimeout;
    @Value("${cache.timeout.entity.user}")
    private long userTimeout;

    @Bean
    public GeneralCrudService<StringIdKey, Permission> permissionGeneralCrudService() {
        return new GeneralCrudService<>(
                daoConfiguration.permissionDaoDelegate(),
                cacheConfiguration.permissionCacheDelegate(),
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                permissionTimeout
        );
    }

    @Bean
    public GeneralCrudService<LongIdKey, Pexp> pexpGeneralCrudService() {
        return new GeneralCrudService<>(
                daoConfiguration.pexpDaoDelegate(),
                cacheConfiguration.pexpCacheDelegate(),
                keyFetcher,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                pexpTimeout
        );
    }

    @Bean
    public GeneralCrudService<StringIdKey, Role> roleGeneralCrudService() {
        return new GeneralCrudService<>(
                daoConfiguration.roleDaoDelegate(),
                cacheConfiguration.roleCacheDelegate(),
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                roleTimeout
        );
    }

    @Bean
    public GeneralCrudService<StringIdKey, User> userGeneralCrudService() {
        return new GeneralCrudService<>(
                daoConfiguration.userDaoDelegate(),
                cacheConfiguration.userCacheDelegate(),
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                userTimeout
        );
    }
}

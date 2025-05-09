package com.dwarfeng.rbacds.node.configuration;

import com.dwarfeng.rbacds.impl.bean.BeanMapper;
import com.dwarfeng.rbacds.impl.bean.entity.*;
import com.dwarfeng.rbacds.impl.dao.preset.*;
import com.dwarfeng.rbacds.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchRelationDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.sdk.hibernate.modification.DefaultDeletionMod;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.Collections;

@Configuration
public class DaoConfiguration {

    private final HibernateTemplate template;

    private final PexpPresetCriteriaMaker pexpPresetCriteriaMaker;
    private final RolePresetCriteriaMaker rolePresetCriteriaMaker;
    private final UserPresetCriteriaMaker userPresetCriteriaMaker;
    private final PermissionPresetCriteriaMaker permissionPresetCriteriaMaker;
    private final PermissionGroupPresetCriteriaMaker permissionGroupPresetCriteriaMaker;

    public DaoConfiguration(
            HibernateTemplate template,
            PexpPresetCriteriaMaker pexpPresetCriteriaMaker,
            RolePresetCriteriaMaker rolePresetCriteriaMaker,
            UserPresetCriteriaMaker userPresetCriteriaMaker,
            PermissionPresetCriteriaMaker permissionPresetCriteriaMaker,
            PermissionGroupPresetCriteriaMaker permissionGroupPresetCriteriaMaker
    ) {
        this.template = template;
        this.pexpPresetCriteriaMaker = pexpPresetCriteriaMaker;
        this.rolePresetCriteriaMaker = rolePresetCriteriaMaker;
        this.userPresetCriteriaMaker = userPresetCriteriaMaker;
        this.permissionPresetCriteriaMaker = permissionPresetCriteriaMaker;
        this.permissionGroupPresetCriteriaMaker = permissionGroupPresetCriteriaMaker;
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, Permission, HibernatePermission>
    permissionDaoDelegate() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(Permission.class, HibernatePermission.class, BeanMapper.class),
                HibernatePermission.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<Permission, HibernatePermission> permissionHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(Permission.class, HibernatePermission.class, BeanMapper.class),
                HibernatePermission.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Permission, HibernatePermission> permissionHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(Permission.class, HibernatePermission.class, BeanMapper.class),
                HibernatePermission.class,
                permissionPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, Pexp, HibernatePexp> pexpDaoDelegate() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(Pexp.class, HibernatePexp.class, BeanMapper.class),
                HibernatePexp.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Pexp, HibernatePexp> pexpHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(Pexp.class, HibernatePexp.class, BeanMapper.class),
                HibernatePexp.class,
                pexpPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, Role, HibernateRole> roleDaoDelegate() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(Role.class, HibernateRole.class, BeanMapper.class),
                HibernateRole.class,
                new DefaultDeletionMod<>(),
                Collections.singleton("users")
        );
    }

    @Bean
    public HibernatePresetLookupDao<Role, HibernateRole> roleHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(Role.class, HibernateRole.class, BeanMapper.class),
                HibernateRole.class,
                rolePresetCriteriaMaker
        );
    }

    @Bean
    public HibernateEntireLookupDao<Role, HibernateRole> roleHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(Role.class, HibernateRole.class, BeanMapper.class),
                HibernateRole.class
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, User, HibernateUser> userDaoDelegate() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(User.class, HibernateUser.class, BeanMapper.class),
                HibernateUser.class,
                new DefaultDeletionMod<>()
        );
    }

    @Bean
    public HibernatePresetLookupDao<User, HibernateUser> userHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(User.class, HibernateUser.class, BeanMapper.class),
                HibernateUser.class,
                userPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateEntireLookupDao<User, HibernateUser> userHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(User.class, HibernateUser.class, BeanMapper.class),
                HibernateUser.class
        );
    }

    @Bean
    public HibernateBatchRelationDao<StringIdKey, User, StringIdKey, Role, HibernateStringIdKey, HibernateUser,
            HibernateStringIdKey, HibernateRole> userRoleBatchRelationDao() {
        return new HibernateBatchRelationDao<>(
                template,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(User.class, HibernateUser.class, BeanMapper.class),
                new MapStructBeanTransformer<>(Role.class, HibernateRole.class, BeanMapper.class),
                HibernateUser.class,
                HibernateRole.class,
                "roles",
                "users",
                HibernateBatchRelationDao.JoinType.JOIN_BY_CHILD
        );
    }

    @Bean
    public HibernateBatchRelationDao<StringIdKey, Role, StringIdKey, User, HibernateStringIdKey, HibernateRole,
            HibernateStringIdKey, HibernateUser> roleUserBatchRelationDao() {
        return new HibernateBatchRelationDao<>(
                template,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(Role.class, HibernateRole.class, BeanMapper.class),
                new MapStructBeanTransformer<>(User.class, HibernateUser.class, BeanMapper.class),
                HibernateRole.class,
                HibernateUser.class,
                "users",
                "roles",
                HibernateBatchRelationDao.JoinType.JOIN_BY_PARENT
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, PermissionGroup, HibernatePermissionGroup>
    permissionGroupDaoDelegate() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        PermissionGroup.class, HibernatePermissionGroup.class, BeanMapper.class
                ),
                HibernatePermissionGroup.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<PermissionGroup, HibernatePermissionGroup>
    permissionGroupHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        PermissionGroup.class, HibernatePermissionGroup.class, BeanMapper.class
                ),
                HibernatePermissionGroup.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<PermissionGroup, HibernatePermissionGroup>
    permissionGroupHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        PermissionGroup.class, HibernatePermissionGroup.class, BeanMapper.class
                ),
                HibernatePermissionGroup.class,
                permissionGroupPresetCriteriaMaker
        );
    }
}

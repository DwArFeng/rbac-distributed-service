package com.dwarfeng.rbacds.node.configuration;

import com.dwarfeng.rbacds.impl.bean.entity.*;
import com.dwarfeng.rbacds.impl.dao.modifacation.RoleDeletionMod;
import com.dwarfeng.rbacds.impl.dao.modifacation.UserDeletionMod;
import com.dwarfeng.rbacds.impl.dao.preset.*;
import com.dwarfeng.rbacds.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchRelationDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.Collections;

@Configuration
public class DaoConfiguration {

    @Autowired
    private HibernateTemplate template;
    @Autowired
    private BeanTransformerConfiguration beanTransformerConfiguration;
    @Autowired
    private PexpPresetCriteriaMaker pexpPresetCriteriaMaker;
    @Autowired
    private RoleDeletionMod roleDeletionMod;
    @Autowired
    private RolePresetCriteriaMaker rolePresetCriteriaMaker;
    @Autowired
    private UserDeletionMod userDeletionMod;
    @Autowired
    private UserPresetCriteriaMaker userPresetCriteriaMaker;
    @Autowired
    private PermissionPresetCriteriaMaker permissionPresetCriteriaMaker;
    @Autowired
    private RoleGroupPresetCriteriaMaker roleGroupPresetCriteriaMaker;
    @Autowired
    private PermissionGroupPresetCriteriaMaker permissionGroupPresetCriteriaMaker;

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, Permission, HibernatePermission> permissionDaoDelegate() {
        return new HibernateBatchBaseDao<>(
                template,
                beanTransformerConfiguration.stringIdKeyBeanTransformer(),
                beanTransformerConfiguration.permissionBeanTransformer(),
                HibernatePermission.class);
    }

    @Bean
    public HibernateEntireLookupDao<Permission, HibernatePermission> permissionHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                beanTransformerConfiguration.permissionBeanTransformer(),
                HibernatePermission.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Permission, HibernatePermission> permissionHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                beanTransformerConfiguration.permissionBeanTransformer(),
                HibernatePermission.class,
                permissionPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, Pexp, HibernatePexp> pexpDaoDelegate() {
        return new HibernateBatchBaseDao<>(
                template,
                beanTransformerConfiguration.longIdKeyBeanTransformer(),
                beanTransformerConfiguration.pexpBeanTransformer(),
                HibernatePexp.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Pexp, HibernatePexp> pexpHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                beanTransformerConfiguration.pexpBeanTransformer(),
                HibernatePexp.class,
                pexpPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, Role, HibernateRole> roleDaoDelegate() {
        return new HibernateBatchBaseDao<>(
                template,
                beanTransformerConfiguration.stringIdKeyBeanTransformer(),
                beanTransformerConfiguration.roleBeanTransformer(),
                HibernateRole.class,
                roleDeletionMod,
                Collections.singleton("users")
        );
    }

    @Bean
    public HibernatePresetLookupDao<Role, HibernateRole> roleHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                beanTransformerConfiguration.roleBeanTransformer(),
                HibernateRole.class,
                rolePresetCriteriaMaker
        );
    }

    @Bean
    public HibernateEntireLookupDao<Role, HibernateRole> roleHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                beanTransformerConfiguration.roleBeanTransformer(),
                HibernateRole.class
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, User, HibernateUser> userDaoDelegate() {
        return new HibernateBatchBaseDao<>(
                template,
                beanTransformerConfiguration.stringIdKeyBeanTransformer(),
                beanTransformerConfiguration.userBeanTransformer(),
                HibernateUser.class,
                userDeletionMod
        );
    }

    @Bean
    public HibernatePresetLookupDao<User, HibernateUser> userHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                beanTransformerConfiguration.userBeanTransformer(),
                HibernateUser.class,
                userPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateEntireLookupDao<User, HibernateUser> userHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                beanTransformerConfiguration.userBeanTransformer(),
                HibernateUser.class
        );
    }

    @Bean
    public HibernateBatchRelationDao<StringIdKey, User, StringIdKey, Role, HibernateStringIdKey, HibernateUser,
            HibernateStringIdKey, HibernateRole> userRoleBatchRelationDao() {
        return new HibernateBatchRelationDao<>(
                template,
                beanTransformerConfiguration.stringIdKeyBeanTransformer(),
                beanTransformerConfiguration.stringIdKeyBeanTransformer(),
                beanTransformerConfiguration.userBeanTransformer(),
                beanTransformerConfiguration.roleBeanTransformer(),
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
                beanTransformerConfiguration.stringIdKeyBeanTransformer(),
                beanTransformerConfiguration.stringIdKeyBeanTransformer(),
                beanTransformerConfiguration.roleBeanTransformer(),
                beanTransformerConfiguration.userBeanTransformer(),
                HibernateRole.class,
                HibernateUser.class,
                "users",
                "roles",
                HibernateBatchRelationDao.JoinType.JOIN_BY_PARENT
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, PermissionGroup, HibernatePermissionGroup> permissionGroupDaoDelegate() {
        return new HibernateBatchBaseDao<>(
                template,
                beanTransformerConfiguration.stringIdKeyBeanTransformer(),
                beanTransformerConfiguration.permissionGroupBeanTransformer(),
                HibernatePermissionGroup.class);
    }

    @Bean
    public HibernateEntireLookupDao<PermissionGroup, HibernatePermissionGroup> permissionGroupHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                beanTransformerConfiguration.permissionGroupBeanTransformer(),
                HibernatePermissionGroup.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<PermissionGroup, HibernatePermissionGroup> permissionGroupHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                beanTransformerConfiguration.permissionGroupBeanTransformer(),
                HibernatePermissionGroup.class,
                permissionGroupPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, RoleGroup, HibernateRoleGroup> roleGroupDaoDelegate() {
        return new HibernateBatchBaseDao<>(
                template,
                beanTransformerConfiguration.stringIdKeyBeanTransformer(),
                beanTransformerConfiguration.roleGroupBeanTransformer(),
                HibernateRoleGroup.class);
    }

    @Bean
    public HibernateEntireLookupDao<RoleGroup, HibernateRoleGroup> roleGroupHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                beanTransformerConfiguration.roleGroupBeanTransformer(),
                HibernateRoleGroup.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<RoleGroup, HibernateRoleGroup> roleGroupHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                beanTransformerConfiguration.roleGroupBeanTransformer(),
                HibernateRoleGroup.class,
                roleGroupPresetCriteriaMaker
        );
    }
}

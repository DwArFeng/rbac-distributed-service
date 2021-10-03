package com.dwarfeng.rbacds.impl.configuration;

import com.dwarfeng.rbacds.impl.bean.entity.*;
import com.dwarfeng.rbacds.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanTransformerConfiguration {

    @Autowired
    private Mapper mapper;

    @Bean
    public BeanTransformer<LongIdKey, HibernateLongIdKey> longIdKeyBeanTransformer() {
        return new DozerBeanTransformer<>(
                LongIdKey.class,
                HibernateLongIdKey.class,
                mapper
        );
    }

    @Bean
    public BeanTransformer<StringIdKey, HibernateStringIdKey> stringIdKeyBeanTransformer() {
        return new DozerBeanTransformer<>(
                StringIdKey.class,
                HibernateStringIdKey.class,
                mapper
        );
    }

    @Bean
    public BeanTransformer<Permission, HibernatePermission> permissionBeanTransformer() {
        return new DozerBeanTransformer<>(
                Permission.class,
                HibernatePermission.class,
                mapper
        );
    }

    @Bean
    public BeanTransformer<Pexp, HibernatePexp> pexpBeanTransformer() {
        return new DozerBeanTransformer<>(
                Pexp.class,
                HibernatePexp.class,
                mapper
        );
    }

    @Bean
    public BeanTransformer<Role, HibernateRole> roleBeanTransformer() {
        return new DozerBeanTransformer<>(
                Role.class,
                HibernateRole.class,
                mapper
        );
    }

    @Bean
    public BeanTransformer<User, HibernateUser> userBeanTransformer() {
        return new DozerBeanTransformer<>(
                User.class,
                HibernateUser.class,
                mapper
        );
    }

    @Bean
    public BeanTransformer<PermissionGroup, HibernatePermissionGroup> permissionGroupBeanTransformer() {
        return new DozerBeanTransformer<>(
                PermissionGroup.class,
                HibernatePermissionGroup.class,
                mapper
        );
    }
}

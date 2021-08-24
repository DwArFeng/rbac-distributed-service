package com.dwarfeng.rbacds.impl.configuration;

import com.dwarfeng.rbacds.sdk.bean.entity.*;
import com.dwarfeng.rbacds.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.impl.cache.RedisKeyListCache;
import com.dwarfeng.subgrade.impl.cache.RedisListCache;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheConfiguration {

    @Autowired
    private RedisTemplate<String, ?> template;
    @Autowired
    private Mapper mapper;

    @Value("${cache.prefix.entity.permission}")
    private String permissionPrefix;
    @Value("${cache.prefix.entity.pexp}")
    private String pexpPrefix;
    @Value("${cache.prefix.entity.role}")
    private String rolePrefix;
    @Value("${cache.prefix.entity.user}")
    private String userPrefix;
    @Value("${cache.key.list.permission}")
    private String permissionListKey;
    @Value("${cache.prefix.list.user_has_permission}")
    private String userPermissionListKey;
    @Value("${cache.prefix.list.role_has_permission}")
    private String rolePermissionListKey;
    @Value("${cache.prefix.list.permission_has_user}")
    private String permissionUserListKey;
    @Value("${cache.prefix.entity.permission_group}")
    private String permissionGroupPrefix;
    @Value("${cache.prefix.entity.role_group}")
    private String roleGroupPrefix;

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, Permission, FastJsonPermission> permissionCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPermission>) template,
                new StringIdStringKeyFormatter(permissionPrefix),
                new DozerBeanTransformer<>(Permission.class, FastJsonPermission.class, mapper)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisListCache<Permission, FastJsonPermission> permissionRedisListCache() {
        return new RedisListCache<>(
                permissionListKey,
                (RedisTemplate<String, FastJsonPermission>) template,
                new DozerBeanTransformer<>(Permission.class, FastJsonPermission.class, mapper)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, Pexp, FastJsonPexp> pexpCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPexp>) template,
                new LongIdStringKeyFormatter(pexpPrefix),
                new DozerBeanTransformer<>(Pexp.class, FastJsonPexp.class, mapper)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, Role, FastJsonRole> roleCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonRole>) template,
                new StringIdStringKeyFormatter(rolePrefix),
                new DozerBeanTransformer<>(Role.class, FastJsonRole.class, mapper)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, User, FastJsonUser> userCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonUser>) template,
                new StringIdStringKeyFormatter(userPrefix),
                new DozerBeanTransformer<>(User.class, FastJsonUser.class, mapper)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisKeyListCache<StringIdKey, Permission, FastJsonPermission> userPermissionRedisKeyListCache() {
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonPermission>) template,
                new StringIdStringKeyFormatter(userPermissionListKey),
                new DozerBeanTransformer<>(Permission.class, FastJsonPermission.class, mapper)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisKeyListCache<StringIdKey, Permission, FastJsonPermission> rolePermissionRedisKeyListCache() {
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonPermission>) template,
                new StringIdStringKeyFormatter(rolePermissionListKey),
                new DozerBeanTransformer<>(Permission.class, FastJsonPermission.class, mapper)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisKeyListCache<StringIdKey, User, FastJsonUser> permissionUserRedisKeyListCache() {
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonUser>) template,
                new StringIdStringKeyFormatter(permissionUserListKey),
                new DozerBeanTransformer<>(User.class, FastJsonUser.class, mapper)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, PermissionGroup, FastJsonPermissionGroup> permissionGroupCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPermissionGroup>) template,
                new StringIdStringKeyFormatter(permissionGroupPrefix),
                new DozerBeanTransformer<>(PermissionGroup.class, FastJsonPermissionGroup.class, mapper)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, RoleGroup, FastJsonRoleGroup> roleGroupCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonRoleGroup>) template,
                new StringIdStringKeyFormatter(roleGroupPrefix),
                new DozerBeanTransformer<>(RoleGroup.class, FastJsonRoleGroup.class, mapper)
        );
    }
}

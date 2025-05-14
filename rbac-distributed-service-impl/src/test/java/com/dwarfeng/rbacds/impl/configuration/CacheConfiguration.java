package com.dwarfeng.rbacds.impl.configuration;

import com.dwarfeng.rbacds.sdk.bean.BeanMapper;
import com.dwarfeng.rbacds.sdk.bean.entity.*;
import com.dwarfeng.rbacds.sdk.bean.key.formatter.PermissionMetaStringKeyFormatter;
import com.dwarfeng.rbacds.stack.bean.entity.*;
import com.dwarfeng.rbacds.stack.bean.key.PermissionMetaKey;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.impl.cache.RedisKeyListCache;
import com.dwarfeng.subgrade.impl.cache.RedisListCache;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheConfiguration {

    private final RedisTemplate<String, ?> template;

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
    @Value("${cache.prefix.entity.permission_meta}")
    private String permissionMetaPrefix;
    @Value("${cache.prefix.entity.permission_filter_support}")
    private String permissionFilterSupportPrefix;

    public CacheConfiguration(RedisTemplate<String, ?> template) {
        this.template = template;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, Permission, FastJsonPermission> permissionCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPermission>) template,
                new StringIdStringKeyFormatter(permissionPrefix),
                new MapStructBeanTransformer<>(Permission.class, FastJsonPermission.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisListCache<Permission, FastJsonPermission> permissionRedisListCache() {
        return new RedisListCache<>(
                permissionListKey,
                (RedisTemplate<String, FastJsonPermission>) template,
                new MapStructBeanTransformer<>(Permission.class, FastJsonPermission.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, Pexp, FastJsonPexp> pexpCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPexp>) template,
                new LongIdStringKeyFormatter(pexpPrefix),
                new MapStructBeanTransformer<>(Pexp.class, FastJsonPexp.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, Role, FastJsonRole> roleCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonRole>) template,
                new StringIdStringKeyFormatter(rolePrefix),
                new MapStructBeanTransformer<>(Role.class, FastJsonRole.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, User, FastJsonUser> userCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonUser>) template,
                new StringIdStringKeyFormatter(userPrefix),
                new MapStructBeanTransformer<>(User.class, FastJsonUser.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisKeyListCache<StringIdKey, Permission, FastJsonPermission> userPermissionRedisKeyListCache() {
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonPermission>) template,
                new StringIdStringKeyFormatter(userPermissionListKey),
                new MapStructBeanTransformer<>(Permission.class, FastJsonPermission.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisKeyListCache<StringIdKey, Permission, FastJsonPermission> rolePermissionRedisKeyListCache() {
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonPermission>) template,
                new StringIdStringKeyFormatter(rolePermissionListKey),
                new MapStructBeanTransformer<>(Permission.class, FastJsonPermission.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisKeyListCache<StringIdKey, User, FastJsonUser> permissionUserRedisKeyListCache() {
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonUser>) template,
                new StringIdStringKeyFormatter(permissionUserListKey),
                new MapStructBeanTransformer<>(User.class, FastJsonUser.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, PermissionGroup, FastJsonPermissionGroup> permissionGroupCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPermissionGroup>) template,
                new StringIdStringKeyFormatter(permissionGroupPrefix),
                new MapStructBeanTransformer<>(
                        PermissionGroup.class, FastJsonPermissionGroup.class, BeanMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<PermissionMetaKey, PermissionMeta, FastJsonPermissionMeta>
    permissionMetaCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPermissionMeta>) template,
                new PermissionMetaStringKeyFormatter(permissionMetaPrefix),
                new MapStructBeanTransformer<>(
                        PermissionMeta.class, FastJsonPermissionMeta.class, BeanMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, PermissionFilterSupport, FastJsonPermissionFilterSupport>
    permissionFilterSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPermissionFilterSupport>) template,
                new StringIdStringKeyFormatter(permissionFilterSupportPrefix),
                new MapStructBeanTransformer<>(
                        PermissionFilterSupport.class, FastJsonPermissionFilterSupport.class, BeanMapper.class
                )
        );
    }
}

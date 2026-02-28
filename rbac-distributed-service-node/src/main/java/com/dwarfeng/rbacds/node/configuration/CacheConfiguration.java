package com.dwarfeng.rbacds.node.configuration;

import com.dwarfeng.rbacds.sdk.bean.BeanMapper;
import com.dwarfeng.rbacds.sdk.bean.entity.*;
import com.dwarfeng.rbacds.sdk.bean.key.formatter.PermissionGroupStringKeyFormatter;
import com.dwarfeng.rbacds.sdk.bean.key.formatter.PermissionStringKeyFormatter;
import com.dwarfeng.rbacds.sdk.bean.key.formatter.PexpStringKeyFormatter;
import com.dwarfeng.rbacds.sdk.bean.key.formatter.RoleUserRelationStringKeyFormatter;
import com.dwarfeng.rbacds.stack.bean.entity.*;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.rbacds.stack.bean.key.RoleUserRelationKey;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringIdStringKeyFormatter;
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
    @Value("${cache.prefix.entity.permission_group}")
    private String permissionGroupPrefix;
    @Value("${cache.prefix.entity.filter_support}")
    private String filterSupportPrefix;
    @Value("${cache.prefix.entity.role_user_relation}")
    private String roleUserRelationPrefix;
    @Value("${cache.prefix.entity.scope}")
    private String scopePrefix;

    public CacheConfiguration(RedisTemplate<String, ?> template) {
        this.template = template;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<PermissionKey, Permission, FastJsonPermission> permissionCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPermission>) template,
                new PermissionStringKeyFormatter(permissionPrefix),
                new MapStructBeanTransformer<>(Permission.class, FastJsonPermission.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<PexpKey, Pexp, FastJsonPexp> pexpCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPexp>) template,
                new PexpStringKeyFormatter(pexpPrefix),
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
    public RedisBatchBaseCache<PermissionGroupKey, PermissionGroup, FastJsonPermissionGroup>
    permissionGroupCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPermissionGroup>) template,
                new PermissionGroupStringKeyFormatter(permissionGroupPrefix),
                new MapStructBeanTransformer<>(
                        PermissionGroup.class, FastJsonPermissionGroup.class, BeanMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, FilterSupport, FastJsonFilterSupport> filterSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonFilterSupport>) template,
                new StringIdStringKeyFormatter(filterSupportPrefix),
                new MapStructBeanTransformer<>(
                        FilterSupport.class, FastJsonFilterSupport.class, BeanMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<RoleUserRelationKey, RoleUserRelation, FastJsonRoleUserRelation>
    roleUserRelationCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonRoleUserRelation>) template,
                new RoleUserRelationStringKeyFormatter(roleUserRelationPrefix),
                new MapStructBeanTransformer<>(RoleUserRelation.class, FastJsonRoleUserRelation.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, Scope, FastJsonScope> scopeCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonScope>) template,
                new StringIdStringKeyFormatter(scopePrefix),
                new MapStructBeanTransformer<>(Scope.class, FastJsonScope.class, BeanMapper.class)
        );
    }
}

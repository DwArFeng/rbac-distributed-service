package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.cache.RoleCache;
import com.dwarfeng.rbacds.stack.cache.UserCache;
import com.dwarfeng.rbacds.stack.dao.RoleDao;
import com.dwarfeng.rbacds.stack.dao.UserDao;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class UserRoleUpdateRelationTest {

    @Autowired
    private UserMaintainService userMaintainService;
    @Autowired
    private RoleMaintainService roleMaintainService;

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserCache userCache;
    @Autowired
    private RoleCache roleCache;

    private User user;
    private Role role;

    @Before
    public void setUp() {
        user = new User(new StringIdKey(UUID.randomUUID().toString()), "测试用户");
        role = new Role(new StringIdKey(UUID.randomUUID().toString()), "name", true, "测试角色");
    }

    @After
    public void tearDown() {
        user = null;
        role = null;
    }

    @Test
    public void testSingle() throws ServiceException {
        try {
            userMaintainService.insertOrUpdate(user);
            roleMaintainService.insertOrUpdate(role);

            userMaintainService.addRoleRelation(user.getKey(), role.getKey());
            assertEquals(1, userMaintainService.lookup(UserMaintainService.CHILD_FOR_ROLE_SET,
                    new Object[]{Collections.singletonList(role.getKey())}).getCount());

            userMaintainService.update(user);
            assertEquals(1, userMaintainService.lookup(UserMaintainService.CHILD_FOR_ROLE_SET,
                    new Object[]{Collections.singletonList(role.getKey())}).getCount());

            roleMaintainService.update(role);
            assertEquals(1, userMaintainService.lookup(UserMaintainService.CHILD_FOR_ROLE_SET,
                    new Object[]{Collections.singletonList(role.getKey())}).getCount());
        } finally {
            if (Objects.nonNull(role.getKey())) {
                roleMaintainService.deleteIfExists(role.getKey());
            }
            if (Objects.nonNull(user.getKey())) {
                userMaintainService.deleteIfExists(user.getKey());
            }
        }
    }

    @Test
    public void testBatch() throws Exception {
        try {
            userMaintainService.insertOrUpdate(user);
            roleMaintainService.insertOrUpdate(role);

            userMaintainService.addRoleRelation(user.getKey(), role.getKey());
            assertEquals(1, userMaintainService.lookup(UserMaintainService.CHILD_FOR_ROLE_SET,
                    new Object[]{Collections.singletonList(role.getKey())}).getCount());

            userCache.delete(user.getKey());
            userDao.batchUpdate(Collections.singletonList(user));
            assertEquals(1, userMaintainService.lookup(UserMaintainService.CHILD_FOR_ROLE_SET,
                    new Object[]{Collections.singletonList(role.getKey())}).getCount());

            roleCache.delete(role.getKey());
            roleDao.batchUpdate(Collections.singletonList(role));
            assertEquals(1, userMaintainService.lookup(UserMaintainService.CHILD_FOR_ROLE_SET,
                    new Object[]{Collections.singletonList(role.getKey())}).getCount());
        } finally {
            if (Objects.nonNull(role.getKey())) {
                roleMaintainService.deleteIfExists(role.getKey());
            }
            if (Objects.nonNull(user.getKey())) {
                userMaintainService.deleteIfExists(user.getKey());
            }
        }
    }
}

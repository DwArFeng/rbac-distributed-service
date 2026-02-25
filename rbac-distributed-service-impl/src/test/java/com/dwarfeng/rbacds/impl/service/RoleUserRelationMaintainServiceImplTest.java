package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.RoleUserRelation;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.bean.key.RoleUserRelationKey;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.rbacds.stack.service.RoleUserRelationMaintainService;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class RoleUserRelationMaintainServiceImplTest {

    private static final String ROLE_STRING_ID = "test_role";
    private static final String USER_STRING_ID = "test_user";

    @Autowired
    private RoleMaintainService roleMaintainService;
    @Autowired
    private UserMaintainService userMaintainService;
    @Autowired
    private RoleUserRelationMaintainService roleUserRelationMaintainService;

    private Role role;
    private User user;
    private RoleUserRelation roleUserRelation;

    @Before
    public void setUp() {
        role = new Role(new StringIdKey(ROLE_STRING_ID), "name", true, "remark");
        user = new User(new StringIdKey(USER_STRING_ID), "remark");
        roleUserRelation = new RoleUserRelation(
                new RoleUserRelationKey(ROLE_STRING_ID, USER_STRING_ID), true, "remark"
        );
    }

    @After
    public void tearDown() {
        role = null;
        user = null;
        roleUserRelation = null;
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            roleMaintainService.insertOrUpdate(role);
            userMaintainService.insertOrUpdate(user);
            roleUserRelationMaintainService.insertOrUpdate(roleUserRelation);
            RoleUserRelation testRoleUserRelation = roleUserRelationMaintainService.get(roleUserRelation.getKey());
            assertEquals(BeanUtils.describe(roleUserRelation), BeanUtils.describe(testRoleUserRelation));
            testRoleUserRelation = roleUserRelationMaintainService.get(roleUserRelation.getKey());
            assertEquals(BeanUtils.describe(roleUserRelation), BeanUtils.describe(testRoleUserRelation));
        } finally {
            if (Objects.nonNull(roleUserRelation.getKey())) {
                roleUserRelationMaintainService.deleteIfExists(roleUserRelation.getKey());
            }
            if (Objects.nonNull(user.getKey())) {
                userMaintainService.deleteIfExists(user.getKey());
            }
            if (Objects.nonNull(role.getKey())) {
                roleMaintainService.deleteIfExists(role.getKey());
            }
        }
    }

    @Test
    public void testForRoleCascade() throws Exception {
        try {
            roleMaintainService.insertOrUpdate(role);
            userMaintainService.insertOrUpdate(user);
            roleUserRelationMaintainService.insertOrUpdate(roleUserRelation);

            assertEquals(
                    1,
                    roleUserRelationMaintainService.lookupAsList(
                            RoleUserRelationMaintainService.CHILD_FOR_ROLE, new Object[]{role.getKey()}
                    ).size()
            );

            roleMaintainService.deleteIfExists(role.getKey());

            assertEquals(
                    0,
                    roleUserRelationMaintainService.lookupAsList(
                            RoleUserRelationMaintainService.CHILD_FOR_ROLE, new Object[]{role.getKey()}
                    ).size()
            );

            assertFalse(roleUserRelationMaintainService.exists(roleUserRelation.getKey()));
        } finally {
            if (Objects.nonNull(roleUserRelation.getKey())) {
                roleUserRelationMaintainService.deleteIfExists(roleUserRelation.getKey());
            }
            if (Objects.nonNull(user.getKey())) {
                userMaintainService.deleteIfExists(user.getKey());
            }
            if (Objects.nonNull(role.getKey())) {
                roleMaintainService.deleteIfExists(role.getKey());
            }
        }
    }

    @Test
    public void testForUserCascade() throws Exception {
        try {
            roleMaintainService.insertOrUpdate(role);
            userMaintainService.insertOrUpdate(user);
            roleUserRelationMaintainService.insertOrUpdate(roleUserRelation);

            assertEquals(
                    1,
                    roleUserRelationMaintainService.lookupAsList(
                            RoleUserRelationMaintainService.CHILD_FOR_USER, new Object[]{user.getKey()}
                    ).size()
            );

            userMaintainService.deleteIfExists(user.getKey());

            assertEquals(
                    0,
                    roleUserRelationMaintainService.lookupAsList(
                            RoleUserRelationMaintainService.CHILD_FOR_USER, new Object[]{user.getKey()}
                    ).size()
            );

            assertFalse(roleUserRelationMaintainService.exists(roleUserRelation.getKey()));
        } finally {
            if (Objects.nonNull(roleUserRelation.getKey())) {
                roleUserRelationMaintainService.deleteIfExists(roleUserRelation.getKey());
            }
            if (Objects.nonNull(user.getKey())) {
                userMaintainService.deleteIfExists(user.getKey());
            }
            if (Objects.nonNull(role.getKey())) {
                roleMaintainService.deleteIfExists(role.getKey());
            }
        }
    }
}

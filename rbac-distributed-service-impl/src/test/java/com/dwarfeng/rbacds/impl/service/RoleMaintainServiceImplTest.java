package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class RoleMaintainServiceImplTest {

    @Autowired
    private UserMaintainService userMaintainService;
    @Autowired
    private RoleMaintainService roleMaintainService;

    private List<Role> roles;

    private User zhangSan;
    private User liSi;
    private User wangWu;

    private Role admin;
    private Role guest;
    private Role moderator;

    @Before
    public void setUp() {
        zhangSan = new User(new StringIdKey("zhang_san"), "测试用账号");
        liSi = new User(new StringIdKey("li_si"), "测试用账号");
        wangWu = new User(new StringIdKey("wang_wu"), "测试用账号");
        admin = new Role(new StringIdKey("admin"), "管理员", true, "测试用角色");
        moderator = new Role(new StringIdKey("moderator"), "操作员", true, "测试用角色");
        guest = new Role(new StringIdKey("guest"), "访客", false, "测试用角色");

        roles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Role role = new Role(
                    new StringIdKey("role." + i),
                    "角色" + i,
                    true,
                    "测试用角色"
            );
            roles.add(role);
        }
    }

    @After
    public void tearDown() {
        zhangSan = null;
        liSi = null;
        wangWu = null;
        admin = null;
        guest = null;
        moderator = null;
        roles = null;
    }

    @Test
    public void test() throws Exception {
        try {
            for (Role role : roles) {
                roleMaintainService.insertOrUpdate(role);
                Role testRole = roleMaintainService.get(role.getKey());
                assertEquals(BeanUtils.describe(role), BeanUtils.describe(testRole));
            }
        } finally {
            for (Role role : roles) {
                if (Objects.isNull(role)) {
                    continue;
                }
                roleMaintainService.deleteIfExists(role.getKey());
            }
        }
    }

    @Test
    public void testRelation() throws ServiceException {
        try {
            userMaintainService.insertOrUpdate(zhangSan);
            userMaintainService.insertOrUpdate(liSi);
            userMaintainService.insertOrUpdate(wangWu);
            roleMaintainService.insertOrUpdate(admin);
            roleMaintainService.insertOrUpdate(guest);
            roleMaintainService.insertOrUpdate(moderator);
            roleMaintainService.addUserRelation(admin.getKey(), zhangSan.getKey());
            roleMaintainService.batchAddUserRelations(moderator.getKey(), Arrays.asList(zhangSan.getKey(), liSi.getKey()));
            roleMaintainService.batchAddUserRelations(guest.getKey(), Arrays.asList(zhangSan.getKey(), liSi.getKey(), wangWu.getKey()));

            //此处用断点观测roles的值，判断是否正确。
            List<Role> roles = roleMaintainService.lookup(RoleMaintainService.ROLE_FOR_USER, new Object[]{zhangSan.getKey()}).getData();
            List<StringIdKey> keys = roles.stream().map(Role::getKey).collect(Collectors.toList());
            assertTrue(keys.containsAll(Arrays.asList(admin.getKey(), moderator.getKey(), guest.getKey())));

            roles = roleMaintainService.lookup(RoleMaintainService.ROLE_FOR_USER, new Object[]{liSi.getKey()}).getData();
            keys = roles.stream().map(Role::getKey).collect(Collectors.toList());
            assertTrue(keys.containsAll(Arrays.asList(moderator.getKey(), guest.getKey())));

            roles = roleMaintainService.lookup(RoleMaintainService.ROLE_FOR_USER, new Object[]{wangWu.getKey()}).getData();
            keys = roles.stream().map(Role::getKey).collect(Collectors.toList());
            assertTrue(keys.contains(guest.getKey()));

            roles = roleMaintainService.lookup(RoleMaintainService.ENABLED_ROLE_FOR_USER, new Object[]{zhangSan.getKey()}).getData();
            keys = roles.stream().map(Role::getKey).collect(Collectors.toList());
            assertTrue(keys.containsAll(Arrays.asList(admin.getKey(), moderator.getKey())));

            roles = roleMaintainService.lookup(RoleMaintainService.ENABLED_ROLE_FOR_USER, new Object[]{liSi.getKey()}).getData();
            keys = roles.stream().map(Role::getKey).collect(Collectors.toList());
            assertTrue(keys.contains(moderator.getKey()));

            roles = roleMaintainService.lookup(RoleMaintainService.ENABLED_ROLE_FOR_USER, new Object[]{wangWu.getKey()}).getData();
            keys = roles.stream().map(Role::getKey).collect(Collectors.toList());
            assertTrue(keys.isEmpty());
        } finally {
            if (Objects.nonNull(moderator.getKey())) {
                roleMaintainService.deleteIfExists(moderator.getKey());
            }
            if (Objects.nonNull(guest.getKey())) {
                roleMaintainService.deleteIfExists(guest.getKey());
            }
            if (Objects.nonNull(admin.getKey())) {
                roleMaintainService.deleteIfExists(admin.getKey());
            }
            if (Objects.nonNull(wangWu.getKey())) {
                userMaintainService.deleteIfExists(wangWu.getKey());
            }
            if (Objects.nonNull(liSi.getKey())) {
                userMaintainService.deleteIfExists(liSi.getKey());
            }
            if (Objects.nonNull(zhangSan.getKey())) {
                userMaintainService.deleteIfExists(zhangSan.getKey());
            }
        }
    }
}

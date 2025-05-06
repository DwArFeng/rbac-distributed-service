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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class UserMaintainServiceImplTest {

    @Autowired
    private UserMaintainService userMaintainService;
    @Autowired
    private RoleMaintainService roleMaintainService;

    private User zhangSan;
    private User liSi;
    private User wangWu;
    private User zhaoLiu;

    private Role admin;
    private Role guest;
    private Role moderator;

    @Before
    public void setUp() {
        zhangSan = new User(new StringIdKey("zhang_san"), "测试用账号");
        liSi = new User(new StringIdKey("li_si"), "测试用账号");
        wangWu = new User(new StringIdKey("wang_wu"), "测试用账号");
        zhaoLiu = new User(new StringIdKey("zhao_liu"), "测试用账号");
        admin = new Role(new StringIdKey("admin"), "管理员", true, "测试用角色");
        moderator = new Role(new StringIdKey("moderator"), "操作员", true, "测试用角色");
        guest = new Role(new StringIdKey("guest"), "访客", false, "测试用角色");
    }

    @After
    public void tearDown() {
        zhangSan = null;
        liSi = null;
        wangWu = null;
        zhaoLiu = null;
        admin = null;
        guest = null;
        moderator = null;
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            userMaintainService.insertOrUpdate(zhangSan);

            User testZhangSan = userMaintainService.get(zhangSan.getKey());
            assertEquals(BeanUtils.describe(zhangSan), BeanUtils.describe(testZhangSan));
            userMaintainService.update(zhangSan);
            testZhangSan = userMaintainService.get(zhangSan.getKey());
            assertEquals(BeanUtils.describe(zhangSan), BeanUtils.describe(testZhangSan));
        } finally {
            userMaintainService.deleteIfExists(zhangSan.getKey());
        }
    }

    @Test
    public void testForLookup() throws ServiceException {
        try {
            userMaintainService.insertOrUpdate(zhangSan);
            userMaintainService.insertOrUpdate(liSi);
            userMaintainService.insertOrUpdate(wangWu);
            userMaintainService.insertOrUpdate(zhaoLiu);
            roleMaintainService.insertOrUpdate(admin);
            roleMaintainService.insertOrUpdate(guest);
            roleMaintainService.insertOrUpdate(moderator);
            userMaintainService.batchAddRoleRelations(
                    zhangSan.getKey(), Arrays.asList(admin.getKey(), moderator.getKey(), guest.getKey())
            );
            userMaintainService.batchAddRoleRelations(liSi.getKey(), Arrays.asList(moderator.getKey(), guest.getKey()));
            userMaintainService.addRoleRelation(wangWu.getKey(), guest.getKey());

            List<StringIdKey> lookupUserKeys = userMaintainService.lookup(
                    UserMaintainService.CHILD_FOR_ROLE, new Object[]{null}
            ).getData().stream().map(User::getKey).collect(Collectors.toList());
            assertFalse(lookupUserKeys.isEmpty());
            assertTrue(lookupUserKeys.contains(zhaoLiu.getKey()));
            lookupUserKeys = userMaintainService.lookup(
                    UserMaintainService.CHILD_FOR_ROLE, new Object[]{guest.getKey()}
            ).getData().stream().map(User::getKey).collect(Collectors.toList());
            assertTrue(lookupUserKeys.size() >= 3);
            assertTrue(lookupUserKeys.contains(zhangSan.getKey()));
            assertTrue(lookupUserKeys.contains(liSi.getKey()));
            assertTrue(lookupUserKeys.contains(wangWu.getKey()));
        } finally {
            if (Objects.nonNull(zhangSan)) userMaintainService.deleteIfExists(zhangSan.getKey());
            if (Objects.nonNull(liSi)) userMaintainService.deleteIfExists(liSi.getKey());
            if (Objects.nonNull(wangWu)) userMaintainService.deleteIfExists(wangWu.getKey());
            if (Objects.nonNull(zhaoLiu)) userMaintainService.deleteIfExists(zhaoLiu.getKey());
            roleMaintainService.deleteIfExists(admin.getKey());
            roleMaintainService.deleteIfExists(moderator.getKey());
            roleMaintainService.deleteIfExists(guest.getKey());
        }
    }
}

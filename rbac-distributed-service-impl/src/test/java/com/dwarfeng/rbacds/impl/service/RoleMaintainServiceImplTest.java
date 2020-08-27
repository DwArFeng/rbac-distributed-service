package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class RoleMaintainServiceImplTest {

    @Autowired
    private UserMaintainService userMaintainService;
    @Autowired
    private RoleMaintainService roleMaintainService;

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
        admin = new Role(new StringIdKey("admin"), true, "测试用角色");
        guest = new Role(new StringIdKey("guest"), false, "测试用角色");
        moderator = new Role(new StringIdKey("moderator"), true, "测试用角色");
    }

    @After
    public void tearDown() {
        zhangSan = null;
        liSi = null;
        wangWu = null;
        admin = null;
        guest = null;
        moderator = null;
    }


    @Test
    public void test() throws ServiceException {
        try {
            userMaintainService.insert(zhangSan);
            userMaintainService.insert(liSi);
            userMaintainService.insert(wangWu);
            roleMaintainService.insert(admin);
            roleMaintainService.insert(guest);
            roleMaintainService.insert(moderator);
            roleMaintainService.addUserRelation(admin.getKey(), zhangSan.getKey());
            roleMaintainService.batchAddUserRelations(moderator.getKey(), Arrays.asList(zhangSan.getKey(), liSi.getKey()));
            roleMaintainService.batchAddUserRelations(guest.getKey(), Arrays.asList(zhangSan.getKey(), liSi.getKey(), wangWu.getKey()));

            //此处用断点观测roles的值，判断是否正确。
            //noinspection unused
            List<Role> roles = roleMaintainService.lookup(RoleMaintainService.ROLE_FOR_USER, new Object[]{zhangSan.getKey()}).getData();
            //noinspection UnusedAssignment
            roles = roleMaintainService.lookup(RoleMaintainService.ROLE_FOR_USER, new Object[]{liSi.getKey()}).getData();
            //noinspection UnusedAssignment
            roles = roleMaintainService.lookup(RoleMaintainService.ROLE_FOR_USER, new Object[]{wangWu.getKey()}).getData();
            //noinspection UnusedAssignment
            roles = roleMaintainService.lookup(RoleMaintainService.ENABLED_ROLE_FOR_USER, new Object[]{zhangSan.getKey()}).getData();
            //noinspection UnusedAssignment
            roles = roleMaintainService.lookup(RoleMaintainService.ENABLED_ROLE_FOR_USER, new Object[]{liSi.getKey()}).getData();
            //noinspection UnusedAssignment
            roles = roleMaintainService.lookup(RoleMaintainService.ENABLED_ROLE_FOR_USER, new Object[]{wangWu.getKey()}).getData();
            //noinspection UnusedAssignment
            roles = null;
        } finally {
            if (Objects.nonNull(zhangSan)) userMaintainService.delete(zhangSan.getKey());
            roleMaintainService.delete(admin.getKey());
            if (Objects.nonNull(liSi)) userMaintainService.delete(liSi.getKey());
            roleMaintainService.delete(moderator.getKey());
            if (Objects.nonNull(wangWu)) userMaintainService.delete(wangWu.getKey());
            roleMaintainService.delete(guest.getKey());
        }
    }
}

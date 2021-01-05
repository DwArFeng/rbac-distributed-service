package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class PexpMaintainServiceImplTest {

    @Autowired
    private RoleMaintainService roleMaintainService;
    @Autowired
    private PexpMaintainService pexpMaintainService;

    private Role admin;
    private Role moderator;
    private Role guest;

    private Pexp pexpA;
    private Pexp pexpB;
    private Pexp pexpC;
    private Pexp pexpD;
    private Pexp pexpE;
    private Pexp pexpF;

    @Before
    public void setUp() {
        admin = new Role(new StringIdKey("admin"), null, "管理员", true, "测试用角色");
        moderator = new Role(new StringIdKey("moderator"), null, "操作员", true, "测试用角色");
        guest = new Role(new StringIdKey("guest"), null, "访客", false, "测试用角色");

        pexpA = new Pexp(new LongIdKey(1L), admin.getKey(), "pexp.a", "测试用权限表达式");
        pexpB = new Pexp(new LongIdKey(2L), admin.getKey(), "pexp.b", "测试用权限表达式");
        pexpC = new Pexp(new LongIdKey(3L), moderator.getKey(), "pexp.c", "测试用权限表达式");
        pexpD = new Pexp(new LongIdKey(4L), moderator.getKey(), "pexp.d", "测试用权限表达式");
        pexpE = new Pexp(new LongIdKey(5L), guest.getKey(), "pexp.e", "测试用权限表达式");
        pexpF = new Pexp(new LongIdKey(6L), guest.getKey(), "pexp.f", "测试用权限表达式");
    }

    @After
    public void tearDown() {
        admin = null;
        guest = null;
        moderator = null;

        pexpA = null;
        pexpB = null;
        pexpC = null;
        pexpD = null;
        pexpE = null;
        pexpF = null;
    }

    @Test
    public void test() throws ServiceException {
        try {
            roleMaintainService.insertOrUpdate(admin);
            roleMaintainService.insertOrUpdate(guest);
            roleMaintainService.insertOrUpdate(moderator);
            pexpMaintainService.insertOrUpdate(pexpA);
            pexpMaintainService.insertOrUpdate(pexpB);
            pexpMaintainService.insertOrUpdate(pexpC);
            pexpMaintainService.insertOrUpdate(pexpD);
            pexpMaintainService.insertOrUpdate(pexpE);
            pexpMaintainService.insertOrUpdate(pexpF);

            //此处用断点观测roles的值，判断是否正确。
            //noinspection unused
            List<Pexp> pexps = pexpMaintainService.lookup(PexpMaintainService.PEXP_FOR_ROLE, new Object[]{admin.getKey()}).getData();
            //noinspection UnusedAssignment
            pexps = pexpMaintainService.lookup(PexpMaintainService.PEXP_FOR_ROLE, new Object[]{moderator.getKey()}).getData();
            //noinspection UnusedAssignment
            pexps = pexpMaintainService.lookup(PexpMaintainService.PEXP_FOR_ROLE, new Object[]{guest.getKey()}).getData();
            //noinspection UnusedAssignment
            pexps = pexpMaintainService.lookup(PexpMaintainService.PEXP_FOR_ROLE_SET, new Object[]{Arrays.asList(admin.getKey(), moderator.getKey())}).getData();
            //noinspection UnusedAssignment
            pexps = pexpMaintainService.lookup(PexpMaintainService.PEXP_FOR_ROLE_SET, new Object[]{Arrays.asList(moderator.getKey(), guest.getKey())}).getData();
            //noinspection UnusedAssignment
            pexps = null;
        } finally {
            roleMaintainService.deleteIfExists(admin.getKey());
            roleMaintainService.deleteIfExists(moderator.getKey());
            roleMaintainService.deleteIfExists(guest.getKey());
            pexpMaintainService.deleteIfExists(pexpA.getKey());
            pexpMaintainService.deleteIfExists(pexpB.getKey());
            pexpMaintainService.deleteIfExists(pexpC.getKey());
            pexpMaintainService.deleteIfExists(pexpD.getKey());
            pexpMaintainService.deleteIfExists(pexpE.getKey());
            pexpMaintainService.deleteIfExists(pexpF.getKey());
        }
    }
}

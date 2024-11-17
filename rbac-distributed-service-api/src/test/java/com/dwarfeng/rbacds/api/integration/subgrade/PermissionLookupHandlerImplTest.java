package com.dwarfeng.rbacds.api.integration.subgrade;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.subgrade.sdk.interceptor.ExceptionContext;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.RequestUser;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class PermissionLookupHandlerImplTest {

    @Autowired
    private UserMaintainService userMaintainService;
    @Autowired
    private RoleMaintainService roleMaintainService;
    @Autowired
    private PexpMaintainService pexpMaintainService;
    @Autowired
    private PermissionMaintainService permissionMaintainService;
    @Autowired
    private AopTester aopTester;

    private User zhangSan;
    private User liSi;
    private User wangWu;

    private Role roleA;
    private Role roleB;
    private Role roleC;

    private Pexp pexp1;
    private Pexp pexp2;
    private Pexp pexp3;
    private Pexp pexp4;
    private Pexp pexp5;
    private Pexp pexp6;

    private Permission permission1;
    private Permission permission2;
    private Permission permission3;
    private Permission permission4;
    private Permission permission5;
    private Permission permission6;
    private Permission permission7;
    private Permission permission8;
    private Permission permission9;

    @Before
    public void setUp() {
        zhangSan = new User(new StringIdKey("zhang_san"), "测试用账号");
        liSi = new User(new StringIdKey("li_si"), "测试用账号");
        wangWu = new User(new StringIdKey("wang_wu"), "测试用账号");
        roleA = new Role(new StringIdKey("role.a"), "角色A", true, "测试用角色");
        roleB = new Role(new StringIdKey("role.b"), "角色B", false, "测试用角色");
        roleC = new Role(new StringIdKey("role.c"), "角色C", true, "测试用角色");
        pexp1 = new Pexp(new LongIdKey(1L), roleA.getKey(), "+id_regex@^.*\\.1$", "正则:匹配所有以1结尾的权限");
        pexp2 = new Pexp(new LongIdKey(2L), roleA.getKey(), "!exact@permission.a.1", "精确:去除permission.a.1");
        pexp3 = new Pexp(new LongIdKey(3L), roleB.getKey(), "!id_regex@^.*$", "正则:去除所有权限");
        pexp4 = new Pexp(new LongIdKey(4L), roleB.getKey(), "!id_regex@^.*$", "正则:去除所有权限");
        pexp5 = new Pexp(new LongIdKey(5L), roleC.getKey(), "+id_regex@^.*\\.3$", "正则:匹配所有以3结尾的权限");
        pexp6 = new Pexp(new LongIdKey(6L), roleC.getKey(), "!exact@permission.c.3", "精确:去除permission.c.3");
        permission1 = new Permission(new StringIdKey("permission.a.1"), null, "测试权限a.1", "测试用权限");
        permission2 = new Permission(new StringIdKey("permission.a.2"), null, "测试权限a.2", "测试用权限");
        permission3 = new Permission(new StringIdKey("permission.a.3"), null, "测试权限a.3", "测试用权限");
        permission4 = new Permission(new StringIdKey("permission.b.1"), null, "测试权限b.1", "测试用权限");
        permission5 = new Permission(new StringIdKey("permission.b.2"), null, "测试权限b.2", "测试用权限");
        permission6 = new Permission(new StringIdKey("permission.b.3"), null, "测试权限b.3", "测试用权限");
        permission7 = new Permission(new StringIdKey("permission.c.1"), null, "测试权限c.1", "测试用权限");
        permission8 = new Permission(new StringIdKey("permission.c.2"), null, "测试权限c.2", "测试用权限");
        permission9 = new Permission(new StringIdKey("permission.c.3"), null, "测试权限c.3", "测试用权限");
    }

    @After
    public void tearDown() {
        zhangSan = null;
        liSi = null;
        wangWu = null;
        roleA = null;
        roleB = null;
        roleC = null;
        pexp1 = null;
        pexp2 = null;
        pexp3 = null;
        pexp4 = null;
        pexp5 = null;
        pexp6 = null;
        permission1 = null;
        permission2 = null;
        permission3 = null;
        permission4 = null;
        permission5 = null;
        permission6 = null;
        permission7 = null;
        permission8 = null;
        permission9 = null;
    }

    @Test
    public void test() throws Exception {
        try {
            userMaintainService.insertOrUpdate(zhangSan);
            userMaintainService.insertOrUpdate(liSi);
            userMaintainService.insertOrUpdate(wangWu);

            roleMaintainService.insertOrUpdate(roleA);
            roleMaintainService.insertOrUpdate(roleB);
            roleMaintainService.insertOrUpdate(roleC);

            userMaintainService.batchAddRoleRelations(zhangSan.getKey(), Arrays.asList(roleA.getKey(), roleB.getKey(), roleC.getKey()));
            userMaintainService.batchAddRoleRelations(liSi.getKey(), Arrays.asList(roleB.getKey(), roleC.getKey()));
            userMaintainService.batchAddRoleRelations(wangWu.getKey(), Collections.singletonList(roleC.getKey()));

            pexpMaintainService.insertOrUpdate(pexp1);
            pexpMaintainService.insertOrUpdate(pexp2);
            pexpMaintainService.insertOrUpdate(pexp3);
            pexpMaintainService.insertOrUpdate(pexp4);
            pexpMaintainService.insertOrUpdate(pexp5);
            pexpMaintainService.insertOrUpdate(pexp6);

            permissionMaintainService.insertOrUpdate(permission1);
            permissionMaintainService.insertOrUpdate(permission2);
            permissionMaintainService.insertOrUpdate(permission3);
            permissionMaintainService.insertOrUpdate(permission4);
            permissionMaintainService.insertOrUpdate(permission5);
            permissionMaintainService.insertOrUpdate(permission6);
            permissionMaintainService.insertOrUpdate(permission7);
            permissionMaintainService.insertOrUpdate(permission8);
            permissionMaintainService.insertOrUpdate(permission9);

            ExceptionContext exceptionContext = aopTester.test(zhangSan.getKey(), null);
            assertEquals(1, exceptionContext.getExceptions().size());
            assertEquals(
                    "Missing permission [permission.a.1, permission.a.2, permission.b.2, permission.c.2, permission.c.3]",
                    exceptionContext.getExceptions().get(0).getMessage()
            );

            exceptionContext = aopTester.test(liSi.getKey(), null);
            assertEquals(1, exceptionContext.getExceptions().size());
            assertEquals(
                    "Missing permission [permission.a.1, permission.a.2, permission.b.1, permission.b.2, permission.c.1, permission.c.2, permission.c.3]",
                    exceptionContext.getExceptions().get(0).getMessage()
            );

            exceptionContext = aopTester.test(wangWu.getKey(), null);
            assertEquals(1, exceptionContext.getExceptions().size());
            assertEquals(
                    "Missing permission [permission.a.1, permission.a.2, permission.b.1, permission.b.2, permission.c.1, permission.c.2, permission.c.3]",
                    exceptionContext.getExceptions().get(0).getMessage()
            );

            exceptionContext = aopTester.test(zhangSan.getKey(), null);
            assertEquals(1, exceptionContext.getExceptions().size());
            assertEquals(
                    "Missing permission [permission.a.1, permission.a.2, permission.b.2, permission.c.2, permission.c.3]",
                    exceptionContext.getExceptions().get(0).getMessage()
            );
        } finally {
            permissionMaintainService.deleteIfExists(permission1.getKey());
            permissionMaintainService.deleteIfExists(permission2.getKey());
            permissionMaintainService.deleteIfExists(permission3.getKey());
            permissionMaintainService.deleteIfExists(permission4.getKey());
            permissionMaintainService.deleteIfExists(permission5.getKey());
            permissionMaintainService.deleteIfExists(permission6.getKey());
            permissionMaintainService.deleteIfExists(permission7.getKey());
            permissionMaintainService.deleteIfExists(permission8.getKey());
            permissionMaintainService.deleteIfExists(permission9.getKey());

            pexpMaintainService.deleteIfExists(pexp1.getKey());
            pexpMaintainService.deleteIfExists(pexp2.getKey());
            pexpMaintainService.deleteIfExists(pexp3.getKey());
            pexpMaintainService.deleteIfExists(pexp4.getKey());
            pexpMaintainService.deleteIfExists(pexp5.getKey());
            pexpMaintainService.deleteIfExists(pexp6.getKey());

            roleMaintainService.deleteIfExists(roleA.getKey());
            roleMaintainService.deleteIfExists(roleB.getKey());
            roleMaintainService.deleteIfExists(roleC.getKey());

            if (Objects.nonNull(zhangSan)) {
                userMaintainService.deleteIfExists(zhangSan.getKey());
            }
            if (Objects.nonNull(liSi)) {
                userMaintainService.deleteIfExists(liSi.getKey());
            }
            if (Objects.nonNull(wangWu)) {
                userMaintainService.deleteIfExists(wangWu.getKey());
            }
        }
    }

    @Component
    public static class AopTester {

        @PermissionRequired({
                "permission.a.1",
                "permission.a.2",
                "permission.a.3",
                "permission.b.1",
                "permission.b.2",
                "permission.b.3",
                "permission.c.1",
                "permission.c.2",
                "permission.c.3",
        })
        public ExceptionContext test(@RequestUser StringIdKey userKey, ExceptionContext exceptionContext) {
            return exceptionContext;
        }
    }
}

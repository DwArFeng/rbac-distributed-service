package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.service.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class PermissionLookupServiceImplTest {

    @Autowired
    private UserMaintainService userMaintainService;
    @Autowired
    private RoleMaintainService roleMaintainService;
    @Autowired
    private PexpMaintainService pexpMaintainService;
    @Autowired
    private PermissionMaintainService permissionMaintainService;
    @Autowired
    private PermissionLookupService permissionLookupService;

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
        roleA = new Role(new StringIdKey("role.a"), true, "测试用角色");
        roleB = new Role(new StringIdKey("role.b"), false, "测试用角色");
        roleC = new Role(new StringIdKey("role.c"), true, "测试用角色");
        pexp1 = new Pexp(new LongIdKey(1L), roleA.getKey(), "+regex@^.*\\.1$", "正则:匹配所有以1结尾的权限");
        pexp2 = new Pexp(new LongIdKey(2L), roleA.getKey(), "!exact@permission.a.1", "精确:去除permission.a.1");
        pexp3 = new Pexp(new LongIdKey(3L), roleB.getKey(), "!regex@^.*$", "正则:去除所有权限");
        pexp4 = new Pexp(new LongIdKey(4L), roleB.getKey(), "!regex@^.*$", "正则:去除所有权限");
        pexp5 = new Pexp(new LongIdKey(5L), roleC.getKey(), "+regex@^.*\\.3$", "正则:匹配所有以3结尾的权限");
        pexp6 = new Pexp(new LongIdKey(6L), roleC.getKey(), "!exact@permission.c.3", "精确:去除permission.c.3");
        permission1 = new Permission(new StringIdKey("permission.a.1"), "测试用权限");
        permission2 = new Permission(new StringIdKey("permission.a.2"), "测试用权限");
        permission3 = new Permission(new StringIdKey("permission.a.3"), "测试用权限");
        permission4 = new Permission(new StringIdKey("permission.b.1"), "测试用权限");
        permission5 = new Permission(new StringIdKey("permission.b.2"), "测试用权限");
        permission6 = new Permission(new StringIdKey("permission.b.3"), "测试用权限");
        permission7 = new Permission(new StringIdKey("permission.c.1"), "测试用权限");
        permission8 = new Permission(new StringIdKey("permission.c.2"), "测试用权限");
        permission9 = new Permission(new StringIdKey("permission.c.3"), "测试用权限");
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
            userMaintainService.insert(zhangSan);
            userMaintainService.insert(liSi);
            userMaintainService.insert(wangWu);

            roleMaintainService.insert(roleA);
            roleMaintainService.insert(roleB);
            roleMaintainService.insert(roleC);

            userMaintainService.batchAddRoleRelations(zhangSan.getKey(), Arrays.asList(roleA.getKey(), roleB.getKey(), roleC.getKey()));
            userMaintainService.batchAddRoleRelations(liSi.getKey(), Arrays.asList(roleB.getKey(), roleC.getKey()));
            userMaintainService.batchAddRoleRelations(wangWu.getKey(), Collections.singletonList(roleC.getKey()));

            pexpMaintainService.insert(pexp1);
            pexpMaintainService.insert(pexp2);
            pexpMaintainService.insert(pexp3);
            pexpMaintainService.insert(pexp4);
            pexpMaintainService.insert(pexp5);
            pexpMaintainService.insert(pexp6);

            permissionMaintainService.insert(permission1);
            permissionMaintainService.insert(permission2);
            permissionMaintainService.insert(permission3);
            permissionMaintainService.insert(permission4);
            permissionMaintainService.insert(permission5);
            permissionMaintainService.insert(permission6);
            permissionMaintainService.insert(permission7);
            permissionMaintainService.insert(permission8);
            permissionMaintainService.insert(permission9);

            List<Permission> permissions = permissionLookupService.lookupPermissions(zhangSan.getKey());
            //noinspection UnusedAssignment
            permissions = permissionLookupService.lookupPermissions(liSi.getKey());
            //noinspection UnusedAssignment
            permissions = permissionLookupService.lookupPermissions(wangWu.getKey());
            //noinspection UnusedAssignment
            permissions = null;
        } finally {
            if (Objects.nonNull(zhangSan)) userMaintainService.delete(zhangSan.getKey());
            if (Objects.nonNull(liSi)) userMaintainService.delete(liSi.getKey());
            if (Objects.nonNull(wangWu)) userMaintainService.delete(wangWu.getKey());

            roleMaintainService.delete(roleA.getKey());
            roleMaintainService.delete(roleB.getKey());
            roleMaintainService.delete(roleC.getKey());

            pexpMaintainService.delete(pexp1.getKey());
            pexpMaintainService.delete(pexp2.getKey());
            pexpMaintainService.delete(pexp3.getKey());
            pexpMaintainService.delete(pexp4.getKey());
            pexpMaintainService.delete(pexp5.getKey());
            pexpMaintainService.delete(pexp6.getKey());

            permissionMaintainService.delete(permission1.getKey());
            permissionMaintainService.delete(permission2.getKey());
            permissionMaintainService.delete(permission3.getKey());
            permissionMaintainService.delete(permission4.getKey());
            permissionMaintainService.delete(permission5.getKey());
            permissionMaintainService.delete(permission6.getKey());
            permissionMaintainService.delete(permission7.getKey());
            permissionMaintainService.delete(permission8.getKey());
            permissionMaintainService.delete(permission9.getKey());
        }
    }
}

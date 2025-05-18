package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class PermissionMaintainServiceImplTest {

    @Autowired
    private PermissionMaintainService permissionMaintainService;
    @Autowired
    private PermissionGroupMaintainService permissionGroupMaintainService;

    private PermissionGroup parentPermissionGroup;
    private List<Permission> permissions;

    @Before
    public void setUp() {
        parentPermissionGroup = new PermissionGroup(
                new StringIdKey("permission_group"),
                null,
                "权限组",
                "测试用权限组"
        );
        permissions = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Permission permission = new Permission(
                    new StringIdKey("permission." + i),
                    new StringIdKey("permission_group"),
                    "权限" + i,
                    "测试用权限",
                    12450
            );
            permissions.add(permission);
        }
    }

    @After
    public void tearDown() {
        parentPermissionGroup = null;
        permissions = null;
    }

    @Test
    public void test() throws Exception {
        try {
            permissionGroupMaintainService.insertOrUpdate(parentPermissionGroup);
            for (Permission permission : permissions) {
                permissionMaintainService.insertOrUpdate(permission);
                Permission testPermission = permissionMaintainService.get(permission.getKey());
                assertEquals(BeanUtils.describe(permission), BeanUtils.describe(testPermission));
            }
            PagedData<Permission> lookup = permissionMaintainService.lookup(
                    PermissionMaintainService.CHILD_FOR_GROUP, new Object[]{new StringIdKey("permission_group")}
            );
            assertEquals(permissions.size(), lookup.getCount());
        } finally {
            for (Permission permission : permissions) {
                if (Objects.isNull(permission.getKey())) {
                    continue;
                }
                permissionMaintainService.deleteIfExists(permission.getKey());
            }
            if (Objects.nonNull(parentPermissionGroup.getKey())) {
                permissionGroupMaintainService.deleteIfExists(parentPermissionGroup.getKey());
            }
        }
    }
}

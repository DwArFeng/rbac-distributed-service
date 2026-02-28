package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.bean.entity.Scope;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.rbacds.stack.service.ScopeMaintainService;
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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class PermissionMaintainServiceImplTest {

    private static final String SCOPE_STRING_ID = "test_scope";
    private static final String PARENT_PERMISSION_GROUP_STRING_ID = "test_parent_permission_group";

    @Autowired
    private ScopeMaintainService scopeMaintainService;
    @Autowired
    private PermissionGroupMaintainService permissionGroupMaintainService;
    @Autowired
    private PermissionMaintainService permissionMaintainService;

    private Scope scope;
    private PermissionGroup parentPermissionGroup;
    private List<Permission> permissions;

    @Before
    public void setUp() {
        scope = new Scope(new StringIdKey(SCOPE_STRING_ID), "name", "remark");
        parentPermissionGroup = new PermissionGroup(
                new PermissionGroupKey(SCOPE_STRING_ID, PARENT_PERMISSION_GROUP_STRING_ID), null, "name", "remark"
        );
        permissions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Permission permission = new Permission(
                    new PermissionKey(SCOPE_STRING_ID, "test_permission." + i),
                    new PermissionGroupKey(SCOPE_STRING_ID, PARENT_PERMISSION_GROUP_STRING_ID), "name", "remark",
                    12450, new String[]{"group_path.1", "group_path.2"}
            );
            permissions.add(permission);
        }
    }

    @After
    public void tearDown() {
        scope = null;
        parentPermissionGroup = null;
        permissions = null;
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            scopeMaintainService.insertOrUpdate(scope);
            permissionGroupMaintainService.insertOrUpdate(parentPermissionGroup);
            for (Permission permission : permissions) {
                permissionMaintainService.insertOrUpdate(permission);
                Permission testPermission = permissionMaintainService.get(permission.getKey());
                assertEquals(BeanUtils.describe(permission), BeanUtils.describe(testPermission));
                permissionMaintainService.get(permission.getKey());
                assertEquals(BeanUtils.describe(permission), BeanUtils.describe(testPermission));
            }
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
            if (Objects.nonNull(scope.getKey())) {
                scopeMaintainService.deleteIfExists(scope.getKey());
            }
        }
    }

    @Test
    public void testForScopeCascade() throws Exception {
        try {
            scopeMaintainService.insertOrUpdate(scope);
            permissionGroupMaintainService.insertOrUpdate(parentPermissionGroup);
            for (Permission permission : permissions) {
                permissionMaintainService.insertOrUpdate(permission);
            }

            assertEquals(
                    permissions.size(),
                    permissionMaintainService.lookupAsList(
                            PermissionMaintainService.CHILD_FOR_SCOPE,
                            new Object[]{scope.getKey()}
                    ).size()
            );

            scopeMaintainService.deleteIfExists(scope.getKey());

            assertEquals(
                    0,
                    permissionMaintainService.lookupAsList(
                            PermissionMaintainService.CHILD_FOR_SCOPE,
                            new Object[]{scope.getKey()}
                    ).size()
            );

            for (Permission permission : permissions) {
                assertFalse(permissionMaintainService.exists(permission.getKey()));
            }
        } finally {
            for (Permission permission : permissions) {
                if (Objects.nonNull(permission.getKey())) {
                    permissionMaintainService.deleteIfExists(permission.getKey());
                }
            }
            if (Objects.nonNull(parentPermissionGroup.getKey())) {
                permissionGroupMaintainService.deleteIfExists(parentPermissionGroup.getKey());
            }
            if (Objects.nonNull(scope.getKey())) {
                scopeMaintainService.deleteIfExists(scope.getKey());
            }
        }
    }

    @Test
    public void testForPermissionGroupCascade() throws Exception {
        try {
            scopeMaintainService.insertOrUpdate(scope);
            permissionGroupMaintainService.insertOrUpdate(parentPermissionGroup);
            for (Permission permission : permissions) {
                permissionMaintainService.insertOrUpdate(permission);
            }

            assertEquals(
                    permissions.size(),
                    permissionMaintainService.lookupAsList(
                            PermissionMaintainService.CHILD_FOR_GROUP,
                            new Object[]{parentPermissionGroup.getKey()}
                    ).size()
            );

            permissionGroupMaintainService.deleteIfExists(parentPermissionGroup.getKey());

            assertEquals(
                    0,
                    permissionMaintainService.lookupAsList(
                            PermissionMaintainService.CHILD_FOR_GROUP,
                            new Object[]{parentPermissionGroup.getKey()}
                    ).size()
            );

            for (Permission permission : permissions) {
                assertTrue(permissionMaintainService.exists(permission.getKey()));
                Permission testPermission = permissionMaintainService.get(permission.getKey());
                assertNull(testPermission.getGroupKey());
            }
        } finally {
            for (Permission permission : permissions) {
                if (Objects.nonNull(permission.getKey())) {
                    permissionMaintainService.deleteIfExists(permission.getKey());
                }
            }
            if (Objects.nonNull(parentPermissionGroup.getKey())) {
                permissionGroupMaintainService.deleteIfExists(parentPermissionGroup.getKey());
            }
            if (Objects.nonNull(scope.getKey())) {
                scopeMaintainService.deleteIfExists(scope.getKey());
            }
        }
    }
}

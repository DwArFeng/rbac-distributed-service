package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.bean.entity.Scope;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
import com.dwarfeng.rbacds.stack.service.ScopeMaintainService;
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
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class PermissionGroupMaintainServiceImplTest {

    private static final String SCOPE_STRING_ID = "test_scope";
    private static final String PARENT_PERMISSION_GROUP_STRING_ID = "test_parent_permission_group";

    @Autowired
    private ScopeMaintainService scopeMaintainService;
    @Autowired
    private PermissionGroupMaintainService permissionGroupMaintainService;

    private Scope scope;
    private PermissionGroup parentPermissionGroup;
    private List<PermissionGroup> permissionGroups;

    @Before
    public void setUp() {
        scope = new Scope(new StringIdKey(SCOPE_STRING_ID), "name", "remark");
        parentPermissionGroup = new PermissionGroup(
                new PermissionGroupKey(SCOPE_STRING_ID, PARENT_PERMISSION_GROUP_STRING_ID), null, "name", "remark"
        );
        permissionGroups = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PermissionGroup permissionGroup = new PermissionGroup(
                    new PermissionGroupKey(SCOPE_STRING_ID, "child_permission_group." + i),
                    new PermissionGroupKey(SCOPE_STRING_ID, PARENT_PERMISSION_GROUP_STRING_ID), "name", "remark"
            );
            permissionGroups.add(permissionGroup);
        }
    }

    @After
    public void tearDown() {
        scope = null;
        parentPermissionGroup = null;
        permissionGroups = null;
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            scopeMaintainService.insertOrUpdate(scope);
            permissionGroupMaintainService.insertOrUpdate(parentPermissionGroup);
            for (PermissionGroup permissionGroup : permissionGroups) {
                permissionGroupMaintainService.insertOrUpdate(permissionGroup);
                PermissionGroup testPermissionGroup = permissionGroupMaintainService.get(permissionGroup.getKey());
                assertEquals(BeanUtils.describe(permissionGroup), BeanUtils.describe(testPermissionGroup));
                permissionGroupMaintainService.get(permissionGroup.getKey());
                assertEquals(BeanUtils.describe(permissionGroup), BeanUtils.describe(testPermissionGroup));
            }
        } finally {
            for (PermissionGroup permissionGroup : permissionGroups) {
                if (Objects.isNull(permissionGroup.getKey())) {
                    continue;
                }
                permissionGroupMaintainService.deleteIfExists(permissionGroup.getKey());
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
            for (PermissionGroup permissionGroup : permissionGroups) {
                permissionGroupMaintainService.insertOrUpdate(permissionGroup);
            }

            scopeMaintainService.deleteIfExists(scope.getKey());

            for (PermissionGroup permissionGroup : permissionGroups) {
                assertFalse(permissionGroupMaintainService.exists(permissionGroup.getKey()));
            }
        } finally {
            for (PermissionGroup permissionGroup : permissionGroups) {
                if (Objects.isNull(permissionGroup.getKey())) {
                    continue;
                }
                permissionGroupMaintainService.deleteIfExists(permissionGroup.getKey());
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
            for (PermissionGroup permissionGroup : permissionGroups) {
                permissionGroupMaintainService.insertOrUpdate(permissionGroup);
            }

            PagedData<PermissionGroup> lookup = permissionGroupMaintainService.lookup(
                    PermissionGroupMaintainService.CHILD_FOR_PARENT,
                    new Object[]{parentPermissionGroup.getKey()}
            );
            assertEquals(permissionGroups.size(), lookup.getCount());

            permissionGroupMaintainService.deleteIfExists(parentPermissionGroup.getKey());

            assertEquals(
                    0,
                    permissionGroupMaintainService.lookup(
                            PermissionGroupMaintainService.CHILD_FOR_PARENT,
                            new Object[]{parentPermissionGroup.getKey()}
                    ).getCount()
            );

            assertFalse(permissionGroupMaintainService.exists(parentPermissionGroup.getKey()));
        } finally {
            for (PermissionGroup permissionGroup : permissionGroups) {
                if (Objects.isNull(permissionGroup.getKey())) {
                    continue;
                }
                permissionGroupMaintainService.deleteIfExists(permissionGroup.getKey());
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

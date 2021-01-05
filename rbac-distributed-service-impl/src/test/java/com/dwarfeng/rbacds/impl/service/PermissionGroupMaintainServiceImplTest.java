package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class PermissionGroupMaintainServiceImplTest {

    @Autowired
    private PermissionGroupMaintainService permissionGroupMaintainService;

    private PermissionGroup parentPermissionGroup;
    private List<PermissionGroup> permissionGroups;

    @Before
    public void setUp() {
        parentPermissionGroup = new PermissionGroup(
                new StringIdKey("parent_permission_group"),
                null,
                "权限组",
                "测试用权限组"
        );
        permissionGroups = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            PermissionGroup permissionGroup = new PermissionGroup(
                    new StringIdKey("child_permission_group." + i),
                    new StringIdKey("parent_permission_group"),
                    "权限组" + i,
                    "测试用权限组"
            );
            permissionGroups.add(permissionGroup);
        }
    }

    @After
    public void tearDown() {
        parentPermissionGroup = null;
        permissionGroups = null;
    }

    @Test
    public void test() throws Exception {
        try {
            permissionGroupMaintainService.insertOrUpdate(parentPermissionGroup);
            for (PermissionGroup permissionGroup : permissionGroups) {
                permissionGroupMaintainService.insertOrUpdate(permissionGroup);
                PermissionGroup testPermissionGroup = permissionGroupMaintainService.get(permissionGroup.getKey());
                assertEquals(BeanUtils.describe(permissionGroup), BeanUtils.describe(testPermissionGroup));
            }
            PagedData<PermissionGroup> lookup = permissionGroupMaintainService.lookup(
                    PermissionGroupMaintainService.CHILD_FOR_PARENT, new Object[]{new StringIdKey("parent_permission_group")}
            );
            assertEquals(permissionGroups.size(), lookup.getCount());
        } finally {
            for (PermissionGroup permissionGroup : permissionGroups) {
                permissionGroupMaintainService.deleteIfExists(permissionGroup.getKey());
            }
            permissionGroupMaintainService.deleteIfExists(parentPermissionGroup.getKey());
        }
    }
}

package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.RoleGroup;
import com.dwarfeng.rbacds.stack.service.RoleGroupMaintainService;
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
public class RoleGroupMaintainServiceImplTest {

    @Autowired
    private RoleGroupMaintainService roleGroupMaintainService;

    private RoleGroup parentRoleGroup;
    private List<RoleGroup> roleGroups;

    @Before
    public void setUp() {
        parentRoleGroup = new RoleGroup(
                new StringIdKey("parent_role_group"),
                null,
                "权限组",
                "测试用权限组"
        );
        roleGroups = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            RoleGroup roleGroup = new RoleGroup(
                    new StringIdKey("child_role_group." + i),
                    new StringIdKey("parent_role_group"),
                    "权限组" + i,
                    "测试用权限组"
            );
            roleGroups.add(roleGroup);
        }
    }

    @After
    public void tearDown() {
        parentRoleGroup = null;
        roleGroups = null;
    }

    @Test
    public void test() throws Exception {
        try {
            roleGroupMaintainService.insertOrUpdate(parentRoleGroup);
            for (RoleGroup roleGroup : roleGroups) {
                roleGroupMaintainService.insertOrUpdate(roleGroup);
                RoleGroup testRoleGroup = roleGroupMaintainService.get(roleGroup.getKey());
                assertEquals(BeanUtils.describe(roleGroup), BeanUtils.describe(testRoleGroup));
            }
            PagedData<RoleGroup> lookup = roleGroupMaintainService.lookup(
                    RoleGroupMaintainService.CHILD_FOR_PARENT, new Object[]{new StringIdKey("parent_role_group")}
            );
            assertEquals(roleGroups.size(), lookup.getCount());
        } finally {
            for (RoleGroup roleGroup : roleGroups) {
                roleGroupMaintainService.deleteIfExists(roleGroup.getKey());
            }
            roleGroupMaintainService.deleteIfExists(parentRoleGroup.getKey());
        }
    }
}

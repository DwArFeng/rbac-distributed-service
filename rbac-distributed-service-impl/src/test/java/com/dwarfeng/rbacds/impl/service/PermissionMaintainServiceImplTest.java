package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.cache.PermissionListCache;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
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
public class PermissionMaintainServiceImplTest {

    @Autowired
    private PermissionMaintainService permissionMaintainService;
    @Autowired
    private PermissionListCache permissionListCache;

    private List<Permission> permissions;

    @Before
    public void setUp() {
        permissions = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Permission permission = new Permission(new StringIdKey("permission." + i), "测试用权限");
            permissions.add(permission);
        }
    }

    @After
    public void tearDown() {
        permissions = null;
    }

    @Test
    public void test() throws Exception {
        try {
            for (Permission permission : permissions) {
                permissionMaintainService.insertOrUpdate(permission);
                Permission testPermission = permissionMaintainService.get(permission.getKey());
                assertEquals(BeanUtils.describe(permission), BeanUtils.describe(testPermission));
            }
        } finally {
            for (Permission permission : permissions) {
                permissionMaintainService.deleteIfExists(permission.getKey());
            }
        }
    }
}

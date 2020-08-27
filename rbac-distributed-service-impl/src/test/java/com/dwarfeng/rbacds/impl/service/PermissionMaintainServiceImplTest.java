package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.cache.PermissionListCache;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
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
    public void test() throws ServiceException, CacheException {
        try {
            for (Permission permission : permissions) {
                if (!permissionMaintainService.exists(permission.getKey())) {
                    permissionMaintainService.insert(permission);
                }
            }

            assertEquals(0, permissionListCache.size());
            //此处用断点观测roles的值，判断是否正确。
            //noinspection unused
            List<Permission> lookup = permissionMaintainService.lookup().getData();
            //noinspection UnusedAssignment
            lookup = permissionMaintainService.lookup(new PagingInfo(2, 5)).getData();
            //noinspection UnusedAssignment
            lookup = null;
            assertEquals(20, permissionListCache.size());
        } finally {
            for (Permission permission : permissions) {
                if (permissionMaintainService.exists(permission.getKey())) {
                    permissionMaintainService.delete(permission.getKey());
                }
            }
        }
    }
}

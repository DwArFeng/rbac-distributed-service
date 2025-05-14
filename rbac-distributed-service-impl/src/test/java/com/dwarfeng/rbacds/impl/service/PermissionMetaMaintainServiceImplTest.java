package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionMeta;
import com.dwarfeng.rbacds.stack.bean.key.PermissionMetaKey;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.rbacds.stack.service.PermissionMetaMaintainService;
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
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class PermissionMetaMaintainServiceImplTest {

    private static final String PERMISSION_ID = "test.permission";
    private static final String PERMISSION_META_ID_PREFIX = "test.permission_meta.";

    @Autowired
    private PermissionMaintainService permissionMaintainService;
    @Autowired
    private PermissionMetaMaintainService permissionMetaMaintainService;

    private Permission permission;
    private final List<PermissionMeta> permissionMetas = new ArrayList<>();

    @Before
    public void setUp() {
        permission = new Permission(new StringIdKey(PERMISSION_ID), null, "name", "remark");
        for (int i = 0; i < 20; i++) {
            PermissionMeta permissionMeta = new PermissionMeta(
                    new PermissionMetaKey(PERMISSION_ID, PERMISSION_META_ID_PREFIX + i), "value", "remark"
            );
            permissionMetas.add(permissionMeta);
        }
    }

    @After
    public void tearDown() {
        permission = null;
        permissionMetas.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            permissionMaintainService.insertOrUpdate(permission);

            for (PermissionMeta permissionMeta : permissionMetas) {
                permissionMetaMaintainService.insertOrUpdate(permissionMeta);
                PermissionMeta testPermissionMeta = permissionMetaMaintainService.get(permissionMeta.getKey());
                assertEquals(BeanUtils.describe(permissionMeta), BeanUtils.describe(testPermissionMeta));
                testPermissionMeta = permissionMetaMaintainService.get(permissionMeta.getKey());
                assertEquals(BeanUtils.describe(permissionMeta), BeanUtils.describe(testPermissionMeta));
                permissionMetaMaintainService.update(permissionMeta);
                testPermissionMeta = permissionMetaMaintainService.get(permissionMeta.getKey());
                assertEquals(BeanUtils.describe(permissionMeta), BeanUtils.describe(testPermissionMeta));
            }
        } finally {
            for (PermissionMeta permissionMeta : permissionMetas) {
                if (Objects.isNull(permissionMeta.getKey())) {
                    continue;
                }
                permissionMetaMaintainService.deleteIfExists(permissionMeta.getKey());
            }
            if (Objects.nonNull(permission.getKey())) {
                permissionMaintainService.deleteIfExists(permission.getKey());
            }
        }
    }

    @Test
    public void testForPermissionCascade() throws Exception {
        try {
            permissionMaintainService.insertOrUpdate(permission);
            for (PermissionMeta permissionMeta : permissionMetas) {
                permissionMetaMaintainService.insertOrUpdate(permissionMeta);
            }

            assertEquals(permissionMetas.size(), permissionMetaMaintainService.lookup(
                    PermissionMetaMaintainService.CHILD_FOR_PERMISSION,
                    new Object[]{permission.getKey()}).getCount()
            );

            permissionMaintainService.deleteIfExists(permission.getKey());

            assertEquals(0, permissionMetaMaintainService.lookup(
                    PermissionMetaMaintainService.CHILD_FOR_PERMISSION,
                    new Object[]{permission.getKey()}).getCount()
            );
            assertTrue(permissionMetaMaintainService.nonExists(
                    permissionMetas.stream().map(PermissionMeta::getKey).collect(Collectors.toList())
            ));
        } finally {
            for (PermissionMeta permissionMeta : permissionMetas) {
                if (Objects.isNull(permissionMeta.getKey())) {
                    continue;
                }
                permissionMetaMaintainService.deleteIfExists(permissionMeta.getKey());
            }
            if (Objects.nonNull(permission.getKey())) {
                permissionMaintainService.deleteIfExists(permission.getKey());
            }
        }
    }
}

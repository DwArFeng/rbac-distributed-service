package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionFilterSupport;
import com.dwarfeng.rbacds.stack.service.PermissionFilterSupportMaintainService;
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
public class PermissionFilterSupportMaintainServiceImplTest {

    @Autowired
    private PermissionFilterSupportMaintainService service;

    private final List<PermissionFilterSupport> permissionFilterSupports = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            PermissionFilterSupport permissionFilterSupport = new PermissionFilterSupport(
                    new StringIdKey("test.permission-filter-support." + (i + 1)),
                    "label", "description", "examplePattern"
            );
            permissionFilterSupports.add(permissionFilterSupport);
        }
    }

    @After
    public void tearDown() {
        permissionFilterSupports.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (PermissionFilterSupport permissionFilterSupport : permissionFilterSupports) {
                permissionFilterSupport.setKey(service.insert(permissionFilterSupport));
                PermissionFilterSupport testPermissionFilterSupport = service.get(permissionFilterSupport.getKey());
                assertEquals(BeanUtils.describe(
                        permissionFilterSupport), BeanUtils.describe(testPermissionFilterSupport
                ));
                service.update(permissionFilterSupport);
                testPermissionFilterSupport = service.get(permissionFilterSupport.getKey());
                assertEquals(BeanUtils.describe(
                        permissionFilterSupport), BeanUtils.describe(testPermissionFilterSupport
                ));
            }
        } finally {
            for (PermissionFilterSupport permissionFilterSupport : permissionFilterSupports) {
                service.deleteIfExists(permissionFilterSupport.getKey());
            }
        }
    }
}

package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
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
public class RoleMaintainServiceImplTest {

    @Autowired
    private RoleMaintainService roleMaintainService;

    private List<Role> roles;

    @Before
    public void setUp() {
        roles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Role role = new Role(new StringIdKey("test_role." + i), "name", true, "remark");
            roles.add(role);
        }
    }

    @After
    public void tearDown() {
        roles.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (Role role : roles) {
                roleMaintainService.insertOrUpdate(role);
                Role testRole = roleMaintainService.get(role.getKey());
                assertEquals(BeanUtils.describe(role), BeanUtils.describe(testRole));
                testRole = roleMaintainService.get(role.getKey());
                assertEquals(BeanUtils.describe(role), BeanUtils.describe(testRole));
            }
        } finally {
            for (Role role : roles) {
                if (Objects.isNull(role)) {
                    continue;
                }
                roleMaintainService.deleteIfExists(role.getKey());
            }
        }
    }
}

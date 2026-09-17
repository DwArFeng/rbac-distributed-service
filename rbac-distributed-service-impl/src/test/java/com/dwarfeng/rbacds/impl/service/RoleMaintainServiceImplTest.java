package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:com/dwarfeng/rbacds/impl/spring/application-context*.xml")
public class RoleMaintainServiceImplTest {

    @Autowired
    private RoleMaintainService roleMaintainService;

    private List<Role> roles;

    @BeforeEach
    public void setUp() {
        roles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Role role = new Role(new StringIdKey("test_role." + i), "name", true, "remark");
            roles.add(role);
        }
    }

    @AfterEach
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
                if (Objects.isNull(role.getKey())) {
                    continue;
                }
                roleMaintainService.deleteIfExists(role.getKey());
            }
        }
    }
}

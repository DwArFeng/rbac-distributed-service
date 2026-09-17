package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
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
public class UserMaintainServiceImplTest {

    @Autowired
    private UserMaintainService userMaintainService;

    private List<User> users;

    @BeforeEach
    public void setUp() {
        users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User(new StringIdKey("test_user." + i), "remark");
            users.add(user);
        }
    }

    @AfterEach
    public void tearDown() {
        users.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (User user : users) {
                userMaintainService.insertOrUpdate(user);
                User testUser = userMaintainService.get(user.getKey());
                assertEquals(BeanUtils.describe(user), BeanUtils.describe(testUser));
                testUser = userMaintainService.get(user.getKey());
                assertEquals(BeanUtils.describe(user), BeanUtils.describe(testUser));
            }
        } finally {
            for (User user : users) {
                if (Objects.isNull(user.getKey())) {
                    continue;
                }
                userMaintainService.deleteIfExists(user.getKey());
            }
        }
    }
}

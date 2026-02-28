package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
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
public class UserMaintainServiceImplTest {

    @Autowired
    private UserMaintainService userMaintainService;

    private List<User> users;

    @Before
    public void setUp() {
        users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User(new StringIdKey("test_user." + i), "remark");
            users.add(user);
        }
    }

    @After
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
                if (Objects.isNull(user)) {
                    continue;
                }
                userMaintainService.deleteIfExists(user.getKey());
            }
        }
    }
}

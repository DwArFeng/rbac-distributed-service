package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Scope;
import com.dwarfeng.rbacds.stack.service.ScopeMaintainService;
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
public class ScopeMaintainServiceImplTest {

    @Autowired
    private ScopeMaintainService scopeMaintainService;

    private List<Scope> scopes;

    @Before
    public void setUp() {
        scopes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Scope scope = new Scope(new StringIdKey("test_scope." + i), "name", "remark");
            scopes.add(scope);
        }
    }

    @After
    public void tearDown() {
        scopes.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            for (Scope scope : scopes) {
                scopeMaintainService.insertOrUpdate(scope);
                Scope testScope = scopeMaintainService.get(scope.getKey());
                assertEquals(BeanUtils.describe(scope), BeanUtils.describe(testScope));
                scopeMaintainService.get(scope.getKey());
                assertEquals(BeanUtils.describe(scope), BeanUtils.describe(testScope));
            }
        } finally {
            for (Scope scope : scopes) {
                if (Objects.isNull(scope.getKey())) {
                    continue;
                }
                scopeMaintainService.deleteIfExists(scope.getKey());
            }
        }
    }
}

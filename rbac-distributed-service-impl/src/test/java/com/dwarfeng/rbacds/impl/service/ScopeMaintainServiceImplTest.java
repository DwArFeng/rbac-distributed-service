package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Scope;
import com.dwarfeng.rbacds.stack.service.ScopeMaintainService;
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
public class ScopeMaintainServiceImplTest {

    @Autowired
    private ScopeMaintainService scopeMaintainService;

    private List<Scope> scopes;

    @BeforeEach
    public void setUp() {
        scopes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Scope scope = new Scope(new StringIdKey("test_scope." + i), "name", "remark");
            scopes.add(scope);
        }
    }

    @AfterEach
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

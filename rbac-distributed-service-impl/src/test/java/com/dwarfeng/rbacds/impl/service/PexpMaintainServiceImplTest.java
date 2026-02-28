package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.Scope;
import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
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
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class PexpMaintainServiceImplTest {

    private static final String SCOPE_STRING_ID = "test_scope";
    private static final String ROLE_STRING_ID = "test_role";

    @Autowired
    private ScopeMaintainService scopeMaintainService;
    @Autowired
    private RoleMaintainService roleMaintainService;
    @Autowired
    private PexpMaintainService pexpMaintainService;

    private Scope scope;
    private Role role;
    private List<Pexp> pexps;

    @Before
    public void setUp() {
        scope = new Scope(new StringIdKey(SCOPE_STRING_ID), "name", "remark");
        role = new Role(new StringIdKey(ROLE_STRING_ID), "name", true, "remark");
        pexps = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Pexp pexp = new Pexp(new PexpKey(SCOPE_STRING_ID, ROLE_STRING_ID, "test_pexp." + i), "content", "remark");
            pexps.add(pexp);
        }
    }

    @After
    public void tearDown() {
        scope = null;
        role = null;
        pexps = null;
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            scopeMaintainService.insertOrUpdate(scope);
            roleMaintainService.insertOrUpdate(role);
            for (Pexp pexp : pexps) {
                pexpMaintainService.insertOrUpdate(pexp);
                Pexp testPexp = pexpMaintainService.get(pexp.getKey());
                assertEquals(BeanUtils.describe(pexp), BeanUtils.describe(testPexp));
                pexpMaintainService.get(pexp.getKey());
                assertEquals(BeanUtils.describe(pexp), BeanUtils.describe(testPexp));
            }
        } finally {
            for (Pexp pexp : pexps) {
                if (Objects.isNull(pexp.getKey())) {
                    continue;
                }
                pexpMaintainService.deleteIfExists(pexp.getKey());
            }
            if (Objects.nonNull(role.getKey())) {
                roleMaintainService.deleteIfExists(role.getKey());
            }
            if (Objects.nonNull(scope.getKey())) {
                scopeMaintainService.deleteIfExists(scope.getKey());
            }
        }
    }

    @Test
    public void testForScopeCascade() throws Exception {
        try {
            scopeMaintainService.insertOrUpdate(scope);
            roleMaintainService.insertOrUpdate(role);
            for (Pexp pexp : pexps) {
                pexpMaintainService.insertOrUpdate(pexp);
            }

            assertEquals(
                    pexps.size(),
                    pexpMaintainService.lookupAsList(
                            PermissionMaintainService.CHILD_FOR_SCOPE,
                            new Object[]{scope.getKey()}
                    ).size()
            );

            scopeMaintainService.deleteIfExists(scope.getKey());

            assertEquals(
                    0,
                    pexpMaintainService.lookupAsList(
                            PermissionMaintainService.CHILD_FOR_SCOPE,
                            new Object[]{scope.getKey()}
                    ).size()
            );

            for (Pexp pexp : pexps) {
                assertFalse(pexpMaintainService.exists(pexp.getKey()));
            }
        } finally {
            for (Pexp pexp : pexps) {
                if (Objects.nonNull(pexp.getKey())) {
                    pexpMaintainService.deleteIfExists(pexp.getKey());
                }
            }
            if (Objects.nonNull(role.getKey())) {
                roleMaintainService.deleteIfExists(role.getKey());
            }
            if (Objects.nonNull(scope.getKey())) {
                scopeMaintainService.deleteIfExists(scope.getKey());
            }
        }
    }

    @Test
    public void testForRoleCascade() throws Exception {
        try {
            scopeMaintainService.insertOrUpdate(scope);
            roleMaintainService.insertOrUpdate(role);
            for (Pexp pexp : pexps) {
                pexpMaintainService.insertOrUpdate(pexp);
            }

            assertEquals(
                    pexps.size(),
                    pexpMaintainService.lookupAsList(
                            PexpMaintainService.CHILD_FOR_ROLE,
                            new Object[]{role.getKey()}
                    ).size()
            );

            roleMaintainService.deleteIfExists(role.getKey());

            assertEquals(
                    0,
                    pexpMaintainService.lookupAsList(
                            PexpMaintainService.CHILD_FOR_ROLE,
                            new Object[]{role.getKey()}
                    ).size()
            );

            for (Pexp pexp : pexps) {
                assertFalse(pexpMaintainService.exists(pexp.getKey()));
            }
        } finally {
            for (Pexp pexp : pexps) {
                if (Objects.nonNull(pexp.getKey())) {
                    pexpMaintainService.deleteIfExists(pexp.getKey());
                }
            }
            if (Objects.nonNull(role.getKey())) {
                roleMaintainService.deleteIfExists(role.getKey());
            }
            if (Objects.nonNull(scope.getKey())) {
                scopeMaintainService.deleteIfExists(scope.getKey());
            }
        }
    }
}

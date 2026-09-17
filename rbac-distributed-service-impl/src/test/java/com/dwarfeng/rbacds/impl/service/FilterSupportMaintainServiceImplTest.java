package com.dwarfeng.rbacds.impl.service;

import com.dwarfeng.rbacds.stack.bean.entity.FilterSupport;
import com.dwarfeng.rbacds.stack.service.FilterSupportMaintainService;
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
public class FilterSupportMaintainServiceImplTest {

    @Autowired
    private FilterSupportMaintainService service;

    private final List<FilterSupport> filterSupports = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            FilterSupport filterSupport = new FilterSupport(
                    new StringIdKey("test.filter-support." + (i + 1)),
                    "label", "description", "examplePattern"
            );
            filterSupports.add(filterSupport);
        }
    }

    @AfterEach
    public void tearDown() {
        filterSupports.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (FilterSupport filterSupport : filterSupports) {
                filterSupport.setKey(service.insert(filterSupport));
                FilterSupport testFilterSupport = service.get(filterSupport.getKey());
                assertEquals(BeanUtils.describe(
                        filterSupport), BeanUtils.describe(testFilterSupport
                ));
                service.update(filterSupport);
                testFilterSupport = service.get(filterSupport.getKey());
                assertEquals(BeanUtils.describe(
                        filterSupport), BeanUtils.describe(testFilterSupport
                ));
            }
        } finally {
            for (FilterSupport filterSupport : filterSupports) {
                if (Objects.isNull(filterSupport.getKey())) {
                    continue;
                }
                service.deleteIfExists(filterSupport.getKey());
            }
        }
    }
}

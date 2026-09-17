package com.dwarfeng.rbacds.sdk.util;

import com.dwarfeng.rbacds.sdk.exception.ServiceExceptionCodeSuppliers;
import com.dwarfeng.rbacds.stack.exception.UserNotExistsException;
import com.dwarfeng.subgrade.basic.impl.exception.MapServiceExceptionMapper;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;
import org.junit.jupiter.api.Test;

import java.io.Serial;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 服务异常帮助类测试。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public class ServiceExceptionHelperTest {

    @Test
    public void shouldProvideSuppliersForDirectParentAndDefaultRoutes() {
        int originalOffset = ServiceExceptionCodeSuppliers.getExceptionCodeOffset();
        try {
            ServiceExceptionCodeSuppliers.setExceptionCodeOffset(1500);
            Map<Class<? extends Exception>, Supplier<ServiceException.Code>> destination =
                    ServiceExceptionHelper.putDefaultDestination(null);
            AtomicInteger defaultSequence = new AtomicInteger();
            MapServiceExceptionMapper mapper = new MapServiceExceptionMapper(
                    destination,
                    () -> new ServiceException.Code(9000 + defaultSequence.incrementAndGet(), "undefined")
            );

            assertEquals(
                    1500, mapper.map(new UserNotExistsException(new StringIdKey("test.user"))).getCode().getCode()
            );
            ServiceExceptionCodeSuppliers.setExceptionCodeOffset(1600);
            assertEquals(
                    1600, mapper.map(new UserNotExistsException(new StringIdKey("test.user"))).getCode().getCode()
            );

            ServiceExceptionCodeSuppliers.setExceptionCodeOffset(1700);
            assertEquals(
                    1700,
                    mapper.map(new ExtendedUserNotExistsException(new StringIdKey("test.user"))).getCode().getCode()
            );
            ServiceExceptionCodeSuppliers.setExceptionCodeOffset(1800);
            assertEquals(
                    1800,
                    mapper.map(new ExtendedUserNotExistsException(new StringIdKey("test.user"))).getCode().getCode()
            );

            assertEquals(9001, mapper.map(new IllegalStateException()).getCode().getCode());
            assertEquals(9002, mapper.map(new IllegalStateException()).getCode().getCode());
        } finally {
            ServiceExceptionCodeSuppliers.setExceptionCodeOffset(originalOffset);
        }
    }

    private static class ExtendedUserNotExistsException extends UserNotExistsException {

        @Serial
        private static final long serialVersionUID = -1451630796868500969L;

        private ExtendedUserNotExistsException(StringIdKey userKey) {
            super(userKey);
        }
    }
}

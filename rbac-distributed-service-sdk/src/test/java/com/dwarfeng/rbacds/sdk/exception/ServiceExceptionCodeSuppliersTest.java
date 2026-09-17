package com.dwarfeng.rbacds.sdk.exception;

import com.dwarfeng.rbacds.base.sdk.i18n.MessageContext;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * 服务异常代码供应器测试。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public class ServiceExceptionCodeSuppliersTest {

    @Test
    public void shouldResolveLocaleAndCreateIndependentCodeSnapshots() {
        int originalOffset = ServiceExceptionCodeSuppliers.getExceptionCodeOffset();
        try {
            ServiceExceptionCodeSuppliers.setExceptionCodeOffset(1500);

            ServiceException.Code englishCode = MessageContext.call(
                    Locale.ENGLISH, ServiceExceptionCodeSuppliers.USER_NOT_EXISTS::get
            );
            ServiceException.Code chineseCode = MessageContext.call(
                    Locale.SIMPLIFIED_CHINESE, ServiceExceptionCodeSuppliers.USER_NOT_EXISTS::get
            );

            assertEquals(1500, englishCode.getCode());
            assertEquals("User not exists", englishCode.getTip());
            assertEquals(1500, chineseCode.getCode());
            assertEquals("用户不存在", chineseCode.getTip());
            assertNotSame(englishCode, chineseCode);

            ServiceExceptionCodeSuppliers.setExceptionCodeOffset(2500);
            ServiceException.Code updatedCode = ServiceExceptionCodeSuppliers.USER_NOT_EXISTS.get();

            assertEquals(1500, englishCode.getCode());
            assertEquals(2500, updatedCode.getCode());
            assertNotSame(englishCode, updatedCode);
        } finally {
            ServiceExceptionCodeSuppliers.setExceptionCodeOffset(originalOffset);
        }
    }
}

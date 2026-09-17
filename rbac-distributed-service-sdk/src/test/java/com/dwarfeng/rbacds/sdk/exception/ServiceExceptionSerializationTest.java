package com.dwarfeng.rbacds.sdk.exception;

import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 服务异常序列化测试。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public class ServiceExceptionSerializationTest {

    @Test
    public void shouldPreserveCodeTipAndMessageAfterSerialization() throws Exception {
        ServiceException source = new ServiceException(new ServiceException.Code(500, "User not exists"));

        byte[] serialized;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(source);
            serialized = byteArrayOutputStream.toByteArray();
        }

        ServiceException restored;
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serialized);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            restored = (ServiceException) objectInputStream.readObject();
        }

        assertEquals(source.getCode().getCode(), restored.getCode().getCode());
        assertEquals(source.getCode().getTip(), restored.getCode().getTip());
        assertEquals(source.getMessage(), restored.getMessage());
    }
}

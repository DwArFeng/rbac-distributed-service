package com.dwarfeng.rbacds.api.configuration;

import com.dwarfeng.rbacds.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.rbacds.stack.exception.PexpFormatException;
import com.dwarfeng.rbacds.stack.exception.PexpTestException;
import com.dwarfeng.subgrade.impl.exception.MapServiceExceptionMapper;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ServiceExceptionMapperConfiguration {

    @Bean
    public MapServiceExceptionMapper mapServiceExceptionMapper() {
        Map<Class<? extends Exception>, ServiceException.Code> destination = ServiceExceptionHelper.putDefaultDestination(null);
        destination.put(PexpFormatException.class, ServiceExceptionCodes.PEXP_FORMAT_ERROR);
        destination.put(PexpTestException.class, ServiceExceptionCodes.PEXP_TEST_ERROR);
        return new MapServiceExceptionMapper(destination, com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.UNDEFINE);
    }
}

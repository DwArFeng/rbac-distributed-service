package com.dwarfeng.rbacds.node.configuration;

import com.dwarfeng.rbacds.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.rbacds.stack.exception.*;
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
        destination.put(PermissionNotExistsException.class, ServiceExceptionCodes.PERMISSION_NOT_EXISTS);
        destination.put(RoleNotExistsException.class, ServiceExceptionCodes.ROLE_NOT_EXISTS);
        destination.put(UserNotExistsException.class, ServiceExceptionCodes.USER_NOT_EXISTS);
        return new MapServiceExceptionMapper(destination, com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.UNDEFINED);
    }
}

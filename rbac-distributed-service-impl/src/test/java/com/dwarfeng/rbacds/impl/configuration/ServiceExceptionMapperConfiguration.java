package com.dwarfeng.rbacds.impl.configuration;

import com.dwarfeng.subgrade.basic.impl.exception.MapServiceExceptionMapper;
import com.dwarfeng.subgrade.basic.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ServiceExceptionMapperConfiguration {

    @Bean
    public MapServiceExceptionMapper mapServiceExceptionMapper() {
        Map<Class<? extends Exception>, java.util.function.Supplier<ServiceException.Code>> des =
                ServiceExceptionHelper.putDefaultDestination(null);
        des = com.dwarfeng.springtelqos.sdk.exception.ServiceExceptionHelper.putDefaultDestination(des);
        des = com.dwarfeng.springterminator.sdk.exception.ServiceExceptionHelper.putDefaultDestination(des);
        des = com.dwarfeng.datamark.sdk.exception.ServiceExceptionHelper.putDefaultDestination(des);
        des = com.dwarfeng.rbacds.sdk.util.ServiceExceptionHelper.putDefaultDestination(des);
        return new MapServiceExceptionMapper(
                des, com.dwarfeng.subgrade.basic.sdk.exception.ServiceExceptionCodeSuppliers.UNDEFINED
        );
    }
}

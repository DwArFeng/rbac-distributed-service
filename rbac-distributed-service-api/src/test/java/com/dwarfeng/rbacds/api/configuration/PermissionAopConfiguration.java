package com.dwarfeng.rbacds.api.configuration;

import com.dwarfeng.subgrade.sdk.interceptor.permission.DefaultPermissionRequiredAopManager;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequiredAopManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionAopConfiguration {

    @Bean
    public PermissionRequiredAopManager permissionRequiredAopManager() {
        return new DefaultPermissionRequiredAopManager();
    }
}

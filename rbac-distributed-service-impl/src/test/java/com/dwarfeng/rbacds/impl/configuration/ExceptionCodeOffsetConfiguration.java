package com.dwarfeng.rbacds.impl.configuration;

import com.dwarfeng.rbacds.sdk.util.ServiceExceptionCodes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ExceptionCodeOffsetConfiguration {

    @Value("${rbacds.exception_code_offset}")
    private int exceptionCodeOffset;
    @Value("${rbacds.exception_code_offset.subgrade}")
    private int subgradeExceptionCodeOffset;
    @Value("${rbacds.exception_code_offset.snowflake}")
    private int snowflakeExceptionCodeOffset;
    @Value("${rbacds.exception_code_offset.dwarfeng_datamark}")
    private int dwarfengDatamarkExceptionCodeOffset;

    @PostConstruct
    public void init() {
        ServiceExceptionCodes.setExceptionCodeOffset(exceptionCodeOffset);
        com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.setExceptionCodeOffset(subgradeExceptionCodeOffset);
        com.dwarfeng.sfds.sdk.util.ServiceExceptionCodes.setExceptionCodeOffset(snowflakeExceptionCodeOffset);
        com.dwarfeng.datamark.util.ServiceExceptionCodes.setExceptionCodeOffset(dwarfengDatamarkExceptionCodeOffset);
    }
}

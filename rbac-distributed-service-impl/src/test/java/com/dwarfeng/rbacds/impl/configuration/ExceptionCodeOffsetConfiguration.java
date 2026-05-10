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
    @Value("${rbacds.exception_code_offset.spring_telqos}")
    private int springTelqosExceptionCodeOffset;
    @Value("${rbacds.exception_code_offset.spring_terminator}")
    private int springTerminatorExceptionCodeOffset;
    @Value("${rbacds.exception_code_offset.dwarfeng_datamark}")
    private int dwarfengDatamarkExceptionCodeOffset;

    @PostConstruct
    public void init() {
        ServiceExceptionCodes.setExceptionCodeOffset(exceptionCodeOffset);
        com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.setExceptionCodeOffset(
                subgradeExceptionCodeOffset
        );
        com.dwarfeng.springtelqos.sdk.util.ServiceExceptionCodes.setExceptionCodeOffset(
                springTelqosExceptionCodeOffset
        );
        com.dwarfeng.springterminator.sdk.util.ServiceExceptionCodes.setExceptionCodeOffset(
                springTerminatorExceptionCodeOffset
        );
        com.dwarfeng.datamark.sdk.util.ServiceExceptionCodes.setExceptionCodeOffset(
                dwarfengDatamarkExceptionCodeOffset
        );
    }
}

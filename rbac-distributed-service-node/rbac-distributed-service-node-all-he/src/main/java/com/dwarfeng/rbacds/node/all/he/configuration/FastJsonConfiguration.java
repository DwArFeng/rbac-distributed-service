package com.dwarfeng.rbacds.node.all.he.configuration;

import com.alibaba.fastjson2.JSONFactory;
import com.dwarfeng.rbacds.sdk.bean.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FastJsonConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastJsonConfiguration.class);

    public FastJsonConfiguration() {
        LOGGER.info("正在配置 FastJson autotype 白名单");
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept(FastJsonUser.class.getCanonicalName());
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept(FastJsonRole.class.getCanonicalName());
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept(FastJsonPexp.class.getCanonicalName());
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept(FastJsonPermission.class.getCanonicalName());
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept(FastJsonPermissionGroup.class.getCanonicalName());
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept(FastJsonFilterSupport.class.getCanonicalName());
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept(FastJsonRoleUserRelation.class.getCanonicalName());
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept(FastJsonScope.class.getCanonicalName());
        LOGGER.debug("FastJson autotype 白名单配置完毕");
    }
}

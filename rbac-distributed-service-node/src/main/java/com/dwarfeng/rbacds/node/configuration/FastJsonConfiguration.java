package com.dwarfeng.rbacds.node.configuration;

import com.alibaba.fastjson.parser.ParserConfig;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermission;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPexp;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonRole;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FastJsonConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastJsonConfiguration.class);

    public FastJsonConfiguration() {
        LOGGER.info("正在配置 FastJson autotype 白名单");
        ParserConfig.getGlobalInstance().addAccept(FastJsonUser.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonRole.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonPexp.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonPermission.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonUser.class.getCanonicalName());
        LOGGER.debug("FastJson autotype 白名单配置完毕");
    }
}

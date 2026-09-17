package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.handler.PexpParseHandler;
import com.dwarfeng.subgrade.aop.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.basic.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.dwarfeng.rbacds.impl.internal.i18n.ImplMessageKey.*;
import static com.dwarfeng.rbacds.impl.internal.i18n.ImplMessages.message;

@Component
public class PexpParseHandlerImpl implements PexpParseHandler {

    /**
     * PEXP 内容版本前缀：V1。
     */
    public static final String PEXP_CONTENT_VERSION_PREFIX_V1 = "v1;";

    /**
     * PEXP 内容版本前缀：V2。
     */
    public static final String PEXP_CONTENT_VERSION_PREFIX_V2 = "v2;";

    private static final Logger LOGGER = LoggerFactory.getLogger(PexpParseHandlerImpl.class);

    private final ApplicationContext ctx;

    public PexpParseHandlerImpl(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @BehaviorAnalyse
    @Override
    public ParseResult parse(Pexp pexp) throws HandlerException {
        try {
            return parse0(pexp);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private ParseResult parse0(Pexp pexp) throws Exception {
        // 展开参数。
        String content = Optional.ofNullable(pexp.getContent()).orElse(StringUtils.EMPTY);

        // 解析版本号以及内容。
        UsingVersion usingVersion;
        String contentWithoutVersion;

        // 如果 content 以 PEXP_CONTENT_VERSION_PREFIX_V1 开头，则使用 V1 版本。
        if (Strings.CI.startsWith(content, PEXP_CONTENT_VERSION_PREFIX_V1)) {
            LOGGER.debug(message(LOG_PEXP_PARSE_USING_V1_PREFIX));
            usingVersion = UsingVersion.V1;
            contentWithoutVersion = content.substring(PEXP_CONTENT_VERSION_PREFIX_V1.length());
        }
        // 如果 content 以 PEXP_CONTENT_VERSION_PREFIX_V2 开头，则使用 V2 版本。
        else if (Strings.CI.startsWith(content, PEXP_CONTENT_VERSION_PREFIX_V2)) {
            LOGGER.debug(message(LOG_PEXP_PARSE_USING_V2_PREFIX));
            usingVersion = UsingVersion.V2;
            contentWithoutVersion = content.substring(PEXP_CONTENT_VERSION_PREFIX_V2.length());
        }
        // 如果 content 没有以特定的版本前缀开头，则使用 V1 版本，以兼容早期数据。
        else {
            LOGGER.debug(message(LOG_PEXP_PARSE_USING_V1_DEFAULT));
            usingVersion = UsingVersion.V1;
            contentWithoutVersion = content;
        }

        // 记录日志。
        LOGGER.debug(message(LOG_PEXP_PARSE_VERSION_RESOLVED, usingVersion, contentWithoutVersion));

        // 根据版本号，创建解析引擎。
        PexpParseEngine engine = switch (usingVersion) {
            case V1 -> ctx.getBean(V1PexpParseEngine.class, contentWithoutVersion);
            case V2 -> ctx.getBean(V2PexpParseEngine.class, contentWithoutVersion);
        };

        // 解析并返回结果。
        return engine.parse();
    }

    private enum UsingVersion {
        V1, V2
    }
}

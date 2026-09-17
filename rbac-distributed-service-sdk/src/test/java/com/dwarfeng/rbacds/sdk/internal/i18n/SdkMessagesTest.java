package com.dwarfeng.rbacds.sdk.internal.i18n;

import com.dwarfeng.rbacds.base.sdk.i18n.Messages;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SDK 模块消息测试。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public class SdkMessagesTest {

    private static final String SEGMENT = "[a-z][a-z0-9]*(?:_[a-z0-9]+)*";

    private static final Pattern KEY_PATTERN = Pattern.compile("^" + SEGMENT + "(?:\\." + SEGMENT + ")+$");
    private static final Pattern EXCEPTION_PATTERN = Pattern.compile("^exception\\." + SEGMENT + "$");
    private static final Pattern SERVICE_EXCEPTION_PATTERN =
            Pattern.compile("^service_exception\\." + SEGMENT + "$");
    private static final Pattern LOG_PATTERN = Pattern.compile("^log\\." + SEGMENT + "$");
    private static final Pattern SPI_PATTERN =
            Pattern.compile("^spi\\." + SEGMENT + "\\." + SEGMENT + "\\." + SEGMENT + "$");
    private static final Pattern TELQOS_PATTERN = Pattern.compile(
            "^telqos\\." + SEGMENT + "(?:\\.description|\\.(?:option|field|result|type)\\." + SEGMENT + ")$"
    );
    private static final Pattern MESSAGE_ARGUMENT_PATTERN = Pattern.compile("\\{(\\d+)(?:,[^{}]*)?}");

    @Test
    public void shouldResolveEnglishAndSimplifiedChineseMessages() {
        assertEquals(
                "Filter failed",
                SdkMessages.message(Locale.ENGLISH, SdkMessageKey.SERVICE_EXCEPTION_FILTER_FAILED)
        );
        assertEquals(
                "过滤器失败",
                SdkMessages.message(Locale.SIMPLIFIED_CHINESE, SdkMessageKey.SERVICE_EXCEPTION_FILTER_FAILED)
        );
    }

    @Test
    public void shouldFallbackToMissingKeyMarker() {
        assertEquals(
                "!missing.key!",
                Messages.resolve(SdkMessages.Catalog.SDK.messageCatalog(), "missing.key", Locale.ENGLISH)
        );
    }

    @Test
    public void shouldKeepKeysAlignedWithResourcesAndConvention() throws IOException {
        for (SdkMessages.Catalog catalog : SdkMessages.Catalog.values()) {
            MessageResource rootResource = loadResource(catalog, Locale.ROOT);
            MessageResource chineseResource = loadResource(catalog, Locale.SIMPLIFIED_CHINESE);
            List<SdkMessageKey> messageKeys = Arrays.stream(SdkMessageKey.values())
                    .filter(key -> key.catalog() == catalog).toList();
            List<String> keys = messageKeys.stream().map(SdkMessageKey::key).toList();

            assertEquals(keys, rootResource.keys(), catalog.name());
            assertEquals(rootResource.keys(), chineseResource.keys(), catalog.name());
            assertEquals(keys.stream().sorted().toList(), keys, catalog.name());
            assertEquals(keys.size(), new HashSet<>(keys).size(), catalog.name());

            for (SdkMessageKey messageKey : messageKeys) {
                String key = messageKey.key();
                assertEquals(toConstantName(key), messageKey.name(), key);
                assertKeyMatchesConvention(key);
                assertEquals(
                        argumentIndexes(rootResource.properties().getProperty(key)),
                        argumentIndexes(chineseResource.properties().getProperty(key)),
                        key
                );
            }
        }
    }

    @Test
    public void shouldCacheOneMessageCatalogPerCatalog() {
        Object first = SdkMessages.Catalog.SDK.messageCatalog();
        Object second = SdkMessages.Catalog.SDK.messageCatalog();
        assertSame(first, second);
    }

    private MessageResource loadResource(SdkMessages.Catalog catalog, Locale locale) throws IOException {
        String resourceName = catalog.messageCatalog().baseName().replace('.', '/');
        if (!Locale.ROOT.equals(locale)) {
            resourceName += "_" + locale;
        }
        resourceName += ".properties";

        try (InputStream inputStream = catalog.messageCatalog().module().getResourceAsStream(resourceName)) {
            assertNotNull(inputStream, resourceName);
            byte[] data = inputStream.readAllBytes();
            Properties properties = new Properties();
            properties.load(new InputStreamReader(new ByteArrayInputStream(data), StandardCharsets.UTF_8));
            List<String> keys = new String(data, StandardCharsets.UTF_8).lines()
                    .filter(line -> !line.isBlank() && !line.startsWith("#") && !line.startsWith("!"))
                    .map(line -> line.substring(0, line.indexOf('=')))
                    .toList();
            assertEquals(keys.size(), properties.size(), resourceName);
            return new MessageResource(keys, properties);
        }
    }

    private void assertKeyMatchesConvention(String key) {
        assertTrue(KEY_PATTERN.matcher(key).matches(), key);
        String category = key.substring(0, key.indexOf('.'));
        boolean matches = switch (category) {
            case "exception" -> EXCEPTION_PATTERN.matcher(key).matches();
            case "service_exception" -> SERVICE_EXCEPTION_PATTERN.matcher(key).matches();
            case "log" -> LOG_PATTERN.matcher(key).matches();
            case "spi" -> SPI_PATTERN.matcher(key).matches();
            case "telqos" -> TELQOS_PATTERN.matcher(key).matches();
            default -> false;
        };
        assertTrue(matches, key);
    }

    private String toConstantName(String key) {
        return key.replace('.', '_').toUpperCase(Locale.ROOT);
    }

    private Set<Integer> argumentIndexes(String pattern) {
        assertNotNull(pattern);
        new MessageFormat(pattern, Locale.ROOT);
        Set<Integer> indexes = new TreeSet<>();
        Matcher matcher = MESSAGE_ARGUMENT_PATTERN.matcher(pattern);
        while (matcher.find()) {
            indexes.add(Integer.parseInt(matcher.group(1)));
        }
        return indexes;
    }

    private record MessageResource(List<String> keys, Properties properties) {
    }
}

# Opt Directory - 可选配置目录

## 总览

本项目的可选配置位于 `opt/` 目录下，包括：

```text
opt
├─ opt-filter.xml
├─ opt-pusher.xml
└─ opt-resetter.xml
```

所有的可选配置都为每个单独的可选项提供了加载配置，默认是注释的，如果用户需要使用某个可选项，
只需要将其对应的配置项取消注释即可。

此处展示默认的可选配置文件。

## opt-filter.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!--suppress SpringFacetInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!--扫描 handler 的实现包。 -->
    <context:component-scan base-package="com.dwarfeng.rbacds.impl.handler.filter" use-default-filters="false">
        <!-- 加载 DirectSubGroupFilterRegistry -->
        <!--
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.rbacds.impl.handler.filter.DirectSubGroupFilterRegistry"
        />
        -->

        <!-- 加载 ExactFilterRegistry -->
        <!--
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.rbacds.impl.handler.filter.ExactFilterRegistry"
        />
        -->

        <!-- 加载 IdPrefixFilterRegistry -->
        <!--
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.rbacds.impl.handler.filter.IdPrefixFilterRegistry"
        />
        -->

        <!-- 加载 IdRegexFilterRegistry -->
        <!--
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.rbacds.impl.handler.filter.IdRegexFilterRegistry"
        />
        -->

        <!-- 加载 NameRegexFilterRegistry -->
        <!--
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.rbacds.impl.handler.filter.NameRegexFilterRegistry"
        />
        -->

        <!-- 加载 NestedSubGroupFilterRegistry -->
        <!--
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.rbacds.impl.handler.filter.NestedSubGroupFilterRegistry"
        />
        -->

        <!-- 加载 LevelFilterRegistry -->
        <!--
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.rbacds.impl.handler.filter.LevelFilterRegistry"
        />
        -->
    </context:component-scan>
</beans>
```

## opt-pusher.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!--suppress SpringFacetInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!--扫描 handler 的实现包。 -->
    <context:component-scan
            base-package="com.dwarfeng.rbacds.impl.handler.pusher" use-default-filters="false"
    >
        <!-- 加载 DrainPusher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.rbacds.impl.handler.pusher.DrainPusher"
        />
        -->

        <!-- 加载 LogPusher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.rbacds.impl.handler.pusher.LogPusher"
        />
        -->

        <!-- 加载 MultiPusher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.rbacds.impl.handler.pusher.MultiPusher"
        />
        -->
    </context:component-scan>
</beans>
```

## opt-resetter.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!--suppress SpringFacetInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描 handler 的实现包。 -->
    <context:component-scan
            base-package="com.dwarfeng.rbacds.impl.handler.resetter" use-default-filters="false"
    >
        <!-- 加载 CronResetter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.rbacds.impl.handler.resetter.CronResetter"
        />
        -->

        <!-- 加载 DubboResetter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.rbacds.impl.handler.resetter.DubboResetter"
        />
        -->

        <!-- 加载 FixedDelayResetter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.rbacds.impl.handler.resetter.FixedDelayResetter"
        />
        -->

        <!-- 加载 FixedRateResetter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.rbacds.impl.handler.resetter.FixedRateResetter"
        />
        -->

        <!-- 加载 NeverResetter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.rbacds.impl.handler.resetter.NeverResetter"
        />
        -->
    </context:component-scan>
</beans>
```

# Opt Directory - 可选配置目录

## 总览

本项目的可选配置位于 `opt/` 目录下，包括：

```text
opt
    opt-pfilter.xml
    opt-pusher.xml
    opt-resetter.xml
```

所有的可选配置都为每个单独的可选项提供了加载配置，默认是注释的，如果用户需要使用某个可选项，
只需要将其对应的配置项取消注释即可。

此处展示默认的可选配置文件。

## opt-pfilter.xml

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

    <!--扫描handler的实现包。 -->
    <context:component-scan base-package="com.dwarfeng.rbacds.impl.handler.pfilter" use-default-filters="false">
        <!-- 加载 DirectSubGroupPermissionFilterRegistry -->
        <!--
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.rbacds.impl.handler.pfilter.DirectSubGroupPermissionFilterRegistry"
        />
        -->

        <!-- 加载 ExactPermissionFilterRegistry -->
        <!--
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.rbacds.impl.handler.pfilter.ExactPermissionFilterRegistry"
        />
        -->

        <!-- 加载 IdPrefixPermissionFilterRegistry -->
        <!--
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.rbacds.impl.handler.pfilter.IdPrefixPermissionFilterRegistry"
        />
        -->

        <!-- 加载 IdRegexPermissionFilterRegistry -->
        <!--
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.rbacds.impl.handler.pfilter.IdRegexPermissionFilterRegistry"
        />
        -->

        <!-- 加载 NameRegexPermissionFilterRegistry -->
        <!--
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.rbacds.impl.handler.pfilter.NameRegexPermissionFilterRegistry"
        />
        -->

        <!-- 加载 NestedSubGroupPermissionFilterRegistry -->
        <!--
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.rbacds.impl.handler.pfilter.NestedSubGroupPermissionFilterRegistry"
        />
        -->

        <!-- 加载 LevelPermissionFilterRegistry -->
        <!--
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.rbacds.impl.handler.pfilter.LevelPermissionFilterRegistry"
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

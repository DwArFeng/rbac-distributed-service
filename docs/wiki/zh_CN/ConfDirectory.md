# ConfDirectory - 配置目录

## 总览

本项目的配置文件位于 `conf/` 目录下，包括：

```text
conf
├─database
│      connection.properties
│      performance.properties
├─datamark
│      settings.properties
│
├─dubbo
│      connection.properties
│
├─logging
│      README.md
│      settings.xml
│      settings-linux.xml
│      settings-windows.xml
│
├─rbacds
│      background.properties
│      exception.properties
│      launcher.properties
│      push.properties
│      reset.properties
│
├─redis
│      connection.properties
│      prefix.properties
│      timeout.properties
│
└─telqos
        connection.properties
```

鉴于大部分配置文件的配置项中都有详细地注释，此处将展示默认的配置，并重点说明一些必须要修改的配置项。

## database 目录

| 文件名                    | 说明        |
|------------------------|-----------|
| connection.properties  | 数据库连接配置文件 |
| performance.properties | 数据库性能配置文件 |

### connection.properties

数据库连接配置文件，除了标准的数据库配置四要素之外，还包括 Hibernate 的方言配置。

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://your-host-here:3306/fdr?serverTimezone=Asia/Shanghai&autoReconnect=true
jdbc.username=root
jdbc.password=your-password-here
hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### performance.properties

数据库性能配置文件，使用默认值即可，或按照实际情况进行修改。

```properties
# 数据库的批量写入量，设置激进的值以提高数据库的写入效率。
hibernate.jdbc.batch_size=100
# 数据库的批量抓取量，设置激进的值以提高数据库的读取效率。
hibernate.jdbc.fetch_size=50
# 连接池最大活动连接数量
data_source.max_active=20
# 连接池最小空闲连接数量
data_source.min_idle=0
```

## datamark 目录

| 文件名                 | 说明   |
|---------------------|------|
| settings.properties | 配置文件 |

### settings.properties

datamark 组件的配置文件。

Datamark 是一款数据标记处理工具，基于 `subgrade` 项目，用于提升数据的运维便捷性和可追溯性。该文件作为其配置文件。

```properties
#---------------------------------配置说明----------------------------------------
# 数据标记资源的 URL，格式参考 Spring 资源路径。
# datamark.xxx.resource_url=classpath:datamark/default.storage
# 数据标记资源的字符集。
# datamark.xxx.resource_charset=UTF-8
# 数据标记服务是否允许更新。
# datamark.xxx.update_allowed=true
#
#---------------------------------Role----------------------------------------
datamark.role.resource_url=classpath:datamark/default.storage
datamark.role.resource_charset=UTF-8
datamark.role.update_allowed=true
#
#---------------------------------Permission----------------------------------------
datamark.permission.resource_url=classpath:datamark/default.storage
datamark.permission.resource_charset=UTF-8
datamark.permission.update_allowed=true
#
#---------------------------------User----------------------------------------
datamark.user.resource_url=classpath:datamark/default.storage
datamark.user.resource_charset=UTF-8
datamark.user.update_allowed=true
```

## dubbo 目录

| 文件名                   | 说明           |
|-----------------------|--------------|
| connection.properties | Dubbo 连接配置文件 |

### connection.properties

Dubbo 连接配置文件。

```properties
dubbo.registry.zookeeper.address=zookeeper://your-host-here:2181
dubbo.registry.zookeeper.timeout=3000
dubbo.protocol.dubbo.port=20000
dubbo.protocol.dubbo.host=your-host-here
dubbo.provider.group=
dubbo.consumer.snowflake.group=
```

其中，`dubbo.registry.zookeeper.address` 需要配置为 ZooKeeper 的地址，
`dubbo.protocol.dubbo.host` 需要配置为本机的 IP 地址。

如果您需要在本机启动多个 FDR 实例，那么需要为每个实例配置不同的 `dubbo.protocol.dubbo.port`。

如果您在本机上部署了多个项目，每个项目中都使用了 FDR，那么需要为每个项目配置不同的 `dubbo.provider.group`，
以避免微服务错误的调用。

## logging 目录

| 文件名                  | 说明                   |
|----------------------|----------------------|
| README.md            | 说明文档                 |
| settings.xml         | 日志配置文件               |
| settings-linux.xml   | Linux 系统中日志配置文件的参考   |
| settings-windows.xml | Windows 系统中日志配置文件的参考 |

### README.md

logging 目录的说明文档。

```markdown
# logging - 日志配置文件夹

该目录存放了日志配置文件及其不同操作系统的参考配置文件，以供用户自定义日志配置。

该目录下 `settings.xml` 是日志配置文件的主文件，用户可以通过修改该文件来自定义日志配置。

该目录下 `settings-*.xml` 是不同操作系统的参考配置文件。

用户可以根据自己的需求将对应的参考配置文件中的内容复制到 `settings.xml` 中以应用对应操作系统的默认配置，
或者直接修改 `settings.xml` 以自定义日志配置。
```

### settings.xml

日志配置文件。

需要注意的是，只有在该文件中的配置内容才会生效，`settings-os.xml` 中的内容仅作为参考，任何修改均不会生效。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <properties>
        <!--############################################### Console ###############################################-->
        <!-- 控制台输出文本的编码 -->
        <property name="console.encoding">UTF-8</property>
        <!-- 控制台输出的日志级别 -->
        <property name="console.level">INFO</property>
        <!--############################################# Rolling file ############################################-->
        <!-- 滚动文件的目录 -->
        <property name="rolling_file.dir">logs</property>
        <!-- 滚动文件的编码 -->
        <property name="rolling_file.encoding">UTF-8</property>
        <!-- 滚动文件的触发间隔（小时） -->
        <property name="rolling_file.triggering.interval">1</property>
        <!-- 滚动文件的触发大小 -->
        <property name="rolling_file.triggering.size">40MB</property>
        <!-- 滚动文件的最大数量 -->
        <property name="rolling_file.rollover.max">100</property>
        <!-- 滚动文件的删除时间 -->
        <property name="rolling_file.rollover.delete_age">7D</property>
    </properties>

    <Appenders>
        <!--############################################### Console ###############################################-->
        <Console name="std.console" target="SYSTEM_OUT" follow="true">
            <ThresholdFilter level="${console.level}" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{DEFAULT}] [%p] [%t] [%c{1.}]: %m%n" charset="${console.encoding}"/>
        </Console>
        <Async name="sync.console">
            <AppenderRef ref="std.console"/>
        </Async>
        <!--############################################# Rolling file ############################################-->
        <RollingFile
                name="std.debug.rolling_file" fileName="${rolling_file.dir}/debug.log"
                filePattern="${rolling_file.dir}/%d{yyyy-MM}/debug-%d{MM-dd-yyyy}-%i.log.gz"
        >
            <ThresholdFilter level="DEBUG" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{DEFAULT}] [%p] [%t] [%c{1.}]: %m%n" charset="${rolling_file.encoding}"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="${rolling_file.triggering.interval}" modulate="true"/>
                <SizeBasedTriggeringPolicy size="${rolling_file.triggering.size}"/>
            </Policies>
            <DefaultRolloverStrategy max="${rolling_file.rollover.max}">
                <Delete basePath="${rolling_file.dir}" maxDepth="2">
                    <IfFileName glob="*/*debug*.log.gz"/>
                    <IfLastModified age="${rolling_file.rollover.delete_age}"/>
                </Delete>
            </DefaultRolloverStrategy>
        </RollingFile>
        <RollingFile
                name="std.info.rolling_file" fileName="${rolling_file.dir}/info.log"
                filePattern="${rolling_file.dir}/%d{yyyy-MM}/info-%d{MM-dd-yyyy}-%i.log.gz"
        >
            <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{DEFAULT}] [%p] [%t] [%c{1.}]: %m%n" charset="${rolling_file.encoding}"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="${rolling_file.triggering.interval}" modulate="true"/>
                <SizeBasedTriggeringPolicy size="${rolling_file.triggering.size}"/>
            </Policies>
            <DefaultRolloverStrategy max="${rolling_file.rollover.max}">
                <Delete basePath="${rolling_file.dir}" maxDepth="2">
                    <IfFileName glob="*/*info*.log.gz"/>
                    <IfLastModified age="${rolling_file.rollover.delete_age}"/>
                </Delete>
            </DefaultRolloverStrategy>
        </RollingFile>
        <RollingFile
                name="std.warn.rolling_file" fileName="${rolling_file.dir}/warn.log"
                filePattern="${rolling_file.dir}/%d{yyyy-MM}/warn-%d{MM-dd-yyyy}-%i.log.gz"
        >
            <ThresholdFilter level="WARN" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{DEFAULT}] [%p] [%t] [%c{1.}]: %m%n" charset="${rolling_file.encoding}"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="${rolling_file.triggering.interval}" modulate="true"/>
                <SizeBasedTriggeringPolicy size="${rolling_file.triggering.size}"/>
            </Policies>
            <DefaultRolloverStrategy max="${rolling_file.rollover.max}">
                <Delete basePath="${rolling_file.dir}" maxDepth="2">
                    <IfFileName glob="*/*warn*.log.gz"/>
                    <IfLastModified age="${rolling_file.rollover.delete_age}"/>
                </Delete>
            </DefaultRolloverStrategy>
        </RollingFile>
        <RollingFile
                name="std.error.rolling_file" fileName="${rolling_file.dir}/error.log"
                filePattern="${rolling_file.dir}/%d{yyyy-MM}/error-%d{MM-dd-yyyy}-%i.log.gz"
        >
            <ThresholdFilter level="ERROR" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{DEFAULT}] [%p] [%t] [%c{1.}]: %m%n" charset="${rolling_file.encoding}"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="${rolling_file.triggering.interval}" modulate="true"/>
                <SizeBasedTriggeringPolicy size="${rolling_file.triggering.size}"/>
            </Policies>
            <DefaultRolloverStrategy max="${rolling_file.rollover.max}">
                <Delete basePath="${rolling_file.dir}" maxDepth="2">
                    <IfFileName glob="*/*error*.log.gz"/>
                    <IfLastModified age="${rolling_file.rollover.delete_age}"/>
                </Delete>
            </DefaultRolloverStrategy>
        </RollingFile>
        <Async name="sync.console">
            <AppenderRef ref="std.console"/>
        </Async>
        <Async name="sync.debug.rolling_file">
            <AppenderRef ref="std.debug.rolling_file"/>
        </Async>
        <Async name="sync.info.rolling_file">
            <AppenderRef ref="std.info.rolling_file"/>
        </Async>
        <Async name="sync.warn.rolling_file">
            <AppenderRef ref="std.warn.rolling_file"/>
        </Async>
        <Async name="sync.error.rolling_file">
            <AppenderRef ref="std.error.rolling_file"/>
        </Async>
    </Appenders>

    <Loggers>
        <!--############################################# Root logger #############################################-->
        <Root level="ALL">
            <appender-ref ref="sync.console"/>
            <appender-ref ref="sync.debug.rolling_file"/>
            <appender-ref ref="sync.info.rolling_file"/>
            <appender-ref ref="sync.warn.rolling_file"/>
            <appender-ref ref="sync.error.rolling_file"/>
        </Root>
    </Loggers>
</Configuration>
```

### settings-linux.xml

Linux 系统中日志配置文件的参考。

如果项目部署在 Linux 系统中，可以把此文件中的全部内容粘贴到 `setting.xml` 中，以使用推荐的 Linux 系统的日志配置。

需要注意的是，项目的日志配置只以 `setting.xml` 中的内容为准，在该文件中进行任何修改，均不会生效。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <properties>
        <!--############################################### Console ###############################################-->
        <!-- 控制台输出文本的编码 -->
        <property name="console.encoding">UTF-8</property>
        <!-- 控制台输出的日志级别 -->
        <property name="console.level">INFO</property>
        <!--############################################# Rolling file ############################################-->
        <!-- 滚动文件的目录 -->
        <property name="rolling_file.dir">/var/log/rbac</property>
        <!-- 滚动文件的编码 -->
        <property name="rolling_file.encoding">UTF-8</property>
        <!-- 滚动文件的触发间隔（小时） -->
        <property name="rolling_file.triggering.interval">1</property>
        <!-- 滚动文件的触发大小 -->
        <property name="rolling_file.triggering.size">40MB</property>
        <!-- 滚动文件的最大数量 -->
        <property name="rolling_file.rollover.max">100</property>
        <!-- 滚动文件的删除时间 -->
        <property name="rolling_file.rollover.delete_age">7D</property>
    </properties>

    <Appenders>
        <!--############################################### Console ###############################################-->
        <Console name="std.console" target="SYSTEM_OUT" follow="true">
            <ThresholdFilter level="${console.level}" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{DEFAULT}] [%p] [%t] [%c{1.}]: %m%n" charset="${console.encoding}"/>
        </Console>
        <Async name="sync.console">
            <AppenderRef ref="std.console"/>
        </Async>
        <!--############################################# Rolling file ############################################-->
        <RollingFile
                name="std.debug.rolling_file" fileName="${rolling_file.dir}/debug.log"
                filePattern="${rolling_file.dir}/%d{yyyy-MM}/debug-%d{MM-dd-yyyy}-%i.log.gz"
        >
            <ThresholdFilter level="DEBUG" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{DEFAULT}] [%p] [%t] [%c{1.}]: %m%n" charset="${rolling_file.encoding}"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="${rolling_file.triggering.interval}" modulate="true"/>
                <SizeBasedTriggeringPolicy size="${rolling_file.triggering.size}"/>
            </Policies>
            <DefaultRolloverStrategy max="${rolling_file.rollover.max}">
                <Delete basePath="${rolling_file.dir}" maxDepth="2">
                    <IfFileName glob="*/*debug*.log.gz"/>
                    <IfLastModified age="${rolling_file.rollover.delete_age}"/>
                </Delete>
            </DefaultRolloverStrategy>
        </RollingFile>
        <RollingFile
                name="std.info.rolling_file" fileName="${rolling_file.dir}/info.log"
                filePattern="${rolling_file.dir}/%d{yyyy-MM}/info-%d{MM-dd-yyyy}-%i.log.gz"
        >
            <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{DEFAULT}] [%p] [%t] [%c{1.}]: %m%n" charset="${rolling_file.encoding}"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="${rolling_file.triggering.interval}" modulate="true"/>
                <SizeBasedTriggeringPolicy size="${rolling_file.triggering.size}"/>
            </Policies>
            <DefaultRolloverStrategy max="${rolling_file.rollover.max}">
                <Delete basePath="${rolling_file.dir}" maxDepth="2">
                    <IfFileName glob="*/*info*.log.gz"/>
                    <IfLastModified age="${rolling_file.rollover.delete_age}"/>
                </Delete>
            </DefaultRolloverStrategy>
        </RollingFile>
        <RollingFile
                name="std.warn.rolling_file" fileName="${rolling_file.dir}/warn.log"
                filePattern="${rolling_file.dir}/%d{yyyy-MM}/warn-%d{MM-dd-yyyy}-%i.log.gz"
        >
            <ThresholdFilter level="WARN" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{DEFAULT}] [%p] [%t] [%c{1.}]: %m%n" charset="${rolling_file.encoding}"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="${rolling_file.triggering.interval}" modulate="true"/>
                <SizeBasedTriggeringPolicy size="${rolling_file.triggering.size}"/>
            </Policies>
            <DefaultRolloverStrategy max="${rolling_file.rollover.max}">
                <Delete basePath="${rolling_file.dir}" maxDepth="2">
                    <IfFileName glob="*/*warn*.log.gz"/>
                    <IfLastModified age="${rolling_file.rollover.delete_age}"/>
                </Delete>
            </DefaultRolloverStrategy>
        </RollingFile>
        <RollingFile
                name="std.error.rolling_file" fileName="${rolling_file.dir}/error.log"
                filePattern="${rolling_file.dir}/%d{yyyy-MM}/error-%d{MM-dd-yyyy}-%i.log.gz"
        >
            <ThresholdFilter level="ERROR" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{DEFAULT}] [%p] [%t] [%c{1.}]: %m%n" charset="${rolling_file.encoding}"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="${rolling_file.triggering.interval}" modulate="true"/>
                <SizeBasedTriggeringPolicy size="${rolling_file.triggering.size}"/>
            </Policies>
            <DefaultRolloverStrategy max="${rolling_file.rollover.max}">
                <Delete basePath="${rolling_file.dir}" maxDepth="2">
                    <IfFileName glob="*/*error*.log.gz"/>
                    <IfLastModified age="${rolling_file.rollover.delete_age}"/>
                </Delete>
            </DefaultRolloverStrategy>
        </RollingFile>
        <Async name="sync.console">
            <AppenderRef ref="std.console"/>
        </Async>
        <Async name="sync.debug.rolling_file">
            <AppenderRef ref="std.debug.rolling_file"/>
        </Async>
        <Async name="sync.info.rolling_file">
            <AppenderRef ref="std.info.rolling_file"/>
        </Async>
        <Async name="sync.warn.rolling_file">
            <AppenderRef ref="std.warn.rolling_file"/>
        </Async>
        <Async name="sync.error.rolling_file">
            <AppenderRef ref="std.error.rolling_file"/>
        </Async>
    </Appenders>

    <Loggers>
        <!--############################################# Root logger #############################################-->
        <Root level="ALL">
            <appender-ref ref="sync.console"/>
            <appender-ref ref="sync.debug.rolling_file"/>
            <appender-ref ref="sync.info.rolling_file"/>
            <appender-ref ref="sync.warn.rolling_file"/>
            <appender-ref ref="sync.error.rolling_file"/>
        </Root>
    </Loggers>
</Configuration>
```

### settings-windows.xml

Windows 系统中日志配置文件的参考。

如果项目部署在 Windows 系统中，可以把此文件中的全部内容粘贴到 `setting.xml` 中，以使用推荐的 Windows 系统的日志配置。

需要注意的是，项目的日志配置只以 `setting.xml` 中的内容为准，在该文件中进行任何修改，均不会生效。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <properties>
        <!--############################################### Console ###############################################-->
        <!-- 控制台输出文本的编码 -->
        <property name="console.encoding">GBK</property>
        <!-- 控制台输出的日志级别 -->
        <property name="console.level">INFO</property>
        <!--############################################# Rolling file ############################################-->
        <!-- 滚动文件的目录 -->
        <property name="rolling_file.dir">logs</property>
        <!-- 滚动文件的编码 -->
        <property name="rolling_file.encoding">UTF-8</property>
        <!-- 滚动文件的触发间隔（小时） -->
        <property name="rolling_file.triggering.interval">1</property>
        <!-- 滚动文件的触发大小 -->
        <property name="rolling_file.triggering.size">40MB</property>
        <!-- 滚动文件的最大数量 -->
        <property name="rolling_file.rollover.max">100</property>
        <!-- 滚动文件的删除时间 -->
        <property name="rolling_file.rollover.delete_age">7D</property>
    </properties>

    <Appenders>
        <!--############################################### Console ###############################################-->
        <Console name="std.console" target="SYSTEM_OUT" follow="true">
            <ThresholdFilter level="${console.level}" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{DEFAULT}] [%p] [%t] [%c{1.}]: %m%n" charset="${console.encoding}"/>
        </Console>
        <Async name="sync.console">
            <AppenderRef ref="std.console"/>
        </Async>
        <!--############################################# Rolling file ############################################-->
        <RollingFile
                name="std.debug.rolling_file" fileName="${rolling_file.dir}/debug.log"
                filePattern="${rolling_file.dir}/%d{yyyy-MM}/debug-%d{MM-dd-yyyy}-%i.log.gz"
        >
            <ThresholdFilter level="DEBUG" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{DEFAULT}] [%p] [%t] [%c{1.}]: %m%n" charset="${rolling_file.encoding}"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="${rolling_file.triggering.interval}" modulate="true"/>
                <SizeBasedTriggeringPolicy size="${rolling_file.triggering.size}"/>
            </Policies>
            <DefaultRolloverStrategy max="${rolling_file.rollover.max}">
                <Delete basePath="${rolling_file.dir}" maxDepth="2">
                    <IfFileName glob="*/*debug*.log.gz"/>
                    <IfLastModified age="${rolling_file.rollover.delete_age}"/>
                </Delete>
            </DefaultRolloverStrategy>
        </RollingFile>
        <RollingFile
                name="std.info.rolling_file" fileName="${rolling_file.dir}/info.log"
                filePattern="${rolling_file.dir}/%d{yyyy-MM}/info-%d{MM-dd-yyyy}-%i.log.gz"
        >
            <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{DEFAULT}] [%p] [%t] [%c{1.}]: %m%n" charset="${rolling_file.encoding}"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="${rolling_file.triggering.interval}" modulate="true"/>
                <SizeBasedTriggeringPolicy size="${rolling_file.triggering.size}"/>
            </Policies>
            <DefaultRolloverStrategy max="${rolling_file.rollover.max}">
                <Delete basePath="${rolling_file.dir}" maxDepth="2">
                    <IfFileName glob="*/*info*.log.gz"/>
                    <IfLastModified age="${rolling_file.rollover.delete_age}"/>
                </Delete>
            </DefaultRolloverStrategy>
        </RollingFile>
        <RollingFile
                name="std.warn.rolling_file" fileName="${rolling_file.dir}/warn.log"
                filePattern="${rolling_file.dir}/%d{yyyy-MM}/warn-%d{MM-dd-yyyy}-%i.log.gz"
        >
            <ThresholdFilter level="WARN" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{DEFAULT}] [%p] [%t] [%c{1.}]: %m%n" charset="${rolling_file.encoding}"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="${rolling_file.triggering.interval}" modulate="true"/>
                <SizeBasedTriggeringPolicy size="${rolling_file.triggering.size}"/>
            </Policies>
            <DefaultRolloverStrategy max="${rolling_file.rollover.max}">
                <Delete basePath="${rolling_file.dir}" maxDepth="2">
                    <IfFileName glob="*/*warn*.log.gz"/>
                    <IfLastModified age="${rolling_file.rollover.delete_age}"/>
                </Delete>
            </DefaultRolloverStrategy>
        </RollingFile>
        <RollingFile
                name="std.error.rolling_file" fileName="${rolling_file.dir}/error.log"
                filePattern="${rolling_file.dir}/%d{yyyy-MM}/error-%d{MM-dd-yyyy}-%i.log.gz"
        >
            <ThresholdFilter level="ERROR" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{DEFAULT}] [%p] [%t] [%c{1.}]: %m%n" charset="${rolling_file.encoding}"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="${rolling_file.triggering.interval}" modulate="true"/>
                <SizeBasedTriggeringPolicy size="${rolling_file.triggering.size}"/>
            </Policies>
            <DefaultRolloverStrategy max="${rolling_file.rollover.max}">
                <Delete basePath="${rolling_file.dir}" maxDepth="2">
                    <IfFileName glob="*/*error*.log.gz"/>
                    <IfLastModified age="${rolling_file.rollover.delete_age}"/>
                </Delete>
            </DefaultRolloverStrategy>
        </RollingFile>
        <Async name="sync.console">
            <AppenderRef ref="std.console"/>
        </Async>
        <Async name="sync.debug.rolling_file">
            <AppenderRef ref="std.debug.rolling_file"/>
        </Async>
        <Async name="sync.info.rolling_file">
            <AppenderRef ref="std.info.rolling_file"/>
        </Async>
        <Async name="sync.warn.rolling_file">
            <AppenderRef ref="std.warn.rolling_file"/>
        </Async>
        <Async name="sync.error.rolling_file">
            <AppenderRef ref="std.error.rolling_file"/>
        </Async>
    </Appenders>

    <Loggers>
        <!--############################################# Root logger #############################################-->
        <Root level="ALL">
            <appender-ref ref="sync.console"/>
            <appender-ref ref="sync.debug.rolling_file"/>
            <appender-ref ref="sync.info.rolling_file"/>
            <appender-ref ref="sync.warn.rolling_file"/>
            <appender-ref ref="sync.error.rolling_file"/>
        </Root>
    </Loggers>
</Configuration>
```

## rbacds 目录

| 文件名                   | 说明                           |
|-----------------------|------------------------------|
| background.properties | 后台服务配置文件，包括线程池的线程数及其它        |
| exception.properties  | ServiceException 的异常代码的偏移量配置 |
| launcher.properties   | 启动器配置文件                      |
| push.properties       | 推送服务配置文件                     |
| reset.properties      | 重置服务配置文件                     |

### background.properties

后台服务配置文件，包括线程池的线程数及其它参数，如遇到项目并发过多，线程池无法处理全部请求，可按需优化该参数。

```properties
# 任务执行器的线程池数量范围。
executor.pool_size=20-40
# 任务执行器的队列容量。
executor.queue_capacity=100
# 任务执行器的保活时间（秒）。
executor.keep_alive=120
# 计划执行器的线程池数量范围。
scheduler.pool_size=10
```

### exception.properties

ServiceException 的异常代码的偏移量配置。

```properties
# rbacds 自身的异常代号偏移量。
rbacds.exception_code_offset=1000
# rbacds 工程中 subgrade 的异常代号偏移量。
rbacds.exception_code_offset.subgrade=0
# rbacds 工程中 snowflake 的异常代号偏移量。
rbacds.exception_code_offset.snowflake=1500
# rbacds 工程中 dwarfeng-datamark 的异常代号偏移量。
rbacds.exception_code_offset.dwarfeng_datamark=2000
```

Subgrade 框架中，会将微服务抛出的异常映射为 `ServiceException`，每个 `ServiceException` 都有一个异常代码，
用于标识异常的类型。

如果您的项目中使用了多个基于 Subgrade 框架的微服务，那么，您需要为每个微服务配置一个异常代码偏移量，
以免不同的微服务生成异常代码相同的 `ServiceException`。

### launcher.properties

启动器配置文件，决定了启动时的一些行为，包括是否上线重置支持等。可以按需修改。

```properties
# 程序启动完成后，是否重置权限过滤器支持。
launcher.reset_permission_filter_support=true
#
# 程序启动完成后，启动重置的延时时间。
# 有些数据仓库以及重置器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于0，意味着启动后立即启动重置服务。
# 该参数小于0，意味着程序不主动启动重置服务，需要手动启动。
launcher.start_reset_delay=30000
```

### push.properties

推送服务配置文件。

```properties
###################################################
#                     global                      #
###################################################
# 当前的推送器类型。
# 目前该项目支持的推送器类型有:
#   drain: 简单的丢弃掉所有消息的推送器。
#   multi: 同时将消息推送给所有代理的多重推送器。
#   log: 将消息输出到日志中的推送器。
#
# 对于一个具体的项目，很可能只用一个推送器。此时如果希望程序加载时只加载一个推送器，可以通过编辑
# opt/opt-push.xml 文件实现。
# 可以通过编辑 application-context-scan.xml 实现。
pusher.type=drain
#
###################################################
#                      drain                      #
###################################################
# drain推送器没有任何配置。
#
###################################################
#                      multi                      #
###################################################
# 代理的推送器，推送器之间以逗号分隔。
pusher.multi.delegate_types=log
#
###################################################
#                       log                       #
###################################################
# 记录日志的等级，由低到高依次是 TRACE, DEBUG, INFO, WARN, ERROR。
pusher.log.log_level=INFO
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-pusher.xml`，决定项目中需要使用哪些推送器。您只需要修改使用的推送器的配置。

### reset.properties

重置服务配置文件。

```properties
###################################################
#                      never                      #
###################################################
# Never 推送器没有任何配置。
#
###################################################
#                   fixed_delay                   #
###################################################
# 重置的间隔。
resetter.fixed_delay.delay=43200000
#
###################################################
#                   fixed_rate                    #
###################################################
# 重置的间隔。
resetter.fixed_rate.rate=43200000
#
###################################################
#                      cron                       #
###################################################
# 执行重置的 CRON 表达式。
resetter.cron.cron=0 0 1 * * *
#
###################################################
#                      dubbo                      #
###################################################
# Dubbo 推送器没有任何配置。
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-resetter.xml`，决定项目中需要使用哪些重置器。您只需要修改使用的重置器的配置。

## redis 目录

| 文件名                   | 说明   |
|-----------------------|------|
| connection.properties | 连接配置 |
| prefix.properties     | 前缀配置 |
| timeout.properties    | 超时配置 |

### connection.properties

Redis 连接配置文件。

```properties
#ip地址
redis.hostName=your-host-here
#端口号
redis.port=6379
#如果有密码
redis.password=your-password-here
#客户端超时时间单位是毫秒 默认是2000
redis.timeout=10000
#最大空闲数
redis.maxIdle=300
#连接池的最大数据库连接数。设为0表示无限制,如果是jedis 2.4以后用redis.maxTotal
#redis.maxActive=600
#控制一个pool可分配多少个jedis实例,用来替换上面的redis.maxActive,如果是jedis 2.4以后用该属性
redis.maxTotal=1000
#最大建立连接等待时间。如果超过此时间将接到异常。设为-1表示无限制。
redis.maxWaitMillis=1000
#连接的最小空闲时间 默认1800000毫秒(30分钟)
redis.minEvictableIdleTimeMillis=300000
#每次释放连接的最大数目,默认3
redis.numTestsPerEvictionRun=1024
#逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
redis.timeBetweenEvictionRunsMillis=30000
#是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
redis.testOnBorrow=true
#在空闲时检查有效性, 默认false
redis.testWhileIdle=true
#redis.sentinel.host1=172.20.1.230
#redis.sentinel.port1=26379
#redis.sentinel.host2=172.20.1.231
#redis.sentinel.port2=26379
#redis.sentinel.host3=172.20.1.232
#redis.sentinel.port3=26379
```

### prefix.properties

Redis 前缀配置文件。

```properties
#------------------------------------------------------------------------------------
#  缓存时实体的键的格式
#------------------------------------------------------------------------------------
# 用户对象的主键格式。
cache.prefix.entity.user=entity.user.
# 角色对象的主键格式。
cache.prefix.entity.role=entity.role.
# 权限表达式对象的主键格式。
cache.prefix.entity.pexp=entity.pexp.
# 权限对象的主键格式。
cache.prefix.entity.permission=entity.permission.
# 权限组对象的主键格式。
cache.prefix.entity.permission_group=entity.permission_group.
# 权限元数据对象的主键格式。
cache.prefix.entity.permission_meta=entity.permission_meta.
# 权限过滤器支持对象的主键格式。
cache.prefix.entity.permission_filter_support=entity.permission_filter_support.
#------------------------------------------------------------------------------------
#  固定列表的键的格式。
#------------------------------------------------------------------------------------
# 权限列表的主键。
cache.key.list.permission=list.permission
#------------------------------------------------------------------------------------
#  键值列表的键的格式。
#------------------------------------------------------------------------------------
# 用户持有权限列表的主键。
cache.prefix.list.user_has_permission=list.user_has_permission.
# 角色持有权限列表的主键。
cache.prefix.list.role_has_permission=list.role_has_permission.
# 权限持有用户列表的主键。
cache.prefix.list.permission_has_user=list.permission_has_user.
```

Redis 利用该配置文件，为缓存的主键添加前缀，以示区分。

如果您的项目包含其它使用 Redis 的模块，您可以修改该配置文件，以避免不同项目的同名实体前缀冲突，相互覆盖。

一个典型的前缀更改方式是在前缀的头部添加项目的名称，如：

```properties
# 用户对象的主键格式。
cache.prefix.entity.user=entity.rbac.user.
# etc...
```

### timeout.properties

Redis 缓存的超时配置文件。

```properties
#------------------------------------------------------------------------------------
#  实体缓存时的超时时间
#------------------------------------------------------------------------------------
# 用户对象缓存的超时时间。
cache.timeout.entity.user=3600000
# 角色对象缓存的超时时间。
cache.timeout.entity.role=3600000
# 权限表达式对象缓存的超时时间。
cache.timeout.entity.pexp=3600000
# 权限对象缓存的超时时间。
cache.timeout.entity.permission=3600000
# 权限组对象缓存的超时时间。
cache.timeout.entity.permission_group=3600000
# 权限元数据对象缓存的超时时间。
cache.timeout.entity.permission_meta=3600000
# 权限过滤器支持对象缓存的超时时间。
cache.timeout.entity.permission_filter_support=3600000
#------------------------------------------------------------------------------------
#  固定列表的键的超时时间。
#------------------------------------------------------------------------------------
cache.timeout.list.permission=36000000
#------------------------------------------------------------------------------------
#  键值列表的键的超时时间。
#------------------------------------------------------------------------------------
# 用户持有权限列表的超时时间。
cache.timeout.list.user_has_permission=14400000
# 角色持有权限列表的超时时间。
cache.timeout.list.role_has_permission=14400000
# 权限持有用户列表的超时时间。
cache.timeout.list.permission_has_user=14400000
```

如果您希望缓存更快或更慢地过期，您可以修改该配置文件。

## telqos 目录

| 文件名                   | 说明   |
|-----------------------|------|
| connection.properties | 连接配置 |

### connection.properties

Telqos 连接配置文件。

```properties
#Telnet 端口
telqos.port=23
#字符集
telqos.charset=UTF-8
#白名单表达式
telqos.whitelist_regex=
#黑名单表达式
telqos.blacklist_regex=
```

如果您的项目中有多个包含 Telqos 模块的服务，您应该修改 `telqos.port` 的值，以避免端口冲突。

请根据操作系统的默认字符集，修改 `telqos.charset` 的值，以避免乱码。一般情况下，Windows 系统的默认字符集为 `GBK`，
Linux 系统的默认字符集为 `UTF-8`。

如果您希望限制 Telqos 的使用范围，您可以修改 `telqos.whitelist_regex` 和 `telqos.blacklist_regex` 的值。

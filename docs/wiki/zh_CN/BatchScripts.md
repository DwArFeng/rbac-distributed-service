# Batch Scripts - 批处理脚本

## rbac-start.bat

### 可配置变量

#### jvm_memory_opts

jvm_memory_opts 是本项目的 JVM 内存选项，默认值为：

```bat
SET jvm_memory_opts=^
-Xmx100m ^
-XX:MaxMetaspaceSize=130m ^
-XX:ReservedCodeCacheSize=15m ^
-XX:CompressedClassSpaceSize=15m
```

如果您希望修改本项目的 JVM 内存选项，可以修改此变量，变量中各选项含义如下：

- Xmx：最大堆内存。
- MaxMetaspaceSize：最大元空间大小。
- ReservedCodeCacheSize：保留代码缓存大小。
- CompressedClassSpaceSize：压缩类空间大小。

#### java_jmxremote_opts

java_jmxremote_opts 是本项目的 JMX 远程选项，默认值为：

```bat
SET java_jmxremote_opts=
```

该脚本默认关闭 JMX 远程选项，如果您需要开启 JMX 远程选项，可以修改此选项。

您可以通过该选项注释中的提示，修改该选项的值，以开启 JMX 远程选项。

下面是一种开启 JMX 远程选项的示例：

```bat
SET java_jmxremote_opts=^
-Dcom.sun.management.jmxremote.port=23000 ^
-Dcom.sun.management.jmxremote.authenticate=false ^
-Dcom.sun.management.jmxremote.ssl=false
```

### 固定变量

#### basedir

basedir 是本项目的根目录，其值为：

```bat
cd /d "%~dp0.."
SET "basedir=%cd%"
```

当启动脚本运行时，basedir 被动态地指定为当前脚本所在目录的上一级。无论在哪个目录中部署本项目，basedir 总会指向正确的路径。
基于以上原因，您无需更改该变量的值。

### java_logging_opts

java_logging_opts 是本项目的日志配置选项，其值为：

```bat
SET java_logging_opts=^
-Dlog4j2.configurationFile=confext/logging-settings.xml,conf/logging/settings.xml ^
-Dlog4j.shutdownHookEnabled=false ^
-Dlog4j2.is.webapp=false
```

该选项为项目中的 log4j2 日志框架提供了相关的系统参数（System Property）。

如果想要调整该项目的日志行为，您应该修改日志的配置文件，而不是该变量，以下文件可供您修改：

- `conf\logging\settings.xml`：项目内部代码的日志行为推荐使用该文件更改。
- `confext\logging-settings.xml`：插件代码的日志行为推荐使用该文件更改，以便于与项目的日志配置文件隔离。

上述文件修改时，如果日志配置发生冲突，以 `conf\logging\settings.xml` 中的配置为准。

# Telqos Commands - Telqos 命令

## 命令列表

rbac-distributed-service 提供的 Telqos 命令如下所示：

| 命令                       | 说明           | 可用版本    |
|--------------------------|--------------|---------|
| [lc](#lc-命令)             | 列出指令         | 1.1.0.a |
| [man](#man-命令)           | 显示指令的详细信息    | 1.1.0.a |
| [datamark](#datamark-命令) | 数据标记服务       | 1.7.0.a |
| [dubbo](#dubbo-命令)       | 分布式服务上线/下线   | 1.1.0.a |
| [log4j2](#log4j2-命令)     | Log4j2 命令    | 1.6.0.a |
| [memory](#memory-命令)     | 内存监视         | 1.1.0.a |
| [pflc](#pflc-命令)         | 权限过滤查询本地缓存操作 | 1.8.0.a |
| [pl](#pl-命令)             | 查询权限信息       | 1.6.0.a |
| [quit](#quit-命令)         | 退出           | 1.1.0.a |
| [reset](#reset-命令)       | 重置处理器操作/查看   | 1.8.0.a |
| [shutdown](#shutdown-命令) | 关闭/重启程序      | 1.1.0.a |
| [ul](#ul-命令)             | 查询用户信息       | 1.6.0.a |

## lc 命令

列出 Telqos 支持的所有命令。

### 语法

```text
usage: lc [-p prefix|--prefix prefix]
列出指令
 -p,--prefix <prefix>   列出包含指定前缀的命令
```

### 示例

```text
lc -p r
1   reset   重置处理器操作/查看
----------------------
共 1 条
OK
```

## man 命令

显示指定命令的详细信息。

### 语法

```text
usage: man [command]
显示指令的详细信息
```

### 示例

```text
man man
usage: man [command]
显示指令的详细信息
```

## datamark 命令

数据标记服务。

### 语法

```text
usage: datamark -lh
datamark -ua [-hn handler-name]
datamark -get [-hn handler-name]
datamark -refresh [-hn handler-name]
datamark -update [-hn handler-name] [-dv datamark-value]
数据标记服务
 -dv <arg>              数据标记值
 -get                   获取数据标记值
 -hn <arg>              数据服务 ID
 -lh,--list-handlers    列出所有可用的数据标记处理器
 -refresh               刷新数据标记值
 -ua,--update-allowed   返回处理器是否允许更新
 -update                更新数据标记值
```

### 示例

#### 列出所有可用的数据标记处理器

```text
datamark -lh
可用的处理器名称:
    1: permissionDatamarkHandler
    2: roleDatamarkHandler
    3: userDatamarkHandler
OK
```

#### 获取数据标记值

```text
datamark -get -hn permissionDatamarkHandler
处理器名称: permissionDatamarkHandler, 数据标记值: test@1.7.0
OK
```

#### 刷新数据标记值

更改 `permissionDatamarkHandler` 处理器的数据标记值中的内容（标记值依据配置，通常存放在 `conf/datamark` 目录下的文件中）。

```text
datamark -refresh -hn permissionDatamarkHandler
刷新成功!
处理器名称: permissionDatamarkHandler, 刷新后的数据标记值: test@1.7.0
OK
```

#### 更新数据标记值

```text
datamark -update -hn permissionDatamarkHandler -dv test@1.8.1更新成功!
处理器名称: permissionDatamarkHandler, 更新的数据标记值: test@1.8.1
OK
```

## dubbo 命令

分布式服务查询/上线/下线。

### 语法

```text
usage: dubbo -online [service-name]
dubbo -offline [service-name]
dubbo -ls
分布式服务上线/下线
 -ls              列出服务
 -offline <arg>   下线服务
 -online <arg>    上线服务
```

`[service-name]` 参数为正则表达式，只有服务的全名称匹配正则表达式时，才会被上线/下线。

如果想要上线/下线名称中包含 包含 `FooBar` 的服务，则可以使用 `.*FooBar.*` 作为正则表达式。

如果不指定 `[service-name]` 参数，则使所有服务上线/下线。

### 示例

#### 列出服务

```text
dubbo -ls
As Provider side:
+-------------------------------------------------------------------------+---+
|                          Provider Service Name                          |PUB|
+-------------------------------------------------------------------------+---+
|com.dwarfeng.rbacds.impl.handler.resetter.DubboResetter$DubboResetService| Y |
+-------------------------------------------------------------------------+---+
|     com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService    | Y |
+-------------------------------------------------------------------------+---+
| com.dwarfeng.rbacds.stack.service.PermissionFilterSupportMaintainService| Y |
+-------------------------------------------------------------------------+---+
|          com.dwarfeng.rbacds.stack.service.UserMaintainService          | Y |
+-------------------------------------------------------------------------+---+
|          com.dwarfeng.rbacds.stack.service.RoleMaintainService          | Y |
+-------------------------------------------------------------------------+---+
|          com.dwarfeng.rbacds.stack.service.PexpMaintainService          | Y |
+-------------------------------------------------------------------------+---+
|        com.dwarfeng.rbacds.stack.service.PermissionLookupService        | Y |
+-------------------------------------------------------------------------+---+
|       com.dwarfeng.rbacds.stack.service.PermissionMaintainService       | Y |
+-------------------------------------------------------------------------+---+
|           com.dwarfeng.rbacds.stack.service.UserLookupService           | Y |
+-------------------------------------------------------------------------+---+
As Consumer side:
+-----------------------------------------------+---+
|             Consumer Service Name             |NUM|
+-----------------------------------------------+---+
|com.dwarfeng.sfds.stack.service.GenerateService| 3 |
+-----------------------------------------------+---+
OK
```

#### 上线服务

```text
dubbo -online .*PermissionLookupService.*
OK
```

随后可以列出服务观察上线效果。

#### 下线服务

```text
dubbo -offline .*PermissionLookupService.*
OK
```

随后可以列出服务观察下线效果。

## log4j2 命令

Log4j2 命令

### 语法

```text
usage: log4j2 -reconfigure
Log4j2 命令
 -reconfigure   重新配置 Log4j2
```

### 示例

```text
log4j2 -reconfigure
Log4j2 已重新配置!
OK
```

## memory 命令

内存监视

### 语法

```text
usage: memory [-u unit]
内存监视
 -u <arg>   显示单位
```

### 示例

```text
memory -u mib
JVM 最大内存: 7212.50MiB
JVM 分配内存: 1223.00MiB
JVM 可用内存: 1120.00MiB
OK
```

## pflc 命令

权限过滤查询本地缓存操作。

### 语法

```text
usage: pflc -l permission-filter-type
pflc -c
权限过滤查询本地缓存操作
 -c                            清除缓存
 -l <permission-filter-type>   查看指定权限过滤类型的权限过滤器，如果本地缓存中不存在，则尝试抓取
```

### 示例

#### 清除缓存

```text
pflc -c
缓存已清空
OK
```

#### 查看指定权限过滤类型的权限过滤器

```text
pflc -l level
permissionFilter: LevelPermissionFilter{}
OK
```

## pl 命令

查询权限信息。

### 语法

```text
usage: pl -u user-id
pl -r role-id
查询权限信息
 -r <arg>   查询角色
 -u <arg>   查询用户
```

### 示例

#### 查询角色

```text
pl -r role_for_root

执行时间：189ms

查询到 90 条权限信息

输入 all 查看所有数据
输入 begin-end 查看指定范围的数据(开始于 0)
输入 q 退出查询
```

#### 查询用户

```text
pl -u root

执行时间：71ms

查询到 90 条权限信息

输入 all 查看所有数据
输入 begin-end 查看指定范围的数据(开始于 0)
输入 q 退出查询
```

## quit 命令

退出 Telqos 运维平台。

### 语法

```text
usage: quit
退出
```

### 示例

```text
quit
Bye
服务端主动与您中断连接
再见!


遗失对主机的连接。
```

## reset 命令

重置处理器查看与操作。

用于查看当前生效的重置器，以及基于指令手动重置服务功能。

### 语法

```text
usage: reset -l
reset -start
reset -stop
reset -status
reset -rpf
重置处理器操作/查看
 -l                               查看重置处理器
 -rpf,--reset-permission-filter   执行权限过滤重置操作
 -start                           启动重置处理器
 -status                          查看重置处理器状态
 -stop                            停止重置处理器
```

`reset -rpf` 执行权限过滤重置操作，会执行以下操作：

1. 重置权限过滤。

### 示例

#### 查看当前生效的重置器

```text
reset -l
01. CronResetter{cron='0 0 1 * * *'}
02. NeverResetter{}
OK
```

#### 查看重置处理器的启停状态

```text
reset -status
started: true
OK
```

#### 启动重置处理器

```text
reset -start
重置处理器已启动!
OK
```

#### 停止重置处理器

```text
reset -stop
重置处理器已停止!
OK
```

#### 重置记录功能

```text
reset -rpf
重置成功!
OK
```

## shutdown 命令

关闭/重启程序。

在本项目中，重启程序功能不可用，只能使用关闭程序功能。

### 语法

```text
usage: shutdown [-s/-r] [-e exit-code] [-c comment]
关闭/重启程序
 -c <comment>     备注
 -e <exit-code>   退出代码
 -r               重启程序
 -s               退出程序
```

增加 `-c` 参数，可以在关闭程序时添加备注信息，备注信息将会被记录到日志中。

`-r` 参数不可用。

### 示例

```text
shutdown
服务将会关闭，您可能需要登录远程主机才能重新启动该服务，是否继续? Y/N
Y
已确认请求，服务即将关闭...
服务端主动与您中断连接
再见!
```

## ul 命令

查询用户信息。

### 语法

```text
usage: ul -p permission-id
查询用户信息
 -p <arg>   查询权限
```

### 示例

#### 查询权限

```text
ul -p ui.pc.menu_visible.toolhouse.visualizer_support

执行时间：74ms

查询到 6 条用户信息

输入 all 查看所有数据
输入 begin-end 查看指定范围的数据(开始于 0)
输入 q 退出查询
```

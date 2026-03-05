# Telqos Commands - Telqos 命令

## 命令列表

rbac-distributed-service 提供的 Telqos 命令如下所示：

| 命令                       | 说明              | 可用版本    |
|--------------------------|-----------------|---------|
| [lc](#lc-命令)             | 列出指令            | 1.1.0.a |
| [man](#man-命令)           | 显示指令的详细信息       | 1.1.0.a |
| [datamark](#datamark-命令) | 数据标记服务          | 1.7.0.a |
| [dubbo](#dubbo-命令)       | 分布式服务上线/下线      | 1.1.0.a |
| [flc](#flc-命令)           | 过滤器本地缓存操作       | 2.0.0.a |
| [inspect](#inspect-命令)   | 查看服务            | 2.0.0.a |
| [log4j2](#log4j2-命令)     | Log4j2 命令       | 1.6.0.a |
| [memory](#memory-命令)     | 内存监视            | 1.1.0.a |
| [pm](#pm-命令)             | 权限操作服务          | 2.0.0.a |
| [pexp](#pexp-命令)         | 权限表达式操作服务       | 2.0.0.a |
| [pg](#pg-命令)             | 权限组操作服务         | 2.0.0.a |
| [pualc](#pualc-命令)       | 权限用户分析本地缓存操作    | 2.0.0.a |
| [quit](#quit-命令)         | 退出              | 1.1.0.a |
| [reset](#reset-命令)       | 重置处理器操作/查看      | 1.8.0.a |
| [shutdown](#shutdown-命令) | 关闭/重启程序         | 1.1.0.a |
| [spalc](#spalc-命令)       | 作用域权限分析本地缓存操作   | 2.0.0.a |
| [srpalc](#srpalc-命令)     | 作用域角色权限分析本地缓存操作 | 2.0.0.a |
| [support](#support-命令)   | 支持操作            | 2.0.0.a |
| [supalc](#supalc-命令)     | 作用域用户权限分析本地缓存操作 | 2.0.0.a |
| [uralc](#uralc-命令)       | 用户角色分析本地缓存操作    | 2.0.0.a |

鉴于所有指令都可以实际操作验证，因此本文对于较长的输出将予以省略，省略的部分将会使用 `etc...` 进行标注。

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
etc...
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

## flc 命令

过滤器本地缓存操作。

### 语法

```text
usage: flc -l filter-type
flc -c
过滤器本地缓存操作
 -c              清除缓存
 -l <filter-type>   查看指定过滤类型的过滤器，如果本地缓存中不存在，则尝试抓取
```

### 示例

#### 清除缓存

```text
flc -c
缓存已清空
OK
```

#### 查看指定过滤类型的过滤器

```text
flc -l level
filter: LevelFilter{}
OK
```

## inspect 命令

查看服务。

### 语法

```text
usage: inspect --inspect-permission-user [-json json-string] [-jf
               json-file]
inspect --inspect-role-permission [-json json-string] [-jf json-file]
inspect --inspect-user-permission [-json json-string] [-jf json-file]
查看服务
    --inspect-permission-user   查询权限的用户信息
    --inspect-role-permission   查询角色的权限信息
    --inspect-user-permission   查询用户的权限信息
 -jf,--json-file <arg>          JSON 文件
 -json <arg>                    JSON 字符串
```

### 示例

```text
inspect --inspect-role-permission -jf confext\inspect\inspect-role-permission.json
查询成功:

matchedFlag: true
matchedPermissions: 11
acceptedPermissions: 11
rejectedPermissions: 0 (空)
globalRejectedPermissions: 0 (空)

输入 m 查看 matchedPermissions
输入 a 查看 acceptedPermissions
输入 r 查看 rejectedPermissions
输入 g 查看 globalRejectedPermissions
输入 q 退出查询

etc...
q
OK
```

## pm 命令

权限操作服务。

### 语法

```text
usage: pm -create [-json json-string] [-jf json-file]
pm -update [-json json-string] [-jf json-file]
pm -remove [-json json-string] [-jf json-file]
权限操作服务
 -create                 创建权限
 -jf,--json-file <arg>   JSON 文件
 -json <arg>             JSON 字符串
 -remove                 移除权限
 -update                 更新权限
```

create/update/remove 操作必须配合 `-json` 或 `--json-file` 之一。
JSON 格式参考
`WebInputPermissionCreateInfo`、`WebInputPermissionUpdateInfo`、`WebInputPermissionRemoveInfo`。
JSON 文件可置于 `confext/` 目录（rbac-distributed-service-node 的外部配置文件夹）。

### 示例

#### 创建权限

```text
pm -create -jf confext/pm/create.json
创建成功, key: PermissionKey{scopeStringId='default', permissionStringId='example'}
OK
```

#### 更新权限

```text
pm -update -jf confext/pm/update.json
更新成功
OK
```

#### 移除权限

```text
pm -remove -jf confext/pm/remove.json
移除成功
OK
```

## pexp 命令

权限表达式操作服务。

### 语法

```text
usage: pexp -create [-json json-string] [-jf json-file]
pexp -update [-json json-string] [-jf json-file]
pexp -remove [-json json-string] [-jf json-file]
权限表达式操作服务
 -create                 创建权限表达式
 -jf,--json-file <arg>   JSON 文件
 -json <arg>             JSON 字符串
 -remove                 移除权限表达式
 -update                 更新权限表达式
```

create/update/remove 操作必须配合 `-json` 或 `--json-file` 之一。
JSON 格式参考
`WebInputPexpCreateInfo`、`WebInputPexpUpdateInfo`、`WebInputPexpRemoveInfo`。
JSON 文件可置于 `confext/` 目录（rbac-distributed-service-node 的外部配置文件夹）。

### 示例

#### 创建权限表达式

```text
pexp -create -jf confext/pexp/create.json
创建成功, key: PexpKey{scopeStringId='default', roleStringId='admin', pexpStringId='example'}
OK
```

#### 更新权限表达式

```text
pexp -update -jf confext/pexp/update.json
更新成功
OK
```

#### 移除权限表达式

```text
pexp -remove -jf confext/pexp/remove.json
移除成功
OK
```

## pg 命令

权限组操作服务。

### 语法

```text
usage: pg -create [-json json-string] [-jf json-file]
pg -update [-json json-string] [-jf json-file]
pg -remove [-json json-string] [-jf json-file]
权限组操作服务
 -create                 创建权限组
 -jf,--json-file <arg>   JSON 文件
 -json <arg>             JSON 字符串
 -remove                 移除权限组
 -update                 更新权限组
```

create/update/remove 操作必须配合 `-json` 或 `--json-file` 之一。
JSON 格式参考
`WebInputPermissionGroupCreateInfo`、`WebInputPermissionGroupUpdateInfo`、`WebInputPermissionGroupRemoveInfo`。
JSON 文件可置于 `confext/` 目录（rbac-distributed-service-node 的外部配置文件夹）。

### 示例

#### 创建权限组

```text
pg -create -jf confext/pg/create.json
创建成功, key: PermissionGroupKey{scopeStringId='default', permissionGroupStringId='example'}
OK
```

#### 更新权限组

```text
pg -update -jf confext/pg/update.json
更新成功
OK
```

#### 移除权限组

```text
pg -remove -jf confext/pg/remove.json
移除成功
OK
```

## pualc 命令

权限用户分析本地缓存操作。

### 语法

```text
usage: pualc -l -ls scopeId -lp permissionId
pualc -c
权限用户分析本地缓存操作
 -c                   清除缓存
 -l                   查看指定权限键的权限用户分析，如果本地缓存中不存在，则尝试抓取
 -lp <permissionId>   权限 ID
 -ls <scopeId>        作用域 ID
```

### 示例

#### 查看权限用户分析

```text
pualc -l -ls foo -lp foo.bar.1

matchedUsers: 1
acceptedRoles: 1
rejectedRoles: 0 (空)

输入 u 查看 matchedUsers
输入 a 查看 acceptedRoles
输入 r 查看 rejectedRoles
输入 q 退出查询

etc...
q
OK
```

#### 清除缓存

```text
pualc -c
缓存已清空
OK
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
reset -rf
reset -ra
重置处理器操作/查看
 -l                     查看重置处理器
 -ra,--reset-analysis   执行分析结果重置操作
 -rf,--reset-filter     执行过滤重置操作
 -start                 启动重置处理器
 -status                查看重置处理器状态
 -stop                  停止重置处理器
```

`reset -rf` 执行过滤重置操作，会重置权限过滤。

`reset -ra` 执行分析结果重置操作，会重置各类分析本地缓存。

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

#### 执行过滤重置操作

```text
reset -rf
重置成功!
OK
```

#### 执行分析结果重置操作

```text
reset -ra
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

## spalc 命令

作用域权限分析本地缓存操作。

### 语法

```text
usage: spalc -l -ls scopeId
spalc -c
作用域权限分析本地缓存操作
 -c              清除缓存
 -l              查看指定作用域键的作用域权限分析，如果本地缓存中不存在，则尝试抓取
 -ls <scopeId>   作用域 ID
```

### 示例

#### 查看作用域权限分析

```text
spalc -l -ls foo

permissions: 11

输入 p 查看 permissions
输入 q 退出查询

etc...
q
OK
```

#### 清除缓存

```text
spalc -c
缓存已清空
OK
```

## srpalc 命令

作用域角色权限分析本地缓存操作。

### 语法

```text
usage: srpalc -l -ls scopeId -lr roleId
srpalc -c
作用域角色权限分析本地缓存操作
 -c              清除缓存
 -l              查看指定作用域角色键的作用域角色权限分析，如果本地缓存中不存在，则尝试抓取
 -lr <roleId>    角色 ID
 -ls <scopeId>   作用域 ID
```

### 示例

#### 查看作用域角色权限分析

```text
srpalc -l -ls foo -lr admin

matchedPermissions: 11
acceptedPermissions: 11
rejectedPermissions: 0 (空)
globalRejectedPermissions: 0 (空)

输入 m 查看 matchedPermissions
输入 a 查看 acceptedPermissions
输入 r 查看 rejectedPermissions
输入 g 查看 globalRejectedPermissions
输入 q 退出查询

etc...
q
OK
```

#### 清除缓存

```text
srpalc -c
缓存已清空
OK
```

## support 命令

支持操作。

### 语法

```text
usage: support --reset-filter
支持操作
 --reset-filter   重置过滤器
```

### 示例

#### 重置过滤器

```text
support --reset-filter
重置过滤器成功。
OK
```

## supalc 命令

作用域用户权限分析本地缓存操作。

### 语法

```text
usage: supalc -l -ls scopeId -lu userId
supalc -c
作用域用户权限分析本地缓存操作
 -c              清除缓存
 -l              查看指定作用域用户键的作用域用户权限分析，如果本地缓存中不存在，则尝试抓取
 -ls <scopeId>   作用域 ID
 -lu <userId>    用户 ID
```

### 示例

#### 查看作用域用户权限分析

```text
supalc -l -ls foo -lu test.a

matchedPermissions: 11
roleDetails: 1

输入 p 查看 matchedPermissions
输入 r 查看 roleDetails
输入 q 退出查询

etc...
q
OK
```

#### 清除缓存

```text
supalc -c
缓存已清空
OK
```

## uralc 命令

用户角色分析本地缓存操作。

### 语法

```text
usage: uralc -l -lu userId
uralc -c
用户角色分析本地缓存操作
 -c             清除缓存
 -l             查看指定用户键的用户角色分析，如果本地缓存中不存在，则尝试抓取
 -lu <userId>   用户 ID
```

### 示例

#### 查看用户角色分析

```text
uralc -l -lu test.a

matchedRoles: 1

输入 r 查看 matchedRoles
输入 q 退出查询

etc...
q
OK
```

#### 清除缓存

```text
uralc -c
缓存已清空
OK
```

# Using Telqos - 使用 Telqos

## 说明

Telqos 是本项目的 telnet 运维平台。该文档详细说明了本项目的Telqos命令。

Telqos 是作者的一个开源项目，它能使用简单的配置快速搭建一个 telnet 运维平台。
Telqos 项目地址：[Github](https://github.com/DwArFeng/spring-telqos)
或 [Gitee](https://gitee.com/dwarfeng/spring-telqos)。

## 配置 Telqos 的端口号以及连接规则

Telqos 的端口号以及连接规则可以在 `conf/telqos/connection.properties` 中进行配置。

默认的配置如下：

```properties
# Telnet 端口。
telqos.port=23
# 字符集。
telqos.charset=UTF-8
# 白名单表达式。
telqos.whitelist_regex=
# 黑名单表达式。
telqos.blacklist_regex=
```

`telqos.port` 为 Telqos 的端口号，默认值为 telnet 的默认端口号 23。需要注意的是，
如果你的操作系统部署了多个包含 Telqos 的项目，那么这些项目的端口号不能相同。

`telqos.charset` 为 Telqos 的字符集，默认值为 UTF-8。如果在 Windows 系统中使用，则该值在大多数情况下应该为 GBK。

`telqos.whitelist_regex` 和 `telqos.blacklist_regex` 为 Telqos 的白名单和黑名单表达式。
如果你不需要使用白名单或者黑名单，那么可以将这两个值留空。一旦不留空，则 Telqos 会根据表达式对连接进行过滤。
当客户机的 IP 地址能够匹配白名单表达式，且不能够匹配黑名单表达式时，Telqos 会接受该连接。

## Telqos 连接

Telqos 的连接方式为 telnet，因此你可以使用任何支持 telnet 的客户端连接 Telqos。

对于没有 telnet 命令的 CentOS 系统，你可以使用 yum 安装 telnet。

```shell
yum -y install telnet
```

## 使用 telnet 连接 Telqos 运维平台

使用 telnet 连接 Telqos 运维平台的命令为：

```shell
telnet <host> <port>
```

其中 `<host>` 为 Telqos 所在的主机的 IP 地址，`<port>` 为 Telqos 的端口号。

输入指令后，Telqos 会返回一个欢迎信息，如下所示：

```Text
------------------------------------------------------------------------------------------------
               8 888888888o.   8 888888888o          .8.           ,o888888o.
               8 8888    `88.  8 8888    `88.       .888.         8888     `88.
               8 8888     `88  8 8888     `88      :88888.     ,8 8888       `8.
               8 8888     ,88  8 8888     ,88     . `88888.    88 8888
               8 8888.   ,88'  8 8888.   ,88'    .8. `88888.   88 8888
               8 888888888P'   8 8888888888     .8`8. `88888.  88 8888
               8 8888`8b       8 8888    `88.  .8' `8. `88888. 88 8888
               8 8888 `8b.     8 8888      88 .8'   `8. `88888.`8 8888       .8'
               8 8888   `8b.   8 8888    ,88'.888888888. `88888.  8888     ,88'
               8 8888     `88. 8 888888888P .8'       `8. `88888.  `8888888P'
------------------------------------------------------------------------------------------------
RBAC QOS 运维系统                                                                  版本: x.x.x.x
-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


欢迎您 [0:0:0:0:0:0:0:1]:60462

```

收到此欢迎信息，说明你已经成功连接到了 Telqos 运维平台。

## 参阅

- [Telqos Commands](./TelqosCommands.md) - Telqos 命令，详细说明了本项目的 Telqos 命令。

# Compile By Source - 从源码编译

## 准备依赖

该项目的部分依赖不在中央仓库下，需要从其它仓库下载，或者下载源码自行编译。

### 使用其它仓库

您可以在 `settings.xml` 中添加如下配置，以使用其它仓库，通常 `settings.xml` 在 `$HOME/.m2/` 文件目录下。

```xml

<settings
        xmlns="http://maven.apache.org/SETTINGS/1.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
        http://maven.apache.org/xsd/settings-1.0.0.xsd"
>

    <servers>
        <server>
            <id>xxx-releases</id>
            <username>your-username-here</username>
            <password>your-password-here</password>
        </server>
        <server>
            <id>rdc-snapshots</id>
            <username>your-username-here</username>
            <password>your-password-here</password>
        </server>
    </servers>

    <profiles>
        <profile>
            <id>xxx</id>
            <properties>
                <altReleaseDeploymentRepository>
                    xxx-releases::default::https://your-repository-url-here/
                </altReleaseDeploymentRepository>
                <altSnapshotDeploymentRepository>
                    xxx-snapshots::default::https://your-repository-url-here/
                </altSnapshotDeploymentRepository>
            </properties>
        </profile>
    </profiles>

    <activeProfiles>
        <activeProfile>xxx</activeProfile>
    </activeProfiles>
</settings>
```

使用的仓库需要保证有如下依赖，否则编译过程会因为找不到依赖而失败：

- com.dwarfeng:dutil
- com.dwarfeng:dwarfeng-datamark
- com.dwarfeng:snowflake-distributed-service
- com.dwarfeng:spring-telqos
- com.dwarfeng:spring-terminator
- com.dwarfeng:subgrade

### 下载依赖源码

您可以在 [Dwarfeng's Github](https://github.com/DwArFeng) 或 [Dwarfeng's Gitee](https://gitee.com/dwarfeng)
下载克隆依赖的源码， 然后使用 `mvn install` 命令将其安装到本地仓库中。

- com.dwarfeng:dutil

  github: [https://github.com/DwArFeng/dutil](https://github.com/DwArFeng/dutil)

  gitee: [https://gitee.com/dwarfeng/dutil](https://gitee.com/dwarfeng/dutil)

- com.dwarfeng:dwarfeng-datamark

  github: [https://github.com/DwArFeng/dwarfeng-datamark](https://github.com/DwArFeng/dwarfeng-datamark)

  gitee: [https://gitee.com/dwarfeng/dwarfeng-datamark](https://gitee.com/dwarfeng/dwarfeng-datamark)

- com.dwarfeng:snowflake-distributed-service

  github: [https://github.com/DwArFeng/snowflake-distributed-service](https://github.com/DwArFeng/snowflake-distributed-service)

  gitee: [https://gitee.com/dwarfeng/snowflake-distributed-service](https://gitee.com/dwarfeng/snowflake-distributed-service)

- com.dwarfeng:spring-telqos

  github: [https://github.com/DwArFeng/spring-telqos](https://github.com/DwArFeng/spring-telqos)

  gitee: [https://gitee.com/dwarfeng/spring-telqos](https://gitee.com/dwarfeng/spring-telqos)

- com.dwarfeng:spring-terminator

  github: [https://github.com/DwArFeng/spring-terminator](https://github.com/DwArFeng/spring-terminator)

  gitee: [https://gitee.com/dwarfeng/spring-terminator](https://gitee.com/dwarfeng/spring-terminator)

- com.dwarfeng:subgrade

  github: [https://github.com/DwArFeng/subgrade](https://github.com/DwArFeng/subgrade)

  gitee: [https://gitee.com/dwarfeng/subgrade](https://gitee.com/dwarfeng/subgrade)

## 下载源码

使用 git 进行源码下载。

```shell
git clone git@github.com:DwArFeng/rbac-distributed-service.git
```

对于中国用户，可以使用 gitee 进行下载。

```shell
git clone git@gitee.com:dwarfeng/rbac-distributed-service.git
```

## 项目编译、打包

进入项目根目录，执行 maven 命令。

```shell
mvn clean package
```

如果上述命令执行失败，请仔细阅读报错内容，绝大部分情况下是因为上述依赖缺失。请您重复上述步骤，直到编译成功。

## 寻找打包后的目标文件

找到打包后的目标目录

```
rbac-distributed-service-node/target/rbac-${version}-release
```

如能找到该目录，则说明编译成功。

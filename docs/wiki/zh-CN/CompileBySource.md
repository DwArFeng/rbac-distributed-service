# Compile By Source - 从源码编译

## 准备依赖

该项目的部分依赖不在中央仓库下，需要从其它仓库下载，或者下载源码自行编译。

### 使用其它仓库

您可以在 `settings.xml` 中添加如下配置，以使用其它仓库，通常 `settings.xml` 在 `$HOME/.m2/` 文件目录下。

```xml
<?xml version="1.0" encoding="UTF-8"?>

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
- com.dwarfeng:subgrade
- com.dwarfeng:spring-terminator
- com.dwarfeng:spring-telqos
- com.dwarfeng:dwarfeng-datamark

### 下载依赖源码

您可以在 [Dwarfeng's Github](https://github.com/DwArFeng) 或 [Dwarfeng's Gitee](https://gitee.com/dwarfeng)
下载克隆依赖的源码， 然后使用 `mvn install` 命令将其安装到本地仓库中。

- com.dwarfeng:dutil

  github: [https://github.com/DwArFeng/dutil](https://github.com/DwArFeng/dutil)

  gitee: [https://gitee.com/dwarfeng/dutil](https://gitee.com/dwarfeng/dutil)

- com.dwarfeng:subgrade

  github: [https://github.com/DwArFeng/subgrade](https://github.com/DwArFeng/subgrade)

  gitee: [https://gitee.com/dwarfeng/subgrade](https://gitee.com/dwarfeng/subgrade)

- com.dwarfeng:spring-terminator

  github: [https://github.com/DwArFeng/spring-terminator](https://github.com/DwArFeng/spring-terminator)

  gitee: [https://gitee.com/dwarfeng/spring-terminator](https://gitee.com/dwarfeng/spring-terminator)

- com.dwarfeng:spring-telqos

  github: [https://github.com/DwArFeng/spring-telqos](https://github.com/DwArFeng/spring-telqos)

  gitee: [https://gitee.com/dwarfeng/spring-telqos](https://gitee.com/dwarfeng/spring-telqos)

- com.dwarfeng:dwarfeng-datamark

  github: [https://github.com/DwArFeng/dwarfeng-datamark](https://github.com/DwArFeng/dwarfeng-datamark)

  gitee: [https://gitee.com/dwarfeng/dwarfeng-datamark](https://gitee.com/dwarfeng/dwarfeng-datamark)

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

打包完成后，各运行节点按功能（function）与构型（configuration）在对应的 node 子模块中生成发布物；
`rbac-distributed-service-distribute` 模块会将各节点产物汇总到统一的输出目录（见下一节）。

如果上述命令执行失败，请仔细阅读报错内容，绝大部分情况下是因为上述依赖缺失。请您重复上述步骤，直到编译成功。

## 寻找打包后的目标文件

运行节点按功能与构型划分；各节点在 `rbac-distributed-service-node` 下以子模块形式维护。

以 `rbac-distributed-service-node-all-he` 为例：

- `all` 表示功能维度（与构建中的 `node.function` 一致），
- `he` 表示构型维度（与 `node.configuration` 一致）。

若日后增加其它节点，`rbac-distributed-service-distribute` 的装配可能增加对应的汇总子目录，请以该模块的装配描述为准。

推荐用于确认打包成功的主路径（各节点发布物的汇总目录）：

```
rbac-distributed-service-distribute/target/distribute
```

其下按节点分目录存放。

当前可看到的文件为：

- `rbac-all-he/`
  - 压缩包形态文件 `rbac-all-he-${version}-release.tar.gz`
  - 解压形态目录 `rbac-all-he-${version}-release/`

若上述 tar 包或同名 `-release` 目录存在，即可认为对应节点的打包已成功。

如需单独查看本仓库构建出的制品（例如调试），路径为：

- `rbac-distributed-service-node-all-he/`
  - 压缩包形态文件 `target/rbac-all-he-${version}-release.tar.gz`
  - 解压形态目录 `target/rbac-all-he-${version}-release/`

| 模块名称                                   | 制品路径                                     |
|:---------------------------------------|:-----------------------------------------|
| `rbac-distributed-service-node-all-he` | `target/rbac-all-he-${version}-release/` |

对交付与发布而言，以 `rbac-distributed-service-distribute/target/distribute` 下的发布包与目录为准。

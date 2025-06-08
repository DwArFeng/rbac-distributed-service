# rbac-distributed-service

登录、角色、权限的综合解决方案。

## 特性

1. Subgrade 架构支持。
2. 实现基础的 RBAC 权限控制，关键查询服务使用分布式缓存提供查询效率。
3. 支持权限表达式，提供更灵活的权限设置方式。
4. 提供 QoS 运维平台，能够在前端页面、GUI 尚未开发完成的环境下使用本服务的功能，并进行运维操作。
5. 关键数据支持数据标记，为运维调试、数据迁移、数据追溯提供一定的辅助作用。

## 文档

该项目的文档位于 [docs](./docs) 目录下，包括：

### wiki

wiki 为项目的开发人员为本项目编写的详细文档，包含不同语言的版本，主要入口为：

1. [简介](./docs/wiki/zh_CN/Introduction.md) - 镜像的 `README.md`，与本文件内容基本相同。
2. [目录](./docs/wiki/zh_CN/Contents.md) - 文档目录。

## 安装说明

1. 下载源码

   使用 git 进行源码下载：

   ```shell
   git clone git@github.com:DwArFeng/rbac-weapon-rack.git
   ```

   对于中国用户，可以使用 gitee 进行高速下载：

   ```shell
   git clone git@gitee.com:dwarfeng/rbac-weapon-rack.git
   ```

2. 项目打包

   进入项目根目录，执行 maven 命令：

   ```shell
   mvn clean package
   ```

3. 解压

   找到打包后的目标文件：

   ```
   rbac-weapon-rack-node/target/rbacwr-[version]-release.tar.gz
   ```

   将其解压至 windows 系统或者 linux 系统。

4. 配置

   1. 修改 conf 文件夹下的配置文件，着重修改各连接的 url 与密码。

5. enjoy it

## 分布式说明

该项目使用 `dubbo` 作为RPC框架，本身支持分布式，您可以在实际使用时，部署该项目任意数量，以进行分布式运算。

## 项目的使用

1. RBAC

   本权限维护系统是基于RBAC的。

   **RBAC是指定义人员、角色、权限，人员与角色关联，角色与权限关联，最终达到根据人员获得其所有权限的目的。**

   本权限维护系统在传统的RBAC之上新增了一个权限表达式层，通过权限表达式层可以更好的与权限对接，其工作机制如下：

   - 定义人员、角色、权限表达式、权限四个实体对象。
   - 人员与角色多对多。
   - 角色与权限表达式多对多。
   - 权限表达式根据一定的规则匹配具体的一个或多个权限。

2. 权限查询机制

   本系统在权限查询时，会查按照如下的逻辑执行查询：

   1. 查询一个人员对应的所有（有效的）角色，并且查询每个角色的所有权限表达式。
   2. 为每个角色创建包含权限集合、排除权限集合，并且创建一个全局的排除权限集合。
   3. 遍历所有权限，通过其权限表达式向权限对应的包含权限集合、排除权限集合，以及全局的权限排除集合中添加权限。
   4. 遍历所有权限，从每个权限对应的包含权限集合中减去每个权限对应的排除权限集合，每个权限得到的新集合合并，生成全局包含权限集合。
   5. 全局包含权限集合减去全局排除权限集合，得到最终的权限。

## 权限表达式

权限表达式是本项目中的一个重要概念，权限表达式是一个字符串，表示一个权限的匹配规则。

有关权限表达式的详细信息，请参考 [目录](./docs/wiki/zh_CN/Contents.md) 中有关权限表达式的部分章节。

## 插件

该项目可以进行插件扩展，将自己编写的插件放在项目的 `libext` 文件夹中，并且在 `optext` 中编写spring加载文件，以实现插件的加载。

**注意：`optext` 目录下的spring加载文件请满足`opt*.xml`的格式，即以opt开头，以.xml结尾。**

## 项目的扩展

本项目支持权限表达式的扩展

扩展权限表达式时，请实现接口实现接口
`com.dwarfeng.rbacwr.impl.handler.PermissionFilter`，并将实现类注入到spring的IoC容器中。

以下代码是表达式标识符EXACT的实现代码，以供参考

```java
/**
 * 精确匹配的权限过滤器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class ExactPermissionFilter implements PermissionFilter {

    @Override
    public String getIdentifier() {
        return "EXACT";
    }

    @Override
    public boolean accept(String pattern, String permissionContent) {
        return Objects.equals(pattern, permissionContent);
    }
}
```

## 兼容性说明

### subgrade 兼容性说明

该项目依赖于 `subgrade` 项目。

该项目自从 `1.5.3.a` 以后，与 `subgarde` 的 `<1.4.0.a` 版本不兼容。

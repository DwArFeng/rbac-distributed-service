# ChangeLog

### Release_1.8.2_20250521_build_A

#### 功能构建

- 优化权限表达式解析逻辑相关的部分类名。
  - PexpParseHandler.PipeModifier -> PexpParseHandler.Modifier。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.8.1_20250520_build_A

#### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/TelqosCommands.md。

#### Bug修复

- 更正运维指令错误的类名。
  - MapLocalCacheCommand -> PermissionFilterLocalCacheCommand。

- 更正 wiki 中错误的内容。
  - docs/wiki/zh_CN/PexpV2.md。

- 补充 `.gitignore` 中缺失的配置。

#### 功能移除

- (无)

---

### Release_1.8.0_20250519_build_A

#### 功能构建

- 更新 README.md。

- Wiki 更新。
  - docs/wiki/zh_CN/Introduction.md。

- Wiki 编写。
  - docs/wiki/zh_CN/PexpGeneral.md。
  - docs/wiki/zh_CN/PexpV1.md。
  - docs/wiki/zh_CN/PexpV2.md。

- 实现预设权限过滤器。
  - com.dwarfeng.rbacds.impl.handler.pfilter.LevelPermissionFilterRegistry。

- 新增实体字段。
  - com.dwarfeng.rbacds.stack.bean.entity.Permission.level。

- 实现核心机制。
  - 推送机制。
  - 重置机制。

- 实现预设推送器。
  - com.dwarfeng.rbacds.impl.handler.pusher.DrainPusher。
  - com.dwarfeng.rbacds.impl.handler.pusher.LogPusher。
  - com.dwarfeng.rbacds.impl.handler.pusher.MultiPusher。

- 实现预设重置器。
  - com.dwarfeng.rbacds.impl.handler.resetter.CronResetter。
  - com.dwarfeng.rbacds.impl.handler.resetter.DubboResetter。
  - com.dwarfeng.rbacds.impl.handler.resetter.FixedDelayResetter。
  - com.dwarfeng.rbacds.impl.handler.resetter.FixedRateResetter。
  - com.dwarfeng.rbacds.impl.handler.resetter.NeverResetter。

- 实现运维指令。
  - com.dwarfeng.rbacds.impl.service.telqos.ResetCommand。
  - com.dwarfeng.rbacds.impl.service.telqos.MapLocalCacheCommand。

- 重构权限表达式机制。
  - 重构权限过滤器，使其成为标准的插件式组件。
  - 重构权限表达式的解析机制，将解析过程委托给 `PexpHandler`，并在内部实现中使用解析引擎进行解析。
  - 增加第二代权限表达式，引入了权限管道等概念，并提供了相应的实现。

- 新增实体及其维护服务，并通过单元测试。
  - com.dwarfeng.rbacds.stack.bean.entity.PermissionFilterSupport。

- 优化部分字符串字段的长度约束。
  - com.dwarfeng.rbacds.impl.bean.entity.HibernatePermission。
  - com.dwarfeng.rbacds.impl.bean.entity.HibernatePermissionGroup。
  - com.dwarfeng.rbacds.impl.bean.entity.HibernateRole。
  - com.dwarfeng.rbacds.impl.bean.entity.HibernateUser。
  - com.dwarfeng.rbacds.sdk.bean.entity.WebInputPermission。
  - com.dwarfeng.rbacds.sdk.bean.entity.WebInputPermissionGroup。
  - com.dwarfeng.rbacds.sdk.bean.entity.WebInputPexp。
  - com.dwarfeng.rbacds.sdk.bean.entity.WebInputRole。
  - com.dwarfeng.rbacds.sdk.bean.entity.WebInputUser。

#### Bug修复

- 修正 `FastJsonConfiguration` 中缺失 autotype 白名单实体配置的 bug。
  - com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermissionGroup。

#### 功能移除

- (无)

---

### Release_1.7.2_20250513_build_A

#### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/UsingTelqos.md。

#### Bug修复

- 修复部分单元测试代码执行后未从数据库中清除所有数据的 bug。
  - com.dwarfeng.rbacds.impl.service.UserMaintainServiceImplTest。

#### 功能移除

- (无)

---

### Release_1.7.1_20250512_build_A

#### 功能构建

- 优化默认数据标记值仓库文件中的内容。
  - 优化 `api` 模块 `datamark/default.storage` 中的内容。
  - 优化 `impl` 模块 `datamark/default.storage` 中的内容。

- 部分代码注释优化。
  - com.dwarfeng.rbacds.impl.bean.BeanMapper。
  - com.dwarfeng.rbacds.sdk.bean.BeanMapper。

- Wiki 编写。
  - docs/wiki/zh_CN/ShellScripts.md。
  - docs/wiki/zh_CN/BatchScripts.md。

- 更新 README.md。

- Wiki 更新。
  - docs/wiki/zh_CN/Introduction.md。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.7.0_20250510_build_A

#### 功能构建

- 更新 README.md。

- Wiki 编写。
  - 构建 wiki 目录结构。
  - docs/wiki/en_US/Contents.md。
  - docs/wiki/en_US/Introduction.md。
  - docs/wiki/zh_CN/Contents.md。
  - docs/wiki/zh_CN/Introduction.md。

- 为部分工具类中方法的入口参数增加 `@NotNull` 注解。
  - com.dwarfeng.rbacds.impl.service.telqos.CommandUtil。

- 优化实体映射器机制。

- 优化部分配置文件的名称。
  - `opt/opt-preset.xml` -> `opt/opt-pfilter.xml`。

- 导入运维指令。
  - com.dwarfeng.datamark.service.telqos.*。

- 增加 Hibernate 实体数据标记字段，并应用相关实体侦听器。
  - com.dwarfeng.rbacds.impl.bean.entity.HibernatePermission。
  - com.dwarfeng.rbacds.impl.bean.entity.HibernatePermissionGroup。
  - com.dwarfeng.rbacds.impl.bean.entity.HibernatePexp。
  - com.dwarfeng.rbacds.impl.bean.entity.HibernateRole。

- 增加依赖。
  - 增加依赖 `dwarfeng-datamark` 以应用其新功能，版本为 `1.0.1.a`。

- 优化部分单元测试代码，以规避潜在的 bug。
  - com.dwarfeng.rbacds.api.integration.subgrade.PermissionLookupHandlerImplTest。
  - com.dwarfeng.rbacds.impl.service.PermissionGroupMaintainServiceImplTest。
  - com.dwarfeng.rbacds.impl.service.PermissionLookupServiceImplTest。
  - com.dwarfeng.rbacds.impl.service.PermissionMaintainServiceImplTest。
  - com.dwarfeng.rbacds.impl.service.PexpMaintainServiceImplTest。
  - com.dwarfeng.rbacds.impl.service.RoleMaintainServiceImplTest。
  - com.dwarfeng.rbacds.impl.service.UserLookupServiceImplTest。
  - com.dwarfeng.rbacds.impl.service.UserMaintainServiceImplTest。
  - com.dwarfeng.rbacds.impl.service.UserRoleUpdateRelationTest。

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.39` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.119.Final` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.9.3` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.6.3.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.5.9.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.14.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.13.a` 以规避漏洞。

#### Bug修复

- 修正错误的 dubbo 应用名称。
  - 修正 `api` 模块中错误的 dubbo 应用名称。
  - 修正 `impl` 模块中错误的 dubbo 应用名称。

- 修改部分配置文件，以修复 `opt/opt-pfilter.xml` 不生效的 bug。
  - `spring/application-context-scan.xml`。

- 修复部分单元测试执行完毕后，测试实体没有全部删除的 bug。
  - com.dwarfeng.rbacds.impl.service.UserMaintainServiceImplTest。

#### 功能移除

- (无)

---

### Release_1.6.0_20241117_build_A

#### 功能构建

- 为 dubbo 增加超时时间的配置选项。

- 优化部分配置文件的注释。
  - `database/performance.properties`。

- 优化 Telqos 指令的输出。
  - com.dwarfeng.rbacds.impl.service.telqos.PermissionLookupCommand。
  - com.dwarfeng.rbacds.impl.service.telqos.UserLookupCommand。

- 优化查询服务的调用机制。
  - 优化查询服务的调用链。
  - 优化查询相关的 QOS 服务。
  - 优化查询相关的 Telqos 指令。

- 优化部分类中部分方法的行为分析行为。
  - com.dwarfeng.rbacds.impl.dao.PermissionDaoImpl。
  - com.dwarfeng.rbacds.impl.dao.PermissionGroupDaoImpl。
  - com.dwarfeng.rbacds.impl.dao.RoleDaoImpl。

- 优化部分配置文件的名称。
  - `redis/key.properties` -> `redis/prefix.properties`。

- 启停脚本优化。
  - 优化 Windows 系统的启动脚本。
  - 优化 Linux 系统的启停脚本。

- 优化项目启停脚本设置程序的根目录的方式。

- 优化启停脚本的目录结构。

- 增加预设的运维指令。
  - com.dwarfeng.springtelqos.api.integration.log4j2.Log4j2Command。

- 日志功能优化。
  - 优化默认日志配置，默认配置仅向控制台输出 `INFO` 级别的日志。
  - 优化日志配置结构，提供 `conf/logging/settings.xml` 配置文件及其不同平台的参考配置文件，以供用户自定义日志配置。
  - 优化日志配置结构，提供 `confext/logging-settings.xml` 配置文件，以供外部功能自定义日志配置。
  - 优化启动脚本，使服务支持新的日志配置结构。
  - 优化 `assembly.xml`，使项目打包时输出新的日志配置结构。
  - 优化 `confext/README.md`，添加新的日志配置结构的相关说明。

- 重构实体的维护服务以及部分数据访问层。

- 升级 spring-telqos 并应用其新功能。
  - 使用包扫描的方式注册指令。
  - 优化 `telqos/connection.properties` 中配置的键名。
  - 重构部分指令的实现。
  - 优化部分指令的名称与描述。

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.37` 并解决兼容性问题，以应用其新功能。
  - 升级 `protobuf` 依赖版本为 `3.25.5` 以规避漏洞。
  - 升级 `mysql` 依赖版本为 `8.2.0` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.108.Final` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.7.2` 以规避漏洞。
  - 升级 `slf4j` 依赖版本为 `1.7.36` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.5.2.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.5.6.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.13.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.10.a` 以规避漏洞。

#### Bug修复

- 修复服务异常代号的 bug。
  - 修复 `ServiceExceptionCodes` 设置服务异常代号的偏移量时，服务异常代号中的代码值未更新的的 bug。

- 修复部分服务异常代号的冲突问题。
  - ServiceExceptionCodes.ROLE_NOT_EXISTS。

#### 功能移除

- 删除不需要的依赖。
  - 删除 `aopalliance` 依赖。

---

### Release_1.5.4_20230620_build_A

#### 功能构建

- 依赖升级。
  - 升级 `dubbo` 依赖版本为 `2.7.22` 以规避漏洞。
  - 升级 `guava` 依赖版本为 `32.0.1-jre` 以规避漏洞。

- 优化配置文件。
  - 优化 `application-context-database.xml`，使得更多属性可以在配置文件中配置。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.3_20230509_build_A

#### 功能构建

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.4.0.a` 并解决兼容性问题，以应用其新功能。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.2_20230427_build_A

#### 功能构建

- 增加预设查询。
  - PermissionGroupMaintainService.NAME_LIKE。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.1_20230421_build_A

#### 功能构建

- 重新组织权限过滤器的结构。
  - 添加 com.dwarfeng.rbacds.impl.handler.pfilter.AbstractPermissionFilter。
  - 所有权限过滤器继承 AbstractPermissionFilter。
  - 重新设计 opt/opt-preset.xml。

- 优化项目结构，增加项目目录。
  - `./confext/`。

- 启停脚本优化。
  - 优化 Windows 系统的启动脚本。
  - 优化 Linux 系统的启停脚本。

- 优化 Mapper 接口的文件路径。

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.27` 以规避漏洞。
  - 升级 `snakeyaml` 依赖版本为 `2.0.0` 以规避漏洞。
  - 升级 `dubbo` 依赖版本为 `2.7.21` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.86.Final` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.11.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.3.3.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.11.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.6.a` 以规避漏洞。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.0_20221130_build_A

#### 功能构建

- Dubbo 微服务增加分组配置。

- 使用 `MapStruct` 重构 `BeanTransformer`。

- 增加依赖。
  - 增加依赖 `javassist` 以规避漏洞，版本为 `3.23.2-GA`。
  - 增加依赖 `protobuf` 以规避漏洞，版本为 `3.19.6`。
  - 增加依赖 `guava` 以规避漏洞，版本为 `31.1-jre`。
  - 增加依赖 `gson` 以规避漏洞，版本为 `2.8.9`。
  - 增加依赖 `snakeyaml` 以规避漏洞，版本为 `1.33`。

- 依赖升级。
  - 升级 `mysql` 依赖版本为 `8.0.31` 以规避漏洞。
  - 升级 `jedis` 依赖版本为 `3.8.0` 以规避漏洞。
  - 升级 `spring-data-redis` 依赖版本为 `2.7.5` 以规避漏洞。
  - 升级 `dubbo` 依赖版本为 `2.7.18` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.5.7` 以规避漏洞。
  - 升级 `curator` 依赖版本为 `4.3.0` 以规避漏洞。
  - 升级 `hibernate-validator` 依赖版本为 `6.2.5.Final` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `beta-0.3.2.a` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.10.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.2.14.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.10.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.5.a` 以规避漏洞。

#### Bug修复

- 修正 `DirectSubGroupPermissionFilter` 代码中潜在的空指针异常。

#### 功能移除

- 删除不需要的依赖。
  - 删除 `el` 依赖。
  - 删除 `zkclient` 依赖。
  - 删除 `commons-lang3` 依赖。
  - 删除 `hessian` 依赖。
  - 删除 `jetty` 依赖。
  - 删除 `dozer` 依赖。

---

### Release_1.4.2_20220912_build_A

#### 功能构建

- 依赖升级。
  - 升级 `spring-terminator` 依赖版本为 `1.0.9.a`。
  - 升级 `dutil` 依赖版本为 `beta-0.3.1.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.2.10.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.4.a` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.9.a` 以规避漏洞。

- 插件升级。
  - 升级 `maven-deploy-plugin` 插件版本为 `2.8.2`。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.4.1_20220607_build_A

#### 功能构建

- 依赖升级。
  - 升级 `junit` 依赖版本为 `4.13.2` 以规避漏洞。
  - 升级 `spring` 依赖版本为 `5.3.20` 以规避漏洞。
  - 升级 `mysql` 依赖版本为 `8.0.28` 以规避漏洞。
  - 升级 `fastjson` 依赖版本为 `1.2.83` 以规避漏洞。
  - 升级 `dubbo` 依赖版本为 `2.7.15` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.77.Final` 以规避漏洞。
  - 升级 `hibernate` 依赖版本为 `5.3.20.Final` 以规避漏洞。
  - 升级 `hibernate-validator` 依赖版本为 `6.0.21.Final` 以规避漏洞。
  - 升级 `log4j2` 依赖版本为 `2.17.2` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `beta-0.2.2.a` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.4` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.2.7.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.8.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.3.a` 以规避漏洞。

- 将工程中的 `Spring Bean` 注册方式尽可能地由 `@Autowired` 变更为构造器注入。

#### Bug修复

- (无)

#### 功能移除

- 移除无用的依赖（定义）。
  - 移除 `joda-time` 依赖。
  - 移除 `pagehelper` 依赖。
  - 移除 `jsqlparser` 依赖。
  - 移除 `commons-fileupload` 依赖。
  - 移除 `noggit` 依赖。

---

### Release_1.4.0_20211025_build_A

#### 功能构建

- 预设查询 `UserMaintainService.CHILD_FOR_ROLE` 更名为 `UserMaintainService.CHILD_FOR_ROLE_SET`。
- 重设预设查询 `UserMaintainService.CHILD_FOR_ROLE`，使其查询单个角色下的用户。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.3.1_20211024_build_A

#### 功能构建

- 更新部分接口方法的命名，使其更合理。
  - com.dwarfeng.rbacds.stack.service.PermissionLookupService。
  - com.dwarfeng.rbacds.stack.service.UserLookupService。

- 删除部分不使用的依赖。
  - `httpclient`。
  - `httpmime`。
  - `httpcore`。
  - `solr-solrj`。
  - `spring-web`。
  - `spring-webmvc`。
  - `commons-io`。
  - `commons-net`。

#### Bug修复

- 修复查询接口中的查询方法不包含事务的 bug。
  - com.dwarfeng.rbacds.stack.service.PermissionLookupService。
  - com.dwarfeng.rbacds.stack.service.UserLookupService。

#### 功能移除

- (无)

---

### Release_1.3.0_20211003_build_A

#### 功能构建

- (无)

#### Bug修复

- (无)

#### 功能移除

- 删除实体。
  - com.dwarfeng.rbacds.stack.bean.entity.RoleGroup。

---

### Release_1.2.4_20210824_build_A

#### 功能构建

- 增加角色对应的权限的查询服务方法。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.2.3_20210824_build_A

#### 功能构建

- (无)

#### Bug修复

- 修复角色更新后会丢失角色与用户关联的bug。

#### 功能移除

- (无)

---

### Release_1.2.2_20210118_build_A

#### 功能构建

- (无)

#### Bug修复

- 修复下列实体删除时，无法取消子实体的关联的bug。
  - com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup
  - com.dwarfeng.rbacds.stack.bean.entity.RoleGroup

#### 功能移除

- (无)

---

### Release_1.2.1_20210106_build_A

#### 功能构建

- 数据访问层的父项属性查询支持 null
  - com.dwarfeng.rbacds.impl.dao.preset.PermissionGroupPresetCriteriaMaker
  - com.dwarfeng.rbacds.impl.dao.preset.PermissionPresetCriteriaMaker
  - com.dwarfeng.rbacds.impl.dao.preset.RoleGroupPresetCriteriaMaker
  - com.dwarfeng.rbacds.impl.dao.preset.RolePresetCriteriaMaker

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.2.0_20210105_build_A

#### 功能构建

- 添加实体或增加实体字段
  - com.dwarfeng.rbacds.stack.bean.entity.Permission
  - com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup
  - com.dwarfeng.rbacds.stack.bean.entity.Role
  - com.dwarfeng.rbacds.stack.bean.entity.RoleGroup
- com.dwarfeng.rbacds.impl.handler.PermissionFilter 接口优化。
- 更改、新增 com.dwarfeng.rbacds.impl.handler.PermissionFilter 实现。
  - com.dwarfeng.rbacds.impl.handler.pfilter.DirectSubGroupPermissionFilter
  - com.dwarfeng.rbacds.impl.handler.pfilter.IdPrefixPermissionFilter
  - com.dwarfeng.rbacds.impl.handler.pfilter.IdRegexPermissionFilter
  - com.dwarfeng.rbacds.impl.handler.pfilter.NameRegexPermissionFilter
  - com.dwarfeng.rbacds.impl.handler.pfilter.NestedSubGroupPermissionFilter

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.1.0_20201217_build_A

#### 功能构建

- 更新依赖的版本。
- 优化数据库配置文件。
- 移除未使用的依赖，解决依赖冲突。
- 优化测试代码。
- 优化测试项目下的 log4j2.xml 配置文件。
- 优化 dubbo 的配置。
- 消除预设配置文件中的真实的 ip 地址。
- 增加 QOS 功能。
  - 添加 spring-telqos 依赖。
  - 引入预设指令。
  - 添加 QosService 服务。
  - 添加 PermissionCommand 指令。
  - 添加 UserCommand 指令。
- 新功能实现。
  - 通过权限反查相关用户。
- 优化 BehaviorAnalyse，取消有可能产生大量文本的返回结果以及入口参数的记录。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.0.2_20200828_build_A

#### 功能构建

- 修正程序在dubbo中注册的应用名称。
- 规范数据库连接名称。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.0.1_20200828_build_A

#### 功能构建

- 修改项目打包后的输出路径。
- 优化启动、停止脚本。
- 解决依赖冲突、删除无用依赖。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.0.0_20200828_build_A

#### 功能构建

- 代码迁移。

#### Bug修复

- (无)

#### 功能移除

- (无)

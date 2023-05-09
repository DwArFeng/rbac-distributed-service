# ChangeLog

### Release_1.5.3_20230509_build_A

#### 功能构建

- (无)

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

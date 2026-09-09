# ChangeLog

## Release_3.0.2_20260909_build_A

### 功能构建

- Wiki 更新。
  - docs/wiki/zh-CN/CompileBySource.md。
  - docs/wiki/zh-CN/ConfDirectory.md。
  - docs/wiki/zh-CN/PermissionModel.md。
  - docs/wiki/zh-CN/PexpGeneral.md。
  - docs/wiki/zh-CN/PexpV1.md。
  - docs/wiki/zh-CN/PexpV2.md。
  - docs/wiki/zh-CN/SystemRequirements.md。
  - docs/wiki/zh-CN/TelqosCommands.md。
  - docs/wiki/zh-CN/UsingTelqos.md。
  - docs/wiki/zh-CN/VersionBlacklist.md。

- 依赖升级。
  - 升级 `fastjson` 依赖版本为 `1.2.84` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.8.4.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `2.0.3.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_3.0.1_20260607_build_A

### 功能构建

- Wiki 更新。
  - docs/wiki/zh-CN/InstallToCentos.md。
  - docs/wiki/zh-CN/CompileBySource.md。

- `rbac-distributed-service-impl` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.rbacds.impl.handler.ScopedUserPermissionAnalysisLocalCacheHandlerImpl。
  - com.dwarfeng.rbacds.impl.service.ScopedRolePermissionAnalysisLocalCacheQosServiceImpl。
  - com.dwarfeng.rbacds.impl.service.ScopedUserPermissionAnalysisLocalCacheQosServiceImpl。
  - com.dwarfeng.rbacds.impl.handler.PexpParseHandlerImplTestData。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.8.3.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `2.0.2.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `2.0.2.a` 以规避漏洞。
  - 升级 `dwarfeng-datamark` 依赖版本为 `2.2.0.a` 以规避漏洞。

- 优化文件格式。
  - 优化 `assembly.xml` 文件的格式。
  - 优化 `pom.xml` 文件的格式。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_3.0.0_20260607_build_A

### 功能构建

- Wiki 更新。
  - docs/wiki/zh-CN/CompileBySource.md。

- `rbac-distributed-service-distribute` 模块新增。
  - 新增 `rbac-distributed-service-distribute` 模块，负责各个构型的产物分发。

- `rbac-distributed-service-node` 模块重构。
  - 将 `rbac-distributed-service-node` 调整为聚合模块。
  - 新建 `rbac-distributed-service-node-all-he` 模块，迁移项目原有内容，并形成项目的 `he` 构型。
  - 其它相关文件路径、包路径、配置路径等同步调整。

- 配置命名空间全局唯一化改造。
  - 为 `rbac-distributed-service-impl` 模块配置键统一增加全球唯一前缀，以消除跨微服务同名配置冲突。
  - 为 `rbac-distributed-service-node` 模块配置键统一增加全球唯一前缀，以消除跨微服务同名配置冲突。
  - 处理 `redis/prefix.properties` 缓存前缀配置的实体命名空间。
  - 同步调整 `*.java` 中的配置读取占位符。
  - 同步调整 `*application-context-*.xml` 中的配置读取占位符。

- 部分代理类实现中的字段类型提升为对应的接口，与具体实现解耦。
  - com.dwarfeng.rbacds.impl.cache.FilterSupportCacheImpl。
  - com.dwarfeng.rbacds.impl.cache.PermissionCacheImpl。
  - com.dwarfeng.rbacds.impl.cache.PermissionGroupCacheImpl。
  - com.dwarfeng.rbacds.impl.cache.PexpCacheImpl。
  - com.dwarfeng.rbacds.impl.cache.RoleCacheImpl。
  - com.dwarfeng.rbacds.impl.cache.RoleUserRelationCacheImpl。
  - com.dwarfeng.rbacds.impl.cache.ScopeCacheImpl。
  - com.dwarfeng.rbacds.impl.cache.UserCacheImpl。
  - com.dwarfeng.rbacds.impl.dao.FilterSupportDaoImpl。
  - com.dwarfeng.rbacds.impl.dao.PermissionDaoImpl。
  - com.dwarfeng.rbacds.impl.dao.PermissionGroupDaoImpl。
  - com.dwarfeng.rbacds.impl.dao.PexpDaoImpl。
  - com.dwarfeng.rbacds.impl.dao.RoleDaoImpl。
  - com.dwarfeng.rbacds.impl.dao.RoleUserRelationDaoImpl。
  - com.dwarfeng.rbacds.impl.dao.ScopeDaoImpl。
  - com.dwarfeng.rbacds.impl.dao.UserDaoImpl。
  - com.dwarfeng.rbacds.impl.service.FilterSupportMaintainServiceImpl。
  - com.dwarfeng.rbacds.impl.service.PermissionGroupMaintainServiceImpl。
  - com.dwarfeng.rbacds.impl.service.PermissionMaintainServiceImpl。
  - com.dwarfeng.rbacds.impl.service.PexpMaintainServiceImpl。
  - com.dwarfeng.rbacds.impl.service.RoleMaintainServiceImpl。
  - com.dwarfeng.rbacds.impl.service.RoleUserRelationMaintainServiceImpl。
  - com.dwarfeng.rbacds.impl.service.ScopeMaintainServiceImpl。
  - com.dwarfeng.rbacds.impl.service.UserMaintainServiceImpl。

- 优化项目的异常处理机制。
  - `rbac-distributed-service-sdk` 子模块新增 `ServiceExceptionHelper` 工具类，统一维护项目自身的异常映射关系。
  - `rbac-distributed-service-impl` 子模块 `ServiceExceptionMapperConfiguration` 配置类的异常映射处理逻辑优化。
  - `rbac-distributed-service-node` 子模块 `ServiceExceptionMapperConfiguration` 配置类的异常映射处理逻辑优化。

- 依赖升级。
  - 升级 `spring-terminator` 依赖版本为 `2.0.0.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `spring-telqos` 依赖版本为 `2.0.0.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `dwarfeng-datamark` 依赖版本为 `2.1.0.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `jedis` 依赖版本为 `3.10.0` 以规避漏洞。
  - 升级 `spring-data-redis` 依赖版本为 `2.7.18` 以规避漏洞。
  - 升级 `dubbo` 依赖版本为 `2.7.23` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.9.5` 以规避漏洞。
  - 升级 `log4j2` 依赖版本为 `2.25.4` 以规避漏洞。
  - 升级 `mapstruct` 依赖版本为 `1.5.5.Final` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `0.4.2.a-beta` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.8.2.a` 以规避漏洞。

- 优化文件格式。
  - 优化 `*.properties` 文件的格式。
  - 优化 `application-context-*.xml` 文件的格式。

- 优化开发环境支持。
  - 在 .gitignore 中添加 Vibe Coding 相关文件的忽略规则。

### Bug 修复

- (无)

### 功能移除

- 移除 `rbac-distributed-service-api` 子模块中不需要的测试目录。

- 移除部分 `properties` 文件中的无用的配置项。
  - src/test/resources/redis/prefix.properties。

---

## 更早的版本

[View all changelogs](./changelogs)

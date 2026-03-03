# ChangeLog

## Release_2.0.0_20251117_build_A

### 功能构建

- 重置功能新增。
  - com.dwarfeng.rbacds.stack.handler.Resetter 新增重置分析结果功能。
  - com.dwarfeng.rbacds.stack.handler.PushHandler 增加分析结果重置推送事件。
  - com.dwarfeng.rbacds.impl.service.telqos.ResetCommand 增加分析结果重置指令选项。

- 优化部分 telqos 指令中的选项名称。
  - com.dwarfeng.rbacds.impl.service.telqos.ResetCommand。

- 新增查询服务。
  - com.dwarfeng.rbacds.stack.service.InspectService。

- 新增 telqos 指令。
  - com.dwarfeng.rbacds.impl.service.telqos.InspectCommand。
  - com.dwarfeng.rbacds.impl.service.telqos.PermissionUserAnalysisLocalCacheCommand。
  - com.dwarfeng.rbacds.impl.service.telqos.ScopedRolePermissionAnalysisLocalCacheCommand。
  - com.dwarfeng.rbacds.impl.service.telqos.ScopedUserPermissionAnalysisLocalCacheCommand。
  - com.dwarfeng.rbacds.impl.service.telqos.ScopePermissionAnalysisLocalCacheCommand。
  - com.dwarfeng.rbacds.impl.service.telqos.UserRoleAnalysisLocalCacheCommand。
  - com.dwarfeng.rbacds.impl.service.telqos.PermissionCommand。
  - com.dwarfeng.rbacds.impl.service.telqos.PermissionGroupCommand。
  - com.dwarfeng.rbacds.impl.service.telqos.PexpCommand。

- 新增实体操作服务。
  - com.dwarfeng.rbacds.stack.service.PermissionGroupOperateService。
  - com.dwarfeng.rbacds.stack.service.PermissionOperateService。
  - com.dwarfeng.rbacds.stack.service.PexpOperateService。

- 优化部分单元测试代码，以规避潜在的 bug。
  - com.dwarfeng.rbacds.impl.service.FilterSupportMaintainServiceImplTest。
  - com.dwarfeng.rbacds.impl.service.RoleMaintainServiceImplTest。
  - com.dwarfeng.rbacds.impl.service.UserMaintainServiceImplTest。

- 修改部分 `FilterRegistry` 的类名以及代码，以适配实体字段的变更。
  - com.dwarfeng.rbacds.impl.handler.filter.DirectSubGroupFilterRegistry。
  - com.dwarfeng.rbacds.impl.handler.filter.ExactFilterRegistry。
  - com.dwarfeng.rbacds.impl.handler.filter.IdPrefixFilterRegistry。
  - com.dwarfeng.rbacds.impl.handler.filter.IdRegexFilterRegistry。
  - com.dwarfeng.rbacds.impl.handler.filter.NestedSubGroupFilterRegistry。

- 修改实体字段以及相关业务逻辑，并通过单元测试。
  - com.dwarfeng.rbacds.stack.bean.entity.Permission。
  - com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup。
  - com.dwarfeng.rbacds.stack.bean.entity.Pexp。

- 添加实体以及维护服务，并通过单元测试。
  - com.dwarfeng.rbacds.stack.bean.entity.Scope。
  - com.dwarfeng.rbacds.stack.bean.entity.RoleUserRelation。

- 重构权限表达式处理器输出结构。
  - 将 `PexpHandler` 的 `PermissionReception` 枚举重命名为 `Reception` 并同步相关接口。
  - 将 `testAll` 返回值改为按输入顺序输出的 `Reception` 列表，便于调用方直接映射结果。

- 重构过滤器及其相关机制命名，将 `PermissionFilter` 重命名为 `Filter`。
  - 重命名相关实体及其维护服务。
  - 重命名相关功能方法名称。
  - 重命名相关配置键。
  - 重命名相关文档注释及代码注释。

- 优化部分单元测试的代码中的测试实体的主键，以规避潜在的与正式数据冲突的问题。
  - com.dwarfeng.rbacds.impl.service.PexpMaintainServiceImplTest。
  - com.dwarfeng.rbacds.impl.service.RoleMaintainServiceImplTest。
  - com.dwarfeng.rbacds.impl.service.UserMaintainServiceImplTest。

- 增加预设的运维指令。
  - com.dwarfeng.springtelqos.api.integration.system.UptimeCommand。
  - com.dwarfeng.springtelqos.api.integration.system.JmxRemoteCommand。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.7.2.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `snowflake` 依赖版本为 `1.8.2.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.15.a` 以规避漏洞。
  - 升级 `dwarfeng-datamark` 依赖版本为 `1.1.1.a` 以规避漏洞。

### Bug 修复

- 修复部分配置项在部分场景下未生效的 bug。
  - `database/performance.properties` 中的 `hibernate.jdbc.batch_size` 配置项。

### 功能移除

- 移除角色实体和用户实体的多对多关联及其相关功能。

- 移除旧的权限查询功能使用的缓存。
  - com.dwarfeng.rbacds.stack.cache.PermissionUserCache。
  - com.dwarfeng.rbacds.stack.cache.UserPermissionCache。
  - com.dwarfeng.rbacds.stack.cache.RolePermissionCache。

- 移除无用的缓存。
  - com.dwarfeng.rbacds.stack.cache.PermissionListCache。

- 移除部分功能性服务。
  - com.dwarfeng.rbacds.stack.service.PermissionLookupService。
  - com.dwarfeng.rbacds.stack.service.UserLookupService。

---

## 更早的版本

[View all changelogs](./changelogs)

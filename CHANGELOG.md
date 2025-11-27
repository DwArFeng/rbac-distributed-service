# ChangeLog

## Release_2.0.0_20251117_build_A

### 功能构建

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

- (无)

### 功能移除

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

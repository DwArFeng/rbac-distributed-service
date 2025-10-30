# ChangeLog

## Release_2.0.0_20251117_build_A

### 功能构建

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

- (无)

---

## 更早的版本

[View all changelogs](./changelogs)

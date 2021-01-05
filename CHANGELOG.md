# ChangeLog

### Release_1.2.0_20210105_build_A

#### 功能构建

- 添加实体或增加实体字段
  - com.dwarfeng.rbacds.stack.bean.entity.Permission
  - com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup
  - com.dwarfeng.rbacds.stack.bean.entity.Role
  - com.dwarfeng.rbacds.stack.bean.entity.RoleGroup
- com.dwarfeng.rbacds.impl.handler.PermissionFilter 接口优化。
- 更改、新增 com.dwarfeng.rbacds.impl.handler.PermissionFilter 实现。
  - com.dwarfeng.rbacds.impl.handler.preset.DirectSubGroupPermissionFilter
  - com.dwarfeng.rbacds.impl.handler.preset.IdPrefixPermissionFilter
  - com.dwarfeng.rbacds.impl.handler.preset.IdRegexPermissionFilter
  - com.dwarfeng.rbacds.impl.handler.preset.NameRegexPermissionFilter
  - com.dwarfeng.rbacds.impl.handler.preset.NestedSubGroupPermissionFilter

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

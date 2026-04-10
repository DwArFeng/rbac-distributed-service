# Permission Model - 权限模型

## 说明

本文基于项目实体定义以及权限表达式相关实现代码编写。

本文用于说明本项目的权限模型、实体关系、关键约束与权限计算流程。

## 大纲

1. 核心实体。
2. 实体关系与约束。
3. 权限表达式机制。
4. 用户权限计算流程。
5. 缓存与重置机制。

## 核心实体

本项目的权限模型由以下核心实体组成：

| 实体                 | Stack 主键                                                     | 关键字段                                               | 说明                              |
|:-------------------|:-------------------------------------------------------------|:---------------------------------------------------|:--------------------------------|
| `Scope`            | `StringIdKey`                                                | `name`, `remark`                                   | 作用域，用于隔离权限与权限组。                 |
| `User`             | `StringIdKey`                                                | `remark`                                           | 用户。                             |
| `Role`             | `StringIdKey`                                                | `name`, `enabled`, `remark`                        | 角色，`enabled` 决定角色是否生效。          |
| `RoleUserRelation` | `RoleUserRelationKey(roleStringId, userStringId)`            | `enabled`, `remark`                                | 用户与角色的关联关系，`enabled` 决定该关联是否生效。 |
| `PermissionGroup`  | `PermissionGroupKey(scopeStringId, permissionGroupStringId)` | `parentKey`, `name`, `remark`                      | 权限组，支持同作用域内树形结构。                |
| `Permission`       | `PermissionKey(scopeStringId, permissionStringId)`           | `groupKey`, `name`, `level`, `groupPath`, `remark` | 权限，`groupPath` 为冗余路径字段。         |
| `Pexp`             | `PexpKey(scopeStringId, roleStringId, pexpStringId)`         | `content`, `remark`                                | 权限表达式，定义角色对权限的匹配规则。             |
| `FilterSupport`    | `StringIdKey`                                                | `label`, `description`, `examplePattern`           | 过滤器支持信息，用于描述可用过滤器类型。            |

## 实体关系与约束

### 关系

核心关系如下：

| 关系                                    | 类型                         | 说明              |
|:--------------------------------------|:---------------------------|:----------------|
| `User` - `Role`                       | 多对多（通过 `RoleUserRelation`） | 用户拥有角色。         |
| `Role` - `Pexp`                       | 一对多                        | 一个角色可配置多个权限表达式。 |
| `Scope` - `PermissionGroup`           | 一对多                        | 权限组按作用域隔离。      |
| `Scope` - `Permission`                | 一对多                        | 权限按作用域隔离。       |
| `Scope` - `Pexp`                      | 一对多                        | 权限表达式按作用域隔离。    |
| `PermissionGroup` - `PermissionGroup` | 一对多（自关联）                   | 权限组父子结构。        |
| `PermissionGroup` - `Permission`      | 一对多                        | 一个权限组可包含多个权限。   |

### 业务约束

操作处理器实现了以下业务约束：

1. 创建/更新权限组时，如果指定 `parentKey`，父权限组必须存在，且必须与当前权限组处于同一作用域。
2. 创建/更新权限时，如果指定 `groupKey`，权限组必须存在，且必须与权限主键中的作用域一致。
3. 创建/更新权限时，`groupPath` 会根据权限组父链重新计算，路径顺序为“根组 -> 当前组”。
4. 创建权限表达式时，要求作用域存在、角色存在、权限表达式主键不存在。
5. 用户有效角色查询要求同时满足：`Role.enabled = true` 且 `RoleUserRelation.enabled = true`。

### 删除行为

项目的删除策略并非全部依赖数据库级联，而是在业务层显式处理：

1. 删除作用域时，会先删除该作用域下权限、权限组、权限表达式，再删除作用域自身。
2. 删除角色时，会删除该角色下权限表达式与角色用户关系，再删除角色自身。
3. 删除用户时，会删除该用户下角色用户关系，再删除用户自身。
4. 删除权限组时，不会级联删除子权限组和权限，而是清空它们的关联键（`parentKey` 或 `groupKey`），再删除权限组自身。

## 权限表达式机制

### 解析机制

权限表达式由 `PexpParseHandler` 解析。解析器选择规则如下：

1. 以 `v1;` 开头，按 V1 解析。
2. 以 `v2;` 开头，按 V2 解析。
3. 没有版本前缀，默认按 V1 解析（兼容旧数据）。

V1 与 V2 的核心语法如下：

```text
V1: [v1;]<+|-|!><type>@<pattern>
V2: [v2;]<+|-|!><type>@<pattern>[\|<type>@<pattern>...]
```

在其中：

- `+` 对应 `ACCEPT`。
- `-` 对应 `REJECT`。
- `!` 对应 `GLOBAL_REJECT`。

V2 额外支持管道与转义：

- 管道符 `|` 表示多个过滤器单元。
- `\` 可转义 `\`、`@`、`|`。
- 过滤器类型与匹配模式都支持转义语义。

### 执行机制

`PexpHandler` 的执行流程如下：

1. 解析 `Pexp.content`，得到 `modifier + pipe`。
2. 对管道中的每个单元，按 `filterType` 从 `FilterLocalCacheHandler` 取得过滤器实例。
3. 同一条表达式内，所有过滤器单元是“逻辑与”关系。
4. 只要有一个过滤器单元不通过，结果为 `NOT_ACCEPT`。
5. 如果全部通过，则按修饰符返回 `ACCEPT`、`REJECT` 或 `GLOBAL_REJECT`。

### 过滤器装载与支持信息

过滤器注册器位于 `impl.handler.filter` 包，默认由 `application-context-scan.xml` 排除。

如需启用过滤器，需要在 `opt/opt-filter.xml` 中取消对应 `include-filter` 的注释。

启用后，可通过 `SupportHandler.resetFilter()` 将当前注册器能力写入 `tbl_filter_support`。

项目内置过滤器类型如下：

| 类型                 | 语义                          |
|:-------------------|:----------------------------|
| `exact`            | 权限 ID 精确匹配。                 |
| `id_prefix`        | 权限 ID 前缀匹配。                 |
| `id_regex`         | 权限 ID 正则匹配。                 |
| `name_regex`       | 权限名称正则匹配。                   |
| `level`            | 按等级比较（`eq/ne/gt/ge/lt/le`）。 |
| `direct_sub_group` | 仅匹配“直接属于指定权限组”的权限。          |
| `nested_sub_group` | 匹配“属于指定权限组或其任意子组”的权限。       |

## 用户权限计算流程

权限计算由三层分析结果组合完成：

1. `ScopePermissionAnalysis`：给定作用域，得到该作用域全部权限列表。
2. `ScopedRolePermissionAnalysis`：给定作用域与角色，计算该角色在该作用域下的权限接受情况。
3. `ScopedUserPermissionAnalysis`：给定作用域与用户，合并该用户有效角色的分析结果。

### 角色维度合并规则

对于一个角色：

1. 遍历该角色的全部权限表达式。
2. 每条表达式对作用域权限全集执行 `testAll`。
3. 分别收集 `acceptedPermissionMap`、`rejectedPermissionMap`、`globalRejectedPermissionMap`。
4. 角色最终匹配权限：

```text
matched = accepted - rejected - globalRejected
```

### 用户维度合并规则

对于一个用户：

1. 先查出用户有效角色（关系启用且角色启用）。
2. 对每个角色取其 `ScopedRolePermissionAnalysis`。
3. 对每个角色先做局部差集：`accepted - rejected`，再并入用户接受集合。
4. 汇总所有角色的 `globalRejected` 为全局拒绝集合。
5. 用户最终权限：

```text
matched = union(roleAcceptedMinusRejected) - union(roleGlobalRejected)
```

该规则保证了 `GLOBAL_REJECT` 具有跨角色生效能力。

## 缓存与重置机制

权限模型涉及两类缓存：

1. 实体缓存（如 `User/Role/Pexp/Permission`），由实体维护服务读写时使用。
2. 分析缓存（如作用域权限分析、角色权限分析、用户权限分析），用于加速权限计算。

分析缓存包括：

| 缓存                                       | 键               | 值                              |
|:-----------------------------------------|:----------------|:-------------------------------|
| `ScopePermissionAnalysisLocalCache`      | `scopeKey`      | `ScopePermissionAnalysis`      |
| `ScopedRolePermissionAnalysisLocalCache` | `scopedRoleKey` | `ScopedRolePermissionAnalysis` |
| `UserRoleAnalysisLocalCache`             | `userKey`       | `UserRoleAnalysis`             |
| `ScopedUserPermissionAnalysisLocalCache` | `scopedUserKey` | `ScopedUserPermissionAnalysis` |
| `PermissionUserAnalysisLocalCache`       | `permissionKey` | `PermissionUserAnalysis`       |

其中：

- `UserRoleAnalysis`、`ScopedUserPermissionAnalysis`、`PermissionUserAnalysis` 使用带 TTL 的可过期本地缓存。
- TTL 与清理间隔由 `conf/rbacds/local-cache.properties` 配置。

重置机制：

1. `resetFilter` 会清空过滤器本地缓存。
2. `resetAnalysis` 会清空全部分析本地缓存。

## 参阅

- [Pexp General](./PexpGeneral.md) - 权限表达式通用概念。
- [Pexp V1](./PexpV1.md) - 权限表达式 V1 语法。
- [Pexp V2](./PexpV2.md) - 权限表达式 V2 语法。
- [Opt Directory](./OptDirectory.md) - 可选配置，特别是 `opt-filter.xml`。
- [Conf Directory](./ConfDirectory.md) - 本地缓存与启动参数配置。

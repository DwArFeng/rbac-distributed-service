# ChangeLog

### Release_1.3.2_20200512_build_A

#### 功能构建

- 完善@Transactional注解的回滚机制。
- 更改项目的打包名称。
- 更新README.md。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.3.1_20200410_build_A

#### 功能构建

- 更改优化node模块的程序结构。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.3.0_20200315_build_A

#### 功能构建

- 更改PEXP表达式的计算方法，现支持权限的角色内排除和全局排除两种排除方式。
- 更改PEXP表达式的权限分析架构，现在更容易扩展权限表达式了。
- 优化数据库连接配置，提高数据库访问效率。
- 升级 subgrade 版本为 beta-0.3.1.c。
- 更改启动器为 spring-terminator 风格。

#### Bug修复

- 修复maven install时的报警。

#### 功能移除

- (无)

---

### Release_1.2.4_20200301_build_A

#### 功能构建

- 升级subgrade版本为beta-0.3.0.a。
- 升级snowflake版本为1.2.2.a。
- FastJson实体of方法增加空值判断。

#### Bug修复

- 修正冲突的依赖并去掉无用的依赖。
- 修正单元测试中的dubbo服务的名称错误。

#### 功能移除

- (无)

---

### Release_1.2.3_20200222_build_A

#### 功能构建

- 升级subgrade版本为beta-0.2.4.a，以便依赖更好的RelationDao。

#### Bug修复

- 修复PermissionMaintainServiceImpl.lookup方法返回结果不正常的bug。

#### 功能移除

- (无)

---

### Release_1.2.2_20200218_build_B

#### 功能构建

- (无)

#### Bug修复

- 优化实体服务的预设查询样式。

#### 功能移除

- (无)

---

### Release_1.2.2_20200218_build_A

#### 功能构建

- 为FastJson相关的对象添加 of 静态方法。

#### Bug修复

- 升级subgrade项目版本为beta-0.2.3.a以修复PagedData对象中字段拼写错误bug。
- 删除pom.xml关于项目本身过时的引用。

#### 功能移除

- (无)

---

### Release_1.2.1_20200216_build_B

#### 功能构建

- (无)

#### Bug修复

- 升级subgrade项目版本为beta-0.2.2.b以修复查询对象的总页数错误bug。

#### 功能移除

- (无)

---

### Release_1.2.1_20200216_build_A

#### 功能构建

- 升级subgrade依赖至beta-0.2.2.a。
- 补充部分实体的查询方法。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.2.0_20200215_build_C

#### 功能构建

- snowflake依赖升级至1.2.0.a。

#### Bug修复

- 修复ExceptionCodeOffsetConfiguration配置中的错误。
- 移除PermissionLookupServiceImpl.java代码中未使用的局部变量。
- 升级subgrade至beta-0.2.1.a以避免bug。

#### 功能移除

- (无)

---

### Release_1.2.0_20200213_build_B

#### 功能构建

- 为dubbo框架配置dubbo.host属性。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.2.0_20200213_build_A

#### 功能构建

- ExceptionCode引入偏移量。
- subgrade版本变更为beta-0.2.0.b。
- ExceptionCode偏移量可配置化。

#### Bug修复

- 修改pom.xml中不正确的坐标引用。
- 修正node模块中不正确的装配配置。

#### 功能移除

- ~~工程中关于密码验证与登录的功能。~~

---

### Release_1.0.0_20200203_build_A

#### 功能构建

- 工程全目标实现。

#### Bug修复

- (无)

#### 功能移除

- (无)

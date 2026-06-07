# Install to CentOS - 在 CentOS 上安装

## 确认系统需求

请参考 [SystemRequirements.md](./SystemRequirements.md) 确认系统需求。

## 获取二进制文件

### 从发布包中获取

从 Github 上获取软件包，软件包可以从 Github 的 Release 页面下载。

### 从源码编译

请参考 [CompileBySource.md](./CompileBySource.md) 从源码编译。

## 解压软件包

交付用的发布包汇总到 `rbac-distributed-service-distribute` 模块下的 `target/distribute/` 目录中， 按节点分子目录存放；
功能、构型与制品路径说明见 [CompileBySource.md](./CompileBySource.md)。

以节点 `rbac-all-he` 为例， 软件包的名称格式为 `rbac-all-he-${version}-release.tar.gz`，
其中 `${version}` 为软件包的版本号。
若使用其它功能与构型组合的节点，压缩包及解压目录名亦遵循相同规则。

使用工具软件，将软件包上传至服务器 `/usr/local` 目录下，解压软件包。

```shell
cd /usr/local
tar -zxvf rbac-all-he-${version}-release.tar.gz
mv rbac-all-he-${version}-release rbac
```

## 更改启停脚本

请参考 [ShellScripts.md](./ShellScripts.md) 更改启停脚本。

## 更改配置

请参考 [ConfDirectory.md](./ConfDirectory.md) 更改配置。

## 选择可选功能

请参考 [OptDirectory.md](./OptDirectory.md) 选择可选功能。

## 注册服务

运行如下命令，注册服务。

```shell
cat <<EOF | sudo tee /usr/lib/systemd/system/rbac.service
[Unit]
Description=RBAC Distributed Service
After=syslog.target network.target

[Service]
User=root
Group=root
Type=forking
ExecStart=/usr/local/rbac/bin/rbac-start.sh
ExecStop=/usr/local/rbac/bin/rbac-stop.sh
PrivateTmp=true

[Install]
WantedBy=multi-user.target

EOF
```

*注意：如果 RBAC 安装在其它位置，则需要修改 `ExecStart` 和 `ExecStop` 的路径。*

运行 daemon-reload。

```shell
sudo systemctl daemon-reload
```

设置开机启动。

```shell
sudo systemctl enable rbac
```

## 启动服务

启动服务。

```shell
sudo systemctl start rbac
```

监视日志。

```shell
tail -f /var/log/rbac/info.log
```

*注意：如果 RBAC 安装在其它位置，则需要修改监视路径。*

查看服务状态。

```shell
sudo systemctl status rbac
```

## 参阅

- [Compile by Source](./CompileBySource.md) - 源码编译，详细说明如何从源码编译本项目。
- [Shell Scripts](./ShellScripts.md) - 脚本说明，详细说明了本项目的 Shell 脚本，即 `bin/` 目录下 `*.sh` 文件的用法。
- [Conf Directory](./ConfDirectory.md) - 配置目录说明，详细说明如何配置本项目，即 `conf/` 目录下的内容。
- [Opt Directory](./OptDirectory.md) - 可选配置目录说明，详细介绍了本项目的可选配置，即 `opt/` 目录下的内容。

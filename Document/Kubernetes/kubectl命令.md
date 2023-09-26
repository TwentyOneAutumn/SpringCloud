#  Kubectl命令

### 基本命令

| command   | 说明                                                         |
| --------- | ------------------------------------------------------------ |
| `create`  | 从文件或标准输入创建资源                                     |
| `expose`  | 将复制控制器、服务、部署或 Pod 暴露为新的 Kubernetes 服务    |
| `run`     | 在集群上运行特定镜像                                         |
| `set`     | 在对象上设置特定的功能                                       |
| `explain` | 获取资源的文档                                               |
| `get`     | 显示一个或多个资源                                           |
| `edit`    | 在服务器上编辑资源                                           |
| `delete`  | 通过文件名、标准输入、资源和名称，或通过资源和标签选择器删除资源 |

### 部署命令

| command     | 说明                                       |
| ----------- | ------------------------------------------ |
| `rollout`   | 管理资源的滚动升级                         |
| `scale`     | 为部署、副本集或复制控制器设置新的大小     |
| `autoscale` | 自动扩展部署、副本集、有状态集或复制控制器 |

### 集群管理命令

| command        | 说明                         |
| -------------- | ---------------------------- |
| `certificate`  | 修改证书资源                 |
| `cluster-info` | 显示集群信息                 |
| `top`          | 显示资源（CPU/内存）使用情况 |
| `cordon`       | 将节点标记为不可调度         |
| `uncordon`     | 将节点标记为可调度           |
| `drain`        | 准备维护前的节点排放         |
| `taint`        | 更新一个或多个节点的污点     |

### 排除故障和调试命令

| command        | 说明                                 |
| -------------- | ------------------------------------ |
| `describe`     | 显示特定资源或一组资源的详细信息     |
| `logs`         | 打印 Pod 中容器的日志                |
| `attach`       | 附加到运行中的容器                   |
| `exec`         | 在容器中执行命令                     |
| `port-forward` | 将一个或多个本地端口转发到一个 Pod   |
| `proxy`        | 运行一个代理到 Kubernetes API 服务器 |
| `cp`           | 复制文件和目录到和从容器中           |
| `auth`         | 检查授权                             |
| `debug`        | 为故障排除工作负载和节点创建调试会话 |

### 高级命令

| command     | 说明                                     |
| ----------- | ---------------------------------------- |
| `diff`      | 将实时版本与将要应用的版本进行比较       |
| `apply`     | 通过文件名或标准输入将配置应用到资源     |
| `patch`     | 更新资源的字段                           |
| `replace`   | 通过文件名或标准输入替换资源             |
| `wait`      | 实验性功能：等待一个或多个资源的特定条件 |
| `kustomize` | 从目录或 URL 构建 kustomization 目标     |

### 设置命令

| command      | 说明                                             |
| ------------ | ------------------------------------------------ |
| `label`      | 更新资源的标签                                   |
| `annotate`   | 更新资源的注释                                   |
| `completion` | 为指定的 shell（bash 或 zsh）输出 shell 完成代码 |

### 其他命令

| command         | 说明                                                   |
| --------------- | ------------------------------------------------------ |
| `api-resources` | 打印服务器上支持的 API 资源                            |
| `api-versions`  | 打印服务器上支持的 API 版本，以 "group/version" 的形式 |
| `config`        | 修改 kubeconfig 文件                                   |
| `plugin`        | 提供与插件交互的实用工具                               |
| `version`       | 打印客户端和服务器版本信息                             |




> 最后，使用 `kubectl [flags] [options]` 来执行具体的命令，可以使用 `kubectl <command> --help` 获取有关特定命令的更多信息，也可以使用 `kubectl options` 查看全局命令行选项列表



### create

##### 命令

| command             | 说明                                                         |
| ------------------- | ------------------------------------------------------------ |
| clusterrole         | 创建一个集群角色（ClusterRole）                              |
| clusterrolebinding  | 为特定集群角色（ClusterRole）创建集群角色绑定（ClusterRoleBinding） |
| configmap           | 从本地文件、目录或文字值创建配置映射（ConfigMap）            |
| cronjob             | 创建具有指定名称的定时作业（CronJob）                        |
| deployment          | 创建具有指定名称的部署（Deployment）                         |
| ingress             | 创建具有指定名称的入口（Ingress）                            |
| job                 | 创建具有指定名称的作业（Job）                                |
| namespace           | 创建具有指定名称的命名空间（Namespace）                      |
| poddisruptionbudget | 创建具有指定名称的Pod中断预算（PodDisruptionBudget）         |
| priorityclass       | 创建具有指定名称的优先级类别（PriorityClass）                |
| quota               | 创建具有指定名称的配额（Quota）                              |
| role                | 创建带有单个规则的角色（Role）                               |
| rolebinding         | 为特定角色或集群角色（Role/ClusterRole）创建角色绑定（RoleBinding） |
| secret              | 使用指定的子命令创建秘密（Secret）                           |
| service             | 使用指定的子命令创建服务（Service）                          |
| serviceaccount      | 创建具有指定名称的服务账户（ServiceAccount）                 |

##### 选项

| option                        | 说明                                                         | default        |
| ----------------------------- | ------------------------------------------------------------ | -------------- |
| --allow-missing-template-keys | 如果为`true`，则在模板中缺少字段或映射键时忽略任何错误，仅适用于golang和jsonpath输出格式 | true           |
| --dry-run                     | 必须是`none` `server` `client`，如果选择客户端策略，则仅打印将要发送的对象，而不发送它。如果选择服务器策略，则提交服务器端请求而不保存资源 | none           |
| --edit                        | 在创建之前编辑API资源                                        | false          |
| --field-manager               | 用于跟踪字段所有权的管理器的名称                             | kubectl-create |
| -f                            | 要用于创建资源的文件名、目录或URL                            | []             |
| -k                            | 处理kustomization目录,此标志不能与`-f` 或 `-R`一起使用       | ''             |
| -o                            | 输出格式,可选的格式有`json` `yaml` `name` `go-template` `go-template-file` `template` `templatefile` `jsonpath` `jsonpath-as-json` `jsonpath-file` | ''             |
| --raw                         | 发送到服务器的原始URI,使用kubeconfig文件指定的传输           | ''             |
| -R                            | 递归处理`-f` `--filename`中使用的目录,当您想要管理同一目录中组织的相关清单时很有用 | false          |
| --save-config                 | 如果为true,则当前对象的配置将保存在其注释中,否则注释将不会更改,当您想要在将来对此对象执行kubectl apply时，此标志很有用 | false          |
| -l                            | 标签选择器,支持`=` `==` `!=`,例如-l key1=value1,key2=value2） | ''             |
| --show-managed-fields         | 如果为true,在以JSON或YAML格式打印对象时保留managedFields     | false          |
| --template                    | 用于`-o=go-template` `-o=go-template-file`时使用的模板字符串或模板文件路径,模板格式是Golang模板`http://golang.org/pkg/text/template/#pkg-overview` | ''             |
| --validate                    | 如果为true，在发送输入之前使用模式验证输入                   | true           |
| --windows-line-endings        | 仅在`--edit=true`时相关,默认为您平台的本地换行符             | False          |


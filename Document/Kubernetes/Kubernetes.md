# Kubernetes



## Kubernetes 组件

![Kubernetes 组件](../Img/kubernetes/components-of-kubernetes.svg)

------

### 控制平面组件（Control Plane Components）

> 控制平面组件会为集群做出全局决策，比如资源的调度。 以及检测和响应集群事件，例如当不满足部署的 `replicas` 字段时， 要启动新的 [Pod](https://kubernetes.io/zh-cn/docs/concepts/workloads/pods/)
>

------

#### kube-apiserve

> API Server是 Kubernetes [控制平面](https://kubernetes.io/zh-cn/docs/reference/glossary/?all=true#term-control-plane)的组件， 该组件负责公开了 Kubernetes API（`Rest风格`），负责处理接受请求的工作， API Server是 Kubernetes 控制平面的前端 
>
> Kubernetes API 服务器的主要实现是 [kube-apiserver](https://kubernetes.io/zh-cn/docs/reference/command-line-tools-reference/kube-apiserver/)，`kube-apiserver`设计上考虑了水平扩缩，也就是说，它可通过部署多个实例来进行扩缩， 你可以运行 `kube-apiserver` 的多个实例，并在这些实例之间平衡流量

------

#### etcd

> 一致且高可用的键值存储，用作 Kubernetes 所有集群数据的后台数据库
>
> 如果你的 Kubernetes 集群使用 etcd 作为其后台数据库， 请确保你针对这些数据有一份 [备份](https://kubernetes.io/zh-cn/docs/tasks/administer-cluster/configure-upgrade-etcd/#backing-up-an-etcd-cluster)计划
>
> 你可以在[官方文档](https://etcd.io/docs/)中找到有关 etcd 的深入知识

------

#### kube-scheduler

> `kube-scheduler` 是[控制平面](https://kubernetes.io/zh-cn/docs/reference/glossary/?all=true#term-control-plane)的组件， 负责监视新创建的、未指定运行[Node](https://kubernetes.io/zh-cn/docs/concepts/architecture/nodes/)的 [Pods](https://kubernetes.io/zh-cn/docs/concepts/workloads/pods/)， 并选择节点来让 Pod 在上面运行
>
> 调度决策考虑的因素包括单个 Pod 及 Pods 集合的资源需求、软硬件及策略约束、 亲和性及反亲和性规范、数据位置、工作负载间的干扰及最后时限

------

#### kube-controller-manager

> [kube-controller-manager](https://kubernetes.io/zh-cn/docs/reference/command-line-tools-reference/kube-controller-manager/) 是[控制平面](https://kubernetes.io/zh-cn/docs/reference/glossary/?all=true#term-control-plane)的组件， 负责运行[控制器](https://kubernetes.io/zh-cn/docs/concepts/architecture/controller/)进程
>
> 从逻辑上讲， 每个[控制器](https://kubernetes.io/zh-cn/docs/concepts/architecture/controller/)都是一个单独的进程， 但是为了降低复杂性，它们都被编译到同一个可执行文件，并在同一个进程中运行

有许多不同类型的控制器，以下是一些例子：

- 节点控制器（Node Controller）：负责在节点出现故障时进行通知和响应
- 任务控制器（Job Controller）：监测代表一次性任务的 Job 对象，然后创建 Pods 来运行这些任务直至完成
- 端点分片控制器（EndpointSlice controller）：填充端点分片（EndpointSlice）对象（以提供 Service 和 Pod 之间的链接）
- 服务账号控制器（ServiceAccount controller）：为新的命名空间创建默认的服务账号（ServiceAccount）

以上并不是一个详尽的列表

------

#### cloud-controller-manager

> 一个 Kubernetes [控制平面](https://kubernetes.io/zh-cn/docs/reference/glossary/?all=true#term-control-plane)组件， 嵌入了特定于云平台的控制逻辑。 云控制器管理器（Cloud Controller Manager）允许你将你的集群连接到云提供商的 API 之上， 并将与该云平台交互的组件同与你的集群交互的组件分离开来
>
> `cloud-controller-manager` 仅运行特定于云平台的控制器， 因此如果你在自己的环境中运行 Kubernetes，或者在本地计算机中运行学习环境， 所部署的集群不需要有云控制器管理器

与 `kube-controller-manager` 类似，`cloud-controller-manager` 将若干逻辑上独立的控制回路组合到同一个可执行文件中， 供你以同一进程的方式运行。 你可以对其执行水平扩容（运行不止一个副本）以提升性能或者增强容错能力

下面的控制器都包含对云平台驱动的依赖：

- 节点控制器（Node Controller）：用于在节点终止响应后检查云提供商以确定节点是否已被删除
- 路由控制器（Route Controller）：用于在底层云基础架构中设置路由
- 服务控制器（Service Controller）：用于创建、更新和删除云提供商负载均衡器

------

### Node 组件

> 节点组件会在每个节点上运行，负责维护运行的 Pod 并提供 Kubernetes 运行环境

------

#### kubelet

> `kubelet` 会在集群中每个[节点（node）](https://kubernetes.io/zh-cn/docs/concepts/architecture/nodes/)上运行，，，， 它保证[容器（containers）](https://kubernetes.io/zh-cn/docs/concepts/overview/what-is-kubernetes/#why-containers)都运行在 [Pod](https://kubernetes.io/zh-cn/docs/concepts/workloads/pods/) 中
>
> kubelet 接收一组通过各类机制提供给它的 PodSpecs， 确保这些 PodSpecs 中描述的容器处于运行状态且健康，kubelet 不会管理不是由 Kubernetes 创建的容器

------

#### kube-proxy

> [kube-proxy](https://kubernetes.io/zh-cn/docs/reference/command-line-tools-reference/kube-proxy/) 是集群中每个[节点（node）](https://kubernetes.io/zh-cn/docs/concepts/architecture/nodes/)上所运行的网络代理， 实现 Kubernetes [服务（Service）](https://kubernetes.io/zh-cn/docs/concepts/services-networking/service/) 概念的一部分
>
> kube-proxy 维护节点上的一些网络规则， 这些网络规则会允许从集群内部或外部的网络会话与 Pod 进行网络通信
>
> 如果操作系统提供了可用的数据包过滤层，则 kube-proxy 会通过它来实现网络规则， 否则，kube-proxy 仅做流量转发

------

#### 容器运行时（Container Runtime）

> 容器运行环境是负责运行容器的软件
>
> Kubernetes 支持许多容器运行环境，例如 [containerd](https://containerd.io/docs/)、 [CRI-O](https://cri-o.io/#what-is-cri-o) 以及 [Kubernetes CRI (容器运行环境接口)](https://github.com/kubernetes/community/blob/master/contributors/devel/sig-node/container-runtime-interface.md) 的其他任何实现

------

### 配置文件类型

---

#### kind

| 类型          | 说明                                                         |
| ------------- | ------------------------------------------------------------ |
| `Deployment`  | 用于声明式地管理应用程序的部署，可以自动创建、更新和扩展副本集，一般用于创建无状态应用 |
| `StatefulSet` |                                                              |
| `Service`     |                                                              |
| ``            |                                                              |
| ``            |                                                              |
| ``            |                                                              |
| ``            |                                                              |
| ``            |                                                              |
| ``            |                                                              |

------

#### protocol

| 类型            | 说明                          |
| --------------- | ----------------------------- |
| `TCP (default)` | 表示使用 TCP 协议进行网络通信 |
| `UDP`           | 表示使用 UDP 协议进行网络通信 |

------

#### imagePullPolicy

| 类型                     | 说明                                                         |
| ------------------------ | ------------------------------------------------------------ |
| `IfNotPresent (default)` | 优先使用本地镜像，只有当镜像在本地不存在时才会拉取           |
| `Always`                 | 始终拉取最新版本的镜像，即使本地已经存在镜像，也会尝试拉取最新版本 |
| `Never`                  | 仅使用本地镜像，如果镜像不存在于本地，则会启动失败           |

------

#### restartPolicy

| 类型               | 说明                                                         |
| ------------------ | ------------------------------------------------------------ |
| `Always (default)` | 如果容器终止，无论是正常退出还是出现错误，Kubernetes 都会自动将容器重新启动 |
| `OnFailure`        | 只有当容器终止状态为非零（即出现错误）时，Kubernetes 才会自动将容器重新启动，如果容器正常退出（退出状态为零），它将不会被自动重新启动 |

------

#### 探针

| 类型             | 说明                                                         |
| ---------------- | ------------------------------------------------------------ |
| `livenessProbe`  | 用于确定容器是否仍在运行。当存活探针失败时，Kubernetes 将尝试重新启动容器。这对于检测应用程序或容器内部进程的崩溃或死锁非常有用 |
| `readinessProbe` | 用于确定容器是否已准备好接受流量。当就绪探针失败时，Kubernetes 将停止将流量路由到该容器，直到就绪探针再次成功，这对于确保应用程序在接收流量之前已经初始化或准备好非常有用 |
| `StartupProbe`   | 用于确定容器是否已成功启动。与就绪探针不同，它仅在容器启动时运行一次，如果启动探针失败，Kubernetes 可以尝试重新启动容器 |

------

#### xxx

| 类型 | 说明 |
| ---- | ---- |
|      |      |
|      |      |
|      |      |

------

#### xxx

| 类型 | 说明 |
| ---- | ---- |
|      |      |
|      |      |
|      |      |

------


# Deployment配置



## 配置

```yaml
apiVersion: <kubernetesAPI版本>
kind: deployment
metadata:
	name: Pod的名称
	namespace: Pod的命名空间
	labels:
    key: value
	annotations:
    key: value
spec:
	replicas: Pod数量
	selector:
		matchLables:
      key: value
		matchExpressions:
      - key: key
          operator: In
          values:
            - value1
            - value2
      - key: key
          operator: NotIn
          values:
            - value1
            - value2
      - key: key
          operator: Exists
      - key: key
          operator: DoesNotExist
  template:
  	metadata:
  		name: Pod的名称
      namespace: Pod的命名空间
      labels:
        key: value
      annotations:
        key: value
    spec:
			volumes:
        - name: 空目录存储卷
          emptyDir: {}
        - name: HostPath存储卷
          hostPath:
            path: 路径
        - name:  ConfigMap存储卷
          configMap:
            name: ConfigMap的名称
        - name:  Secret存储卷
          secret:
            secretName: Secret的名称
        - name: PVC存储卷
          persistentVolumeClaim:
            claimName:  PVC存储卷名称
        - name: 
          glusterfs:
            endpoints: gluster-cluster
            path: my-glusterfs-volume
        - name: NFS 存储卷
          nfs:
            server:  服务名
            path: 路径
        - name: PV存储卷
          persistentVolume:
            claimName: PV存储卷名称
        - name: Projected存储卷
          projected:
            sources:
            - configMap:
                name: ConfigMap的名称
            - secret:
                name: Secret的名称
			initContainers:
        - name: 容器名称
          image: 容器镜像
          command: 
            - 命令
          args:
            - 参数
      containers:
        - name: 容器名称
          image: 容器镜像
          ports:
            - name: 名称
              containerPort: 端口号
              protocol: <protocol>
          imagePullPolicy: <imagePullPolicy>
          command: 
            - 命令
          args:
            - 参数
          workingDir: 工作目录
          env:
            - name: ENVIRONMENT
              value: value
            - name: SECRET
              valueFrom:
                secretKeyRef:
                  name: Secret的名称
                  key: Secret的Key
            - name: CONFIG_MAP
              valueFrom:
                configMapKeyRef:
                  name: ConfigMap的名称
                  key: ConfigMap的Key
          envFrom:
            - configMapRef:
              name: ConfigMap的名称
            - secretRef:
              name: Secret的名称
          resources:
            requests:
              cpu: CPU数量
              memory: 内存大小
              nvidia.com/gpu: GPU数量
              ephemeral-storage: 临时资源大小
            limits:
              cpu: CPU数量
              memory: 内存大小
              nvidia.com/gpu: GPU数量
              ephemeral-storage: 临时资源大小
          livenessProbe:
            httpGet:
              path: 请求路径
              port: 请求端口
              scheme: 探测协议
            httpHeaders:
              - name: Header
                value: Value
            initialDelaySeconds: 探测延迟时间
            successThreshold: 探测成功次数上限
            failuerThreshold: 探测失败次数上限
            periodSeconds: 探测间隔时间
            timeoutSeconds: 探测超时时间
          readinessProbe:
            httpGet:
              path: 请求路径
              port: 请求端口
              scheme: 探测协议
            httpHeaders:
              - name: Header
                value: Value
            tcpSocket:
              port: 探测端口
            exec:
              command:
                - 命令
            initialDelaySeconds: 探测延迟时间
            successThreshold: 探测成功次数上限
            failuerThreshold: 探测失败次数上限
            periodSeconds: 探测间隔时间
            timeoutSeconds: 探测超时时间
          startupProbe:
            exec:
              command:
                - 命令
            initialDelaySeconds: 探测延迟时间
            successThreshold: 探测成功次数上限
            failuerThreshold: 探测失败次数上限
            periodSeconds: 探测间隔时间
            timeoutSeconds: 探测超时时间
          volumeMounts:
            - name: 存储卷的名称
              mountPath: 挂载路径
              readOnly: 是否以只读模式挂载
      restartPolicy: <restartPolicy>
  strategy:
    type: <strategy.type>
    rollingUpdate:
      maxUnavailable: 允许不可用的最大副本数
      maxSurge: 允许超出副本数的最大数量
  revisionHistoryLimit: 保存的历史修订记录的数量
```


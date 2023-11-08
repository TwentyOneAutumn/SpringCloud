# Deployment配置



## 配置

```yaml
apiVersion: <kubernetesAPI版本>
kind: Service
metadata:
	name: Pod的名称
	namespace: Pod的命名空间
	labels:
    key: value
	annotations:
    key: value
spec:
  ports:
    - name: http
      protocol: TCP
      port: 80
      targetPort: 8080
    - name: https
      protocol: TCP
      port: 443
      targetPort: 8443
  selector:
    key: value
  type: <spec.type>
  clusterIP: <clusterIP>
  clusterIPs:
    - clusterIP
  externalIPs:
    - externalIP
  externalName: <externalName>
  loadBalancerIP: <loadBalancerIP>
  loadBalancerSourceRanges:
    - <loadBalancerSourceRanges>
  sessionAffinity: ClientIP
  externalTrafficPolicy: Cluster
  publishNotReadyAddresses: <boolean>
  sessionAffinityConfig:
    clientIP:
      timeoutSeconds: 超时时间
  ipFamilies:
    - IPv4
    - IPv6
  ipFamilyPolicy: SingleStack
```


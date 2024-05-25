# 部署Nacos

## 处理集群节点混乱问题

1. 解压helm包

   ```shell
   tar -zxvf nacos-x.x.x.tgz
   ```

2. 修改配置

   ```shell
   vim nacos/templates/deployment-statefulset.yaml
   ```

3. 找到以下配置并修改

   ```yaml
   # 修改前
   env:
   	- name: NACOS_SERVERS
   	  value: {{ range $i, $e := until (int $.Values.replicaCount) -}}
   	  		 {{- $nacosPodName := (printf "%s-%d.%s-headless" (include "common.names.fullname" $root) $i (include "common.names.fullname" %root)) -}}
   	  		 {{- $nacosPodName -}}:8848{{ printf " " }}
   	  		 {{- end }}
   	  		 
   # 修改后
   env:
   	- name: NACOS_SERVERS
   	  value: {{ range $i, $e := until (int $.Values.replicaCount) -}}
   	  		 {{- $nacosPodName := (printf "%s-%d.%s-headless.命名空间名.svc.cluster.local" (include "common.names.fullname" $root) $i (include "common.names.fullname" %root)) -}}
   	  		 {{- $nacosPodName -}}:8848{{ printf " " }}
   	  		 {{- end }}
   ```

4. 重新打包

   ```shell
   # 重新打包
   helm package nacos/
   ```

   
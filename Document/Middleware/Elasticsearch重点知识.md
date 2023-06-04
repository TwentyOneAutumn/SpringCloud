# Elasticsearch重点知识







### Doc文档



---



##### 创建文档

```bash
PUT /index/type/id
{
    "first_name" : "John",
    "last_name" :  "Smith",
    "age" :        25,
    "about" :      "I love to go rock climbing",
    "interests": [ "sports", "music" ]
}
```

index : 索引名称

type : 文档类型

id : 文档的id，文档的内容为Json字符串



---



### 查询文档



---



##### 基本查询

```bash
GET /index/type/id
```



##### 返回

```json
{
  "_index" :   "index",
  "_type" :    "type",
  "_id" :      "id",
  "_version" : 1,
  "found" :    true,
  "_source" :  {
      "first_name" :  "John",
      "last_name" :   "Smith",
      "age" :         25,
      "about" :       "I love to go rock climbing",
      "interests":  [ "sports", "music" ]
    }
}
```

 _version：版本号，每次改动会+1

 found：表示在document是否存在

 _source：document的全部内容



---



##### match_all

```bash
GET /index/type/_search
{
    "query" : {
        "match_all" : {
        }
    }
}
```

match_all：查询所有文档，不加条件限制



---



##### match

```bash
GET /index/type/_search
{
    "query" : {
        "match" : {
        	"name":"张三"
        }
    }
}
```

match：对值进行分词，然后根据文档词条的值匹配



---



##### multi_match

```bash
GET /index/type/_search
{
    "query" : {
        "multi_match" : {
        	"query":"张三男",
        	"fields":["name","sex",......]
        }
    }
}
```

multi_match：对值进行分词，然后根据多个词条的值匹配



---



##### term

```bash
GET /index/type/_search
{
    "query" : {
        "term" : {
        	"name":{
        		"value":"张三"
        	}
        }
    }
}
```

term：根据词条的精确值查询，不会分词


---



##### range

```bash
GET /index/type/_search
{
    "query" : {
        "range" : {
        	"age":{
        		"gte":18,
        		"lte":30
        	}
        }
    }
}
```

range：根据词条值的范围查询

gt大于，lt小于，gte大于等于，lte小于等于



---






# Map



## 简介

> Map是Java中的一个接口，用于存储键值对（key-value pairs）。它提供了一种将键映射到值的方式，因此可以通过键来获取对应的值。Map中的键是唯一的，每个键最多只能映射到一个值。常见的Map实现包括HashMap、TreeMap、LinkedHashMap等
>



## 方法

| 方法                                    | 返回值               | 说明                                                         |
| --------------------------------------- | -------------------- | ------------------------------------------------------------ |
| size()                                  | int                  | 返回此映射中`键值对`的个数                                   |
| isEmpty()                               | boolean              | 判断`Map`不包含`键值对`                                      |
| containsKey(Object key)                 | boolean              | 判断当前`Map`是否包含指定的`key`                             |
| containsValue(Object value)             | boolean              | 判断当前`Map`是否包含指定的`value`                           |
| get(Object key)                         | V                    | 返回指定的`key`所对应的`value`的值，如果该`Map`不包含该`key`，则返回null。 |
| put(K key, V value)                     | V                    | 添加指定的`键值对`到`Map`中,如果该`Map`中已经包含该`key`,则对应的`value`会被覆盖 |
| remove(Object key)                      | V                    | 删除`key`对应的`键值对`                                      |
| putAll(Map<? extends K, ? extends V> m) | void                 | 添加指定的`Map`的所有`键值对`复制到当前`Map`中               |
| clear()                                 | void                 | 清空`Map`中的`键值对`                                        |
| keySet()                                | Set<K>               | 返回一个包含`Map`中所有`key`的`Set`集合                      |
| values();                               | Collection<V>        | 返回一个包含了`Map`中所有的值的`Collection`集合              |
| entrySet()                              | Set<Map.Entry<K, V>> | 返回一个包含Map中所有`Map.Entry<K, V>`映射的`Set`集合        |


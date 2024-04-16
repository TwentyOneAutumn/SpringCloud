# Collection

## 简介

> 在Java中， `Collection` 接口表示一种对象容器，它可以包含一个或多个对象。这些对象可以是任何类型，包括基本数据类型和对象引用，它是Java集合框架的基础，定义了集合操作的通用行为



## 方法

| 方法                                  | 返回值         | 说明                                     |
| ------------------------------------- | -------------- | ---------------------------------------- |
| size()                                | int            | 获取当前集合的长度                       |
| isEmpty()                             | boolean        | 判断当前集合是否有元素存在               |
| contains(Object o)                    | boolean        | 判断当前集合中是否包含指定元素           |
| iterator()                            | Iterator<E>    | 获取迭代器对象                           |
| toArray()                             | Object[]       | 将当前集合转换为Object类型的数组         |
| toArray(T[] a)                        | T[]            | 将当前集合转换为指定类型的数组           |
| add(E e)                              | boolean        | 添加指定元素到当前集合                   |
| remove(Object o)                      | boolean        | 从当前集合中删除指定元素(如果该元素存在) |
| containsAll(Collection<?> c)          | boolean        | 判断当前集合是否包含指定集合中的所有元素 |
| addAll(Collection<? extends E> c)     | boolean        | 添加指定集合中的所有元素到当前集合中     |
| removeAll(Collection<?> c)            | boolean        | 删除指定集合中包含的此集合的所有元素     |
| retainAll(Collection<?> c)            | boolean        | 删删除指定集合中不包含的此集合的所有元素 |
| removeIf(Predicate<? super E> filter) | boolean        | 删除当前集合中满足条件的所有元素         |
| clear()                               | void           | 清空当前集合                             |
| spliterator()                         | Spliterator<E> | 返回一个Spliterator对象                  |
| stream()                              | Stream<E>      | 返回一个串行流对象                       |
| parallelStream()                      | Stream<E>      | 返回一个并行流对象                       |
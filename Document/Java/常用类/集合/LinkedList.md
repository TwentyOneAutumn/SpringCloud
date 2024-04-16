# LinkedList

## 简介

> LinkedList（链表）是一种常见的数据结构，它由一系列节点组成，每个节点包含数据元素以及指向下一个节点的引用。与数组不同，链表中的元素在内存中不必是连续存储的，它们通过指针相互连接。这使得链表能够有效地支持动态的插入和删除操作，而不需要移动大量的元素
>



## 特性

1. 有序
2. 双向循环链表结构
3. 增删效率高,查询效率低



## 源码解析

### 基础属性

#### LinkedList

```java
public class LinkedList<E> {
  	
    /**
     * 链表的长度
     */
  	transient int size = 0;

    /**
     * 指向第一个节点的指针
     */
    transient Node<E> first;

    /**
     * 指向最后一个节点的指针
     */
    transient Node<E> last;
}
```

#### Node

```java
private static class Node<E> {
  		
  	/**
  	 * 节点元素
  	 */
    E item;
  
  	/**
  	 * 指向下一个节点的引用
  	 */
    Node<E> next;
  
  	/**
  	 * 前一个节点的引用
  	 */
    Node<E> prev;

  	/**
  	 * 全参构造
  	 */
    Node(Node<E> prev, E element, Node<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
```



### 构造方法

##### LinkedList()

```java
public LinkedList() {}
```

##### LinkedList(Collection<? extends E> c)

```java
public LinkedList(Collection<? extends E> c) {
    this();
    addAll(c);
}
```



### 添加元素

#### add(E e)

##### add()

```java
/**
 * 将指定的元素追加到链表的末尾。
 * 这个方法等价于`addLast()`
 *
 * @param 被添加的元素
 * @return 是否成功添加
 */
public boolean add(E e) {
    linkLast(e);
    return true;
}
```

##### linkLast()

```java
/**
 * 将指定元素添加到链表的末尾
 */
void linkLast(E e) {
  	// 获取链表最后一个节点
    final Node<E> l = last;
  	// 新建一个节点，存储元素为`e`,将上一个节点的指针指向`l`,下一个节点的指针置空
    final Node<E> newNode = new Node<>(l, e, null);
    // 将新节点设置为链表的最后一个节点
    last = newNode;
    if (l == null){
        // 如果最后一个为空,代表该节点是第一个加入链表的节点,将该节点设置为第一个节点
        first = newNode;
    } else {
        // 最后一个节点的下一个节点的指针指向新节点
        l.next = newNode;
    }
    // 长度自增
    size++;
    // 迭代计数器自增
    modCount++;
}
```

#### addAll(Collection<? extends E> c)

##### addAll()

```java
/**

 * 将指定集合中的所有元素插入到此列表中
 * 从指定位置开始。将当前位于该位置的元素,以及任何后续元素向右移动，新元素将按照指定集合的迭代器返回它们的顺序出现在列表中
 *
 * @param index 要插入指定集合中第一个元素的索引
 * @param c 包含要添加到此列表中的元素的集合
 * @return 如果此列表因调用而更改，则返回`true`
 * @throws IndexOutOfBoundsException 
 * @throws NullPointerException 如果指定的集合为null
*/
public boolean addAll(int index, Collection<? extends E> c) {
    // 判断index是否超出限制
    checkPositionIndex(index);
	// 转换为数组
    Object[] a = c.toArray();
    // 健壮性判断
    int numNew = a.length;
    if (numNew == 0) {
        return false;
    }
       
	// 前驱节点
    Node<E> pred;
    // 后继节点
    Node<E> succ;
    // 判断是否从末尾开始
    if (index == size) { 
        // 从末尾开始,后继节点为null
        succ = null;
        // 末尾节点为前驱节点
        pred = last;
    } else {
        // 获取插入位置的节点作为后继节点
        succ = node(index);
        // 获取前驱节点
        pred = succ.prev;
    }

    // 循环向链表添加元素
    for (Object o : a) {
        // 转换元素类型
        @SuppressWarnings("unchecked") E e = (E) o;
        // 创建新节点,并设置前驱节点引用
        Node<E> newNode = new Node<>(pred, e, null);
        // 判断当前节点是否为链表第一个节点
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        // 将当前节点设置为下一个节点的前驱节点
        pred = newNode;
    }

    // 判断是否有后继节点需要链接
    if (succ == null) {
        // 设置最后一个节点
        last = pred;
    } else {
        // 链接后续节点
        pred.next = succ;
        succ.prev = pred;
    }

    // 增加链表长度
    size += numNew;
    // 迭代计数器自增
    modCount++;
    return true;
}
```

##### node()

```java
/**
 * 用于获取指定索引位置的节点，并返回该节点
 */
Node<E> node(int index) {
	// 判断指定索引位置是否在链表的前半部分
    if (index < (size >> 1)) {
        // 获取第一个节点
        Node<E> x = first;
        // 从头到尾遍历
        for (int i = 0; i < index; i++) {
            x = x.next;
        }
        return x;
    } else {
        // 获取最后一个节点
        Node<E> x = last;
        // 从尾到头遍历
        for (int i = size - 1; i > index; i--) {
            x = x.prev;
        }
        return x;
    }
}
```

### 删除元素

#### remove(Object o)

##### remove()

```java
/**
 * 从列表中移除第一个出现的指定元素，如果列表不包含该元素，则不做任何改变.
 *
 * @param o 要从此列表中移除的元素
 * @return 如果此列表包含指定的元素，则返回`true`
 */
public boolean remove(Object o) {
    if (o == null) {
        // 遍历链表，查找值为`null`的节点
        for (Node<E> x = first; x != null; x = x.next) {
            // 如果找到值为`null`的节点，执行`unlink()`方法移除该节点
            if (x.item == null) {
                unlink(x);
                return true;
            }
        }
    } else {
        // 遍历链表，查找值为`o`的节点
        for (Node<E> x = first; x != null; x = x.next) {
            // 如果找到值为`o`的节点，执行`unlink(x)`方法移除该节点
            if (o.equals(x.item)) {
                unlink(x);
                return true;
            }
        }
    }
    // 如果未找到匹配的节点，则返回 false
    return false;
}

```

##### unlink()

```java
/**
 * 断开节点`x`的链接
 */
E unlink(Node<E> x) {
    // 获取节点`x`的元素值
    final E element = x.item;
    // 获取节点`x`的下一个节点
    final Node<E> next = x.next;
    // 获取节点`x`的前一个节点
    final Node<E> prev = x.prev;  

    // 如果节点`x`的前驱节点为`null`，表示节点`x`是链表的第一个节点
    if (prev == null) {
        // 将链表的头节点指向节点`x`的下一个节点
        first = next;  
    } else {
        // 将节点 x 的前一个节点的 next 指向节点 x 的下一个节点
        prev.next = next;
        // 将节点`x`的前驱节点置为`null`，断开与前一个节点的链接
        x.prev = null;
    }

    // 如果节点`x`的下一个节点为`null`，表示节点`x`是链表的最后一个节点
    if (next == null) {
        // 将链表的尾节点指向节点`x`的前一个节点
        last = prev;  
    } else {
        // 将节点`x`的下一个节点的指向节点`x`的前一个节点
        next.prev = prev;
        // 将节点`x`的后驱节点置为为`null`，断开与下一个节点的链接
        x.next = null;  
    }
    
	// 将节点`x`的元素值置为`null`
    x.item = null;
    // 更新链表的大小
    size--;
    // 更新迭代计数器
    modCount++;
    // 返回节点`x`的元素值
    return element;  
}
```



## 效率

### 查询

LinkedList的随机访问效率较低，因为要访问链表中的第`k`个元素，需要从头节点开始顺序遍历或者从尾节点开始逆序遍历，时间复杂度为`O(n)`，其中`n`是链表的大小

由于链表中的元素不是连续存储的，可能会导致缓存性能问题。当访问链表中的元素时，由于数据不连续，可能会引起缓存未命中，从而影响访问效率

### 增删

LinkedList对于插入和删除操作效率较高，因为它不需要像数组那样移动元素。在链表中，只需修改指针的指向即可完成插入和删除操作，时间复杂度为`O(1)`.

### 存储

LinkedList的空间复杂度较高，因为除了存储元素本身外，还需要存储节点之间的指针信息,这可能导致在存储大量元素时占用更多的内存空间.

### 总结

LinkedList 适用于需要频繁执行插入和删除操作，但不需要频繁进行随机访问的场景.

在对链表进行大量随机访问或遍历操作时，应谨慎选择 LinkedList，考虑到其性能可能不如数组或其他数据结构




































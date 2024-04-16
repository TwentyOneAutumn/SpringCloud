# ArrayList

## 简介

> `ArrayList`是Java中`java.util`包下的一个类,实现了 `List` 接口,它基于动态数组实现了可变大小的数组，底层基于数组实现



## 特性

1. **有序**

   ArrayList底层是由数组实现的，由于数组的存储结构具有连续性，所以ArrayList是有序的

2. **动态扩容**

   ArrayList在添加元素的时候，会判断当前集合的长度是否足够

3. 非线程安全

4. 查询效率高,增删效率低



## 源码分析

### 基础属性

```java
public class ArrayList<E> {
  	
    /**
     * 默认初始容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 共享的空数组实例
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 默认大小的共享的空数组实例
     * 当向这个空实例添加第一个元素时,会将其扩展为默认容量大小
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
      * 存放数组列表元素的数组缓冲区.
      * 数组列表的容量是这个数组缓冲区的长度 
      * 任何elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA的ArrayList
      * 将在添加第一个元素时扩展为DEFAULT_CAPACITY.
     */
    transient Object[] elementData; // non-private to simplify nested class access

    /**
     * 数组列表的大小(包含的元素数量)
     */
    private int size;
}
```



### 构造方法

#### ArrayList()

```java
/**
 * 构造一个初始容量为10的空列表.
 */
public ArrayList() {
    this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
}
```

#### ArrayList(int initialCapacity)

```java
/**
 * 构造具有指定初始容量的空集合.
 *
 * @param  initialCapacity  集合的初始容量
 * @throws IllegalArgumentException 如果指定的容量是负数则抛出`IllegalArgumentException`异常
 */
public ArrayList(int initialCapacity) {
    if (initialCapacity > 0) {
      	// 如果长度大于0,则构建指定长度的集合
        this.elementData = new Object[initialCapacity];
    } else if (initialCapacity == 0) {
      	// 如果长度等于0,则赋值为空集合
        this.elementData = EMPTY_ELEMENTDATA;
    } else {
      	// 如果指定的容量是负数则抛出`IllegalArgumentException`异常
        throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
    }
}
```

#### ArrayList(Collection<? extends E> c)

```java
/**
 * 按照指定集合的迭代器返回的顺序,构造一个包含指定集合元素的集合
 *
 * @param c 将构建新集合的元素放入该集合中
 * @throws NullPointerException 如果指定集合为null则抛出`NullPointerException`异常
 */
public ArrayList(Collection<? extends E> c) {
  	// 将集合转换为数组
    Object[] a = c.toArray();
  	// 将a数组的长度赋值给`size`,然后判断`size`是否不等于0
    if ((size = a.length) != 0) {
        if (c.getClass() == ArrayList.class) {
          	// 如果入参集合的类型为`ArrayList`则直接赋值
            elementData = a;
        } else {
          	// 创建一个长度相当于入参集合的数组,将入参集合中的元素转移到新数组中
            elementData = Arrays.copyOf(a, size, Object[].class);
        }
    } else {
        // 替换为空数组
        elementData = EMPTY_ELEMENTDATA;
    }
}
```





### 添加元素

#### add(E e)

##### add()

```java
/**
 * 将指定的元素追加到此列表的末尾
 * 
 * @param e 要添加的元素
 * @return 是否添加成功
 */
public boolean add(E e) {
  	// 确保`ArrayList`内部数组满足所需的最小容量,如果容量不够则进行扩容
    ensureCapacityInternal(size + 1);
  	// 存储元素
    elementData[size++] = e;
    return true;
}
```

##### ensureCapacityInternal()

```java
/**
 * 如果数组不满足需求则进行扩容
 * 
 * @param minCapacity 所需的最小容量
 */
private void ensureCapacityInternal(int minCapacity) {
  	// 进一步处理
		ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
}
```

##### calculateCapacity()

```java
/**
 * 对数组需求长度进行健壮性判断
 * 
 * @param minCapacity 所需的最小容量
 */
private static int calculateCapacity(Object[] elementData, int minCapacity) {
  	// 判断当前数组是否为空数组
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
      	// 如果数组为空则从`minCapacity`和`DEFAULT_CAPACITY`中取较大的
        return Math.max(DEFAULT_CAPACITY, minCapacity);
    } else {
        // 如果不为空则返回`minCapacity`
        return minCapacity;
    }
}
```

##### ensureExplicitCapacity()

```java
private void ensureExplicitCapacity(int minCapacity) {
  	// 自增
    modCount++;

    // 判断所需的最小容量是否大于`elementData`容量
    if (minCapacity - elementData.length > 0) {
      	// 增加`ArrayList`的容量以满足所需的最小容量
    		grow(minCapacity);
    }
}
```

##### grow()

```java
/**
 * 增加容量以确保它至少可以容纳最小容量参数指定的元素数量
 *
 * @param minCapacity 所需的最小容量
 */	
private void grow(int minCapacity) {
    // 获取elementData的容量
    int oldCapacity = elementData.length;
  	// 计算新的容量,相当于扩容到原本容量的`1.5倍`
    int newCapacity = oldCapacity + (oldCapacity >> 1);
  	// 确保了新容量至少满足了所需的最小容量
    if (newCapacity - minCapacity < 0) {
      	newCapacity = minCapacity;
    }
  	// 确保新容量不会超出数组的最大限制
    if (newCapacity - MAX_ARRAY_SIZE > 0) {
      	newCapacity = hugeCapacity(minCapacity);
    } 
    // 创建一个新数组,将长度设置为`newCapacity`,并将老的elementData中的元素复制到新数组中,再赋值给elementData
    elementData = Arrays.copyOf(elementData, newCapacity);
}
```

#### addAll(Collection<? extends E> c)

```java
public boolean addAll(Collection<? extends E> c) {
  	// 转换为数组
    Object[] a = c.toArray();
  	// 获取长度
    int numNew = a.length;
  	// 确保`ArrayList`内部数组满足所需的最小容量,如果容量不够则进行扩容
    ensureCapacityInternal(size + numNew);
  	// 将`a`中的元素从下标0开始,copy到`elementData`中,从下标size开始,复制numNew个元素
    System.arraycopy(a, 0, elementData, size, numNew);
  	// 更新size
    size += numNew;
    return numNew != 0;
}
```



### 删除元素

#### remove(Object o)

##### remove()

```java
/**
 * 从此列表中删除第一个出现的指定元素(如果该元素存在),如果列表中不包含该元素则保持不变
 *
 * @param o 要删除的元素(如果存在)
 * @return 集合是否包含指定的元素
 */
public boolean remove(Object o) {
    if (o == null) {
        for (int index = 0; index < size; index++)
          	// 直接将元素用==和null比较
            if (elementData[index] == null) {
              	// 调用方法对当前元素进行删除
                fastRemove(index);
                return true;
            }
    } else {
        for (int index = 0; index < size; index++)
          	// 用equals()和当前元素进行比较
            if (o.equals(elementData[index])) {
              	// 调用方法对当前元素进行删除
                fastRemove(index);
                return true;
            }
    }
    return false;
}
```

##### fastRemove()

```java
/**
 * 私有remove方法跳过边界检查并且不返回被移除的值
 */
private void fastRemove(int index) {
  	// 自增
    modCount++;
  	// 获取要移动的元素数量
    int numMoved = size - index - 1;
  	// 确定需要移动的元素数量是否大于0，如果不大于0则被删除的元素是最后一位
    if (numMoved > 0) {
      	// 将目标下标之后的元素整体以向前移动一位,index位置的元素将被移动到最后一位(即将被删除的位置)
      	System.arraycopy(elementData, index+1, elementData, index, numMoved);
    }
  	// 将最后一位的下标位置置,被删除的对象交给GC处理
    elementData[--size] = null; // clear to let GC do its work
}
```

#### remove(int index)

##### remove()

```java
/**
 * 移除此列表中指定位置的元素
 *
 * @param index 要删除的元素的索引
 * @return 从列表中删除的元素
 * @throws IndexOutOfBoundsException {@inheritDoc}
 */
public E remove(int index) {
  	// 检查给定的索引是否在范围内
    rangeCheck(index);
		// 自增
    modCount++;
  	// 获取要被删除的数据
    E oldValue = elementData(index);
		// 获取要移动的元素数量
    int numMoved = size - index - 1;
  	// 确定需要移动的元素数量是否大于0，如果不大于0则被删除的元素是最后一位
    if (numMoved > 0) {
      	// 将目标下标之后的元素整体以向前移动一位,index位置的元素将被移动到最后一位(即将被删除的位置)
        System.arraycopy(elementData, index+1, elementData, index, numMoved);
    }
  	// 将最后一位的下标位置置,被删除的对象交给GC处理
    elementData[--size] = null; // clear to let GC do its work
  	// 返回被删除的元素
    return oldValue;
}
```

##### rangeCheck()

```java
/**
 * 检查给定的索引是否在范围内
 */
private void rangeCheck(int index) { 
		if (index >= size) {
        throw new IndexOutOfBoundsException(outOfBoundsMsg(index)); 
    }
}
```

#### removeAll()

##### removeAll()

```java
/**
 * 从列表中删除所有包含在指定集合中的元素
 */
public boolean removeAll(Collection<?> c) {
    Objects.requireNonNull(c);
    return batchRemove(c, false);
}
```

##### batchRemove()

```java
private boolean batchRemove(Collection<?> c, boolean complement) {
  	// 获取`elementData`
    final Object[] elementData = this.elementData;
  	// 用来控制循环遍历elementData`
    int r = 0;
  	// 用来控制未符合删除条件元素的插入位置
  	int w = 0;
 		// 标记`elementData`是否被修改
    boolean modified = false;
    try {
      	// 循环遍历`elementData`
        for (; r < size; r++)
          	// 筛选出`c`中不包的元素
            if (c.contains(elementData[r]) == complement) {
              	// 将元素插入到数组中
              	elementData[w++] = elementData[r];
            }
    } finally {
        //  保持与AbstractCollection的行为兼容性,即使`c.contains()`抛出异常
      
      	// 处理在便利过程中`c.contains()`方法抛出异常的情况
        if (r != size) {
          	// 追加未遍历的元素
            System.arraycopy(elementData, r, elementData, w, size - r);
          	// 更新位置信息 
            w += size - r;
        }
      	
      	// 如果移动后的`w`不等于列表大小`size`，说明列表发生了修改
        if (w != size) {
          	// 将列表中剩余的元素置为`null`，以便让垃圾回收器进行清理
            for (int i = w; i < size; i++) {
              	// 置空,等待GC回收
              	elementData[i] = null;
            }
            // 更新列表的修改计数器    
            modCount += size - w;
          	// 更新列表的大小
            size = w;
          	// 表示列表已经被修改过了
            modified = true;
        }
    }
    return modified;
}
```



## 效率

### 查询

由于`ArrayList`内部使用数组来存储元素,并且支持随机访问,即可以通过索引直接访问任意位置的元素.这种随机访问的时间复杂度是常数级的,即`O(1)`,因为在数组中,元素的位置是通过索引来直接计算的,而不需要遍历整个列表.

除了随机访问外,ArrayList也提供了高效的迭代方式,可以使用`forEach循环`或者`Iterator`来遍历元素.由于数组的连续存储特性,遍历效率也很高,时间复杂度是 `O(n)`,其中`n`是数组的大小.

由于`ArrayList`内部元素在内存中是连续存储的,这种存储方式对于`CPU缓存`是友好的.当需要遍历数组或者进行大量的查询操作时,连续存储可以利用CPU缓存预取机制,提高访问速度.

### 插入

当在`ArrayList`中间或开头插入一个元素时,需要将插入点后面的所有元素都向后移动一个位置,以腾出空间来存放新元素.这意味着需要将插入点后面的所有元素都移动,所需的时间与列表长度成正比,因此插入操作的时间复杂度是`O(n)`,其中`n`是列表的大小

如果通过`add()`方法在列表末尾添加元素,即将元素追加到列表的最后,这种操作的时间复杂度是常数级的,即 `O(1)`.因为`ArrayList`内部维护一个指向末尾位置的索引,直接将元素放在这个位置,不需要移动其他元素,使用`addAll()`方法同理.

### 删除

如果通过 `remove` 方法删除列表末尾的元素,即删除列表中最后一个元素,因为 ArrayList 内部维护一个指向末尾位置的索引,直接删除这个位置的元素即可,这种操作的时间复杂度是常数级的,即 O(1).

如果通过 `remove` 方法删除列表中间或开头的元素,由于需要将删除点后面的所有元素向前移动一个位置,以填补删除点的空缺,因此这种操作的时间复杂度是线性的,即 O(n),其中 n 是列表的大小.因此,在列表中间或开头频繁进行删除操作会导致性能下降.

### 扩容

当`ArrayList`的容量不足以容纳新元素时,需要进行扩容操作.扩容通常涉及创建一个新的更大的数组,然后将原始数组中的所有元素复制到新数组中.这个过程的时间复杂度是 `O(n)`,其中`n`是原始数组的大小.虽然 `ArrayList` 会在每次扩容时将容量翻倍,但是仍然会出现频繁的扩容操作,特别是在大量插入元素后,导致性能下降.

### 收容

当`ArrayList`中的元素被删除时,如果剩余的元素占用的空间远远小于数组的容量,可能会考虑进行收缩操作,即将内部数组的大小调整为与实际元素数量相匹配的大小.这个过程也涉及创建一个新的数组,并将元素复制到新数组中,时间复杂度也是`O(n)`.

### 总结

ArrayList适合查询多插入删除少的场景,如果插入或删除大部分元素,那么移动的数据量就会非常大,同时会频繁的触发扩容收容操作,这会导致插入和删除操作的时间成本非常高,导致效率下降,所以正常情况不建议对ArrayList进行大量增删操作.


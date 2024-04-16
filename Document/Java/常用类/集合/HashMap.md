# HashMap

## 简介

> HashMap是Java中的一个常用的哈希表实现的Map，它实现了Map接口



## 特性

1. `key`唯一不可重复且只能，value可以重复且可以为`null`
2. 无序
3. 基于哈希表存储
4. 增删改查效率高
5. 非线程安全
6. 自动扩容



## 源码分析

### 基础属性

```java
public class HashMap<K,V> {
    /**
     * 默认初始容量,必须是2的幂
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // 即 16

    /**
     * 最大容量，如果通过构造函数隐式指定了更高的值，则使用该值
     * 必须是小于等于1<<30的2的幂
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 当构造函数中未指定负载因子时使用的加载因子
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 用于在一个桶中添加元素时决定是否使用树而不是列表的桶计数阈值
     * 当向一个至少具有这么多节点的桶中添加元素时，桶将转换为树
     * 该值必须大于2，并且应至少为8，以符合树形删除中关于在收缩时将转换回普通桶的假设
     */
    static final int TREEIFY_THRESHOLD = 8;

    /**
     * 在调整大小操作期间对(分裂)桶进行取消树化的桶计数阈值
     * 应该小于TREEIFY_THRESHOLD，并且最多为6，以便在删除时进行收缩检测
     */
    static final int UNTREEIFY_THRESHOLD = 6;

    /**
     * 可以使桶树化的最小表容量
     * 否则，如果一个桶中的节点太多，则会调整表大小
     * 应该至少为4 * TREEIFY_THRESHOLD，以避免调整大小和树化阈值之间的冲突
     */
    static final int MIN_TREEIFY_CAPACITY = 64;
    
    /**
     * 表，在第一次使用时初始化，并根据需要调整大小
     * 当分配时，长度始终是2的幂
     */
    transient Node<K,V>[] table;

    /**
     * 缓存的entrySet()
     */
    transient Set<Map.Entry<K,V>> entrySet;

    /**
     * 此映射中包含的键值映射的数量
     */
    transient int size;

    /**
     * 迭代计数器
     */
    transient int modCount;

    /**
     * 下一次调整大小的大小阈值(容量 * 负载因子)
     */
    int threshold;

    /**
     * 哈希表的负载因子
     */
    final float loadFactor;
}
```

### 内部类

```java
/**
 * 基本的哈希桶节点，用于大多数条目
 */
static class Node<K,V> implements Map.Entry<K,V> {
    
    /**
     * hash值
     */
    final int hash;
    
    /**
     * 键
     */
    final K key;
    
    /**
     * 值
     */
    V value;
    
    /**
     * 下个节点的指针
     */
    Node<K,V> next;

    Node(int hash, K key, V value, Node<K,V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public final K getKey()        { return key; }
    public final V getValue()      { return value; }
    public final String toString() { return key + "=" + value; }

    public final int hashCode() {
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }

    public final V setValue(V newValue) {
        V oldValue = value;
        value = newValue;
        return oldValue;
    }

    public final boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof Map.Entry) {
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;
            if (Objects.equals(key, e.getKey()) &&
                Objects.equals(value, e.getValue()))
                return true;
        }
        return false;
    }
}
```

### 构造方法

#### HashMap(int initialCapacity, float loadFactor)

```java
/**
 * 使用指定的初始容量和负载因子构造一个空的HashMap。
 *
 * @param  initialCapacity 初始容量
 * @param  loadFactor 负载因子
 * @throws IllegalArgumentException 如果初始容量为负数或负载因子为非正数
 */
public HashMap(int initialCapacity, float loadFactor) {
    if (initialCapacity < 0) {
        throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
    }
    // 如果给定容量超过最大长度，则取最大容量
    if (initialCapacity > MAXIMUM_CAPACITY) {
        initialCapacity = MAXIMUM_CAPACITY;
    }
    // 判断负载因子是否合法
    if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
        throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
    }
    // 设置负载因子
    this.loadFactor = loadFactor;
    // 计算并设置阈值
    this.threshold = tableSizeFor(initialCapacity);
}
```

#### HashMap(int initialCapacity)

```java
/**
 * 使用指定的初始容量和默认负载因子(0.75)构造一个空的HashMap
 *
 * @param  initialCapacity 初始容量
 * @throws IllegalArgumentException 如果初始容量为负数
 */
public HashMap(int initialCapacity) {
    // 调用带两个参数的构造函数，负载因子使用默认值
    this(initialCapacity, DEFAULT_LOAD_FACTOR); 
}
```

#### HashMap()

```java
/**
 * 使用默认的初始容(16)和默认负载因子(0.75)构造一个空的HashMap
 */
public HashMap() {
    // 设置负载因子为默认值，其他字段采用默认值
    this.loadFactor = DEFAULT_LOAD_FACTOR; 
}
```

#### HashMap(Map<? extends K, ? extends V> m)

```java
/**
 * 使用指定的Map的映射构造一个新的HashMap
 * 新的HashMap使用默认负载因子(0.75)，并且初始容量足以容纳指定Map中的映射
 *
 * @param m 要放入此Map中的映射的Map
 * @throws NullPointerException 如果指定的Map为null
 */
public HashMap(Map<? extends K, ? extends V> m) {
    // 设置负载因子为默认值
    this.loadFactor = DEFAULT_LOAD_FACTOR; 
    // 将指定Map中的映射放入此Map中
    putMapEntries(m, false); 
}
```

### 添加

#### put(K key, V value)

1. put()

   ```java
   /**
    * 将指定的值与指定的键关联在此映射中
    * 如果映射以前包含键的映射，则旧值将被替换
    *
    * @param key 要与指定值关联的键
    * @param value 要与指定键关联的值
    * @return 与指定键关联的先前值,如果之前没有与指定键关联的映射，则返回`null`
    */
   public V put(K key, V value) {
       // 调用putVal方法，进行实际的插入操作
       return putVal(hash(key), key, value, false, true); 
   }
   ```

2. putVal()

   ```java
   /**
    * 实现Map.put和相关方法
    *
    * @param hash 键的哈希值
    * @param key 键
    * @param value 要放入的值
    * @param onlyIfAbsent 如果为true，则不更改现有值
    * @param evict 如果为false，则表处于创建模式
    * @return 先前的值，如果没有则返回null
    */
   final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
       // 表数组
       Node<K,V>[] tab;
       // 临时节点变量
       Node<K,V> p;
       // 表的长度和索引
       int n, i; 
       // 如果表为空，则进行扩容操作
       if ((tab = table) == null || (n = tab.length) == 0) {
           n = (tab = resize()).length;
       }
       // 如果对应索引位置为空，则直接插入新节点
       if ((p = tab[i = (n - 1) & hash]) == null) {
           tab[i] = newNode(hash, key, value, null);
       } else {
           Node<K,V> e; 
           K k; 
           // 如果索引位置的节点与要插入的键相同，则更新对应值
           if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
               e = p;
           }
           // 如果索引位置的节点已经是树节点，则调用树节点的插入方法
           else if (p instanceof TreeNode) {
               e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
           }
           // 否则遍历链表，找到合适的位置插入新节点
           else { 
               for (int binCount = 0; ; ++binCount) {
                   // 如果到达链表尾部，则插入新节点
                   if ((e = p.next) == null) { 
                       p.next = newNode(hash, key, value, null);
                       // 如果链表长度大于等于TREEIFY_THRESHOLD，则将链表转换为树
                       if (binCount >= TREEIFY_THRESHOLD - 1) {
                           treeifyBin(tab, hash);
                       }
                       break;
                   }
                   // 如果找到相同的键，则更新对应值
                   if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                       break;
                   }
                   p = e;
               }
           }
           // 存在相同键的映射
           if (e != null) { 
               // 获取旧值
               V oldValue = e.value; 
               // 如果onlyIfAbsent为false或旧值为null，则更新值
               if (!onlyIfAbsent || oldValue == null) {
                   e.value = value;
               }
               // 记录访问次数
               afterNodeAccess(e); 
               // 返回旧值
               return oldValue; 
           }
       }
       // 修改次数加一
       ++modCount; 
       // 如果元素数量大于阈值，则进行扩容操作
       if (++size > threshold) {
           resize();
       }
       // 记录插入操作
       afterNodeInsertion(evict); 
       return null;
   }
   ```

3. resize()

   ```java
   /**
    * 初始化或加倍表的大小
    * 如果为null，则根据字段threshold中保存的初始容量目标进行分配
    * 否则，由于我们使用的是二倍扩展，每个桶中的元素必须要么保持在相同的索引位置，要么在新表中以二的幂偏移移动
    *
    * @return 表
    */
   final Node<K,V>[] resize() {
       // 保存旧表
       Node<K,V>[] oldTab = table; 
       // 旧表的长度
       int oldCap = (oldTab == null) ? 0 : oldTab.length; 
       // 旧的阈值
       int oldThr = threshold; 
       // 新的容量和阈值
       int newCap, newThr = 0; 
       // 如果旧表不为空
       if (oldCap > 0) { 
           // 如果旧表长度大于等于最大容量
           if (oldCap >= MAXIMUM_CAPACITY) { 
               // 阈值设为Integer最大值
               threshold = Integer.MAX_VALUE; 
               // 返回旧表
               return oldTab; 
           }
           // 新容量为旧容量的两倍，但不能超过最大容量
           else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITIAL_CAPACITY) {
               newThr = oldThr << 1; // 新阈值为旧阈值的两倍
           }
       }
       // 如果旧阈值大于0
       else if (oldThr > 0) {
           // 新容量为旧阈值
           newCap = oldThr; 
       }
       // 否则使用默认初始容量和默认负载因子计算新的容量和阈值
       else { 
           newCap = DEFAULT_INITIAL_CAPACITY; // 新容量为默认初始容量
           newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY); // 新阈值为默认初始容量乘以默认负载因子
       }
       // 如果新阈值为0
       if (newThr == 0) { 
           // 计算新的阈值
           float ft = (float)newCap * loadFactor; 
           // 如果新容量小于最大容量且新的阈值小于最大容量，则将新的阈值设为新容量乘以负载因子，否则设为Integer最大值
           newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ? (int)ft : Integer.MAX_VALUE); 
       }
       // 更新阈值
       threshold = newThr; 
       // 创建新的表
       @SuppressWarnings({"rawtypes","unchecked"})
       Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap]; 
       // 更新表
       table = newTab; 
       // 如果旧表不为空
       if (oldTab != null) { 
           // 遍历旧表中的每个桶
           for (int j = 0; j < oldCap; ++j) { 
               // 临时节点变量
               Node<K,V> e; 
               // 如果桶不为空
               if ((e = oldTab[j]) != null) { 
                   // 将旧表中的桶置空
                   oldTab[j] = null; 
                   // 如果桶中只有一个节点
                   if (e.next == null) {
                       // 将该节点直接放入新表对应的索引位置
                       newTab[e.hash & (newCap - 1)] = e;
                   }
                	// 如果桶中是树节点
                   else if (e instanceof TreeNode) {
                       // 将节点进行树化或者取消树化
                       ((TreeNode<K,V>)e).split(this, newTab, j, oldCap); 
                   }
               	// 否则，保持节点顺序
                   else { 
                       // 低位链表的头部和尾部节点
                       Node<K,V> loHead = null, loTail = null; 
                       // 高位链表的头部和尾部节点
                       Node<K,V> hiHead = null, hiTail = null;
                       // 临时节点变量
                       Node<K,V> next; 
                       // 遍历链表中的所有节点
                       do {
                           // 获取下一个节点
                           next = e.next; 
                           // 如果节点的哈希值与旧容量的与操作为0，则说明节点在低位链表中
                           if ((e.hash & oldCap) == 0) { 
                               if (loTail == null) {
                                   loHead = e;
                               } else {
                                   loTail.next = e;
                               }
                               loTail = e;
                           }
                           // 否则,节点在高位链表中
                           else { 
                               if (hiTail == null) {
                                   hiHead = e;
                               } else {
                                   hiTail.next = e;
                               }
                               hiTail = e;
                           }
                       } 
                       while ((e = next) != null); 
                       // 如果低位链表不为空
                       if (loTail != null) { 
                           // 将低位链表尾部节点的next指针置空
                           loTail.next = null; 
                           // 将低位链表的头部节点放入新表对应的索引位置
                           newTab[j] = loHead; 
                       }
                       // 如果高位链表不为空
                       if (hiTail != null) { 
                           // 将高位链表尾部节点的next指针置空
                           hiTail.next = null;
                           // 将高位链表的头部节点放入新表对应的索引位置
                           newTab[j + oldCap] = hiHead; 
                       }
                   }
               }
           }
       }
       // 返回新表
       return newTab; 
   }
   ```

4. split()

   ```java
   /**
    * 将树节点中的节点分裂为低位和高位树节点,或在节点数量太少时取消树化,仅从resize方法中调用
    *
    * @param map 存储桶头的表
    * @param tab 被分裂的表的索引
    * @param index 分裂的索引位置
    * @param bit 哈希值用于分割的位
    */
   final void split(HashMap<K,V> map, Node<K,V>[] tab, int index, int bit) {
       TreeNode<K,V> b = this;
       // 重新链接为低位和高位列表，保持顺序
       TreeNode<K,V> loHead = null, loTail = null;
       TreeNode<K,V> hiHead = null, hiTail = null;
       int lc = 0, hc = 0;
       for (TreeNode<K,V> e = b, next; e != null; e = next) {
           next = (TreeNode<K,V>)e.next;
           e.next = null;
           // 根据哈希值的某一位进行分割判断
           if ((e.hash & bit) == 0) { 
               // 将节点加入低位链表
               if ((e.prev = loTail) == null) {
                   loHead = e;
               } else {
                   loTail.next = e;
               } 
               loTail = e;
               ++lc;
           } else {
               // 将节点加入高位链表
               if ((e.prev = hiTail) == null) {
                   hiHead = e;
               } else {
                 	hiTail.next = e;  
               } 
               hiTail = e;
               ++hc;
           }
       }
   
       // 处理低位链表
       if (loHead != null) {
           // 如果节点数量太少则取消树化
           if (lc <= UNTREEIFY_THRESHOLD) {
               tab[index] = loHead.untreeify(map);
           } else {
               // 否则保持为链表结构
               tab[index] = loHead; 
               // 如果有高位链表则进行树化
               if (hiHead != null) {
                   loHead.treeify(tab);
               }       
           }
       }
       // 处理高位链表
       if (hiHead != null) {
           // 如果节点数量太少则取消树化
           if (hc <= UNTREEIFY_THRESHOLD) {
               tab[index + bit] = hiHead.untreeify(map);
           } else {
               // 否则保持为链表结构
               tab[index + bit] = hiHead; 
               // 如果有低位链表则进行树化
               if (loHead != null) {
                   hiHead.treeify(tab);
               }
           }
       }
   }
   ```

5. untreeify（）

   ```java
   /**
    * 返回一个非树节点的列表，替换从该节点链接的节点。
    *
    * @param map 存储桶头的表
    * @return 替换后的非树节点列表的头部节点
    */
   final Node<K,V> untreeify(HashMap<K,V> map) {
       Node<K,V> hd = null, tl = null;
       // 遍历节点
       for (Node<K,V> q = this; q != null; q = q.next) {
           // 用新节点替换当前节点，并保留原节点的next指针
           Node<K,V> p = map.replacementNode(q, null);
           // 将新节点连接到链表中
           if (tl == null)
               hd = p;
           else
               tl.next = p;
           tl = p;
       }
       // 返回替换后的链表的头部节点
       return hd;
   }
   ```

#### putAll(Map<? extends K, ? extends V> m)

1. putAll()

   ```java
   /**
    * 将指定Map中的所有键值对复制到此Map中
    * 这些键值对将替换此Map当前具有的任何键的的值
    *
    * @param m 要存储在此Map中的Map
    * @throws NullPointerException 如果指定的Map为null
    */
   public void putAll(Map<? extends K, ? extends V> m) {
       putMapEntries(m, true);
   }
   ```

2. putMapEntries()

   ```java
   /**
    * 实现Map.putAll和Map构造函数
    *
    * @param m 映射
    * @param evict 最初构造此映射时为false，否则为true（传递给afterNodeInsertion方法）。
    */
   final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
       // 获取指定Map的大小
       int s = m.size();
       // 如果映射不为空
       if (s > 0) {
           // 如果内部数组未初始化，则预先设置大小
           if (table == null) { 
               // 计算新映射的阈值
               float ft = ((float)s / loadFactor) + 1.0F;
               // 限制阈值的最大值为MAXIMUM_CAPACITY
               int t = ((ft < (float)MAXIMUM_CAPACITY) ? (int)ft : MAXIMUM_CAPACITY);
               if (t > threshold) {
                   // 根据阈值设置内部数组大小
                   threshold = tableSizeFor(t);
               }      
           }
           // 
           else if (s > threshold) {
               // 如果映射大小超过阈值，则进行扩容操作
               resize(); 
           }
           // 遍历指定Map
           for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) { 
               K key = e.getKey();
               V value = e.getValue();
               // 调用putVal方法进行插入操作
               putVal(hash(key), key, value, false, evict); 
           }
       }
   }
   ```

### 删除

#### remove(Object key)

1. remove()

   ```java
   /**
    * 如果存在，从该映射中删除指定键的映射。
    *
    * @param  key 要从映射中删除其映射的键
    * @return 与key关联的先前值，如果没有key的映射，则返回null
    */
   public V remove(Object key) {
       // 声明节点变量
       Node<K,V> e; 
       // 调用removeNode方法进行删除操作，如果删除成功，则返回节点的值；否则返回null
       return (e = removeNode(hash(key), key, null, false, true)) == null ? null : e.value;
   }
   ```

2. removeNode()

   ```java
   /**
    * 实现Map.remove和相关方法。
    *
    * @param hash 键的哈希值
    * @param key 要删除的键
    * @param value 如果matchValue为true，则匹配的值；否则忽略
    * @param matchValue 如果为true，则仅在值相等时才删除
    * @param movable 如果为false，则删除时不移动其他节点
    * @return 节点，如果没有则返回null
    */
   final Node<K,V> removeNode(int hash, Object key, Object value, boolean matchValue, boolean movable) {
       // 声明节点数组变量
       Node<K,V>[] tab;
       // 声明节点变量
       Node<K,V> p; 
       // 声明变量
       int n, index; 
       // 判断如果内部数组不为空且长度大于0，并且在计算得到的索引位置有节点存在
       if ((tab = table) != null && (n = tab.length) > 0 && (p = tab[index = (n - 1) & hash]) != null) { 
           // 声明节点变量
           Node<K,V> node = null, e; 
           // 声明键值变量
           K k; V v; 
           // 如果节点p的哈希值与给定的哈希值相等，并且节点p的key与给定的key相等，则认为节点p与要删除的键key匹配
           if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
               node = p;
           }
           // 如果桶中有多个节点
           else if ((e = p.next) != null) { 
               // 如果桶是树节点
               if (p instanceof TreeNode) {
                   // 从树节点中查找
                   node = ((TreeNode<K,V>)p).getTreeNode(hash, key); 
               } else {
                   // 遍历链表
                   do { 
                       // 如果找到匹配的节点
                       if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                           // 将找到的节点赋值给node
                           node = e; 
                           break;
                       }
                       // 移动p指针
                       p = e; 
                   } 
                   // 直到遍历完链表
                   while ((e = e.next) != null); 
               }
           }
           // 如果找到匹配的节点
           if (node != null && (!matchValue || (v = node.value) == value || (value != null && value.equals(v)))) {
               // 如果节点是树节点
               if (node instanceof TreeNode) {
                   // 调用树节点的删除方法
                   ((TreeNode<K,V>)node).removeTreeNode(this, tab, movable);
               }
            	// 如果要删除的节点是桶中的第一个节点
               else if (node == p) {
                   // 直接将桶的索引位置指向下一个节点
                   tab[index] = node.next;
               }  
               else {
                   // 否则修改链表的指针指向
                   p.next = node.next;
               }
            	// 结构修改次数加一
               ++modCount; 
               // 元素数量减一
               --size; 
               // 调用删除后的处理方法
                (node); 
               // 返回被删除的节点
               return node; 
           }
       }
       // 如果没有找到匹配的节点，则返回null
       return null; 
   }
   ```

3. removeTreeNode()

   ```java
   /**
    * 从树中移除指定的节点，该节点在此调用之前必须存在。
    * 由于我们无法交换内部节点与由“next”指针固定的叶子节点的内容，
    * 所以这比典型的红黑树删除代码要复杂一些。因此，我们交换树的链接。
    * 如果当前树的节点数量似乎太少，则将桶转换回普通的桶。
    * （测试触发在2到6个节点之间，取决于树的结构）。
    *
    * @param map 当前HashMap
    * @param tab 存储桶头的表
    * @param movable 如果为true，则在删除时移动根节点
    */
   final void removeTreeNode(HashMap<K,V> map, Node<K,V>[] tab, boolean movable) {
       int n;
       // 检查表是否为空，如果为空，则无法进行操作
       if (tab == null || (n = tab.length) == 0) {
           return;
       }
       // 计算哈希值对应的索引位置
       int index = (n - 1) & hash;
       // 获取索引位置上的第一个节点，并将其作为根节点
       TreeNode<K,V> first = (TreeNode<K,V>)tab[index], root = first, rl;
       // 获取后继节点和前驱节点
       TreeNode<K,V> succ = (TreeNode<K,V>)next, pred = prev;
       // 如果前驱节点为空，则将索引位置上的节点设置为后继节点
       if (pred == null) {
           tab[index] = first = succ;
       } else {
           pred.next = succ;
       }
       // 如果后继节点不为空，则将后继节点的前驱指针指向前驱节点
       if (succ != null) {
           succ.prev = pred;
       }
       // 如果第一个节点为空，则直接返回
       if (first == null) {
           return;
       } 
       // 如果根节点的父节点不为空，则获取根节点的根节点
       if (root.parent != null) {
           root = root.root();
       }
       // 如果根节点为空，或者树结构较小且可移动，则将桶转换回普通的桶
       if (root == null
           || (movable
               && (root.right == null
                   || (rl = root.left) == null
                   || rl.left == null))) {
           tab[index] = first.untreeify(map);  // 太小，取消树化
           return;
       }
       // 获取要移除的节点及其左右子节点
       TreeNode<K,V> p = this, pl = left, pr = right, replacement;
       // 如果左右子节点均不为空，则进行以下操作
       if (pl != null && pr != null) {
           TreeNode<K,V> s = pr, sl;
           // 找到后继节点
           while ((sl = s.left) != null) {
               s = sl;
           } 
           // 交换颜色
           boolean c = s.red; s.red = p.red; p.red = c;
           TreeNode<K,V> sr = s.right;
           TreeNode<K,V> pp = p.parent;
           // 如果p是s的直接父节点，则将p的父节点设置为s，s的右子节点设置为p
           if (s == pr) {
               p.parent = s;
               s.right = p;
           } else {
               TreeNode<K,V> sp = s.parent;
               if ((p.parent = sp) != null) {
                   if (s == sp.left) {
                       sp.left = p;
                   } else {
                    	sp.right = p;   
                   }
               }
               if ((s.right = pr) != null) {
                   pr.parent = s;
               }
           }
           p.left = null;
           if ((p.right = sr) != null) {
               sr.parent = p;
           }
           if ((s.left = pl) != null) {
               pl.parent = s;
           } 
           if ((s.parent = pp) == null) {
               root = s;
           }
           else if (p == pp.left) {
               pp.left = s;
           }
           else {
               pp.right = s;
           }
           // 如果sr不为空，则将sr设置为替换节点，否则将p设置为替换节点
           if (sr != null) {
               replacement = sr;
           } else {
               replacement = p;
           }
       } 
       else if (pl != null) {
           replacement = pl;
       }
       else if (pr != null) {
           replacement = pr;
       } 
       else {
           replacement = p;
       }
       // 如果替换节点不等于p，则进行替换操作
       if (replacement != p) {
           TreeNode<K,V> pp = replacement.parent = p.parent;
           // 如果pp为空，则将根节点设置为替换节点
           if (pp == null) {
               root = replacement;
           }
           else if (p == pp.left) {
               pp.left = replacement;
           }
           else {
               pp.right = replacement;
           }
           p.left = p.right = p.parent = null;
       }
       // 重新平衡树，并将结果存储在r中
       TreeNode<K,V> r = p.red ? root : balanceDeletion(root, replacement);
       // 如果替换节点等于p，则将p节点与树分离
       if (replacement == p) {  // 分离
           TreeNode<K,V> pp = p.parent;
           p.parent = null;
           if (pp != null) {
               if (p == pp.left) {
                   pp.left = null;
               }
               else if (p == pp.right) {
                   pp.right = null;
               }
           }
       }
       // 如果movable为true，则将根节点移到前面
       if (movable) {
           moveRootToFront(tab, r); 
       }
   }
   ```

   #### remove(Object key, Object value)

   ```java
   public boolean remove(Object key, Object value) {
       return removeNode(hash(key), key, value, true, true) != null;
   }
   ```

### 查找

#### containsValue(Object value)

```java
/**
 * 判断给定value是否在Map中存在
 *
 * @param value 要在该映射中测试其存在的值
 * @return 给定的value是否存在
 */
public boolean containsValue(Object value) {
    // 声明Node数组变量tab，用于存储HashMap的桶
    Node<K,V>[] tab; 
    // 声明变量v，用于存储节点的值
    V v; 
    // 如果table不为null且size大于0
    if ((tab = table) != null && size > 0) { 
        // 遍历所有桶
        for (int i = 0; i < tab.length; ++i) { 
            // 遍历每个桶中的链表或红黑树节点
            for (Node<K,V> e = tab[i]; e != null; e = e.next) { 
                // 如果节点的值等于给定值或者给定值不为null且等于节点的值
                if ((v = e.value) == value || (value != null && value.equals(v))) {
                    // 返回true，表示存在匹配的键值对
                    return true;
                }  
            }
        }
    }
    // 遍历完整个HashMap后未找到匹配的值，返回false，表示不存在匹配的键值对
    return false; 
}
```

#### containsKey(Object key)

1. containsKey()

   ```java
   /**
    * 判断给定key是否在Map中存在
    *
    * @param key 要在该映射中测试其存在的键
    * @return 给定的key是否存在
    */
   public boolean containsKey(Object key) {
       // 返回根据指定键获取的节点是否不为null
       return getNode(hash(key), key) != null; 
   }
   ```

2. getNode()

   ```java
   /**
    * 实现Map.get和相关方法.
    *
    * @param hash 键的哈希值
    * @param key 键
    * @return 返回节点，如果没有则返回null
    */
   final Node<K,V> getNode(int hash, Object key) {
       // Node数组，用于存储HashMap的桶
       Node<K,V>[] tab; 
       // 声明节点变量first和e
       Node<K,V> first, e; 
       // 节点数量
       int n; 
       // 键值
       K k; 
       // 如果table不为null，并且长度大于0，并且第一个节点不为null
       if ((tab = table) != null && (n = tab.length) > 0 && (first = tab[(n - 1) & hash]) != null) {
           // 检查第一个节点是否匹配
           if (first.hash == hash && ((k = first.key) == key || (key != null && key.equals(k)))) {
               // 返回第一个节点
               return first; 
           }
           // 如果存在下一个节点
           if ((e = first.next) != null) {
               // 如果第一个节点是树节点，则调用树节点的getTreeNode方法
               if (first instanceof TreeNode){
               	return ((TreeNode<K,V>)first).getTreeNode(hash, key);   
               }
              	// 循环遍历节点 
               do {
                   if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                       // 如果找到匹配的键值，则返回该节点
                       return e;
                   }
               } 
               // 移动到下一个节点
               while ((e = e.next) != null); 
           }
       }
       // 如果未找到匹配的节点，则返回null
       return null; 
   }
   ```

#### entrySet()

```java
/**
 * 返回此Map中所有Map.Entry的Set集合.
 * 这个集合由map支持，所以任何对map的修改都会反映在这个集合上，反之亦然.
 * 如果在迭代器操作过程中修改了map（除了迭代器本身的删除方法或通过迭代器返回的map条目上的setValue操作外），迭代结果是不确定的
 * 该集合支持元素移除操作，该操作通过Iterator.remove、Set.remove、removeAll、retainAll、clear操作从映射中移除相应的映射关系.
 * 它不支持add或addAll操作.
 *
 * @return 包含此Map中Map.Entry的Set集合
 */
public Set<Map.Entry<K,V>> entrySet() {
    Set<Map.Entry<K,V>> es;
    // 如果 entrySet 为空，则创建一个新的 EntrySet 并赋值给 es，否则直接返回已有的 entrySet
    return (es = entrySet) == null ? (entrySet = new EntrySet()) : es;
}
```

#### keySet()

```java
/**
 * 返回此Map中所有key的Set集合.
 * 这个集合由map支持，所以任何对map的修改都会反映在这个集合上，反之亦然.
 * 如果在迭代器操作过程中修改了map（除了迭代器本身的删除方法或通过迭代器返回的map条目上的setValue操作外），迭代结果是不确定的
 * 该集合支持元素移除操作，该操作通过Iterator.remove、Set.remove、removeAll、retainAll、clear操作从映射中移除相应的映射关系.
 * 它不支持add或addAll操作.
 *
 * @return 包含此Map中key的Set集合
 */
public Set<K> keySet() {
    Set<K> ks = keySet;
    // 如果 keySet 为空，则创建一个新的 KeySet 并赋值给 ks，否则直接返回已有的 keySet
    if (ks == null) {
        ks = new KeySet();
        keySet = ks;
    }
    return ks;
}
```

#### values

```java
/**
 * 返回此Map中所有value的Set集合.
 * 这个集合由map支持，所以任何对map的修改都会反映在这个集合上，反之亦然.
 * 如果在迭代器操作过程中修改了map（除了迭代器本身的删除方法或通过迭代器返回的map条目上的setValue操作外），迭代结果是不确定的
 * 该集合支持元素移除操作，该操作通过Iterator.remove、Set.remove、removeAll、retainAll、clear操作从映射中移除相应的映射关系.
 * 它不支持add或addAll操作.
 *
 * @return 包含此Map中value的Set集合
 */
public Collection<V> values() {
    Collection<V> vs = values;
    // 如果 values 为空，则创建一个新的 Values 并赋值给 vs，否则直接返回已有的 values
    if (vs == null) {
        vs = new Values();
        values = vs;
    }
    return vs;
}
```

mysql-connector-java




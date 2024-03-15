# synchronized关键字

## 用法

### 修饰实例方法

给实例方法加上`synchronized`修饰时，锁住的是当前实例对象。这意味着在同一时间内，同一个实例对象的`synchronized`方法只能被一个线程执行，其他线程需要等待前一个线程执行完毕释放锁之后才能执行。这样可以确保在多线程环境下，对同一个实例对象的操作是线程安全的

```java
/**
 * 同步实例方法类
 */
@Data
public class SyncMethod {

    /**
     * 版本号
     */
    private int version = 0;

    /**
     * 更新版本号
     */
    @SneakyThrows
    public synchronized int updateVersion(){
        // 设置版本号
        this.version++;

        // 模拟执行其他业务逻辑...
        Thread.sleep(2000);

        // 返回更新后的版本号
        return this.version;
    }
}
```

```java
/**
 * 测试类
 */
public class Main {

    /**
     * 模仿多线程高并发环境
     */
    public static void main(String[] args) {
        // 创建任务对象
        SyncEntry sync = new SyncEntry();

        // 构建Runnable,针对同一个对象进行操作
        Runnable runnable = () -> {
            // 更新版本号
            int version = sync.updateVersion();
            // 打印版本号
            System.out.println(version);
        };

        // 基于Runnable构建线程对象
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);

        // 启动线程
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
```



### 修饰静态方法

给静态方法加上`synchronized`关键字的作用是确保在同一时间内只有一个线程能够执行该静态方法，以保证对共享资源的访问是线程安全的。

静态方法是属于类的方法，在类加载时就已经创建并分配内存，因此它们不会涉及到实例对象的创建，因此锁住的是该类的Class对象。

```java
/**
 * 同步静态方法类
 */
@Data
public class SyncStaticMethod {

    /**
     * 版本号
     */
    private static int version = 0;

    /**
     * 更新版本号
     */
    @SneakyThrows
    public static synchronized int updateVersion(){
        // 设置版本号
        version++;

        // 模拟执行其他业务逻辑...
        Thread.sleep(2000);

        // 返回更新后的版本号
        return version;
    }
}
```

```java
/**
 * 测试类
 */
public class Main {

    /**
     * 模仿多线程高并发环境
     */
    public static void main(String[] args) {
        // 构建Runnable,针对同一个Class的静态方法进行操作
        Runnable runnable = () -> {
            // 更新版本号
            int version = SyncStaticMethod.updateVersion();
            // 打印版本号
            System.out.println(version);
        };

        // 基于Runnable构建线程对象
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);

        // 启动线程
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
```



### 同步代码块(普通对象)

```java
/**
 * 同步代码块类
 */
@Data
public class SyncObject {

    /**
     * 版本号
     */
    private int version = 0;

    /**
     * 更新版本号
     */
    @SneakyThrows
    public int updateVersion(){
        // 同步代码块,给当前类对象上锁,只有拿到了当前类的锁才能执行代码块中的内容
        synchronized (this){
            // 设置版本号
            version++;

            // 模拟执行其他业务逻辑...
            Thread.sleep(2000);
        }
        // 返回更新后的版本号
        return version;
    }
}
```

```java
/**
 * 测试类
 */
public class Main {

    /**
     * 模仿多线程高并发环境
     */
    public static void main(String[] args) {
        // 创建任务对象
        SyncObject sync = new SyncObject();

        // 构建Runnable,针对同一个对象进行操作
        Runnable runnable = () -> {
            // 更新版本号
            int version = sync.updateVersion();
            // 打印版本号
            System.out.println(version);
        };

        // 基于Runnable构建线程对象
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);

        // 启动线程
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
```



### 同步代码块(静态对象)

```java
/**
 * 同步代码块类
 */
@Data
public class SyncStaticObject {

    /**
     * 版本号
     */
    private static int version = 0;

    /**
     * 更新版本号
     */
    @SneakyThrows
    public int updateVersion(){
        // 同步代码块,给当前Class对象上锁,只有拿到了当前类Class对象的锁才能执行代码块中的内容
        synchronized (SyncStaticObject.class){
            // 设置版本号
            version++;

            // 模拟执行其他业务逻辑...
            Thread.sleep(2000);
        }
        // 返回更新后的版本号
        return version;
    }
}
```

```java
/**
 * 测试类
 */
public class Main {

    /**
     * 模仿多线程高并发环境
     */
    public static void main(String[] args) {
        // 创建任务对象
        SyncStaticObject sync = new SyncStaticObject();

        // 构建Runnable,针对同一个对象进行操作
        Runnable runnable = () -> {
            // 更新版本号
            int version = sync.updateVersion();
            // 打印版本号
            System.out.println(version);
        };

        // 基于Runnable构建线程对象
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);

        // 启动线程
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
```

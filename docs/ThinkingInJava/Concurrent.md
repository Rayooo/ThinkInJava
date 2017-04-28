## 并发

### 基本的线程机制

1.定义任务，见concurrency/LiftOff类，实现runnable接口

2.Thread类，将Runnable对象转变为工作任务的传统方式是交给一个Thread构造器，见concurrency/LiftOffTest.java，通过start()方法调用

3.Executor，执行器，接管Thread对象，Executor允许异步任务的执行

-   FixedThreadPool一次性预先执行代价高昂的线程分配，限制线程的数量，节省时间
-   CachedThreadPool在执行过程中创建与所需数量相同的线程，然后在它回收旧线程时停止创建新线程，因此它是Executor的首选
-   SingleThreadExecutor就像是数量为1的FixedThreadPool，如果提交了多个任务，那么将会排队，每个任务都会在下一个任务开始之前运行结束，按照提交顺序执行，SingleThreadExecutor会序列化所有的提交给它的任务，并会维护它自己的悬挂任务队列，（文件系统中可以用到）

4.从任务中产生返回值，如果希望在任务完成时能返回一个值，可以实现Callable而不是Runnable接口，见concurrency/TaskWithResult，Future就是对于具体的Runnable或者Callable任务的执行结果进行取消、查询是否完成、获取结果。

5.休眠 见SleepingTask

6.设置优先级，优先级不会导致死锁，可以通过setPriority()设置，getPriority()得到现有线程的优先级

7.让步，yield()，对于任何重要的控制或在调整应用时，都不能过度依赖yield()

8.后台进程，见simpleDaemons，必须在线程启动前调用setDaemons()，才能设置为后台进程，当所有的非后台进程结束时，程序就终止了，会杀死所有的后台进程。TheadFactory可以定制由Executor创建的线程属性

9.join，一个线程可以在其他线程上调用join方法，其效果是等待一段时间，直到第二个线程结束才继续执行，如果某个线程在另一个线程t上调用t.join()，此线程将被挂起，直到目标线程结束才恢复，t.isAlive()返回false，join可以调用interrupt()中断

10.捕获异常，可以通过Executor

### 共享受限资源

1.防止不正确访问资源

2.解决共享资源的竞争

-   synchronized加锁，如果某个任务对标记synchronized的方法调用中，那么在这个线程返回之前，所有要调用类中任何标记synchronized方法的线程都会被阻塞
-   使用显示的Lock对象(lock = new ReentrantLock()  lock.lock()   lock.unlock()   )
-   ReentrantLock，见AttemptLocking，它提供了尝试获取但最终未获取锁，显示的Lock对象在加锁和释放锁方面比Synchronized提供了更细粒度的控制力

3.原子性和易变性

4.原子类（AtomicInteger, AtomicLong, AtomicReference）

5.临界区，同步控制块，在进入此段代码以前，必须得道syncObject对象的锁

```java
synchronized(syncObject){
  	//This code can be accessed by only one task at a time
  	//同一时刻只能有一个任务／线程访问它
}
```

6.在其他对象上同步,见SyncObject.java，DualSync.f()通过整个方法同步，而g()在syncObject上同步的synchronized块，这两个块是相互独立的

7.线程本地储存（线程分配自己的储存，不太理解）

### 终结任务

1.装饰性花园OrnamentalGarden，演示了并发递增一个值，一个线程控制一道门，花园中有多道门，Count计算进入花园总的人数。

2.中断线程，使用Executor的interrupt()，见Interrupting.java

### 线程之间的协作

1.调用sleep()和Thread.yield()时并没有释放锁，而当一个任务在方法里遇到了对wait()的调用的时候，线程的执行被挂起，对象上的锁被释放，wait()可以通过notify()或notifyAll()或者令时间到，从wait()恢复执行，wait是基类Object的一部分而不属于Thread的一部分，示例见WaxOMatic.java

2.生产者消费者例子，见Restaurant.java，代码有点问题

3.解决并发问题的构件

-   CountDownLatch用来同步一个或多个任务，强制它们等待由其他任务执行的一组操作完成
-   CyclicBarrier创建一组任务，他们并行地执行工作，然后在进行下一个步骤之前等待，直至所有任务都完成
-   DelayQueue，无界的BlockingQueue，用于放置实现了Delayed接口的对象，其中的对象只能在到期时才能从队列中取走，这种队列是有序的，即队头对象的延迟到期的时间最长。
-   PriorityBlockingQueue，一个优先级队列，它具有可阻塞的读取操作
-   ScheduledExecutor，可设置Runnable对象在将来某个时刻执行
-   Semaphore，正常的锁（concurrent.locks或synchronized锁）在任何时刻都只允许一个任务访问一项资源，而计数信号量允许N个任务同时访问这个资源（对象池的实现）
-   Exchanger，在两个任务之间交换对象的栅栏，当这些任务进入栅栏时，他们各自拥有一个对象，当他们离开时，他们都拥有之前由对象持有的对象，Exchanger的典型应用场景是：一个任务在创建对象，这些对象的生产代代价很高昂，而另一个任务在消费这些对象，通过这种方式，可以有更多的对象在被创建的同时被消费

### 免锁容器

1.通用策略：对于容器的修改和读取操作同时发生，只要读取者只能看到完成修改的结果即可。修改是在容器数据结构的某个部分的一个单独的副本（有的是整个数据结构的副本上）执行的，并且这个副本在修改过程中是不可视的。只有当修改完成时，被修改的结构才会自动地与主数据结构进行交换，之后读取者就可以看到这个修改了。

2.CopyOnWriteArrayList中，写入将导致创建整个底层数组的副本，而源数组将保留在原地，使得复制的数组在被修改时，读取操作可以安全地执行。当修改完成时，一个原子性的操作将新的数组换入，使得心得读取操作可以看到这个新的修改

3.CopyOnWriteArraySet将使用CopyOnWriteArrayList来实现免锁行为

4.ConcurrentHashMap和ConcurrentLinkedQueue使用了类似的技术

### volatile关键字

1.见volatileTest/volatileTest

http://www.ziwenxie.site/2017/04/24/java-multithread-volatile/

2.JVM内存模型。Java内存模型规定了所有的变量都储存在主内存中(即物理硬件的内存)，每条线程还具有自己的工作内存（工作内存可能位于处理器的高速缓存之中），线程的工作内存中保存了该线程使用到的变量的主内存副本拷贝，线程对变量的所有操作（读取，赋值等）都必须在工作内存中进行，而不能直接读写主内存的变量。不同线程之间无法访问对方工作内存之间的变量，线程间变量值的传递需要通过主内存来完成。在并发编程中，会遇到以下三个问题：原子性，可见性，有序性

3.有序性

```java
public class TestCase1 {

    public static int number;

    public static boolean isinited;

    public static void main(String[] args){
        new Thread(() -> {
            while (!isinited){
                Thread.yield();
            }
            System.out.println(number);
        }).start();
        number = 20;
        isinited = true;
    }

}
```

对于上面的代码，我们本意是想输出20，而可能会输出0。因为有时候为了提高程序的效率，JVM会做进行及时编译，也就是进行指令重排序，将isInited = true 放在 number = 20 前面执行，这样在单线程下面没有问题，而在多线程情况下就会出现重排序问题。如果将 number 声明为 volatile 就可以很好地解决这个问题，这可用静止JVM进行指令重排序

4.可见性，对于变量a，当线程1要修改a的值，首先要将a的值从主内存复制过来，然后将a的值+1，再将a的值复制回主内存。在单线程情况下，没有任何问题。但是在多线程情况下，还有个线程2，在线程1修改a的值的时候，线程2也从a的值复制过来+1，随后线程1和线程2先后将a的值复制回主内存，但是主内存a的值最终只是+1而不是+2。使用volatile可以解决这个问题，可以保证在线程1修改a的值之后立即将修改值同步到主内存中，这样线程2拿到的a的值就是线程1已经修改过的值了

5.原子性，原子性值CPU在执行一条语句的时候，不会中途转去执行另外的语句。如 i = 1 是一个原子操作，而 ++i 就不是一个原子操作了，因为它首先读取i的值，再修改i的值，再写入主存

```java
    public volatile int v = 0;

    public static final int threadCount = 10;

    public void increase() {
        v++;                                //++为非原子操作
    }

    public synchronized void increaseSync(){
        v++;
    }

    Lock lock = new ReentrantLock();

    public void increaseLock(){
        lock.lock();
        try {
            v++;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args){
        TestCase2 testCase = new TestCase2();

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
//                    testCase.increase();
//                    testCase.increaseSync();
                    testCase.increaseLock();
                }
            }).start();
        }

        while (Thread.activeCount() > 1){
            Thread.yield();
        }

        System.out.println(testCase.v);
        //若采用 increase() 无法到达10000，测试为9364
        //采用increaseSync() 可到达10000
        //采用increaseLock() 可达到10000


    }

}
```

volatile 不能保证程序的原子性，调用increase() 方法无法达到10000，因为v++并不是一个原子操作，使用synchronized则可以很好地解决，或者用lock也可以

如何保证unlock()一定能够解锁呢？只需要放到finally块中，unlock()的机制保证一定能够解锁，而前面的操作万一抛出异常了，就不能执行到unlock()的方法

6.单例模式

```java
public class Singleton {

    //volatile的单例模式
    private volatile static Singleton instance;

    public static Singleton getInstance(){
        if(instance == null){
            synchronized (Singleton.class){
                if(instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args){
        Singleton.getInstance();
    }

}
```

instance 必须要用volatile 修饰，因为new Singleton分为三个步骤

1.  给instance指向的对象分配内存，并设置初始值为null（根据JVM类加载机制的原理，对于静态变量这一步应该在 new Singleton 之前就已经完成了）。
2.  执行构造函数真正初始化instance
3.  将instance指向对象分配内存空间（分配内存空间之后instance就是非null了）

步骤2，3可以颠倒，如果线程1在执行步骤3后没有执行步骤2，而是被线程2抢占了，线程2得到的instance是非null的，但是instance却还没有初始化，使用volatile可以保证程序的有序性
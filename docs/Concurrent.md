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
进程：程序运行的最小单位
线程：由CPU调度，是进程运行的最小单位

CPU执行一条指令（1.6G）0.6纳秒

并行：多个线程同时进行多个任务
并发：单线程运行多个任务（单位时间内的并发量），交替进行


高并发编程的意义：充分利用CPU资源，加快相应用户的时间。使代码模块化，异步化，简单化。
JDK线程是协作式的，不是所谓的抢占式
启动线程的三种方式：类Thread，接口Runnable，第三种其实使用的还是Runnable
类Thread，在java中对线程的抽象，而接口Runnable是对任务的抽象
第三种实现线程的方式：实现Callable(有返回值)，其实Callable是jdk内部把Runnable包装成了一个Callable，Callable中调用Call()方法，Call()方法中调用Runnable的run()方法，通过RunnableAdapter(实现类)这个适配器来实现创建线程，

stop():强制停止线程，导致资源无法释放。

interrupt():发起中断线程(不是真真正正的终止一个线程，线程不一定停止)！！！！！

interrupted():判断当前线程是否被中断，最后会修改标识状态位false
interrupted()是静态方法：内部实现是调用的当前线程的isInterrupted()，并且会重置当前线程的中断状态

isInterrupted():判断当前线程是否被中断,最后中断标识位位true
isInterrupted()是实例方法，是调用该方法的对象所表示的那个线程的isInterrupted()，不会重置当前线程的中断状态

(yieId:将线程从运行转到可运行状态，让当前线程让出CPU的占有权)，运行调用seep或wait进行阻塞，seep时间到或者使用interrupt方法进入就绪状态，wait使用notify或notifyAll进入就绪状态，run结束或stop,setDaemon，死亡

volatile关键字，最轻量的同步机制，保证可见性（旧值被更新为新值的时候，可以保证这个新值被其他线程看到）

ThreadLocal和syn的区别：syn利用锁的机制让线程在某一时刻只有一个线程去访问某一段代码或某个变量，ThreadLocal为每一个线程提供一个变量的副本，每个线程都有一个变量的副本，每一个变量都访问自己的副本，这样就实现了线程的隔离，这样就保证了线程的安全性
spring在实现事务的时候用使用了ThreadLocal，(@Transactional)

处于死锁状态的线程，是不会理会中断的

线程的生命周期：新建start，就绪状态，join获取执行权
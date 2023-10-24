package com.integration.demo.threadlocal;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal造成的内存泄漏演示
 */
public class ThreadLocalOOM {

    private static final int TASK_LOOP_SIZE = 500;

    //五个线程在这跑
    final static ThreadPoolExecutor poolExecutor  = new ThreadPoolExecutor(5,5,1, TimeUnit.MINUTES,new LinkedBlockingQueue<>());

    static class LocalVariable{
        private byte[] a = new byte[1024*1024*5];//5M大小的数组
    }

    final static ThreadLocal<LocalVariable> localVariable = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < TASK_LOOP_SIZE; i++) {
            //500个任务
            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {

                    //1.我们有5个线程，一个线程5M，通过jdk提供的 java VisualVm，监控可以看出堆的使用大致在25M左右
                    //new LocalVariable();
                    System.out.println("use local varaible");
                    //2.如果单独使用localVariable.set()不使用remove()的话线程的堆使用会飙升175M，两者对比，发现有150M内存丢失了
                    //原因：set源码中WeakReference()弱引用
                    //set中也有清除key为空的资源，但是不及时
                    localVariable.set(new LocalVariable());
                    //2.使用remove()之后恢复25M左右 当使用remove之后，ThreadLocal为null,找不到指向，所以key会被回收,只能通过线程内部持有的map来找到指向的value，但这时value时强引用的值，也访问不到，所以会发生内存泄露的问题，只有线程被回收了，value才会被回收
                    localVariable.remove();
                }
            });
            Thread.sleep(100);
        }
        System.out.println("pool execute over");
    }

    //强引用：object o = new object  o是在栈中，new object的实例在堆中，这种属于强引用，如果栈上面有一个引用指向了堆中的实例，堆中的实例就不会被gc回收，这就是强引用
    //软引用：当要发生内存溢出的时候，被SoftReference包围的，虚拟机认为是可回收的，先进行回收一次，再进行回收
    //弱引用：只要发生了gc回收，就会被回收掉，在堆上的实例就一定被回收
    //虚引用：发生gc的时候，通知下相关的调用，被回收了，最弱的引用方式
}

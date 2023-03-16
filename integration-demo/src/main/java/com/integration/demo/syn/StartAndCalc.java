package com.integration.demo.syn;

/**
 * 类说明：类锁和锁static变量也是不同的
 *
 * @author cyh
 * @date 2023.03.15
 */
public class StartAndCalc {

    private static class SynClass extends Thread {
        @Override
        public void run() {

            synClass();
        }
    }

    private static class SynStatic extends Thread {
        @Override
        public void run() {

            synStatic();
        }
    }

    //锁的是静态方法，加在静态方法上面，锁的是我们类的class对象，所以如果synClass()方法和synStatic()方法分别有两个线程调用，也是可以并行的
    private static synchronized void synClass() {
        System.out.println(Thread.currentThread().getName() + ":synClass is running...");
        //SleepTools.second(1); 主线程停止1秒
        System.out.println(Thread.currentThread().getName() + ":SynClass is running...");
    }

    private static Object obj = new Object();

    //类锁 每一个类，在类加载的时候，都会在虚拟机里面都会有一个class对象，即class对象加锁
    //类锁和对象锁不冲突
    private static void synStatic() {
        synchronized (obj) {
            System.out.println(Thread.currentThread().getName() + ":synStatic is running...");
            //SleepTools.second(1); 主线程停止1秒
            System.out.println(Thread.currentThread().getName() + ":synStatic is running...");
        }
    }

    public static void main(String[] args) {
        //和对象锁同理，如果两个线程调用同一个实例对象，一定是其中一个线程先执行完，另一个才会执行完，因为使用的对象锁，如果两个线程锁不同的对象实例，是可以并行的
        StartAndCalc startAndCalc = new StartAndCalc();
        SynClass t1 = new SynClass();
        SynStatic t2 = new SynStatic();
//        SynClass t2 = new SynClass();
        t2.start();
        //SleepTools.second(1); 主线程停止1秒
        t1.start();
    }
}

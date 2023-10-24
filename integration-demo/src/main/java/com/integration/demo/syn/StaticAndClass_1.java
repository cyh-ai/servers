package com.integration.demo.syn;

/**
 * 类锁和锁static变量也是不同的
 */
public class StaticAndClass_1 {

    private static class SynClass extends Thread{
        @Override
        public void run() {
            try {
                SynClass();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class SynStatic extends Thread{

        @Override
        public void run() {
            try {
                SynStatic();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //锁的是静态方法，加在静态方法上年，锁的是我们类的class对象，所以如果synClass()方法和synStatic()方法分别有两个线程调用，也是可以并行的
    private static synchronized void SynClass() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName()+"类锁运行中........");
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName()+"类锁运行结束......");
    }

    private static Object object = new Object();

    //类锁 每一个类，在类加载的时候，都会在虚拟机中有一个class对象，即class对象加锁
    private static void SynStatic() throws InterruptedException {
        synchronized (object){
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"锁static运行中........");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"锁static运行结束......");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //如果两个线程调用同一个实例对象，一定是其中一个线程先执行完，另一个才会执行，如果两个线程锁不同的对象实例，是可以并行的
        StaticAndClass_1 staticAndClass_1 = new StaticAndClass_1();
        SynClass synClass = new SynClass();
        SynStatic synStatic = new SynStatic();
        synStatic.start();
        Thread.sleep(1000);
        synClass.start();
    }
}

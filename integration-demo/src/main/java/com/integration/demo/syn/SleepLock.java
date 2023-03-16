package com.integration.demo.syn;

/**
 * @author cyh
 * @date 2023.03.15
 * 类说明：测试sleep对锁的影响
 * 总结：两个线程，如果同时都有sleep，影响锁的时间几乎为两个线程的sleep的时间差，如果只有一个线程有sleep，则对另一个线程的sleep影响相对时间
 */
public class SleepLock {

    private Object lock = new Object();

    public static void main(String[] args) {
        SleepLock sleepTest = new SleepLock();
        Thread threadA = sleepTest.new ThreadSleep();
        threadA.setName("线程睡眠");
        Thread threadB = sleepTest.new ThreadNotSleep();
        threadB.setName("线程不睡眠");
        threadA.start();
        try {
            Thread.sleep(4000);
            System.out.println("主睡眠!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadB.start();
    }

    private class ThreadSleep extends Thread {
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + "会拿走锁");
            try {
                synchronized (lock) {
                    System.out.println(threadName + "拿走锁");
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        }
    }

    private class ThreadNotSleep extends Thread {
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + "会占用锁定时间" + System.currentTimeMillis());
            synchronized (lock) {
                System.out.println(threadName + "占用锁定时间" + System.currentTimeMillis());
                System.out.println("完成工作:" + threadName);
            }
        }
    }
}

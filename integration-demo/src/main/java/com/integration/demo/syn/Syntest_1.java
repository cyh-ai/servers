package com.integration.demo.syn;

/**
 * @author cyh
 * syn关键字的使用方法
 */
public class Syntest_1 {
    private long count = 0;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    private Object object = new Object();

    //第一种解决方式：单独声明一个对象，对象本身当锁，对象当锁称为同步块（对象锁）
    public void incCount(){
        synchronized (object){
            count++;
        }
    }

    //第二种解决方式：在方法上加锁，称为同步方法
    public synchronized void incCount2(){
        count++;
    }
    //第三种解决方式：使用this本身当锁，作用和声明的对象一样，用自身的实例当锁（对象锁）
    public void incCount3(){
        synchronized (this){
            count++;
        }
    }

    private static class Count extends Thread{

        private Syntest_1 syntest_1;

        public Count(Syntest_1 syntest_1){
            this.syntest_1 = syntest_1;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                //使用对象锁来解决线程安全的问题
                //syntest_1.incCount();

                //在方法上加锁，同步方法，解决线程安全问题
                syntest_1.incCount2();

                //使用自身实例对象当锁，来解决线程安全问题.
                syntest_1.incCount3();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Syntest_1 syntest_1 = new Syntest_1();
        //启动两个线程，因为线程安全问题，导致count值相加不为20000

        Count count1 = new Count(syntest_1);
        Count count2 = new Count(syntest_1);
        count1.start();
        count2.start();
        Thread.sleep(100);
        System.out.println(syntest_1.count);
    }
}

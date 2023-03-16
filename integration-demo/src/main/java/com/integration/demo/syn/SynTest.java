package com.integration.demo.syn;

/**
 * @author cyh
 * @date 2023.03.14
 * synchronized关键字的使用方法
 */
public class SynTest {

    private long count = 0;


    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    private Object obj = new Object();

    /*用在同步块上*/
    public void incCount() {
        //使用对象本身当锁
        synchronized (obj) {
            count++;
        }
    }

    /*用在方法上*/
    public synchronized void incCount2() {
        count++;
    }

    //count进行累加
    public void incCount3() {
        synchronized (this) {
            count++;
        }
    }

    private static class Count extends Thread {
        private SynTest simplOper;

        public Count(SynTest simplOper) {
            this.simplOper = simplOper;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                //调用incCount()方法，如果incCunt()方法不加锁，会导致count结果不为20000
                //第二种，单独声明一个对象，对象本身当锁，对象当锁称为同步块(对象锁)
                simplOper.incCount();
                //第一种，在方法上加锁，在方法上称为同步方法，
                simplOper.incCount2();//count = count+10000
                //第三种，使用this本身当锁，作用和声明的对象一样，用自身的实例当锁(对象锁)
                simplOper.incCount3();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynTest simplOper = new SynTest();
        //启动两个线程，因为线程安全的问题，导致count的值不为20000

        Count count1 = new Count(simplOper);
        Count count2 = new Count(simplOper);
        count1.start();
        count2.start();
        Thread.sleep(50);
        System.out.println(simplOper.count);//20000
    }
}

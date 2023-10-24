package com.integration.demo.vola;

import org.aspectj.weaver.ast.Not;

public class NotSafe {

    //该变量使用volatile关键字，不使用syn关键字，并不能保证最后两个线程的和为20000，所以不能保证线程原子性一直，线程不安全
    //只有一个线程写，其他线程只读的情况话使用vola关键字，这种场景适合vola关键字
    private volatile long count = 0;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void incCount(){
        count++;
    }

    private static class Count extends Thread{

        private NotSafe simplOper;

        public Count(NotSafe simplOper){
            this.simplOper = simplOper;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                simplOper.incCount();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NotSafe notSafe = new NotSafe();
        Count count1 = new Count(notSafe);
        Count count2 = new Count(notSafe);
        count1.start();
        count2.start();
        Thread.sleep(50);
        System.out.println(notSafe.count);
    }

}

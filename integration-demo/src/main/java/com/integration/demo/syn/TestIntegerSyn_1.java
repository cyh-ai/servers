package com.integration.demo.syn;

/**
 * 关于错误的加锁和原因分析
 */
public class TestIntegerSyn_1 {

    private static class Worker implements Runnable{

        private Integer i;

        public Worker(Integer i){
            this.i = i;
        }

        private Object object = new Object();

        @Override
        public void run() {

            //synchronized (i){
            //2.该地方可是创建object对象来控制加锁，来解决对象实例发生改变的问题
            synchronized (object){
                Thread thread = Thread.currentThread();
                System.out.println(thread.getName()+"-------"+System.identityHashCode(i));
                //1.通过Java反编译可以发现，i++调用Integer中的valueOf方法，该方法会重新创建对象，所以使用syn锁i，这个地方还是会发生变化
                i++;
                System.out.println(thread.getName()+"---"+i+"---"+System.identityHashCode(i));
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(thread.getName()+"---"+i+"---"+System.identityHashCode(i));
            }
        }
    }

    public static void main(String[] args) {
        Worker worker = new Worker(1);
        //循环开启五个线程
        for (int i = 0; i < 5; i++) {
            new Thread(worker).start();
        }
    }
}

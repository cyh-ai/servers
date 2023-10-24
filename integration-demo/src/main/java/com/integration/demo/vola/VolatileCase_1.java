package com.integration.demo.vola;

/**
 * 演示volatile关键字的可见性
 */
public class VolatileCase_1 {

    //当ready加volatile关键字后，子线程运行的过程中，能感知到主线程中ready的赋值结果
    private volatile static boolean ready;

    private static int number;

    private static class PrintThread extends Thread{
        @Override
        public void run() {
            System.out.println("子线程正在进行中..........");
            while (!ready);
            System.out.println("number = "+number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PrintThread printThread = new PrintThread();
        printThread.start();
        Thread.sleep(3000);
        number = 51;
        //如果ready不加volatile关键，子线程运行过程中，主线程赋值为true时，子线程是感知不到ready的变化的
        ready = true;
        Thread.sleep(3000);
        System.out.println("主方法结束");
    }
}

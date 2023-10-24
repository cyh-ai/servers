package com.integration.demo.vola;

/**
 * 演示volatile提供的可见性
 */
public class VolatileCase {

    //如果不加volatile 子线程调用时，感知不到ready为true，number不会打印
    //加了volatile后，子线程就能看到主线程中ready的值，调用时，while循环就能进行判断，否则，就会无限循环
    //但该关键无法取代syn关键字，只是保证可见性
    private volatile static boolean ready;
    private static int number;

    private static class PrintThread extends Thread{
        @Override
        public void run() {
            System.out.println("PrintThread 进行中............");
            while (!ready);
            System.out.println("number="+number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new PrintThread().start();
        Thread.sleep(5000);
        number = 51;
        ready = true;
        Thread.sleep(5000);
        System.out.println("主方法结束");
    }
}

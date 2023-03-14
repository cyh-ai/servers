package com.integration.demo.xc;

/**
 * @author cyh
 * @date 2023.03.11
 * 停止实现Runnable接口的线程(使用标识位来停止)
 */
public class EndRunnable {


    private static class UserRunnable implements Runnable {

        @Override
        public void run() {
            //实现Runnable接口的线程中没有isInterrupted()方法，但可以通过当前线程获取对应isInterrupted()方法
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("线程名称：" + Thread.currentThread().getName() + "实现了Runnable接口,循环中的标识：" + Thread.currentThread().isInterrupted());
            }
            System.out.println(Thread.currentThread().getName() + "线程的标识状态为：" + Thread.currentThread().isInterrupted());
        }

        public static void main(String[] args) throws InterruptedException {
            UserRunnable userRunnable = new UserRunnable();
            Thread thread = new Thread(userRunnable, "程亚辉");
            thread.start();
            Thread.sleep(200);
            thread.interrupt();
        }
    }
}

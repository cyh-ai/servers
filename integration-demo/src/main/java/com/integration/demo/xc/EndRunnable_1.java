package com.integration.demo.xc;

/**
 * @author cyh
 * 实现Runnable接口线程的停止线程方法（通过标识位来停止）
 */
public class EndRunnable_1 {

    private static class UserRunnable implements Runnable{
        @Override
        public void run() {
            //实现Runnable接口的线程中没有isInterrupted()方法，但是可以通过获取当前多线程的isInterrupted()方法来判断对应的标识位
            System.out.println("循环前的标识位状态："+Thread.currentThread().isInterrupted());
            while (!Thread.currentThread().isInterrupted()){
                System.out.println("线程名称："+Thread.currentThread().getName());
                System.out.println("标识位状态："+Thread.currentThread().isInterrupted());
            }
            System.out.println("循环后的标识位状态："+Thread.currentThread().isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UserRunnable userRunnable = new UserRunnable();
        Thread thread = new Thread(userRunnable,"程亚辉");
        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
    }
}

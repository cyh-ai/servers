package com.integration.demo.xc;

/**
 * @author cyh
 * 创建线程练习
 */
public class NewThread_1 {

    /**
     * 继承Thread类
     */
    private static class UserThread extends Thread{
        @Override
        public void run() {
            System.out.println("我继承了一个线程");
        }
    }

    /**
     * 实现Runnable接口
     */
    private static class UserRunnable implements Runnable{
        @Override
        public void run() {
            System.out.println("我实现了一个线程接口");
        }
    }

    public static void main(String[] args) {
        //两种线程的创建和调用
        UserThread userThread = new UserThread();
        userThread.start();

        UserRunnable userRunnable = new UserRunnable();
        new Thread(userRunnable).start();
    }
}

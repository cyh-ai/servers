package com.integration.demo.xc;

/**
 * @author cyh
 * @date 2023.03.10
 * 创建线程
 */
public class NewThread {


    /**
     * 集成Thread类
     */
    private static class UserThread extends Thread {
        //重写父类run方法
        @Override
        public void run() {
            System.out.println("我继承了线程");
        }
    }

    /**
     * 实现Runnable接口
     */
    private static class UserRunnable implements Runnable {
        //重载run方法
        @Override
        public void run() {
            System.out.println("我实现了一个线程");
        }
    }

    public static void main(String[] args) {
        //两种线程的创建于调用
        //继承Thread类
        UserThread userThread = new UserThread();
        userThread.start();
        //实现Runnable接口
        UserRunnable userRunnable = new UserRunnable();
        new Thread(userRunnable).start();
    }
}

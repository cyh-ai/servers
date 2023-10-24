package com.integration.demo.xc;

/**
 * @author cyh
 * 继承Thread类时停止线程的方法（使用标识位来停止）
 */
public class EndThread_1 {

    private static class UserThread extends Thread {

        public UserThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            //获取当前线程名称
            String name = Thread.currentThread().getName();
            //获取当前线程循环之前的标识状态
            System.out.println("循环前的标识为"+isInterrupted());
            //Thread.interrupted() 获取线程标识位的静态方法，会修改线程标识位的状态为false
            //while (!Thread.interrupted()) {
            //isInterrupted() 获取线程标识位的实例方法，当线程状态被改为true后，不会修改线程的状态
            while (!isInterrupted()) {
                System.out.println("线程名称" + name);
                System.out.println("循环中的标识位" + isInterrupted());
            }
            System.out.println("线程名称：" + name + ",循环结束后的标识状态：" + isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UserThread userThread = new UserThread("程亚辉");
        userThread.start();
        Thread.sleep(2000);
        //告诉线程，停止线程，实际效果时通知线程，线程不一定停止
        userThread.interrupt();
    }

}

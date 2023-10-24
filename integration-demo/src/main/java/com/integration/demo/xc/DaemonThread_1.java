package com.integration.demo.xc;

/**
 * @author cyh
 * 守护线程,设置守护线程
 */
public class DaemonThread_1 {

    private static class UserThread extends Thread{
        @Override
        public void run() {
            try {
                while (!isInterrupted()){
                    System.out.println("线程名称"+Thread.currentThread().getName());
                }
                System.out.println("线程"+Thread.currentThread().getName()+",isInterrupted:"+isInterrupted());
            }finally {
                //当线程被设置为守护线程后，finally有时候可能不一定有效果，看CPU分配，有资源就执行，没资源就不执行
                System.out.println("---------finally");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //创建的线程为用户线程，属于非守护线程
        UserThread userThread = new UserThread();
        //如果想设置一个线程为守护线程，设置线程的setDaemon(true)即可使线程变为守护线程
        userThread.setDaemon(true);
        //线程也可以设置对应的优先级，但是要根据操作系统来设置，一般线程的优先级有三个值，1 10 5，默认等级为5
        //userThread.setPriority(1);
        userThread.start();
        Thread.sleep(5);
        userThread.interrupt();
    }
}

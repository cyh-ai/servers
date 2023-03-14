package com.integration.demo.xc;

/**
 * @author cyh
 * @date 2023.03.11
 * 守护线程，设置守护线程
 */
public class DaemonThread {

    private static class Userthread extends Thread {
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    System.out.println("线程：" + Thread.currentThread().getName() + "继承了Thread线程");
                }
                System.out.println("线程：" + Thread.currentThread().getName() + "isInterrupted标识是：" + isInterrupted());
            } finally {
                //如果设置了线程为守护线程后，finally就不一定有效果了，看cpu分配了，如果分配了资源就会执行
                System.out.println(".................finally");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //创建的线程为用户线程，属于非守护线程
        Userthread userthread = new Userthread();
        //如果想设置一个线程为守护线程，设置线程的setDaemon(true)方法即可使线程改变为守护线程，setDaemon默认为false
        //userthread.setDaemon(true);
        //线程也可以设置优先级，一般有三个值，1到10 默认为5,意义不大，不能用来区分那个先调用
        //userthread.setPriority(1);
        userthread.start();
        Thread.sleep(5);
        userthread.interrupt();
    }
}

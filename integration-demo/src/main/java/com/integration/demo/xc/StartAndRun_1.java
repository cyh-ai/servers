package com.integration.demo.xc;

/**
 * @author cyh
 * start()和Run()方法的区别
 */
public class StartAndRun_1 {

    public static class UserThread extends Thread{
        @Override
        public void run() {
            int i = 90;
            while (i>0){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
                System.out.println("我是线程："+Thread.currentThread().getName()+"，i的值："+i--);
            }
        }
    }

    public static void main(String[] args) {
        UserThread userThread = new UserThread();
        userThread.setName("程亚辉");
        //如果线程userThread直接调用start()方法，可以看出当前是创建的程亚辉线程在调用run方法来执行
        userThread.start();
        //如果线程userThread直接调用run()方法，是当前主线程main在调用run方法，而并非当前创建的新线程在调用
        userThread.run();
    }
}

package com.integration.demo.xc;

/**
 * @author cyh
 * 练习join的调用
 */
public class UserJoin_1 {

    private static class wk implements Runnable{

        private Thread thread;

        public wk(Thread thread){
            this.thread = thread;
        }

        @Override
        public void run() {
            System.out.println("王凯开始排队打饭........");
            try {
                if (thread!=null){
                    thread.join();
                }
            }catch (InterruptedException e){
                try {
                    Thread.sleep(2000);
                }catch (InterruptedException o){
                    o.printStackTrace();
                }

            }
            System.out.println(Thread.currentThread().getName()+"王凯打饭完成");
        }
    }

    private static class ls implements Runnable{
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("李帅开始打饭.....");
            System.out.println(Thread.currentThread().getName()+"李帅打饭完成");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread cyh= Thread.currentThread();
        ls ls = new ls();
        Thread lsThread = new Thread(ls);
        wk wk = new wk(lsThread);
        Thread wkThread = new Thread(wk);
        wkThread.start();
        lsThread.start();
        System.out.println("程亚辉开始打饭.....");
        wkThread.join();
        System.out.println(Thread.currentThread().getName()+"程亚辉打饭完成");
    }
    //通过join方法，可以使县城有序调用
}

package com.integration.demo.xc;

/**
 * @author cyh
 * @date 2023.03.11
 * join的调用
 */
public class UserJoin {


    static class Goddess implements Runnable {
        private Thread thread;

        public Goddess(Thread thread) {
            this.thread = thread;
        }

        public Goddess() {
        }

        @Override
        public void run() {
            System.out.println("Goddess开始排队打饭.....");
            try {
                if (thread != null) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                //SleepTools.second(2);//休眠2秘System
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException o) {
                    o.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "Goddess打饭完成，");
            }

        }
    }

    static class GoddessBoyfriend implements Runnable {
        @Override
        public void run() {
            //sleepTools.second(2);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("GoddessBoyfriend开始排队打饭.....");
            System.out.println(Thread.currentThread().getName() + "GoddessBoyfriend打饭成，");
        }
    }

    public static void main(String[] args) throws Exception {
        Thread lison = Thread.currentThread();//现在previous是主线程
        GoddessBoyfriend goddessBoyfriend = new GoddessBoyfriend();
        Thread gbf = new Thread(goddessBoyfriend);
        Goddess goddess = new Goddess(gbf);
        //Goddess goddess = new Goddess();
        Thread g = new Thread(goddess);
        g.start();
        gbf.start();
        System.out.println("lison开始排队打饭.....");
        g.join();
        //sleepTools.second(2);//让主线程休眠2秘
        System.out.println(Thread.currentThread().getName() + "lison打饭完成");
    }

    //通过join方法，可以使线程有序调用
}

package com.integration.demo.xc;

/**
 * @author cyh
 * @date 2023.03.11
 * 线程使用isInterrupted()方法后，不能使用相关线程的阻塞方法：sleep,wait,join等
 */
public class HasInterruptException {

    private static class UserThread extends Thread {

        public UserThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //线程名称：程亚辉,我的isInterrupted标识状态是：false
                    //打印的标识状态为false，实际应该是true，因为使用阻塞方法，导致失效
                    System.out.println("线程名称：" + Thread.currentThread().getName() + ",我的isInterrupted标识状态是：" + isInterrupted());
                    //如果在线程中使用标识状态出现这种问题，可以手动在catch中手动调用interrupt()方法，来该表标识为true，线程就会中断，资源释放
                    interrupt();
                    e.printStackTrace();
                }
                System.out.println("我是线程：" + Thread.currentThread().getName());
            }
            System.out.println(Thread.currentThread().getName() + "isInterrupted标识是" + isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UserThread userThread = new UserThread("程亚辉");
        userThread.start();
        Thread.sleep(500);
        userThread.interrupt();
    }
}

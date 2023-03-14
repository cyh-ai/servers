package com.integration.demo.xc;

/**
 * @author cyh
 * @date 2023.03.11
 * 停止继承Thread线程的方法(使用标识位来停止)
 */
public class EndThread {

    private static class UserThread extends Thread {

        //创建构造方法，并调用父类构造方法
        public UserThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            //获取当前线程名称
            String name = Thread.currentThread().getName();
            //获取当前线程循环之前的标识状态
            //isInterrupted()方法 线程的标识状态，true或false
            System.out.println("当前线程名称：" + name + ",循环前的标识状态：" + isInterrupted());
            //isInterrupted()方法，属于实例方法，调用该方法的对应所标识状态会被，不会重置当前线程的中断状态，即标识状态被修改过后，一直为true
            //while (!isInterrupted()){
            //interrupted()方法，属于线程的静态方法，底层会重置点给钱线程的中断状态，即标识状态被修改为true后，会重置为false
            while (!Thread.interrupted()) {
                System.out.println("线程名称：" + name);
                System.out.println("循环中的标识状态：" + isInterrupted());
            }
            //打印循环结束后的标识状态值
            System.out.println("线程名称：" + name + ",循环结束后的标识状态：" + isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UserThread userThread = new UserThread("程亚辉");
        userThread.start();
        Thread.sleep(200);
        //告诉线程，停止线程，但实际作用是通知线程，线程不一定会停止
        userThread.interrupt();
    }

    /**
     * 总结 jdk中线程是协作式的，并发抢占式的，
     * stop()方法 可以用来停止线程，但是属于强制停止线程的方法，会导致资源无法完全释放
     * 使用interrupt()方法，设置线程的标识，虽然线程不一定立即停止，但可以根据isInterrupted()方法，判断线程标识的状态从而停止线程
     */
}

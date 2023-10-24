package com.integration.demo.threadlocal;

public class NoThreadLocal {

    static Integer count = new Integer(1);

    //运行3个线程
    public void StartThreadArray(){
        Thread[] threads = new Thread[3];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new TestTask(i));
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }

    //测试线程，线程的工作是将ThreadLocal变量的值变化，并写回，看看先成直线是否会互
    public static class TestTask implements Runnable{

        int id;

        public TestTask(int id){
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+":开始");
            count = count+id;
            System.out.println(Thread.currentThread().getName()+":结束"+count);
            //threadLocal.remove();清空值
        }
    }

    public static void main(String[] args) {
        NoThreadLocal noThreadLocal = new NoThreadLocal();
        noThreadLocal.StartThreadArray();
        //会发现打印的结果不对，通过UserThreadLocal类可以总结出，ThreadLocal可以保证线程在使用ThreadLocal中定义的变量时，可以拥有自己变量的副本，自己管理自己
    }
}

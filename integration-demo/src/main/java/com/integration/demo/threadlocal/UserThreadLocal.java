package com.integration.demo.threadlocal;

/**
 * ThreadLocal的使用
 */
public class UserThreadLocal {

    //1.声明变量 进行初始化
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };


    //运行3个线程
    public void StartThreadArray(){
        Thread[] threads = new Thread[3];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new TestThread(i));
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }

    //测试线程，线程的工作是将ThreadLocal变量的值变化，并写回，看看先成直线是否会互
    public static class TestThread implements Runnable{

        int id;

        public TestThread(int id){
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+":开始");
            //研究get()的底层
            Integer s = threadLocal.get();
            s = s+id;
            threadLocal.set(s);
            System.out.println(Thread.currentThread().getName()+":结束"+threadLocal.get());
            //threadLocal.remove();清空值
        }
    }

    public static void main(String[] args) {
        UserThreadLocal userThreadLocal = new UserThreadLocal();
        userThreadLocal.StartThreadArray();
    }

}

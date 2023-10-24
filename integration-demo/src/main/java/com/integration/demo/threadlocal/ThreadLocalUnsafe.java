package com.integration.demo.threadlocal;

/**
 * ThreadLocal的线程不安全演示
 */
public class ThreadLocalUnsafe implements Runnable {

    public  Number number = new Number(0);


    @Override
    public void run() {
        //每个线程计数加一
        number.setNum(number.getNum()+1);
        //将其存储到ThreadLocal中
        value.set(number);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //输出值
        System.out.println(Thread.currentThread().getName()+"="+value.get().getNum());
    }

    public static ThreadLocal<Number> value = new ThreadLocal<Number>(){};

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new ThreadLocalUnsafe()).start();
        }
        //总结：static Number number是静态，共享导致每个线程的ThreadLocalMap中存的都是同一个对象的引用
        //解决方法，1.去除static关键字，不进行共享
        //2.将public  Number number = new Number(0);换成ThreadLocal，并调用ThreadLocal中的initialValue()方法进行初始化
    }

    private static class Number{

        private int num;

        public Number(int num){
            this.num = num;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}

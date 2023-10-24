package com.integration.demo.syn;

/**
 * 错误示范，如果锁Integer且++的场景，会出现新的对象
 */
public class TestIntegerSyn {

    public static void main(String[] args) {
        Worker worker = new Worker(1);
        for (int i = 0; i < 5; i++) {
            new Thread(worker).start();
        }
    }

    private static class Worker implements  Runnable{

        private Integer i;
        private Object o = new Object();

        public Worker(Integer i){
            this.i = i;
        }


        @Override
        public void run() {
            //synchronized (i){
            synchronized (o){
                Thread thread = Thread.currentThread();
                System.out.println(thread.getName()+"------------@"+System.identityHashCode(i));
                //i++反编译后使用的是Integer中的valueOf方法，会重新创建对象，所以如果synchronized锁的是i，这个地方对象依旧会发生改变.
                i++;
                System.out.println(thread.getName()+"-------"+i+"-----@@"+System.identityHashCode(i));
                try {
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(thread.getName()+"----"+i+"--------@@@"+System.identityHashCode(i));
            }
        }
    }
}

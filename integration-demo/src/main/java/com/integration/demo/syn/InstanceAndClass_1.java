package com.integration.demo.syn;

/**
 * @author cyh
 * 演示实例锁和类锁是不同的，两者可以并行
 */
public class InstanceAndClass_1 {

    private static class SynClass extends Thread{
        @Override
        public void run() {
            System.out.println(currentThread().getName()+"类锁运行开始........");
            try {
                synClass();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class InstanceSyn implements Runnable{

        private InstanceAndClass_1 instanceAndClass_1;


        public InstanceSyn(InstanceAndClass_1 instanceAndClass_1){
            this.instanceAndClass_1 = instanceAndClass_1;
        }

        @Override
        public void run() {
            System.out.println("实例锁运行开始............"+instanceAndClass_1);
            try {
                instanceAndClass_1.instance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //实例锁 对类的当前实例进行加锁，防止其他线程同时访问该类的该实例的所有syn块
    private synchronized void instance() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName()+"实例锁运行中........");
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName()+"实例锁运行结束......");
    }

    //类锁 限制线程同时访问jvm中该类的所有实例同时访问对应的代码块
    private static synchronized void synClass() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName()+"类锁运行中........");
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName()+"类锁运行结束......");
    }

    public static void main(String[] args) throws InterruptedException {
        InstanceAndClass_1 instanceAndClass_1 = new InstanceAndClass_1();
        SynClass synClass = new SynClass();
        InstanceSyn instanceSyn = new InstanceSyn(instanceAndClass_1);
        Thread thread = new Thread(instanceSyn);
        thread.start();
        Thread.sleep(1000);
        synClass.start();
        //通过最终结果可以看出，类锁和实例锁是可以同步进行的


    }
}

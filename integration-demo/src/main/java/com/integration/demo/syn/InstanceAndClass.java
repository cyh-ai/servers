package com.integration.demo.syn;

/**
 * 类说明：演示实例锁和类锁是不同的，两者可以并行
 *
 * @author cyh
 * @date 2023.03.15
 */
public class InstanceAndClass {
    private static class SynClass extends Thread {
        @Override
        public void run() {
            System.out.println(currentThread().getName() + ":SynClass is running...");
            synClass();
        }
    }

    private static class InstanceSyn implements Runnable {

        private InstanceAndClass instanceAndClass;

        public InstanceSyn(InstanceAndClass instanceAndClass) {
            this.instanceAndClass = instanceAndClass;
        }

        @Override
        public void run() {
            System.out.println(":TestInstance is running..." + instanceAndClass);
            instanceAndClass.instance();
        }
    }

    //实例锁 对类的当前实例进行加锁，防止其他线程同时访问该类的该实例的所有synchronized块
    private synchronized void instance() {
        //SleepTools.second(1); 主线程停止1秒
        System.out.println("synInstance is going..." + this.toString());
        //SleepTools.second(3); 主线程停止3秒
        System.out.println("synInstance ended" + this.toString());
    }

    //类锁 限制线程同时访问jvm中该类的所有实例同时访问对应的代码块
    private static synchronized void synClass() {
        //SleepTools.second(1); 主线程停止1秒
        System.out.println(Thread.currentThread().getName() + ":synClass is going......");
        //SleepTools.second(1); 主线程停止1秒
        System.out.println(Thread.currentThread().getName() + ":SynClass ended");
    }

    public static void main(String[] args) {
        InstanceAndClass instanceAndClass = new InstanceAndClass();
        SynClass t1 = new SynClass();
        Thread t2 = new Thread(new InstanceSyn(instanceAndClass));
        t2.start();
        //SleepTools.second(1); 主线程停止1秒
        t1.start();
    }
}

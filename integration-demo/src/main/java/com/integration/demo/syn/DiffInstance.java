package com.integration.demo.syn;

/**
 * @author cyh
 * @date 2023.03.14
 * 类说明：锁的实例不一样，也可以并行的
 */
public class DiffInstance {

    private static class InstanceSyn implements Runnable {

        private DiffInstance diffInstance;

        public InstanceSyn(DiffInstance diffInstance) {
            this.diffInstance = diffInstance;
        }

        @Override
        public void run() {
            diffInstance.instance();
        }
    }

    private static class Instance2Syn implements Runnable {

        private DiffInstance diffInstance;

        public Instance2Syn(DiffInstance diffInstance) {
            this.diffInstance = diffInstance;
        }

        @Override
        public void run() {
            diffInstance.instance2();
        }
    }

    private synchronized void instance() {
        //SleepTools.second(3); 主线程停止3秒
        System.out.println("synInstance is going..." + this.toString());
        //SleepTools.second(3); 主线程停止3秒
        System.out.println("synInstance ended" + this.toString());
    }

    private synchronized void instance2() {
        //SleepTools.second(3); 主线程停止3秒
        System.out.println("synInstance2 is going..." + this.toString());
        //SleepTools.second(3); 主线程停止3秒
        System.out.println("synInstance2 ended" + this.toString());
    }

    public static void main(String[] args) {
        DiffInstance instance1 = new DiffInstance();


        DiffInstance instance2 = new DiffInstance();
        Thread t3 = new Thread(new Instance2Syn(instance2));
        Thread t4 = new Thread(new InstanceSyn(instance1));
//        Thread t4 = new Thread(new InstanceSyn(instance1));

        t3.start();
        t4.start();
        //SleepTools.second(1); 主线程停止1秒
        //总结，如果两个线程调用同一个实例对象，一定是其中一个线程先执行完，另一个才会执行完，因为使用的对象锁，如果两个线程锁不同的对象实例，是可以并行的
    }
}

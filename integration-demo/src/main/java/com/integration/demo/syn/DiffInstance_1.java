package com.integration.demo.syn;

/**
 * 锁的实例不一样，也是可以并行的
 */
public class DiffInstance_1 {

    private static class InstanceSyn implements Runnable{

        private DiffInstance_1 diffInstance_1;

        public InstanceSyn(DiffInstance_1 diffInstance_1){
            this.diffInstance_1 = diffInstance_1;
        }

        @Override
        public void run() {
            try {
                diffInstance_1.instance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static class Instance2Syn implements Runnable{

        private DiffInstance_1 diffInstance_1;

        public Instance2Syn(DiffInstance_1 diffInstance_1){
            this.diffInstance_1 = diffInstance_1;
        }

        @Override
        public void run() {
            try {
                diffInstance_1.instance2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void instance() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("实例1正在运行..."+this.toString());
        Thread.sleep(1000);
        System.out.println("实例1运行结束..."+this.toString());
    }

    private synchronized void instance2() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("实例2正在运行..."+this.toString());
        Thread.sleep(1000);
        System.out.println("实例2运行结束..."+this.toString());
    }

    public static void main(String[] args) throws InterruptedException {
        DiffInstance_1 instance1 = new DiffInstance_1();
        DiffInstance_1 instance2 = new DiffInstance_1();
        Thread thread1 = new Thread(new InstanceSyn(instance1));
        Thread thread2 = new Thread(new Instance2Syn(instance2));
        thread1.start();
        thread2.start();
        Thread.sleep(1000);
        //总结：如果两个线程调用同一个实例对象，一定是其中一个线程先执行完，另外一个才会执行完，因为使用的对象锁，如果两个线程锁不同的对象实例，也是可以并行的
    }

}

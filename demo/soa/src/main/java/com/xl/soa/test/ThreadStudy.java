package com.xl.soa.test;

import java.util.concurrent.*;

/**
 * 学习线程相关工具类
 */
public class ThreadStudy {

    volatile static CountDownLatch countDownLatch = new CountDownLatch(3);

    private final Object o = new Object();

    public static void main(String[] args) {
        //countDownLatch和futureTask都是闭锁实现。
        //futureTask();
//        cyclicBarrierTest();
//        currentHashMap("a");
        ThreadStudy s = new ThreadStudy();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                s.runFalse();
            }
        });
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                s.runTrue();
            }
        });
        t.start();
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (s){
            s.notifyAll();
        }
    }

    /**
     * 程序计数器，通过调用countDown（）方法来减少CountDownLatch中的计数器数字，如果为0，则所有等待的线程，即调用了await（）方法的线程都将继续执行下去
     *
     */
    public static void countDownLatch(){
        for(int i=0;i<3;i++){

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName()+"堵塞"+"countDownLatch.getCount():"+countDownLatch.getCount());
                        countDownLatch.await();
                        System.out.println(Thread.currentThread().getName()+"通过");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
        for(int j=0;j<3;j++){
            System.out.println("减第"+j+"次："+countDownLatch.getCount());
            countDownLatch.countDown();
        }
        System.out.println("执行完成");
    }

    /**
     * 使用futureTask的好处是，可以并发的处理一些初始化，节约时间，如初始化一些数据，放入内存中，在主线程中要使用，则在逻辑处理到需要使用的地方，调用get（）方法来获取所需要的数据，如果这时还没处理完，get（）方法也回
     * 阻塞主进程的进行，直到获取数据或抛出异常为止
     */
    static volatile Integer count;

    public static void futureTask(){
        FutureTask f = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(2000);
                count=1;
               return count;
            }
        });
        Thread t = new Thread(f);
        t.start();
        try {
            System.out.println("進行get方法，阻塞主线程执行");
            Integer i = (Integer)f.get();
            System.out.println("执行完毕，获取返回值："+count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * cyclicBarrier也是一个闭锁，它内部是一个线程，两个参数，第一个参数是一个计数器，  cyclicBarrier.await()调用了几次，对于的计数器就会减1，只有当调到计数器对于的个数的时候，才会执行后面的第二个参数，
     * 也就是 cyclicBarrier声明的线程。并且当 cyclicBarrier中的线程执行完后，才会接着执行调用cyclicBarrier.await()后面的方法体。也就是说 cyclicBarrier.await()具有堵塞线程的作用。
     */
    static Integer a;
    public static void cyclicBarrierTest(){
        a = 1;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println("这里是子线程："+Thread.currentThread().getName());
                try {
                    Thread.sleep(a++*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread被唤醒："+Thread.currentThread().getName());
            }
        });

        count = 1;
        for(int i=0;i<2;i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    //这里在处理逻辑后唤醒前面的线程
                    System.out.println("do something");
                    try {
                        System.out.println("随眠1秒过后唤醒");
                        Thread.sleep(a++*1000);
                        System.out.println("执行完毕，进行await");
                        cyclicBarrier.await();
                        System.out.println("线程被唤醒次数："+count++);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
    }

    /**
     * 这里是对并发容器ConCurrentHashMap进行测试，在并发情况下，从map中取值可能会出现并发问题，ConcurrentHashMap能很好的解决这一现象，但是又会出现另一个问题，就是在计算过程中，另一个线程进来，传入相同数据，
     * 去读ConcurrentHashMap 中的数据，然后看到ConcurrentHashMap中并没有，其实现在刚进行的计算还没有返回，这样就造成的多次计算，所以放一个Future来异步的执行计算，同时获取方法用get，可以等计算结果出了后再
     * 获取值，但是这样也会有一个问题，就是如果在put到map中之前另一个线程就过来去获取值的时候，也会重复计算，只不过计算过程由于是异步，大大的减小了这种情况的出现。最终版如下，使用putIfAbsent方法，因为是原子性
     * 的方法，所以不再出现并发的问题。
     */
    final static ConcurrentHashMap<String, Future<Integer>>conCurrentHashMap = new ConcurrentHashMap();
    public static void currentHashMap(String s){
        a=1;
        Future future = conCurrentHashMap.get(s);
        if(future == null){
            FutureTask ft = new FutureTask(new Callable() {
                @Override
                public Object call() throws Exception {
                    //这里是值运算然后返回一个数值。
                    return a++;
                }
            });
           future = conCurrentHashMap.putIfAbsent(s, ft);
            if(future == null){
                future = ft;
                ft.run();
           }
        }
        try {
            Integer i = (Integer)future.get();
            System.out.println("所得结果是："+i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    volatile boolean flag = false;

    public void runTrue(){
        System.out.println("等待执行runTrue");
        synchronized (this){
            if(flag != true){
                try {
                    wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("执行runTrue");
                flag = false;
            }
        }
    }

    public void runFalse(){
        System.out.println("等待执行runFalse");
        synchronized (this){
            if(flag != true){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("执行runFalse");
                flag = false;
            }
        }
    }


}

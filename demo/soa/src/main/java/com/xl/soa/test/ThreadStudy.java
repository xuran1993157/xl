package com.xl.soa.test;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 学习线程相关工具类
 */
public class ThreadStudy {

    volatile static CountDownLatch countDownLatch = new CountDownLatch(3);
    public static void main(String[] args) {
        //countDownLatch和futureTask都是闭锁实现。
        futureTask();
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
    static Integer count;

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
}

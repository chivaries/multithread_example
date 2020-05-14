package com.example.multithread.returning.thread;

import java.util.concurrent.TimeUnit;

public class ReturningValueBlocking {
    public static void main(String argc[]) {
        System.out.println("Main thread starts here...");

        MyThreadTask task1 = new MyThreadTask();
        MyThreadTask task2 = new MyThreadTask();

        new Thread(task1,"firstThread").start();
        new Thread(task2,"secondThread").start();

        System.out.println("task1 result:" + task1.getRandomSum());
        System.out.println("task2 result:" + task2.getRandomSum());

        System.out.println("Main thread ends here...");
    }
}

class MyThreadTask implements Runnable {
    private static int count = 0;
    private int id;
    private volatile boolean done = false;
    private int randomSum = 0;

    @Override
    public void run() {
        for(int i = 0; i < 5; i++) {
            System.out.println("<" + id + ">TICK TICK " + i);
            randomSum += Math.random()*1000;
            try {
                TimeUnit.MICROSECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /********** Unlock ************/
        done = true;
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + ": Notifying the result");
            this.notifyAll();
        }
    }

    public int getRandomSum() {
        /*********** Lock ************/
        if(!done) {
            synchronized (this) {
                try{
                    System.out.println(Thread.currentThread().getName() + ": Waiting for result");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + ": Woken up");
        }
        return randomSum;
    }

    public MyThreadTask() {
        this.id = ++count;
    }
}

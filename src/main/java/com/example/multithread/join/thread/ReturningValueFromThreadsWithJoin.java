package com.example.multithread.join.thread;

import java.util.concurrent.TimeUnit;

public class ReturningValueFromThreadsWithJoin {
    public static void main(String argc[]) throws InterruptedException {
        System.out.println("Main thread starts here...");

        MyThreadTask task1 = new MyThreadTask();
        MyThreadTask task2 = new MyThreadTask();
        MyThreadTask task3 = new MyThreadTask();

        Thread thread1 = new Thread(task1,"firstThread");
        Thread thread2 = new Thread(task2,"secondThread");
        Thread thread3 = new Thread(task3,"thirdThread");

        thread1.start();
        thread2.start();
        thread3.start();

        System.out.println("task1 result:" + task1.getRandomSum());
        System.out.println("task2 result:" + task2.getRandomSum());
        System.out.println("task3 result:" + task3.getRandomSum());

        thread1.join();
        System.out.println("task1 result:" + task1.getRandomSum());

        thread2.join();
        System.out.println("task2 result:" + task2.getRandomSum());

        thread3.join();
        System.out.println("task3 result:" + task3.getRandomSum());

        System.out.println("Main thread ends here...");
    }
}

class MyThreadTask implements Runnable {
    private static int count = 0;
    private int id;
    private int randomSum = 0;

    @Override
    public void run(){
        for(int i = 0; i < 5; i++) {
            System.out.println("<" + id + ">TICK TICK " + i);
            randomSum += Math.random()*1000;
            try {
                TimeUnit.MICROSECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getRandomSum(){
        return randomSum;
    }

    public MyThreadTask() {
        this.id = ++count;
    }
}
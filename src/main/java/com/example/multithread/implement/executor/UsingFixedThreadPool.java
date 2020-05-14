package com.example.multithread.implement.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UsingFixedThreadPool {
    public static void main(String[] args) {
        System.out.println("Main thread starts here...");

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(new MyThreadTask());
        executorService.execute(new MyThreadTask());
        executorService.execute(new MyThreadTask());
        executorService.execute(new MyThreadTask());

        executorService.shutdown();

        System.out.println("Main thread ends here...");
    }
}

class MyThreadTask implements Runnable {
    private static int count = 0;
    private int id;

    @Override
    public void run() {
        for(int i = 0; i < 5; i++) {
            System.out.println("<" + id + ">TICK TICK " + i);
            try {
                TimeUnit.MICROSECONDS.sleep((long)Math.random()*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public MyThreadTask() {
        this.id = ++count;
    }
}

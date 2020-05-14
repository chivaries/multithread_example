package com.example.multithread.terminating.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TerminatingBlockingExecutorWithInterrupt {
    public static void main(String argc[]) throws InterruptedException {
        String currentThreadName = Thread.currentThread().getName();

        System.out.println("[" + currentThreadName + "]" + " thread starts here...");

        ExecutorService execService = Executors.newSingleThreadExecutor();

        LoopTask3 task1 = new LoopTask3();
        LoopTask3 task2 = new LoopTask3();

        Future<?> future1 = execService.submit(task1);
        Future<?> future2 = execService.submit(task2);

        execService.shutdown();

        TimeUnit.MILLISECONDS.sleep(200);
        future1.cancel(true);
        TimeUnit.MILLISECONDS.sleep(100);
        future2.cancel(true);

        System.out.println("["+ currentThreadName + "]" + " thread ends here...");
    }
}

class LoopTask3 implements Runnable {
    private static int count = 0;
    private int id;
    private String taskId;

    @Override
    public void run() {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("#### <" + currentThreadName +"," + taskId + "> starting...####");
        while(true) {
            System.out.println("<" + currentThreadName + "," + taskId + "> TICK TICK");
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("<" + currentThreadName + "," + taskId + "> Sleep Interrupted. Cancelling...");
                break;
            }
        }
        System.out.println("#### <" + currentThreadName + "," + taskId + "> done...####");
    }

    public LoopTask3() {
        this.id = ++count;
        this.taskId = "Task-" + id;
    }
}
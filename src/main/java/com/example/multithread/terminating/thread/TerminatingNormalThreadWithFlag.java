package com.example.multithread.terminating.thread;

import java.util.concurrent.TimeUnit;

public class TerminatingNormalThreadWithFlag {
    public static void main(String argc[]) throws InterruptedException {
        String currentThreadName = Thread.currentThread().getName();

        System.out.println("["+ currentThreadName + "]" + " thread starts here...");

        LoopTask task1 = new LoopTask();
        LoopTask task2 = new LoopTask();

        new Thread(task1, "Thread-1").start();
        new Thread(task2, "Thread-2").start();

        TimeUnit.MILLISECONDS.sleep(1000);
        task1.cancel();
        task2.cancel();

        System.out.println("["+ currentThreadName + "]" + " thread ends here...");
    }
}

class LoopTask implements Runnable {
    private static int count = 0;
    private int id;
    private String taskId;
    private volatile boolean shutdown = false;

    @Override
    public void run() {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("#### <" + currentThreadName +"," + taskId + "> starting...####");
        while(true) {
            System.out.println("<" + currentThreadName + "," + taskId + "> TICK TICK");
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized(this) {
                if(shutdown) {
                    break;
                }
            }
        }
        System.out.println("#### <" + currentThreadName + "," + taskId + "> done...####");
    }

    public void cancel() {
        System.out.println("... <" + Thread.currentThread().getName() +"," + taskId + "> shutting down...");

        synchronized (this) {
            this.shutdown = true;
        }
    }

    public LoopTask() {
        this.id = ++count;
        this.taskId = "Task-" + id;
    }
}
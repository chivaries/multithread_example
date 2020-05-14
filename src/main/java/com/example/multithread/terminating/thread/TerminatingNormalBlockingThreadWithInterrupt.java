package com.example.multithread.terminating.thread;

import java.util.concurrent.TimeUnit;

public class TerminatingNormalBlockingThreadWithInterrupt {
    public static void main(String argc[]) throws InterruptedException {

        String currentThreadName = Thread.currentThread().getName();

        System.out.println("["+ currentThreadName + "]" + " thread starts here...");

        LoopTask3 task1 = new LoopTask3();
        LoopTask3 task2 = new LoopTask3();

        Thread thread1 = new Thread(task1, "Thread-1");
        Thread thread2 = new Thread(task2, "Thread-2");
        thread1.start();
        thread2.start();

        TimeUnit.MILLISECONDS.sleep(500);

        thread1.interrupt();
        thread2.interrupt();

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
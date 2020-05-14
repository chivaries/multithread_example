package com.example.multithread.alive.thread;

import java.util.concurrent.TimeUnit;

public class ThreadAliveCheck {
    public static void main(String argc[]) throws InterruptedException {
        String currentThreadName = Thread.currentThread().getName();

        System.out.println("["+ currentThreadName + "]" + " thread starts here...");

        Thread thread = new Thread(new MyRunnableTask(10), "Thread-1");

        boolean threadIsAlive = thread.isAlive();
        System.out.println("thread Aliveness before start: " + threadIsAlive);

        thread.start();

        for(int i = 0; i < 4; i++) {
            TimeUnit.MILLISECONDS.sleep(20);
            System.out.println("thread Aliveness : " + thread.isAlive());
        }

        System.out.println("["+ currentThreadName + "]" + " thread ends here...");
    }
}

class MyRunnableTask implements Runnable {
    private static int count = 0;
    private int id;
    private String taskId;
    private long sleepTime;

    @Override
    public void run() {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("#### <" + currentThreadName +"," + taskId + "> starting...####");
        for(int i = 0; i<4; i++) {
            System.out.println("<" + currentThreadName + "," + taskId + "> TICK TICK" + i);
            try {
                TimeUnit.MILLISECONDS.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("#### <" + currentThreadName + "," + taskId + "> done...####");
    }
    public MyRunnableTask(long sleepTime) {
        this.id = ++count;
        this.taskId = "MyRunnableTask" + id;
        this.sleepTime = sleepTime;
    }
}
package com.example.multithread.alive.executor;

import java.util.concurrent.*;

public class ExecutorTaskCompleteCheck {
    public static void main(String argc[]) throws InterruptedException {
        String currentThreadName = Thread.currentThread().getName();

        System.out.println("["+ currentThreadName + "]" + " thread starts here...");

        ExecutorService executorService = Executors.newCachedThreadPool(new NamedThreadsFactory());

        Future<?> future = executorService.submit(new MyRunnableTask(10));

        executorService.shutdown();

        for(int i = 0; i< 4; i++) {
            TimeUnit.MILLISECONDS.sleep(20);
            System.out.println("Task completion : " + future.isDone());
        }

        System.out.println("["+ currentThreadName + "]" + " thread ends here...");
    }
}

class NamedThreadsFactory implements ThreadFactory {
    private static int count = 0;
    private static String Name = "MyThread-";

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, Name + ++count);
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
            System.out.println("<" + currentThreadName +"," + taskId + "> TICK TICK" + i);
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

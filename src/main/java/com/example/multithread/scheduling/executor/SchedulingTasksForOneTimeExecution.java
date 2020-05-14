package com.example.multithread.scheduling.executor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class SchedulingTasksForOneTimeExecution {
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

    public static void main(String argc[]) throws InterruptedException {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("Main thread starts here...");

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new NamedThreadsFactory());

        System.out.println("["+currentThreadName+"] Current time" + dateFormatter.format(new Date()));

        ScheduledFuture<?> schedFuture1 = executorService.schedule(new ScheduledRunnableTask(0), 2, TimeUnit.SECONDS);
        ScheduledFuture<Integer> schedFuture2 = executorService.schedule(new ScheduledCallableTask(0), 4, TimeUnit.SECONDS);

        executorService.shutdown();

        try {
            System.out.println("Task1 result = " + schedFuture1.get());
            System.out.println("Task2 result = " + schedFuture2.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread ends here...");
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

class ScheduledRunnableTask implements Runnable {
    private static int count = 0;
    private long sleepTime;
    private String taskId;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

    @Override
    public void run(){
        Date startTime = new Date();
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("#### <" + currentThreadName +"," + taskId
                + " > Actually started at "
                + dateFormatter.format(startTime)
                + "####");

        for(int i = 0; i<5; i++) {
            try {
                TimeUnit.MICROSECONDS.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("#### <" + currentThreadName + "," + taskId
                + " >Finished at "
                + dateFormatter.format(new Date())
                + "####");
    }


    public ScheduledRunnableTask(long sleepTime) {
        this.sleepTime = sleepTime;
        this.taskId = "ScheduledRunnableTask-" + count++;
    }
}

class ScheduledCallableTask implements Callable<Integer> {
    private static int count = 0;
    private long sleepTime;
    private String taskId;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

    @Override
    public Integer call() throws Exception {
        Date startTime = new Date();
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("#### <" + currentThreadName +"," + taskId
                + " > Actually started at "
                + dateFormatter.format(startTime)
                + "####");

        for(int i = 0; i<5; i++) {
            try {
                TimeUnit.MICROSECONDS.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("#### <" + currentThreadName + "," + taskId
                + " >Finished at "
                + dateFormatter.format(new Date())
                + "####");

        return 10;
    }

    public ScheduledCallableTask(long sleepTime) {
        this.sleepTime = sleepTime;
        this.taskId = "ScheduledCallableTask-" + count++;
    }
}